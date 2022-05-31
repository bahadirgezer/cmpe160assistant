#get all txt files from all subdirectories and rename them "input<num>.txt" from 1 to n
rm -rf unified;
mkdir unified;
num=0;
for d in */ ; do
    if ! [ $d -ef ./unified/ \] ; then
        #echo $d;
        cd "$d";
        for f in *.txt ; do
        
            cp $f ../unified/"input${num}.txt";
            num=$((num+1));
            
        done
        cd ..;
    fi
done
