#! bin/bash

rm -rf submitted;
rm -rf grades;
#mkdir submitted
unzip p1.zip;
mv section146_question1223_jobID_12199_202205010842+0300_202205240100+0300 submitted;

cd late-submissions;
for late in *; do
    echo $late;
    mkdir ../submitted/${late%.*};
    unzip $late -d ../submitted/${late%.*};
done
cd ..;

cd submitted;
for d in */ ; do
    cd "$d";
    mv ./*/src ./src;
    find . -maxdepth 1 \! \( -name src \) -exec rm -rf '{}' \;
    find . -name "*.txt" -type f -delete
    find . -name "*.docx" -type f -delete
    cd ..;
done


#rm -rf !*.java
cd ..;

