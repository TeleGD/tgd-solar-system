build:
	mkdir -p bin
	javac -d bin -cp src:res:lib/* src/Main.java

run:
	java -cp bin:res:lib/* -Djava.library.path=res/natives Main

archive:
	jar cfm main.jar .mf -C bin . -C res . -C lib .

exec:
	java -jar main.jar

clean:
	rm -r -f bin/*

.PHONY: build run archive exec clean
