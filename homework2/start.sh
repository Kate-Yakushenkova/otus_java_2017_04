#!/bin/bash

mvn clean assembly:assembly

java -jar target/L2.jar