all : compile run

compile: 
	javac src/*.java -d bin -target 17

run : bin/project1main input.txt output.txt
	java bin/project1main input.txt output.txt
	