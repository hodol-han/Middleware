create table User (
	id varchar(20) unique not null,
	name varchar(60),
	pwd varchar(20) not null,
	keyhash varchar(50) not null
);

create table Node (
	id int not null primary key auto_increment,
	owner varchar(20) not null,
	name varchar(50),
	serial varchar(50) unique not null,
	product_name varchar(50) not null,
	foreign key(owner) references User(id)
);

--create table Credential (
--	id int not null primary key auto_increment,
--	uid varchar(20) not null,
--	keyhash varchar(50) unique not null,
--	issued_at datetime,
--	expires_at datetime,
--	foreign key(uid) references User(id)
--);

create table UserCommandLog (
	id int not null primary key auto_increment,
	uid varchar(20) not null,
	nid int not null,
	command text,
	logged_at datetime,
	
	foreign key(uid) references User(id),
	foreign key(nid) references Node(id)

);

create table NodeSensorLog (
	id int not null primary key auto_increment,
	nid int not null,
	sensor_data text,
	logged_at datetime,
	
	foreign key(nid) references Node(id)
);