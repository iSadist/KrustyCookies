compile:
	cd ./java && javac -d bin/ -cp src src/Main.java

run:
	cd ./java/bin && java Main

all:
	cd ./SQL && make

clean:
	cd ./SQL && make clean
