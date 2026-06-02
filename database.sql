CREATE DATABASE IF NOT EXISTS restaurant;
USE restaurant;

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

-- Thêm các bảng cho Module Thanh Toán (Payment Module)
CREATE TABLE tblDish (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price FLOAT NOT NULL,
    type VARCHAR(50),
    des VARCHAR(255)
);

CREATE TABLE tblOrder (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tableId INT NOT NULL,
    orderTime DATETIME NOT NULL,
    status VARCHAR(50) DEFAULT 'active',
    FOREIGN KEY (tableId) REFERENCES tblTable(id)
);

CREATE TABLE tblOrderedDish (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT NOT NULL,
    dishId INT NOT NULL,
    quantity INT NOT NULL,
    unitPrice FLOAT NOT NULL,
    FOREIGN KEY (orderId) REFERENCES tblOrder(id),
    FOREIGN KEY (dishId) REFERENCES tblDish(id)
);

CREATE TABLE tblBill (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT NOT NULL,
    totalAmount FLOAT,
    discountAmount FLOAT DEFAULT 0,
    tax FLOAT DEFAULT 0.1,
    finalAmount FLOAT,
    paymentMethod VARCHAR(50),
    paymentTime DATETIME,
    status VARCHAR(50) DEFAULT 'unpaid',
    FOREIGN KEY (orderId) REFERENCES tblOrder(id)
);

-- Insert dummy data cho menu (Dish)
INSERT INTO tblDish(name, price, type) VALUES ('Pho Bo', 50000, 'Main');
INSERT INTO tblDish(name, price, type) VALUES ('Cafe Sua', 30000, 'Drink');

-- Insert dummy data cho 1 kịch bản hoàn chỉnh (Đặt bàn -> Khách đến ăn -> Có hóa đơn chưa thanh toán)
-- Khách đặt bàn
INSERT INTO tblBooking(clientId, userId, bookDate, time, note, status, totalAmount) VALUES (1, 1, CURDATE(), '18:00', 'Khách hẹn 6h tối', 'confirmed', 200000);
INSERT INTO tblBookedTable(bookingId, tableId, price) VALUES (1, 1, 200000);

-- Khách đến bàn số 1 và gọi món
INSERT INTO tblOrder(tableId, orderTime, status) VALUES (1, NOW(), 'active');
INSERT INTO tblOrderedDish(orderId, dishId, quantity, unitPrice) VALUES (1, 1, 2, 50000); -- 2 Phở Bò
INSERT INTO tblOrderedDish(orderId, dishId, quantity, unitPrice) VALUES (1, 2, 2, 30000); -- 2 Cafe

-- Lễ tân chốt bill tạm tính (chưa thanh toán)
INSERT INTO tblBill(orderId, totalAmount, discountAmount, tax, finalAmount, status) VALUES (1, 160000, 0, 0.1, 176000, 'unpaid');
