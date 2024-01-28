DROP TABLE IF EXISTS device;
DROP TABLE IF EXISTS room;

DROP TABLE IF EXISTS house;


CREATE TABLE house(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE room(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at DATE,
    id_house INT,
    Foreign Key (id_house) REFERENCES house(id) ON DELETE CASCADE
);

CREATE TABLE device (
    
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    status VARCHAR(25) NOT NULL,
    created_at DATE,
    id_room INT,
    Foreign Key (id_room) REFERENCES room(id) ON DELETE CASCADE
);
