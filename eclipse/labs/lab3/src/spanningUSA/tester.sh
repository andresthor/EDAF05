#!/bin/bash
clear
echo "---------Starting Script-----------"
echo
INPUT="../../data/USA-highway-miles.txt"
OUTPUT="OutputFiles/MinTreeUSA.txt"
echo "------Removing old test files------"
rm -r OutputFiles

echo "--------Making Directories---------"
mkdir -p OutputFiles/

echo "----------Compiling Source---------"
javac -d ../../bin/ -sourcepath ../../src/ Vertex.java
javac -d ../../bin/ -sourcepath ../../src/ -cp ../../bin Edge.java
javac -d ../../bin/ -sourcepath ../../src/ -cp ../../bin Graph.java
javac -d ../../bin/ -sourcepath ../../src/ -cp ../../bin spanningUSA.java

echo "-----------Running tests-----------"

echo "Press enter to run Spanning USA"
read input
start=$(date +%s.%N)
java -cp ../../bin spanningUSA.spanningUSA $INPUT > $OUTPUT
end=$(date +%s.%N)
runtime=$(python -c "print(${end} - ${start})")
echo "Runtime was $runtime s"

echo
echo
echo "---------Checking Result----------"
echo "Press enter to view contents of result file."
echo "The minimum spanning tree should be of size 16598."
read input
cat $OUTPUT
