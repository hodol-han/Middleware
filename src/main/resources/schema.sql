create table User (
	id varchar(20) unique not null,
	name varchar(60),
	pwd varchar(20) not null
);

create table Node (
	id int not null primary key auto_increment,
	owner varchar(20) not null,
	name varchar(50),
	serial varchar(50) unique not null,
	product_name varchar(50) not null,
	foreign key(owner) references User(id)
);


