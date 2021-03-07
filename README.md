# Ordinals
Welcome to the world of Ordinals. 

This project aims to represent a cardinal number as an ordinal for any language conceivable on planet Earth and beyond! However, first things first, what is an ordinal?

## What is an Ordinal?
An ordinal is a word that is used to describe the position of an entity in a series.

For example, "first" is the English word used to describe the entity that comes before any other entity in a series of entities. "second" is the word that describes the entity that comes after the "first" entity.

It's prudent to note that "first" and "second" refer to different entities. 

Different languages have different words for ordinals. The ordinal representation as used in Spanish is not the same as the one you will find used in French even though the two representations describe the same series.

## What spoken languages do we cover
As many as we can. This project aims to address ordinals for any language conceivable on planet Earth and beyond! It's also meant to be extendable, feel free to provide support for a spoken language of your choice. However, in the meantime, we are fully covering the following languages:
* English
* French
* German

Work on the following languages is in progress:
* Spanish
* Italian

Feel free to provide feedback on the accuracy of our translations. We'll appreciate that.

## How do I contribute
You can start by joining our project here https://github.com/Gilboot/ordinals/projects/1. We welcome contributions to issues that have been logged.

## Can my contributions make a difference
Sure they can. Our mission is to express any number as an ordinal for all languages on planet Earth and beyond! All you need to do is to find a language we are not yet covering and express it as a set or rules using a domain specific language we invented and that should be it.

## Am I allowed to use this project in my own work?
Yes! The project uses an MIT License. You're free to use our software but be sure to give a shout out for us.


## What programming languages are covered?
We are looking into ways of porting the library to any programming language. However, as of now, the following programming languages are supported:
* Java

## How can I get it? Are there any jar/artifacts/releases to download?
We don’t have these yet, download the code and build it.

## How do I run the project?
Make sure you have [ant](https://ant.apache.org/manual/install.html) installed on your system. 
From the command line, build the project by running the following command:
> ant

The project will be compiled, and a few tests will run. If everything goes well, the build will succeed without giving any errors. Once that is done you can proceed to perform a few cli commands.

## Command Line support
The Ordinals project has support for a command line interface that can be used to generate corresponding ordinals of various cardinals in different locales. 
To start the cli, run the following command from your terminal or command prompt, make sure you are in the ordinals directory:
> java -jar dist/ordinals.jar

A prompt will show up that should look like this: 
> ordinals(en_US)>

The locale in the brackets represents the current locale being used and it defaults to English - United States.

Run the following command:
> help

A list of commands that can be used will be listed. They should look like this:
> rules
> 
> locale
> 
> suffix
> 
> help
> 
> exit

To exit out of the prompt run:
> exit

The ordinals prompt should now be exited and you should be returned to the default prompt / terminal. To get back to the prompt run:
> java -jar dist/ordinals.jar

Run the following command to learn how to use the locale command:
> locale

Usage information should be displayed. After that run the following command to change to the French locale:
> locale fr FR

The prompt should now look like this:
> ordinals(fr_FR)>

Switch back to English by running the following command
> locale en US

To see the rules we have for the current locale run:
> rules

A list of rules will be displayed. It is these rules that are used to obtain the ordinals for various numbers or cardinals.

We can find out the ordinal generated for a given number by using the suffix command. From the prompt, type:
> suffix 1

The program should return 
> st

Try getting suffix for other numbers like 2, 3, 5, 11 etc.
> suffix 2
> 
> suffix 3
> 
> suffix 5
> 
> suffix 11
> 
> suffix book

Notice that the correct ordinal will be generated for these numbers and an error will be displayed for a wrong input
> nd
> 
> rd
> 
> th
> 
> th
> 
> error: For input string: "book"


#How to build and test
## Ant
From the command line, 
run: `ant`

A jar file is created in folder `ordinals/dist`. Navigate to that
folder via the command line. 
run: `java -cp ordinals.jar com.github.ordinals.TestXMLParser`
The program should now run.

