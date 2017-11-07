rm -f zoo.zip
zip -r -q ../zoo.zip ../zoo --exclude=*target* --exclude=*/.* --exclude=*/zip.sh --exclude=*/README.md
mv ../zoo.zip zoo.zip 
