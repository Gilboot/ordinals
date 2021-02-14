package com.github.ordinals;

import java.util.Arrays;
import java.io.Console;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Interactive shell for experimenting with ordinals.
 */
public final class Ordinals {
    public static final String EOL = System.getProperty("line.separator", "\n");
    private static final String BREAK_CHARACTER = "ctrl-d on a real system or ctrl-z on windoze (this string is a poison-pill because it contains spaces and split already happened)";
    private static final Console console = System.console();

    private static volatile Locale locale = Locale.US;
    private static volatile OrdinalsFactory ordinalsFactory = OrdinalsFactory.getInstance(locale);

    private static volatile boolean go = true;

    enum Command {
        LOCALE {
            @Override void execute(final String... args) {
                if (args.length < 2 || args.length > 4) {
                    usage("<locale name> [country] [variant]");
                    return;
                }
                final String localeName = args[1];
                final String country = args.length > 2 ? args[2] : null;
                final String variant = args.length > 3 ? args[3] : null;
                locale = (variant == null)
                    ? (country == null) ? new Locale(localeName) : new Locale(localeName, country)
                    : new Locale(localeName, country, variant);
                ordinalsFactory = OrdinalsFactory.getInstance(locale);
            }
        },
        SUFFIX {
            @Override void execute(final String... args) {
                if (args.length != 2) {
                    usage("<ordinal>");
                    return;
                }
                final int ordinal = Integer.parseInt(args[1]);
                println("%s", ordinalsFactory.getOrdinalSuffix(ordinal));
            }
        },
        HELP {
            @Override void execute(final String... args) {
                if (args.length != 1) {
                    usage();
                    return;
                }
                Arrays.stream(values())
                    .sorted()
                    .filter(c -> !(c.equals(NULL) || c.equals(UNKNOWN)))
                    .forEach(c -> println("%s", c.name().toLowerCase()));
            }
        },
        EXIT {
            @Override void execute(final String... args) {
                if (args.length == 1) {
                    go = false;
                } else if (args.length == 2 && args[1].equals(BREAK_CHARACTER)) {
                    go = false;
                    console.printf(EOL);
                } else {
                    usage();
                }
            }
        },
        NULL {
            // runs when user presses enter/return having written no command
            @Override void execute(final String... args) {
            }
        },
        UNKNOWN {
            @Override void execute(final String... args) {
                println("unknown command: %s", args[0]);
            }
        };

        void println(final String fmt, final Object... args) {
            console.printf(fmt, (Object[]) args);
            console.printf(EOL);
        }

        void usage() {
            usage("");
        }

        void usage(final String argumentDescription) {
            println("usage: %s %s", this.toString().toLowerCase(), argumentDescription);
        }

        abstract void execute(String... args);
    }

    public static void main(final String... args) {
        if (console == null) {
            System.err.println("no console");
            System.exit(1);
        }

        final Supplier<String> prompt = () -> "ordinals(" + locale + ")> ";

        final Consumer<String[]> eval = (l) ->
            Arrays.stream(Command.values())
            .filter(c -> l.length != 0 && c.name().equalsIgnoreCase(l[0]))
            .findFirst()
            .orElse(l.length == 0 || l[0].isEmpty() ? Command.NULL : Command.UNKNOWN)
            .execute(l);

        while (go) {
            console.printf(prompt.get());
            final Optional<String> line = Optional.ofNullable(console.readLine());
            try {
                eval.accept(line.isPresent() ? line.get().split("\\s+") : new String[] { "exit", BREAK_CHARACTER });
            } catch (final RuntimeException e) {
                console.printf("error: %s%s", e.getMessage(), EOL);
            }
        }
    }
}
