#!/bin/bash

mvn clean assembly:assembly

java -jar target/L1.jar