create table Admin
(
  ID        int         not null
    primary key,
  FirstName varchar(45) not null,
  LastName  varchar(45) not null,
  Email     varchar(45) not null
);

create table Course
(
  CourseNumber char(45)    not null
    primary key,
  CourseName   varchar(45) not null
);

create table credentials
(
  ID       int         not null
    primary key,
  Password varchar(45) not null,
  constraint credentials_admin__fk
    foreign key (ID) references admin (id)
);

create table Faculty
(
  ID        int         not null
    primary key,
  FirstName varchar(45) not null,
  LastName  varchar(45) not null,
  Email     varchar(45) not null
);

create table facultycourses
(
  CourseNumber char(45) not null
    primary key,
  FacultyID    int      not null,
  constraint facultycourses_faculty__fk
    foreign key (FacultyID) references faculty (id)
);

create table Student
(
  ID        int         not null
    primary key,
  FirstName varchar(45) not null,
  LastName  varchar(45) not null,
  Address   varchar(45) null,
  Email     varchar(45) not null
);

create table studentcourses
(
  StudentID    int      not null,
  CourseNumber char(45) not null
    primary key,
  constraint studentcourses_courses__fk
    foreign key (CourseNumber) references courses (coursenumber),
  constraint studentcourses_students__fk
    foreign key (StudentID) references students (id)
);


