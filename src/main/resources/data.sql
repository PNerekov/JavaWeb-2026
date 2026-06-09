INSERT INTO CATEGORY(NAME)
VALUES ('Mobile Phones');

INSERT INTO CATEGORY(NAME)
VALUES ('Mobile Accessories');

INSERT INTO CATEGORY(NAME)
VALUES ('Laptops');

INSERT INTO CATEGORY(NAME)
VALUES ('Computer Accessories');

INSERT INTO CATEGORY(NAME)
VALUES ('Gaming');


INSERT INTO PRODUCT(NAME, DESCRIPTION, PRICE, STOCK_QUANTITY, IMAGE_URL, CATEGORY_ID)
VALUES ('Samsung Galaxy S25', 'Android smartphone with 256GB storage.', 899.99, 15, 'https://aloalo.hr/upload/catalog/product/26769/1741610836_0904328.jpg', 1);

INSERT INTO PRODUCT(NAME, DESCRIPTION, PRICE, STOCK_QUANTITY, IMAGE_URL, CATEGORY_ID)
VALUES ('iPhone 16', 'Apple smartphone with 128GB storage.', 999.99, 10, 'https://istyle.hr/cdn/shop/files/IMG-14772628.jpg?v=1727230969', 1);

INSERT INTO PRODUCT(NAME, DESCRIPTION, PRICE, STOCK_QUANTITY, IMAGE_URL, CATEGORY_ID)
VALUES ('Screen Protector', 'Tempered glass screen protector for mobile phones.', 9.99, 100, 'https://www.quadlockcase.eu/cdn/shop/files/Pixel-8PRO_ScreenProtector_600x600_012adea4-10a3-4797-8e67-3d1ee9958377.png?v=1723590443', 2);

INSERT INTO PRODUCT(NAME, DESCRIPTION, PRICE, STOCK_QUANTITY, IMAGE_URL, CATEGORY_ID)
VALUES ('Phone Case', 'Protective silicone phone case.', 14.99, 80, 'https://acaso.uk/cdn/shop/files/GIRLYSUMMERBlueSnapVsToughWebInfo_IP17.jpg?v=1762259488&width=1920', 2);

INSERT INTO PRODUCT(NAME, DESCRIPTION, PRICE, STOCK_QUANTITY, IMAGE_URL, CATEGORY_ID)
VALUES ('Lenovo ThinkPad E16', 'Business laptop with 16GB RAM and 512GB SSD.', 1099.99, 8, '/images/thinkpad.jpg', 3);

INSERT INTO PRODUCT(NAME, DESCRIPTION, PRICE, STOCK_QUANTITY, IMAGE_URL, CATEGORY_ID)
VALUES ('MacBook Air M3', 'Apple laptop with M3 chip and 256GB SSD.', 1299.99, 6, '/images/macbook-air.jpg', 3);

INSERT INTO PRODUCT(NAME, DESCRIPTION, PRICE, STOCK_QUANTITY, IMAGE_URL, CATEGORY_ID)
VALUES ('Wireless Mouse', 'Ergonomic wireless mouse.', 24.99, 50, '/images/mouse.jpg', 4);

INSERT INTO PRODUCT(NAME, DESCRIPTION, PRICE, STOCK_QUANTITY, IMAGE_URL, CATEGORY_ID)
VALUES ('Mechanical Keyboard', 'RGB mechanical keyboard for gaming and work.', 79.99, 30, '/images/keyboard.jpg', 4);

INSERT INTO PRODUCT(NAME, DESCRIPTION, PRICE, STOCK_QUANTITY, IMAGE_URL, CATEGORY_ID)
VALUES ('Gaming Headset', 'Over-ear gaming headset with microphone.', 59.99, 25, '/images/headset.jpg', 5);

INSERT INTO PRODUCT(NAME, DESCRIPTION, PRICE, STOCK_QUANTITY, IMAGE_URL, CATEGORY_ID)
VALUES ('PlayStation Controller', 'Wireless controller for console and PC gaming.', 69.99, 20, '/images/controller.jpg', 5);

INSERT INTO USERS(NAME, EMAIL, PASSWORD)
VALUES('user', 'user@user.com', '$2a$10$cYUXXNkDYAyxNqvdoXwapelF12LpWQsLRA9Bwy1kg4uQxfwNvFxGG'); -- password

INSERT INTO USERS(NAME, EMAIL, PASSWORD)
VALUES('admin', 'admin@admin.com', '$2a$10$cYUXXNkDYAyxNqvdoXwapelF12LpWQsLRA9Bwy1kg4uQxfwNvFxGG'); -- password

INSERT INTO USERS(NAME, EMAIL, PASSWORD)
VALUES('read_only', 'read_only@read_only.com', '$2a$10$cYUXXNkDYAyxNqvdoXwapelF12LpWQsLRA9Bwy1kg4uQxfwNvFxGG'); -- password

INSERT INTO ROLES(NAME) VALUES('USER');
INSERT INTO ROLES(NAME) VALUES('ADMIN');
INSERT INTO ROLES(NAME) VALUES('READ_ONLY');

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO USERS_ROLES(USER_ID, ROLE_ID) VALUES (2, 1);
INSERT INTO USERS_ROLES(USER_ID, ROLE_ID) VALUES (2, 2);
INSERT INTO USERS_ROLES(USER_ID, ROLE_ID) VALUES (3, 3);