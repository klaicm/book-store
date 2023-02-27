INSERT INTO book_types (id, type, discount) VALUES (1, 'NEW_RELEASE', 0);
INSERT INTO book_types (id, type, discount) VALUES (2, 'REGULAR', 0.1);
INSERT INTO book_types (id, type, discount) VALUES (3, 'OLD_EDITION', 0.2);

INSERT INTO books (id, uuid, name, price, quantity, book_type_id) VALUES (1, cast('f4a83d13-c29c-42da-97fc-addd750328c8' as UUID), 'Book A', 10.55, 100, 1);
INSERT INTO books (id, uuid, name, price, quantity, book_type_id) VALUES (2, cast('48f9a4c9-7dfd-4a92-8e7c-12607eedac0d' as UUID), 'Book B', 20.99, 50, 1);
INSERT INTO books (id, uuid, name, price, quantity, book_type_id) VALUES (3, cast('9e0708c6-496a-4fd0-90ef-a761e651a1c6' as UUID), 'Book C', 20.99, 25, 1);
INSERT INTO books (id, uuid, name, price, quantity, book_type_id) VALUES (4, cast('b56f5652-745a-417a-beb4-4c1e4fe41ab3' as UUID), 'Book D', 20.99, 0, 1);
INSERT INTO books (id, uuid, name, price, quantity, book_type_id) VALUES (5, cast('7c77c42a-11e8-4c43-822a-4deaf70854d0' as UUID), 'Book E', 10, 2, 2);
INSERT INTO books (id, uuid, name, price, quantity, book_type_id) VALUES (6, cast('d1f6b0d0-7495-476a-a8a7-81c42bff111a' as UUID), 'Book F', 10, 16, 3);
INSERT INTO books (id, uuid, name, price, quantity, book_type_id) VALUES (7, cast('ec979b5e-1364-4e75-b2c7-008945f7f3ba' as UUID), 'Book G', 13, 8, 2);
INSERT INTO books (id, uuid, name, price, quantity, book_type_id) VALUES (8, cast('aab3e188-4924-4bc3-9919-7f5591b67817' as UUID), 'Book H', 3, 11, 2);

INSERT INTO customers (id, uuid, first_name, last_name, loyalty_points) VALUES (1, cast('479a1ca9-b7ed-46b5-ad94-2775f1321426' as UUID), 'Krishan', 'Leach', 0);
INSERT INTO customers (id, uuid, first_name, last_name, loyalty_points) VALUES (2, cast('05a3e52e-95e2-4169-85f7-46226475f2d2' as UUID), 'Beth', 'Lowe', 3);
INSERT INTO customers (id, uuid, first_name, last_name, loyalty_points) VALUES (3, cast('efbc2e71-fa07-4255-b69d-e49719fd6108' as UUID), 'Jazmin', 'Mathews', 4);
INSERT INTO customers (id, uuid, first_name, last_name, loyalty_points) VALUES (4, cast('5da5c610-8408-4230-9aa0-1d82f9995ae5' as UUID), 'Laiba', 'Bennett', 7);
INSERT INTO customers (id, uuid, first_name, last_name, loyalty_points) VALUES (5, cast('2a0d199d-93c1-42fa-91dc-470d503e8b8c' as UUID), 'Katrina', 'Shields', 9);
INSERT INTO customers (id, uuid, first_name, last_name, loyalty_points) VALUES (6, cast('61cbb1d6-53fb-42b4-a746-3cb6eec6c1dd' as UUID), 'Marianne', 'Gonzalez', 1);

INSERT INTO purchases (id, uuid, created_at, customer_id) VALUES (1, RANDOM_UUID(), '2023-01-09T10:15:16+01:00', 2);
INSERT INTO purchases (id, uuid, created_at, customer_id) VALUES (2, RANDOM_UUID(), '2023-02-19T12:18:35+01:00', 3);
INSERT INTO purchases (id, uuid, created_at, customer_id) VALUES (3, RANDOM_UUID(), '2023-02-20T20:33:22+01:00', 3);
INSERT INTO purchases (id, uuid, created_at, customer_id) VALUES (4, RANDOM_UUID(), '2023-02-20T12:27:32+01:00', 4);

INSERT INTO purchased_books (id, purchase_id, book_id, quantity, type, single_copy_original_price, single_copy_final_price) VALUES (1, 1, 1, 5, 0, 15.55, 14);
INSERT INTO purchased_books (id, purchase_id, book_id, quantity, type, single_copy_original_price, single_copy_final_price) VALUES (2, 1, 2, 3, 1, 12.30, 11);
INSERT INTO purchased_books (id, purchase_id, book_id, quantity, type, single_copy_original_price, single_copy_final_price) VALUES (3, 1, 3, 1, 1, 8.55, 7.58);
INSERT INTO purchased_books (id, purchase_id, book_id, quantity, type, single_copy_original_price, single_copy_final_price) VALUES (4, 2, 1, 1, 0, 15.55, 14);
INSERT INTO purchased_books (id, purchase_id, book_id, quantity, type, single_copy_original_price, single_copy_final_price) VALUES (5, 3, 1, 3, 2, 15.55, 14);
INSERT INTO purchased_books (id, purchase_id, book_id, quantity, type, single_copy_original_price, single_copy_final_price) VALUES (6, 4, 3, 1, 2, 10, 6);
INSERT INTO purchased_books (id, purchase_id, book_id, quantity, type, single_copy_original_price, single_copy_final_price) VALUES (7, 4, 4, 10, 2, 25.55, 18.57);
