#!/bin/bash
clear
echo "---------Starting Script-----------"
echo

echo "---------Generating Vars-----------"
OUTPUT10="OutputFiles/OutputFile10.txt"
OUTPUT250="OutputFiles/OutputFile250.txt"
OUTPUT500="OutputFiles/OutputFile500.txt"
OUTPUT5757="OutputFiles/OutputFile5757.txt"
WORD10="../../data/words-10.txt"
WORD250="../../data/words-250.txt"
WORD5757="../../data/words-5757.txt"
INWORD10="../../data/words-10-in.txt"
INWORD250="../../data/words-250-in.txt"
INWORD5757="../../data/words-5757-in.txt"
SOLUTION10="../../data/words-10-out.txt"
SOLUTION250="../../data/words-250-out.txt"
SOLUTION5757="../../data/words-5757-out.txt"

echo "------Removing old test files------"
rm -r OutputFiles

echo "--------Making Directories---------"
mkdir -p OutputFiles/

echo "----------Compiling Source---------"
javac -d ../../bin/ -sourcepath ../../src/ Vertex.java
javac -d ../../bin/ -sourcepath ../../src/ -cp ../../bin Graph.java
javac -d ../../bin/ -sourcepath ../../src/ -cp ../../bin WordLadder.java

echo "-----------Running tests-----------"

echo "Press enter to run Word Ladder on 10 words"
read input
start=$(date +%s.%N)
java -cp ../../bin world_ladder.WordLadder $WORD10 $INWORD10 > $OUTPUT10
end=$(date +%s.%N)
runtime=$(python -c "print(${end} - ${start})")
echo "Runtime was $runtime s"

echo "Press enter to run Word Ladder on 250 words"
read input
start=$(date +%s.%N)
java -cp ../../bin world_ladder.WordLadder $WORD250 $INWORD250 > $OUTPUT250
end=$(date +%s.%N)
runtime=$(python -c "print(${end} - ${start})")
echo "Runtime was $runtime s"

echo "Press enter to run Word Ladder on 5757 words"
read input
start=$(date +%s.%N)
java -cp ../../bin world_ladder.WordLadder $WORD5757 $INWORD5757 > $OUTPUT5757
end=$(date +%s.%N)
runtime=$(python -c "print(${end} - ${start})")
echo "Runtime was $runtime s"

echo "---------Comparing with correct result--------"

if diff $OUTPUT10 $SOLUTION10 >/dev/null ; then
  echo "The result on 10 words was correct."
else
  echo "The result on 10 words was incorrect."
fi

if diff $OUTPUT250 $SOLUTION250 >/dev/null ; then
  echo "The result on 250 words was correct."
else
  echo "The result on 250 words was incorrect."
fi

if diff $OUTPUT5757 $SOLUTION5757 >/dev/null ; then
  echo "The result on 5757 words was correct."
else
  echo "The result on 5757 words was incorrect."
fi
