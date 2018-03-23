compile:
	cd ./java && javac -d bin/ -cp src src/Main.java

run:
	cd ./java && java -cp bin:sqlite-jdbc.jar Main

all:
	make generate-db
	make compile
	make run

generate-db:
	cd ./SQL && make
	cp -i ./SQL/database.db ./java

clean:
	rm ./java/database.db
	rm -rf ./java/bin/*
	cd ./SQL && make clean
