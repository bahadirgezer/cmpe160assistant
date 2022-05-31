for d in /Users/gezer/bounDers/4.Donem/Cmpe160Assistant/project1/grading/grader/submissions/inputs/*; do
	echo $d;
	touch output.txt;
	python3 validity_checker.py $d output.txt 2;
done
