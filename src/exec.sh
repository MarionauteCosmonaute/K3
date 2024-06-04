#!/bin/bash

./clean.sh
javac --release 13 ServTest.java
javac --release 13 Host.java
javac --release 13 Join.java