CREATE DATABASE gradingapp;
USE gradingapp;

CREATE TABLE Classes (
  class_id int4 AUTO_INCREMENT,
  name varchar(255) UNIQUE,
  status varchar(255),
  CONSTRAINT classes_pk PRIMARY KEY (class_id)
);

CREATE TABLE Gradables (
  gradable_id int4 AUTO_INCREMENT,
  name varchar(255),
  class_id int4,
  total_points int,
  relative_weight int,
  category_id int4,
  CONSTRAINT gradables_pk PRIMARY KEY (gradable_id)
);

CREATE TABLE Categories (
  category_id int4 AUTO_INCREMENT,
  name varchar(255),
  class_id int4,
  ugrad_weight int,
  grad_weight int,
  CONSTRAINT categories_pk PRIMARY KEY (category_id)
);

CREATE TABLE Students (
  student_id int4 AUTO_INCREMENT,
  school_id varchar(255),
  name varchar(255),
  type varchar(255),
  CONSTRAINT students_pk PRIMARY KEY (student_id)
);

CREATE TABLE StudentsClasses (
  class_id int4,
  student_id int4,
  CONSTRAINT students_pk PRIMARY KEY (class_id, student_id)
);

CREATE TABLE Grades (
  student_id int4,
  gradable_id int4,
  points_earned int,
  student_weight int,
  comment varchar(255),
  CONSTRAINT grades_pk PRIMARY KEY (student_id, gradable_id)
);