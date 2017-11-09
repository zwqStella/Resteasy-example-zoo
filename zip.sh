rm -f zoo.zip
zip -r -q ../zoo.zip . --exclude=target/* --exclude=.* --exclude=zip.sh --exclude=README.md --exclude=bin/*
mv ../zoo.zip zoo.zip 
