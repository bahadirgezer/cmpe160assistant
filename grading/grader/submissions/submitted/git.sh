for d in user*/ ; do
    cd "$d"
    git clone *.git
    cd ..
done
