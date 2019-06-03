use collegemanagementsystemdb;

insert into Credentials values (123, 'Sonia');

insert into Admin values (123, 'Sonia', 'Arora', 'sonia141@gmail.com');

insert into Credentials values (30001,'Raaghavi');
insert into Credentials values (30002,'Sonia');
insert into Credentials values (30003,'Claire');
insert into Credentials values (30004,'Raaghavi');
insert into Credentials values (30005,'Raaghavi');
insert into Credentials values (30006,'Raaghavi');
insert into Credentials values (30007,'Raaghavi');
insert into Credentials values (30008,'Raaghavi');
insert into Credentials values (30009,'Raaghavi');
insert into Credentials values (30010,'Raaghavi');

insert into Credentials values(500, 'Martine');
insert into Credentials values (501, 'Wes');
insert into Credentials values (502, 'Anderson');
insert into Credentials values (503, 'Kayee');
insert into Credentials values (504, 'Hossam');


insert into Faculty values (500, 'Martine', 'De Cock', 'martine@uw.edu');
insert into Faculty values (501, 'Wes', 'Llyod', 'wes@uw.edu');
insert into Faculty values (502, 'Anderson', 'Nascemento', 'anderson@uw.edu');
insert into Faculty values (503, 'Kayee', 'Yeung', 'kayee@uw.edu');
insert into Faculty values (504, 'Hossam', 'Fattah', 'hossam@uw.edu');

insert into Student values (30001, 'Raaghavi', 'Sivaguru', 'Bellevue', 'raaghavi@uw.edu');
insert into Student values (30002, 'Sonia', 'Arora', 'Bellevue', 'sonia90@uw.edu');
insert into Student values (30003, 'Claire', 'Nie', 'Kirkland', 'niec@uw.edu');
insert into Student values (30004, 'Raaghavi', 'Sivaguru', 'Bellevue', 'raaghavi@uw.edu');
insert into Student values (30005, 'Raaghavi', 'Sivaguru', 'Bellevue', 'raaghavi@uw.edu');
insert into Student values (30006, 'Raaghavi', 'Sivaguru', 'Bellevue', 'raaghavi@uw.edu');
insert into Student values (30007, 'Raaghavi', 'Sivaguru', 'Bellevue', 'raaghavi@uw.edu');
insert into Student values (30008, 'Raaghavi', 'Sivaguru', 'Bellevue', 'raaghavi@uw.edu');
insert into Student values (30009, 'Raaghavi', 'Sivaguru', 'Bellevue', 'raaghavi@uw.edu');
insert into Student values (30010, 'Raaghavi', 'Sivaguru', 'Bellevue', 'raaghavi@uw.edu');

insert into Course values ('TCSS543', 'Advanced Algorithms', 5);
insert into Course values ('TCSS545', 'Database System Design', 5);
insert into Course values ('TCSS551', 'Big Data Analytics', 5);
insert into Course values ('TCSS555', 'Machine Learning', 5);
insert into Course values ('TCSS558', 'Applied Distributed Computing', 5);
insert into Course values ('TCSS562', 'Cloud Computing', 5);
insert into Course values ('TCSS593', 'Research Seminar in Data Science', 1);
insert into Course values ('TCSS598', 'Masters Seminar', 2);
insert into Course values ('TCSS600', 'Independent Study', 3);
insert into Course values ('TCSS700', 'Masters Thesis', 5);

insert into StudentCourses values (30001, 'TCSS543', 'Fall 2018');
insert into StudentCourses values (30001, 'TCSS551', 'Fall 2018');
insert into StudentCourses values (30001, 'TCSS558', 'Winter 2019');
insert into StudentCourses values (30001, 'TCSS600', 'Winter 2019');
insert into StudentCourses values (30001, 'TCSS545', 'Spring 2019');
insert into StudentCourses values (30001, 'TCSS700', 'Spring 2019');
insert into StudentCourses values (30001, 'TCSS555', 'Spring 2018');
insert into StudentCourses values (30001, 'TCSS562', 'Spring 2018');

insert into Credentials values (888, 'Lisa')
insert into Credentials values (999, 'John')

insert into Faculty values(888, 'Lisa', 'Moore', '999@gmail.com')
insert into Faculty values(999, 'John', 'Smith', '999@gmail.com')

insert into FacultyCourses values (888, 'TCSS543');
insert into FacultyCourses values (888, 'TCSS600');
insert into FacultyCourses values (888, 'TCSS558');
insert into FacultyCourses values (888, 'TCSS551');
insert into FacultyCourses values (999, 'TCSS562');
insert into FacultyCourses values (999, 'TCSS555');
insert into FacultyCourses values (999, 'TCSS545');
insert into FacultyCourses values (999, 'TCSS700');
