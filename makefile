build:
	mkdir -p bin
	javac -d bin -cp src:res:lib/* src/Main.java

run:
	java -cp bin:res:lib/* -Djava.library.path=res/natives Main

archive:
	mkdir -p zip
	cp res/natives/* zip
	cp lib/* zip
	jar cfm zip/main.jar .mf -C bin . -C res .
	echo "#/bin/sh\njava -Djava.library.path=. -jar main.jar" > zip/main.sh
	echo "java -Djava.library.path=. -jar main.jar" > zip/main.bat
	chmod u+x zip/main.sh
	chmod u+x zip/main.bat
	zip main.zip zip/*

exec:
	java -Djava.library.path=zip -jar zip/main.jar

clean:
	rm -r -f bin/*
	rm -r -f zip/*

.PHONY: build run archive exec clean
