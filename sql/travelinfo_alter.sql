alter table bus
	add constraint fk_operator_id FOREIGN KEY (operator_id) REFERENCES operator(op_id);

alter table schedule
	add constraint fk_bus FOREIGN KEY (bus_id) REFERENCES bus(bus_id);

alter table schedule
	add constraint fk_route FOREIGN KEY (route_id) REFERENCES route(route_id);