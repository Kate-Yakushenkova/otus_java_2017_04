#!/bin/bash

mvn clean assembly:assembly

echo ''
echo 'SerialGC'
java -jar -Xmx512m -Xms512m -XX:+UseSerialGC target/L4.1.jar
cat ./statistic

echo ''
echo 'ParallelGC'
java -jar -Xmx512m -Xms512m -XX:+UseParallelGC target/L4.1.jar
cat ./statistic

echo ''
echo 'CMS'
java -jar -Xmx512m -Xms512m -XX:+UseConcMarkSweepGC target/L4.1.jar
cat ./statistic

echo ''
echo 'GarbageFirst'
java -jar -Xmx512m -Xms512m -XX:+UseG1GC target/L4.1.jar
cat ./statistic