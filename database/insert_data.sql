use `spring-boot-ecommerce`;

INSERT INTO role(code) VALUES('MANAGER');
INSERT INTO role(code) VALUES('BUYER');
INSERT INTO role(code) VALUES('SELLER');

INSERT INTO account(username, password, full_name, email, status)
VALUES('huyquy', '$2a$10$22dP2Tp4lh19L9qRpn0Ye.fXuahHtYDFgpJYpz3iA2SIqw75cuAJO', 'Nguyễn Huy Quý', 'huyquy2003@gmail.com', 1);
INSERT INTO account(username, password, full_name, email, status)
VALUES('seller', '$2a$10$22dP2Tp4lh19L9qRpn0Ye.fXuahHtYDFgpJYpz3iA2SIqw75cuAJO', 'Nguyễn Van A', 'nhq@gmail.com', 1);	
INSERT INTO account(username, password, full_name, email, status)
VALUES('buyer', '$2a$10$22dP2Tp4lh19L9qRpn0Ye.fXuahHtYDFgpJYpz3iA2SIqw75cuAJO', 'Nguyễn Van B', 'huy@gmail.com', 1);

INSERT INTO cart (account_id) VALUES(3)

INSERT INTO accounts_roles(account_id, role_code) VALUES(1, 'MANAGER');
INSERT INTO accounts_roles(account_id, role_code) VALUES(2, 'SELLER');
INSERT INTO accounts_roles(account_id, role_code) VALUES(3, 'BUYER');

INSERT INTO category(code, thumbnail) VALUES('men-clothes', 'http://res.cloudinary.com/dald4jiyw/image/upload/v1696667534/Category_thumbnail/9a85051f-c704-4079-ad59-abe8de7961c5.jpg')
