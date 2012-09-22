#!/bin/sh

# Compile
javac org/seyster/alienwars/AlienWars.java

# Create a handy jar file
jar -cf alienwars.jar org/seyster/alienwars/*.class org/seyster/alienwars/resources/*.gif
