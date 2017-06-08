#!/bin/bash

mvn clean assembly:assembly

java -jar target/L9.1.jar
