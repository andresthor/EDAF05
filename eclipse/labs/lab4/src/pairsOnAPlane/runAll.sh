#!/bin/bash

find ../../data/ -type f -name "*tsp.txt" | sort | xargs java -cp ../../bin/ pairsOnAPlane.pairsOnAPlane 2>&1 | tee ../../output/output.txt
