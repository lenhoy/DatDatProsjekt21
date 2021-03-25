insert into User_ Values ('a@b.c', 'Lennart', '123'), ('b@c.d', 'Lars', '456'), ('ins@ntnu.no', 'Instruktør', '789');

insert into Student values ('a@b.c', 'Kunstig Kobra'), ('b@c.d', 'Klar Krokodille');

insert into instructor values ('ins@ntnu.no');

insert into course values (4145, 'Datamodellering og databasesystemer', 'Spring 2021', true), (4100, 'Fluidmekanikk', 'Spring 2021', false); 

insert into folder values ('General', 4145); 

insert into managedby values ('ins@ntnu.no', 4145), ('ins@ntnu.no', 4100); 

insert into studentincourse values ('a@b.c', 4145), ('a@b.c', 4100), ('b@c.d', 4145);

nsert into post (content) values ("hei"), ("Jeg er uenig i det."), ("Jeg liker tomat!"), ("Nei, tomater er ekle!");

insert into postedby values ("ins@ntnu.no", 1, '2021-01-05 11:05:00'), 
("a@b.c", 2, '2021-02-01 18:19:45'), 
("a@b.c", 3, '2021-02-02 07:37:14'), 
("b@c.d", 4, '2021-02-03 21:21:21'), 
("b@c.d", 5, '2021-02-04 13:34:03'), 
("a@b.c", 6, '2021-02-05 18:00:00');

insert into readby values ("ins@ntnu.no", 1, '2021-01-05 11:05:00'), 
("a@b.c", 2, '2021-02-01 18:19:45'), 
("a@b.c", 3, '2021-02-02 07:37:14'), 
("b@c.d", 4, '2021-02-03 21:21:21'), 
("b@c.d", 5, '2021-02-04 13:34:03'), 
("a@b.c", 6, '2021-02-05 18:00:00'),
("b@c.d", 2, '2021-02-01 18:19:45'), 
("b@c.d", 3, '2021-02-02 07:37:14'), 
("a@b.c", 4, '2021-02-03 21:21:21'), 
("a@b.c", 5, '2021-02-04 13:34:03'), 
("a@b.c", 1, '2021-01-05 11:05:00'),
("b@c.d", 1, '2021-01-05 11:05:00'),
("b@c.d", 6, '2021-02-05 18:00:00');