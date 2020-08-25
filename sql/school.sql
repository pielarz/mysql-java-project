CREATE DATABASE school CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE school;

CREATE TABLE users_groups
(
    id   INT AUTO_INCREMENT,
    name VARCHAR(256),
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id            INT AUTO_INCREMENT,
    username      VARCHAR(256),
    email         VARCHAR(256) UNIQUE,
    password      VARCHAR(256),
    user_group_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_group_id) REFERENCES users_groups (id)
);

CREATE TABLE exercises
(
    id          INT AUTO_INCREMENT,
    title       VARCHAR(256),
    description TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE solutions
(
    id           INT AUTO_INCREMENT,
    user_id      INT NOT NULL,
    excercise_id INT NOT NULL,
    created      DATETIME,
    updated      DATETIME,
    description  TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (excercise_id) REFERENCES exercises (id)
);