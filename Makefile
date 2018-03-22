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
