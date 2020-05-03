# PolarBearJDBC 
**Probably a totally useless "JDBC" but fun and interesting to code it**

[ALPHA version]

The JDBC for the coldest storage.
Limited feature JDBC to read from partitioned files/S3 buckets by time via SQL. 

![PolarBeard](polarbear.jpeg)

JDBC to query a non-database via SQL. 

Store historic data (data referenced in a point of time) that don't need to be retrieved fast from a cheap storage.

##Use cases
* Needs to query historic data (full scans in an historic table in a range of time) stored in csv or json not very often and where speed is not important but cost.
* Very static star schemes that feeds OLAP system(Hot storage) to display dashboards in a fancy and fast way. Keep the star schema in a DB could mean a waste of resources, as probably the DB is doing nothing 90% of the time. Store the data on files in S3 is cheaper. OLAP using the JDBC would load the  data. 

##How it works
* Define the table schema in the base of the fs/bucket in json format. Defining columns, types, partitioning and format.
* Store the data <name-of-the-table>/year/month/day ... 
(Examples on the tests)


##Limitations
* Only SELECT. Only retrieve data supported.
* Only one time partitioned table per query
* Limit not supported


##Nice and interesting things to add
* Add support to LIMIT
* Add AVRO/other formats
* Google Cloud support
* Columnar storage. Configurable. Shard the columns in different files. If not all columnDefinitions are needed, not needed to retrieve all of them from S3. Reduces transfer cost, increases requests costs.