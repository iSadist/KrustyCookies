compile:
	echo "-------Compiling application-------"
	cd ./java && mkdir -p bin
	cd ./java && javac -d bin/ -cp src src/Main.java

run:
	echo "-------Starting application-------"
ifeq ($(OS), Windows_NT)
	cd ./java && java -cp "bin;sqlite-jdbc.jar" Main
else
	cd ./java && java -cp bin:sqlite-jdbc.jar Main
endif

all:
	make clean
	make generate-db
	make compile
	make run

generate-db:
	echo "-------Creating database-------"
	cd ./SQL && make
	cp -i ./SQL/database.db ./java

clean:
	rm -f ./java/database.db
	rm -rf ./java/bin
	cd ./SQL && make clean
