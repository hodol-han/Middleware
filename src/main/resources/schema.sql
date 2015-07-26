create table User (
	id varchar(20) unique not null,
	name varchar(60),
	pwd varchar(20) not null
);

create table Node (
	id varchar(20) unique not null,
	serial varchar(50) unique not null,
	product_name varchar(50) unique not null
	
);


