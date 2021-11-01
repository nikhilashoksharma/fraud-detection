#!/bin/bash
helpFunction()
{
   echo ""
   echo "Usage: $0 -b build"
   echo -b "\t-b If to build a new jar from source code. mvn will be required. Values: (y/Y)"
   exit 1 # Exit script after printing help
}

while getopts b: opt
do
   case "${opt}" in
      b) build=${OPTARG} ;;
      ? ) helpFunction ;; # Print helpFunction in case parameter is non-existent
   esac
done
echo "Build: " ${build}

if [[ "$build" == "y" || "$build" == "Y" ]]; then
	echo "Building Jar"
	mvn clean install -f rule-based-fraud-detection/pom.xml
fi

echo "Running jar"
java -jar rule-based-fraud-detection/target/rule-based-fraud-detection-1.0.0.jar ./data/sample_transaction.csv

