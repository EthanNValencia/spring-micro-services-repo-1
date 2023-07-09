insert into user_details(id,birthday,name)
values(1001, current_date(), 'Ethan');

insert into user_details(id,birthday,name)
values(1002, current_date(), 'Scott');

insert into user_details(id,birthday,name)
values(1003, current_date(), 'Timothy');

insert into post(id,user_id,description)
values(10001,1001, 'I want to do the dishes');

insert into post(id,user_id,description)
values(10002,1003, 'I Want to learn AWS');

insert into post(id,user_id,description)
values(10003,1001, 'I want to practice basketball');

insert into post(id,user_id,description)
values(10004,1002, 'I do not like to drink soda');