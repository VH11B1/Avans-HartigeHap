use hh;
CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(255) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  employeeId BIGINT,
  PRIMARY KEY (username),
  CONSTRAINT fk_employee FOREIGN KEY (employeeId) REFERENCES employees(id)
);

CREATE TABLE user_roles (
  user_role_id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  ROLE VARCHAR(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (ROLE,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));

INSERT INTO `hh`.`employees` (`version`, `email`, `hoursPerMonth`, `name`, `password`, `username`) VALUES ('0', 'mbartele@avans.nl', '160', 'Mark Bartelen', '', 'mark');
INSERT INTO `hh`.`employees` (`version`, `email`, `hoursPerMonth`, `name`, `password`, `username`) VALUES ('0', 'erco@test.nl', '160', 'Erco Argante', '', 'erco');
INSERT INTO `hh`.`employees` (`version`, `email`, `hoursPerMonth`, `name`, `password`, `username`) VALUES ('0', 'employee@test.nl', '160', 'Emplo Yee', '', 'employee');
insert into users(username,password,enabled,employeeId) values ("mark","$2a$11$ggPr5LwnNTfogg3QCfFNH.UtKRsgaPpa713h9RnaI3pGvLDeqg/lm",1,1);
insert into users(username,password,enabled,employeeId) values ("erco","$2a$11$OUXFrzqqRgWSvwZphtjlge4n9j8qviBs16pFPE1R0QATfX2qpIWm.",1,2);
insert into users(username,password,enabled,employeeId) values ("employee","$2a$11$.9VrL7RHckCtfiq26ywIyODcrGkEIAyBQMTp7.DqWWkuZQLabtHYm",1,3);
insert into users(username,password,enabled) values ("customer","$2a$11$Bp.nN86QR.6j5lQSGQ2dL.urOJHw32N.AXEMPI4W5lHGsyj6517GO",1);

INSERT INTO user_roles (username, ROLE)
VALUES ('erco', 'ROLE_CUSTOMER');
INSERT INTO user_roles (username, ROLE)
VALUES ('employee', 'ROLE_CUSTOMER');
INSERT INTO user_roles (username, ROLE)
VALUES ('customer', 'ROLE_CUSTOMER');

INSERT INTO user_roles (username, ROLE)
VALUES ('erco', 'ROLE_EMPLOYEE');
INSERT INTO user_roles (username, ROLE)
VALUES ('employee', 'ROLE_EMPLOYEE');
INSERT INTO user_roles (username, ROLE)
VALUES ('mark', 'ROLE_EMPLOYEE');
