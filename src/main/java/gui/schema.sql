DROP DATABASE gradingapp;
CREATE DATABASE gradingapp;
USE gradingapp;

CREATE TABLE Classes (
  class_id int4 AUTO_INCREMENT,
  name varchar(255) UNIQUE,
  status varchar(255),
  curve int4,
  CONSTRAINT classes_pk PRIMARY KEY (class_id)
);

CREATE TABLE Gradables (
  name varchar(255),
  class_id int4,
  total_points int,
  relative_weight int,
  category_name varchar(255),
  CONSTRAINT gradables_pk PRIMARY KEY (name, class_id)
);

CREATE TABLE Categories (
  name varchar(255),
  class_id int4,
  ugrad_weight int,
  grad_weight int,
  CONSTRAINT categories_pk PRIMARY KEY (name, class_id)
);

CREATE TABLE Students (
  school_id varchar(255),
  name varchar(255),
  type varchar(255),
  CONSTRAINT students_pk PRIMARY KEY (school_id)
);

CREATE TABLE StudentsClasses (
  class_id int4,
  school_id varchar(255),
  CONSTRAINT students_pk PRIMARY KEY (class_id, school_id)
);

CREATE TABLE Grades (
  school_id varchar(255),
  gradable_name varchar(255),
  class_id int4,
  points_lost int,
  student_weight int,
  comment varchar(255),
  CONSTRAINT grades_pk PRIMARY KEY (school_id, gradable_name, class_id)
);