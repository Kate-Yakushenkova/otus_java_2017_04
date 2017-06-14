#!/bin/bash

mvn clean assembly:assembly

java -jar target/L4.jar