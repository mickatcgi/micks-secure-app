-- First DROP all the tables



drop table todo_category;
drop table todo;
drop table user_preferences;
drop table priority;
drop table folder;
drop table category;
drop table status;
drop table persistent_state;
drop table remember_me;
drop table user;



-- CREATE ALL TABLES



-- Modified to make identity field the primary key instead of userid so it

-- works properly with JPA EntityManager (now returns the generated id).

-- make the userid a unique field so we don't get duplicates.



CREATE TABLE user (

    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(100) NOT NULL,

    password varchar(64),

    description VARCHAR(100),

    email_address VARCHAR(100),

    first_name VARCHAR(64),

    last_name VARCHAR(64),

    enabled BOOLEAN NOT NULL,

    created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    updated_at timestamp, 

    UNIQUE(name)

    ) ENGINE=InnoDB;

    

CREATE TABLE role (

    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(32) NOT NULL,

    description VARCHAR(100),

    enabled BOOLEAN NOT NULL,

    created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    updated_at timestamp, 

    UNIQUE(name)

    ) ENGINE=InnoDB;

    

CREATE TABLE user_role (

    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,

    user_id INTEGER NOT NULL,

    role_id INTEGER NOT NULL,

    last_modified timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES user(id),

    CONSTRAINT fk_role_id FOREIGN KEY(role_id) REFERENCES role(id)

    ) ENGINE=InnoDB;



CREATE TABLE priority (

    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,

    user_id INTEGER NOT NULL,

    name VARCHAR(100) NOT NULL,

    description VARCHAR(100),

    enabled BOOLEAN NOT NULL,

    created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    updated_at timestamp, 

    CONSTRAINT fk_priority_user_id FOREIGN KEY(user_id) REFERENCES user(id)

    ) ENGINE=InnoDB;



CREATE TABLE folder (

    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,

    user_id INTEGER NOT NULL,

    name VARCHAR(100) NOT NULL,

    description VARCHAR(100),

    enabled BOOLEAN NOT NULL,

    created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    updated_at timestamp, 

    CONSTRAINT fk_folder_user_id FOREIGN KEY(user_id) REFERENCES user(id) 

    ) ENGINE=InnoDB;



CREATE TABLE category (

    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,

    user_id INTEGER NOT NULL,

    name VARCHAR(100) NOT NULL,

    description VARCHAR(100),

    enabled BOOLEAN NOT NULL,

    created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    updated_at timestamp, 

    CONSTRAINT fk_category_user_id FOREIGN KEY(user_id) REFERENCES user(id) 

    ) ENGINE=InnoDB;

    

CREATE TABLE status (

    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,

    user_id INTEGER NOT NULL,

    name VARCHAR(100) NOT NULL,

    description VARCHAR(100),

    enabled BOOLEAN NOT NULL,

    created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    updated_at timestamp, 

    CONSTRAINT fk_status_user_id FOREIGN KEY(user_id) REFERENCES user(id)

    ) ENGINE=InnoDB;



CREATE TABLE user_preferences (

    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,

    user_id INTEGER NOT NULL,

    name VARCHAR(100) NOT NULL,

    description VARCHAR(100),

    default_priority_id INTEGER NOT NULL,

    default_folder_id INTEGER NOT NULL,

    default_category_id INTEGER NOT NULL,

    default_status_id INTEGER NOT NULL,

    default_advance_due_date_days INTEGER NOT NULL,

    default_due_days INTEGER NOT NULL,

    overdue_bgcolor char(6),

    enabled BOOLEAN NOT NULL,

    created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    updated_at timestamp,

    CONSTRAINT fk_user_prefs_user_id FOREIGN KEY(user_id) REFERENCES user(id),

    CONSTRAINT fk_user_prefs_priority_id FOREIGN KEY(default_priority_id) REFERENCES priority(id),

    CONSTRAINT fk_user_prefs_folder_id FOREIGN KEY(default_folder_id) REFERENCES folder(id),

    CONSTRAINT fk_user_prefs_category_id FOREIGN KEY(default_category_id) REFERENCES category(id),

    CONSTRAINT fk_user_prefs_status_id FOREIGN KEY(default_status_id) REFERENCES status(id)

    ) ENGINE=InnoDB;



CREATE TABLE todo (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255),
    user_id INTEGER NOT NULL,
    priority varchar(20),
    folder varchar(30),
    status varchar(20),
    due_date DATE,
    complete_date DATE,
    date_created timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    date_updated timestamp,
    -- Put notes at the end because it can be long and messes up SQL view
    notes VARCHAR(16000)
    ) ENGINE=InnoDB;



CREATE TABLE todo_category (

    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,

    todo_id INTEGER NOT NULL,

    category_id INTEGER NOT NULL,

    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category (id),

    CONSTRAINT fk_todo_id FOREIGN KEY (todo_id) REFERENCES todo (id)

    ) ENGINE=InnoDB;



CREATE TABLE persistent_state (

    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,

    state_key VARCHAR(100),

    state_data BLOB,

    created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    updated_at timestamp,

    UNIQUE (state_key)

    ) ENGINE=InnoDB;

    

CREATE TABLE remember_me (

    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,

    user_name VARCHAR(100) NOT NULL,

    token VARCHAR(100) NOT NULL,

    created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    updated_at timestamp 

    ) ENGINE=InnoDB;



-- Put your indexes here    

CREATE UNIQUE INDEX state_key_index ON persistent_state(state_Key) USING BTREE;

CREATE UNIQUE INDEX user_name_index ON remember_me(user_name) USING BTREE;



-- Non-unique indexes

CREATE INDEX due_date_index ON todo(due_date) USING BTREE;



-- Might not need these indexes since MySql seems to add indexes for foreign keys by default

--CREATE UNIQUE INDEX todo_user_index ON todo(user_id) USING BTREE;

--CREATE UNIQUE INDEX todo_priority_index ON todo(priority_id) USING BTREE;

--CREATE UNIQUE INDEX todo_status_index ON todo(status_id) USING BTREE;

--CREATE UNIQUE INDEX todo_folder_index ON todo(folder_id) USING BTREE;





--NO NEED TO USE THESE WHEN SETTING UP A NEW DB INSTANCE - THESE WERE USED BY MICK

-- AFTER THE DATABASE WAS ALREADY CREATED...

--alter table status add column enabled boolean not null after description;

--alter table priority add column enabled boolean not null after description;

--alter table folder add column enabled boolean not null after description;

--alter table category add column enabled boolean not null after description;

--alter table user add column enabled boolean not null after last_name;

--alter table user_preferences add column enabled boolean not null after default_due_days;

--alter table user_preferences add column overdue_bgcolor char(6) after default_due_days;

--alter table role add column enabled boolean not null after description;





    

--------------------------------------------------------------------------------------------------------------    

-- ADD SOME BASE TEST DATA

-- Use DEFAULT to default the auto-generated id fields

--------------------------------------------------------------------------------------------------------------    



insert into user (name, description, email_address, password, first_name, last_name, created_at, updated_at) values ('admin', 'U R da man', 'mickster.gallagher@gmail.com', 'password', 'MinkAdmin', 'GallagherAdmin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into user (name, description, email_address, password, first_name, last_name, created_at, updated_at) values ('minkeybule', 'U R da man', 'mickster.gallagher@gmail.com', 'password', 'Minkster', 'Gallagher', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into role(name, description, enabled, created_at, updated_at) values("admin", "Administrator", 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into role(name, description, enabled, created_at, updated_at) values("user", "Regular User", 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into user_role(user_id, role_id) values (1, 1);

insert into user_role(user_id, role_id) values (2, 2);



insert into priority (user_id, name, description, enabled, created_at, updated_at) values (1, 'Urgent', 'Do it now', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into priority (user_id, name, description, enabled, created_at, updated_at) values (1, 'High', 'Do it soon', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into priority (user_id, name, description, enabled, created_at, updated_at) values (1, 'Medium', 'Do it later', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into priority (user_id, name, description, enabled, created_at, updated_at) values (1, 'Low', 'Do it much later', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into priority (user_id, name, description, enabled, created_at, updated_at) values (2, 'Urgent', 'Do it now', 1,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into priority (user_id, name, description, enabled, created_at, updated_at) values (2, 'High', 'Do it soon', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into priority (user_id, name, description, enabled, created_at, updated_at) values (2, 'Medium', 'Do it later', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into priority (user_id, name, description, enabled, created_at, updated_at) values (2, 'Low', 'Do it much later', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into folder (user_id, name, description, enabled, created_at, updated_at) values (1, 'Admins', 'All this junk to code', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into folder (user_id, name, description, enabled, created_at, updated_at) values (1, 'Java', 'All this junk to code', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into folder (user_id, name, description, enabled, created_at, updated_at) values (1, 'Ruby', 'All this junk to code', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into folder (user_id, name, description, enabled, created_at, updated_at) values (1, 'Perl', 'All this junk to code', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into folder (user_id, name, description, enabled, created_at, updated_at) values (2, 'Minkeys', 'Tour de France', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into folder (user_id, name, description, enabled, created_at, updated_at) values (2, 'Bikes', 'Colnago Central', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into category (user_id, name, description, enabled, created_at, updated_at) values (1, 'Enhancement', 'Makes it better', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into category (user_id, name, description, enabled, created_at, updated_at) values (1, 'Bug', 'Fix me', CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

insert into category (user_id, name, description, enabled, created_at, updated_at) values (1, 'Security', 'Prevent Hacks', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into category (user_id, name, description, enabled, created_at, updated_at) values (1, 'Database', 'Improve performance', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into category (user_id, name, description, enabled, created_at, updated_at) values (1, 'GUI', 'Make the UI better', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into category (user_id, name, description, enabled, created_at, updated_at) values (2, 'Road Race', 'Endurance', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into category (user_id, name, description, enabled, created_at, updated_at) values (2, 'Criterium', 'High Intensity', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into status (user_id, name, description, enabled, created_at, updated_at) values (1, 'Not-started', 'Not-started', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into status (user_id, name, description, enabled, created_at, updated_at) values (1, 'In-progress', 'In-progress', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into status (user_id, name, description, enabled, created_at, updated_at) values (1, 'Waiting-on-info', 'Waiting on info', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into status (user_id, name, description, enabled, created_at, updated_at) values (1, 'Completed', 'Completed', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into status (user_id, name, description, enabled, created_at, updated_at) values (2, 'Not-started', 'Not-started', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into status (user_id, name, description, enabled, created_at, updated_at) values (2, 'In-progress', 'In-progress', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into status (user_id, name, description, enabled, created_at, updated_at) values (2, 'Pending info', 'Waiting on info', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into status (user_id, name, description, enabled, created_at, updated_at) values (2, 'Completed', 'Completed', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into user_preferences (name, description, user_id, default_priority_id, default_folder_id, default_category_id, default_status_id, default_advance_due_date_days, default_due_days, created_at, updated_at) values ('Admins Settings', 'Personal Preferences', 1, 3, 1, 1, 5, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into user_preferences (name, description, user_id, default_priority_id, default_folder_id, default_category_id, default_status_id, default_advance_due_date_days, default_due_days, created_at, updated_at) values ('Micks Settings', 'Personal Preferences', 2, 5, 5, 6, 5, 2, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into todo (user_id, description, notes, due_date, complete_date, priority_id, folder_id, status_id, created_at, updated_at) values (1, 'Admins Todo one', 'Admins Notes one', '2013-07-14', NULL, 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into todo (user_id, description, notes, due_date, complete_date, priority_id, folder_id, status_id, created_at, updated_at) values (1, 'Admins Todo two', 'Admins Notes two', '2013-07-14', NULL, 1, 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into todo (user_id, description, notes, due_date, complete_date, priority_id, folder_id, status_id, created_at, updated_at) values (1, 'Admins Todo three', 'Admins Notes three', '2013-07-14', NULL, 1, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into todo (user_id, description, notes, due_date, complete_date, priority_id, folder_id, status_id, created_at, updated_at) values (1, 'Admins Todo four', 'Admins Notes four', '2013-07-14', NULL, 2, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into todo (user_id, description, notes, due_date, complete_date, priority_id, folder_id, status_id, created_at, updated_at) values (1, 'Admins Todo five', 'Admins Notes five', '2013-07-14', NULL, 2, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into todo (user_id, description, notes, due_date, complete_date, priority_id, folder_id, status_id, created_at, updated_at) values (1, 'Admins Todo six', 'Admins Notes six', '2013-07-14', NULL, 2, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into todo (user_id, description, notes, due_date, complete_date, priority_id, folder_id, status_id, created_at, updated_at) values (1, 'Admins Todo seven', 'Admins Notes seven', '2013-07-14', NULL, 3, 1, 3,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into todo (user_id, description, notes, due_date, complete_date, priority_id, folder_id, status_id, created_at, updated_at) values (1, 'Admins Todo eight', 'Admins Notes eight', '2013-07-14', NULL, 3, 2, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into todo (user_id, description, notes, due_date, complete_date, priority_id, folder_id, status_id, created_at, updated_at) values (1, 'Admins Todo nine', 'Admins Notes nine', '2013-07-14', NULL, 4, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into todo (user_id, description, notes, due_date, complete_date, priority_id, folder_id, status_id, created_at, updated_at) values (1, 'Admins Todo ten', 'Admins Notes ten', '2013-07-14', NULL, 4, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into todo (user_id, description, notes, due_date, complete_date, priority_id, folder_id, status_id, created_at, updated_at) values (2, 'Minkeybules Todo nine', 'Minkeybules Notes nine', '2013-07-14', NULL, 8, 5, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into todo (user_id, description, notes, due_date, complete_date, priority_id, folder_id, status_id, created_at, updated_at) values (2, 'Minkeybules Todo ten', 'Minkeybules Notes ten', '2013-07-14', NULL, 5, 6, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



insert into todo_category(todo_id, category_id) values (1, 1);

insert into todo_category(todo_id, category_id) values (2, 1);

insert into todo_category(todo_id, category_id) values (3, 1);

insert into todo_category(todo_id, category_id) values (4, 1);

insert into todo_category(todo_id, category_id) values (5, 1);

insert into todo_category(todo_id, category_id) values (6, 1);

insert into todo_category(todo_id, category_id) values (7, 1);

insert into todo_category(todo_id, category_id) values (8, 1);

insert into todo_category(todo_id, category_id) values (9, 1);

insert into todo_category(todo_id, category_id) values (10, 1);



insert into todo_category(todo_id, category_id) values (11, 6);

insert into todo_category(todo_id, category_id) values (12, 7);

