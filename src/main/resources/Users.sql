CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(45) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  employeeId INT,
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

INSERT INTO users(username,password,enabled)
VALUES ('erco','erco', TRUE);
INSERT INTO users(username,password,enabled)
VALUES ('employee','employee', TRUE);
INSERT INTO users(username,password,enabled)
VALUES ('customer','customer', TRUE);
 
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