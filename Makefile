compile:
	cd ./java && javac -d bin/ -cp src src/Main.java

run:
	cd ./java/bin && java Main

all:
	make clean
	make generate-db
	make compile
	make run

generate-db: 
	cd ./SQL && make
	
compile:

run:
	
clean:
	cd ./SQL && make clean
