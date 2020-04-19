# PolarBearJDBC [Work in progress]

The JDBC for the coldest storage.
Feature limited JDBC to read/write from/into partitioned files/buckets by time via SQL. 

![PolarBeard](polarbear.jpeg)

JDBC to query a non-database via SQL.


//TODO & WIP
* Support filtering by other fields not ts
* Support star schema (main table indexed by ts, FK tables without ts partition)
* Columnas storage. Configurable. A part of store rows in files partitioned by time, every row is stored in many files. If not all columnDefinitions are needed, not needed to retrieve all of them from S3. Reduces transfer cost, increases requests cost


