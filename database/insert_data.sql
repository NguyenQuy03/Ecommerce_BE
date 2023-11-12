INSERT INTO role(code) VALUES('MANAGER');
INSERT INTO role(code) VALUES('BUYER');
INSERT INTO role(code) VALUES('SELLER');
GO

INSERT INTO account(username, password, full_name, email, status)
VALUES('huyquy', '$2a$10$22dP2Tp4lh19L9qRpn0Ye.fXuahHtYDFgpJYpz3iA2SIqw75cuAJO', 'Nguyen Huy Quy', 'huyquy2003@gmail.com', 1);

INSERT INTO account(username, password, full_name, email, status)
VALUES('menClothes', '$2a$10$22dP2Tp4lh19L9qRpn0Ye.fXuahHtYDFgpJYpz3iA2SIqw75cuAJO', 'Nguyen Van A', 'nhq@gmail.com', 1);

INSERT INTO account(username, password, full_name, email, status)
VALUES('womenClothes', '$2a$10$22dP2Tp4lh19L9qRpn0Ye.fXuahHtYDFgpJYpz3iA2SIqw75cuAJO', 'Nguyen Van C', 'abc@gmail.com', 1);

INSERT INTO account(username, password, full_name, email, status)
VALUES('buyer', '$2a$10$22dP2Tp4lh19L9qRpn0Ye.fXuahHtYDFgpJYpz3iA2SIqw75cuAJO', 'Nguyen Van B', 'huy@gmail.com', 1);

GO
INSERT INTO cart(account_id) VALUES(1)
INSERT INTO cart(account_id) VALUES(2)
INSERT INTO cart(account_id) VALUES(3)
INSERT INTO cart(account_id) VALUES(4)

INSERT INTO accounts_roles(account_id, role_code) VALUES(1, 'MANAGER');
INSERT INTO accounts_roles(account_id, role_code) VALUES(2, 'SELLER');
INSERT INTO accounts_roles(account_id, role_code) VALUES(3, 'SELLER');
INSERT INTO accounts_roles(account_id, role_code) VALUES(4, 'BUYER');

GO
INSERT INTO category(code, thumbnail, account_id) VALUES('men-clothes', 'https://res.cloudinary.com/dald4jiyw/image/upload/v1698766111/Category_thumbnail/13774b35-aafd-4dc3-bc9a-eb1203fdbaa5.png', 1)
INSERT INTO category(code, thumbnail, account_id) VALUES('women-clothes', 'https://res.cloudinary.com/dald4jiyw/image/upload/v1698766253/Category_thumbnail/15f9511b-a71d-448f-84c3-d8f2dc8b3fd5.png', 1)

GO
INSERT INTO product VALUES('men-clothes', GETDATE(), 'men-clothes', GETDATE(), '<p>Des</p>', 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696924488/Product_Image/f15313fc-64d0-4837-8276-667f1f04ac3a.jpg'
, 'Crop-top', '{meterial=leather}', 'ACTIVE', 2, 1);

INSERT INTO product VALUES('men-clothes', GETDATE(), 'men-clothes', GETDATE(), '<p>Des</p>', 'https://res.cloudinary.com/dald4jiyw/image/upload/v1697097597/Product_Image/09744977-7ac3-4833-8d03-6f7cddb9a856.jpg'
, 'Wrist watch', '{meterial=leather}', 'ACTIVE', 2, 1);

INSERT INTO product VALUES('men-clothes', GETDATE(), 'men-clothes', GETDATE(), '<p>Des</p>', 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696758233/Product_Image/ed81892d-e71d-41b5-9e96-01a1f9f56067.jpg'
, 'T-shirt', '{meterial=leather}', 'ACTIVE', 2, 1);

INSERT INTO product VALUES('women-clothes', GETDATE(), 'women-clothes', GETDATE(), '<p>Des</p>', 'https://res.cloudinary.com/dald4jiyw/image/upload/v1697894776/Product_Image/2340ad54-a4d5-41b8-a3fc-543271be6181.jpg'
, 'Jeans', '{meterial=leather}', 'ACTIVE', 3, 2);

INSERT INTO product VALUES('women-clothes', GETDATE(), 'women-clothes', GETDATE(), '<p>Des</p>', 'https://res.cloudinary.com/dald4jiyw/image/upload/v1698675384/Product_Image/290c6376-6b86-4de8-b7fd-9949cb42c61f.jpg'
, 'Polo', '{meterial=leather}', 'ACTIVE', 3, 2);

INSERT INTO product VALUES('women-clothes', GETDATE(), 'women-clothes', GETDATE(), '<p>Des</p>', 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696758233/Product_Image/ed81892d-e71d-41b5-9e96-01a1f9f56067.jpg'
, 'Tank top', '{meterial=leather}', 'ACTIVE', 3, 2);

GO
INSERT INTO product_item VALUES('men-clothes', GETDATE(), 'men-clothes', GETDATE(), 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696758154/Product_Item_Image/b882a901-cb11-490f-a751-8aae8beb7bc6.jpg'
, 1.3, 0, 'ACTIVE', 200, 'size', 'M', 1);

INSERT INTO product_item VALUES('men-clothes', GETDATE(), 'men-clothes', GETDATE(), 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696758235/Product_Item_Image/104aa0fb-cff6-4630-8cc4-7bc5325cf93a.jpg'
, 1.6, 0, 'ACTIVE', 300, 'size', 'L', 1);

INSERT INTO product_item VALUES('men-clothes', GETDATE(), 'men-clothes', GETDATE(), 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696924491/Product_Item_Image/48bcb237-db85-4343-b833-58fd3bba711d.jpg'
, 1.6, 0, 'ACTIVE', 300, 'color', 'Red', 2);

INSERT INTO product_item VALUES('men-clothes', GETDATE(), 'men-clothes', GETDATE(), 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696924490/Product_Item_Image/794313d6-fc87-43c6-8fdd-eedd92072b4a.jpg'
, 1.6, 0, 'ACTIVE', 300, 'color', 'Blue', 2);

INSERT INTO product_item VALUES('men-clothes', GETDATE(), 'men-clothes', GETDATE(), 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696924443/Product_Item_Image/f5864c59-3b2a-4858-9fbd-03a36e9c55de.jpg'
, 1.6, 0, 'ACTIVE', 300, 'size', 'L', 3);

INSERT INTO product_item VALUES('men-clothes', GETDATE(), 'men-clothes', GETDATE(), 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696758235/Product_Item_Image/9bb7fe81-d176-44a5-8a5d-97c5572eb761.jpg'
, 1.6, 0, 'ACTIVE', 300, 'size', 'XL', 3);


INSERT INTO product_item VALUES('women-clothes', GETDATE(), 'women-clothes', GETDATE(), 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696758154/Product_Item_Image/b882a901-cb11-490f-a751-8aae8beb7bc6.jpg'
, 1.3, 0, 'ACTIVE', 200, 'size', 'M', 4);

INSERT INTO product_item VALUES('women-clothes', GETDATE(), 'women-clothes', GETDATE(), 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696758235/Product_Item_Image/104aa0fb-cff6-4630-8cc4-7bc5325cf93a.jpg'
, 1.6, 0, 'ACTIVE', 300, 'size', 'L', 4);

INSERT INTO product_item VALUES('women-clothes', GETDATE(), 'women-clothes', GETDATE(), 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696924491/Product_Item_Image/48bcb237-db85-4343-b833-58fd3bba711d.jpg'
, 1.8, 0, 'ACTIVE', 300, 'color', 'Red', 5);

INSERT INTO product_item VALUES('women-clothes', GETDATE(), 'women-clothes', GETDATE(), 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696924490/Product_Item_Image/794313d6-fc87-43c6-8fdd-eedd92072b4a.jpg'
, 2.0, 0, 'ACTIVE', 300, 'color', 'Blue', 5);

INSERT INTO product_item VALUES('women-clothes', GETDATE(), 'women-clothes', GETDATE(), 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696924443/Product_Item_Image/f5864c59-3b2a-4858-9fbd-03a36e9c55de.jpg'
, 3.6, 0, 'ACTIVE', 300, 'size', 'L', 6);

INSERT INTO product_item VALUES('women-clothes', GETDATE(), 'women-clothes', GETDATE(), 'https://res.cloudinary.com/dald4jiyw/image/upload/v1696758235/Product_Item_Image/9bb7fe81-d176-44a5-8a5d-97c5572eb761.jpg'
, 1.6, 0, 'ACTIVE', 300, 'size', 'XL', 6);
