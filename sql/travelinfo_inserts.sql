insert into operator(op_id,opname,contactno,email) values
(1,'Orange Tours and Travels', '9876543210','orangebus@gmail.com'),
(2,'Abhishek Travels','8976543210', 'abhishektravels@gmail.com'),
(3,'Vyjayanti Travels','7986543211', 'vjytravels@gmail.com');

insert into bus(bus_id,vehicle_number,ac_type,seat_type,operator_id) values
(1,'KA12AB1234', 'AC', 'Sleeper', 1),
(2,'KA05CD5678', 'Non-AC', 'Seater', 1),
(3,'TN19AJ2845', 'AC', 'Sleeper', 1),
(4,'AP16GH2843', 'AC', 'Sleeper', 2),
(5,'KA19IJ2942', 'AC', 'Seater', 2),
(6,'AP23KL1257', 'AC', 'Sleeper', 2),
(7,'KA12DJ2621', 'AC', 'Sleeper', 1),
(8,'KA05CD5678', 'AC', 'Sleeper', 3),
(9,'AP17NE8927', 'Non-AC', 'Seater', 3);

insert into route(route_id,start_city,dest_city,duration) values
(1,'Bangalore', 'Vijayawada', '13:00:00'),
(2,'Bangalore', 'Hyderabad', '10:00:00'),
(3,'Bangalore', 'Chennai', '07:00:00');

insert into schedule (bus_id, route_id, departure_time, price) values
(1, 1, '22:00:00', 1350.00),
(2, 3, '13:30:00', 550.00),
(3, 3, '16:30:00', 700.00),
(4, 1, '17:00:00', 1200.00),
(5, 2, '09:30:00', 850.00),
(6, 1, '19:00:00', 1300.00),
(7, 1, '18:00:00', 1350.00),
(8, 2, '19:30:00', 1000.00),
(9, 1, '09:00:00', 800.00);