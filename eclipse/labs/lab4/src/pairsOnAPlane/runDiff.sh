#!/bin/bash

INPUT="../../output/output.txt"
OUTPUT="../../data/closest-pair-out.txt"


clear
echo "--- This needs wdiff and colordiff to work ---"
wdiff -ns $INPUT $OUTPUT | colordiff
echo "----------------------------------------------"
