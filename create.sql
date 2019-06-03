create database CollegeManagementSystemDB;

use CollegeManagementSystemDB;
create table Course
(
  CourseNumber varchar(45) not null,
  CourseName varchar(45) not null,
  Credit int not null,
  constraint course_pk primary key (CourseNumber)
);

create table Credentials
(
  ID int not null,
  Password varchar(45) not null,
  constraint credentials_pk primary key (ID)
);

create table Admin
(
  ID int not null,
  FirstName varchar(45) not null,
  LastName varchar(45) not null,
  Email varchar(45) not null,
  constraint admin_fk foreign key (ID) references Credentials (ID) on delete cascade,
  constraint admin_pk primary key (ID)
);

create table Faculty
(
  ID int not null,
  FirstName varchar(45) not null,
  LastName  varchar(45) not null,
  Email varchar(45) not null,
  constraint faculty_fk foreign key (ID) references Credentials (ID) on delete cascade,
  constraint faculty_pk primary key (ID)
);

create table FacultyCourses
(
  CourseNumber varchar(45) not null,
  FacultyID int not null,
  constraint faculty_courses_coursenum_fk foreign key (CourseNumber) references Course (CourseNumber) on delete cascade,
  constraint faculty_courses_facultyid_fk foreign key (FacultyID) references Faculty (ID) on delete cascade,
  constraint faculty_courses_pk primary key (CourseNumber, FacultyID)
);

create table Student
(
  ID int not null,
  FirstName varchar(45) not null,
  LastName varchar(45) not null,
  Address varchar(45),
  Email varchar(45) not null,
  constraint student_fk foreign key (ID) references Credentials (ID) on delete cascade,
  constraint student_pk primary key (ID)
);

create table StudentCourses
(
  StudentID int not null,
  CourseNumber varchar(45) not null,
  Quarter varchar(45) not null,
  constraint student_courses_coursenum_fk foreign key (CourseNumber) references Course (CourseNumber) on delete cascade,
  constraint student_courses_studentid_fk foreign key (StudentID) references Student (ID) on delete cascade,
  constraint student_courses_pk primary key (StudentID, CourseNumber)
);

create table StudentGrades
(
  StudentID int not null,
  CourseNumber varchar(45) not null,
  Grade decimal(2,1),
  constraint student_grades_fk foreign key (StudentID, CourseNumber) references StudentCourses(StudentID, CourseNumber) on delete cascade,
  constraint student_grades_pk primary key (StudentID, CourseNumber)
);


create view TranscriptView as select StudentID, CourseNumber, CourseName, Credit, Quarter, Grade from StudentCourses natural join Course natural join StudentGrades;


delimiter $
create trigger AfterAddCourse
  after insert on StudentCourses
  for each row
  begin
    insert into StudentGrades values (new.StudentID, new.CourseNumber, NULL);
  end$
delimiter ;


delimiter $
create procedure total_credits (in id int, in qr varchar(45), out credits int)
begin
  if not exists (select sum(Credit) from StudentCourses natural join Course group by StudentID, Quarter having StudentID=id and Quarter=qr) then
    set credits=0;
  else
    select sum(Credit) into credits from StudentCourses natural join Course group by StudentID, Quarter having StudentID=id and Quarter=qr;
  end if;
end$
delimiter ;

