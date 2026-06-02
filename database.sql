CREATE DATABASE IF NOT EXISTS hotel;
USE hotel;

CREATE TABLE tblUser (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    position VARCHAR(255)
);

CREATE TABLE tblClient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    tel VARCHAR(255),
    address VARCHAR(255),
    note VARCHAR(255)
);

CREATE TABLE tblTable (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    des VARCHAR(255)
);

CREATE TABLE tblBooking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    clientId INT NOT NULL,
    userId INT NOT NULL,
    bookDate DATE NOT NULL,
    time VARCHAR(50) NOT NULL,
    note VARCHAR(255),
    status VARCHAR(50),
    totalAmount FLOAT,
    FOREIGN KEY (clientId) REFERENCES tblClient(id),
    FOREIGN KEY (userId) REFERENCES tblUser(id)
);

CREATE TABLE tblBookedTable (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bookingId INT NOT NULL,
    tableId INT NOT NULL,
    price FLOAT NOT NULL,
    FOREIGN KEY (bookingId) REFERENCES tblBooking(id),
    FOREIGN KEY (tableId) REFERENCES tblTable(id)
);

-- Insert dummy data for testing
INSERT INTO tblUser(username, password, name, position) VALUES ('admin', '123456', 'Le tan Admin', 'Receptionist');
INSERT INTO tblTable(name, capacity, des) VALUES ('Ban 1', 2, 'Ban 2 nguoi canh cua so');
INSERT INTO tblTable(name, capacity, des) VALUES ('Ban 2', 4, 'Ban 4 nguoi o giua');
INSERT INTO tblTable(name, capacity, des) VALUES ('Ban VIP', 10, 'Ban VIP trong phong kin');
INSERT INTO tblClient(name, email, tel, address, note) VALUES ('Nguyen Van A', 'a@gmail.com', '0123456789', 'Hanoi', 'Khach VIP');
