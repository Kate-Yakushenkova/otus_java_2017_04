#!/bin/bash

mvn clean assembly:assembly
java -jar -Xmx512m -Xms512m target/L3.1.jar