all:
	sqlite3 database.db < database.sql
	sqlite3 database.db < ingredients.sql
	sqlite3 database.db < customers.sql
	sqlite3 database.db < blocked.sql
	sqlite3 database.db < orders.sql
	sqlite3 database.db < pallets.sql
	sqlite3 database.db < products.sql

clean:
	rm database.db
