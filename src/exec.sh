#!/bin/bash

rm Model/*.class
javac --release 13 test.java
java test