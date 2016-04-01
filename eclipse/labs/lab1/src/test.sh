clear
echo "Testing script for Gale-Shapley algorithm."
echo "--------------------------------------------------------"
echo "Press any key to run GS on Big Bang Theory relations."
read input
start=$(date +%s.%N)
java GS ../data/sm-bbt-in.txt
end=$(date +%s.%N)
runtime=$(python -c "print(${end} - ${start})")
echo "Runtime was $runtime s"
echo "--------------------------------------------------------"
echo "Press any key to run GS on Friends relations."
read input
start=$(date +%s.%N)
java GS ../data/sm-friends-in.txt
end=$(date +%s.%N)
runtime=$(python -c "print(${end} - ${start})")
echo "Runtime was $runtime s"
echo "--------------------------------------------------------"
echo "Press any key to run GS on Homer's Illiad relations."
read input
java GS ../data/sm-illiad-in.txt
echo "--------------------------------------------------------"
echo "Press any key to run GS on four relations."
read input
java GS ../data/sm-kt-p-4-in.txt
echo "--------------------------------------------------------"
echo "Press any key to run GS on five relations."
read input
java GS ../data/sm-kt-p-5-in.txt
echo "--------------------------------------------------------"
echo "Press any key to run GS on five random relations."
read input
java GS ../data/sm-random-5-in.txt
echo "--------------------------------------------------------"
echo "Press any key to run GS fifty random relations."
read input
java GS ../data/sm-random-50-in.txt
echo "--------------------------------------------------------"
echo "Press any key to run GS on 500 random relations."
read input
java GS ../data/sm-random-500-in.txt
echo "--------------------------------------------------------"
echo "Press any key to run GS on five worst case relations"
read input
start=$(date +%s.%N)
java GS ../data/sm-worst-5-in.txt
end=$(date +%s.%N)
runtime=$(python -c "print(${end} - ${start})")
echo "Runtime was $runtime s"
echo "--------------------------------------------------------"
echo "Press any key to run GS on fifty worst case relations"
read input
start=$(date +%s.%N)
java GS ../data/sm-worst-50-in.txt
end=$(date +%s.%N)
runtime=$(python -c "print(${end} - ${start})")
echo "Runtime was $runtime s"
echo "--------------------------------------------------------"
echo "Press any key to run GS on 500 worst case relations"
read input
start=$(date +%s.%N)
java GS ../data/sm-worst-500-in.txt
end=$(date +%s.%N)
runtime=$(python -c "print(${end} - ${start})")
echo "Runtime was $runtime s"
echo "--------------------------------------------------------"
