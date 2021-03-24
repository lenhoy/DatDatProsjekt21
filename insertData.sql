insert into User_ Values ('a@b.c', 'Lennart', '123');
insert into User_ Values ('b@c.d', 'Lars', '456');
insert into User_ Values ('ins@ntnu.no', 'Instruktør', '789');

insert into Student values ('a@b.c', 'Kunstig Kobra');
insert into Student values ('b@c.d', 'Klar Krokodille');

insert into instructor values ('ins@ntnu.no');

insert into course values (4145, 'Datamodellering og databasesystemer', 'Spring 2021', true); 
insert into course values (4100, 'Fluidmekanikk', 'Spring 2021', false);

insert into folder values ('General', 4145); 

insert into managedby values ('ins@ntnu.no', 4145); 
insert into managedby values ('ins@ntnu.no', 4100);

insert into studentincourse values ('a@b.c', 4145);
insert into studentincourse values ('a@b.c', 4100);
insert into studentincourse values ('b@c.d', 4145);


insert into post values (( , 'Hei alle sammen!'));
 