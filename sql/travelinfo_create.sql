create table operator(
	op_id smallint auto_increment,
	opname varchar(30) NOT NULL,
	contactno char(10),
	email varchar(50),
	constraint pk_operator PRIMARY KEY (op_id),
    constraint contactinfo_required CHECK (contactno IS NOT NULL OR email IS NOT NULL)
);

create table bus(
	bus_id smallint auto_increment,
	vehicle_number char(10),
    ac_type ENUM('AC', 'Non-AC') NOT NULL,
    seat_type ENUM('Seater', 'Sleeper') NOT NULL,
	operator_id smallint NOT NULL,
	constraint pk_bus PRIMARY KEY (bus_id)
);

create table route(
	route_id smallint auto_increment,
	start_city varchar(30) NOT NULL,
    dest_city varchar(30) NOT NULL,
    duration time,
	constraint pk_route PRIMARY KEY (route_id)
);

create table schedule(
	sched_id smallint auto_increment,
	bus_id smallint NOT NULL,
	route_id smallint NOT NULL,
	departure_time time NOT NULL,
	-- with departure_time + route.duration, you can get arrival_time
	price decimal(10,2) NOT NULL,
	constraint pk_schedule PRIMARY KEY (sched_id)
);
