insert into category values (0, 'TOP');
insert into category values (1, 'OUTER');
insert into category values (2, 'PANTS');
insert into category values (3, 'SNEAKERS');
insert into category values (4, 'BAG');
insert into category values (5, 'HAT');
insert into category values (6, 'SOCKS');
insert into category values (7, 'ACCESSORY');

insert into brand values (0, 'A');
insert into brand values (1, 'B');
insert into brand values (2, 'C');
insert into brand values (3, 'D');
insert into brand values (4, 'E');
insert into brand values (5, 'F');
insert into brand values (6, 'G');
insert into brand values (7, 'H');
insert into brand values (8, 'I');

-- 브랜드 A
insert into product values (0, 0, 0, 11200);  -- TOP
insert into product values (1, 0, 1, 5500);   -- OUTER
insert into product values (2, 0, 2, 4200);   -- PANTS
insert into product values (3, 0, 3, 9000);   -- SNEAKERS
insert into product values (4, 0, 4, 2000);   -- BAG
insert into product values (5, 0, 5, 1700);   -- HAT
insert into product values (6, 0, 6, 1800);   -- SOCKS
insert into product values (7, 0, 7, 2300);   -- ACCESSORY

-- 브랜드 B
insert into product values (8, 1, 0, 10500);  -- TOP
insert into product values (9, 1, 1, 5900);   -- OUTER
insert into product values (10, 1, 2, 3800);  -- PANTS
insert into product values (11, 1, 3, 9100);  -- SNEAKERS
insert into product values (12, 1, 4, 2100);  -- BAG
insert into product values (13, 1, 5, 2000);  -- HAT
insert into product values (14, 1, 6, 2000);  -- SOCKS
insert into product values (15, 1, 7, 2200);  -- ACCESSORY

-- 브랜드 C
insert into product values (16, 2, 0, 10000); -- TOP
insert into product values (17, 2, 1, 6200);  -- OUTER
insert into product values (18, 2, 2, 3300);  -- PANTS
insert into product values (19, 2, 3, 9200);  -- SNEAKERS
insert into product values (20, 2, 4, 2200);  -- BAG
insert into product values (21, 2, 5, 1900);  -- HAT
insert into product values (22, 2, 6, 2200);  -- SOCKS
insert into product values (23, 2, 7, 2100);  -- ACCESSORY

-- 브랜드 D
insert into product values (24, 3, 0, 10100); -- TOP
insert into product values (25, 3, 1, 5100);  -- OUTER
insert into product values (26, 3, 2, 3000);  -- PANTS
insert into product values (27, 3, 3, 9500);  -- SNEAKERS
insert into product values (28, 3, 4, 2500);  -- BAG
insert into product values (29, 3, 5, 1500);  -- HAT
insert into product values (30, 3, 6, 2400);  -- SOCKS
insert into product values (31, 3, 7, 2000);  -- ACCESSORY

-- 브랜드 E
insert into product values (32, 4, 0, 10700); -- TOP
insert into product values (33, 4, 1, 5000);  -- OUTER
insert into product values (34, 4, 2, 3800);  -- PANTS
insert into product values (35, 4, 3, 9900);  -- SNEAKERS
insert into product values (36, 4, 4, 2300);  -- BAG
insert into product values (37, 4, 5, 1800);  -- HAT
insert into product values (38, 4, 6, 2100);  -- SOCKS
insert into product values (39, 4, 7, 2100);  -- ACCESSORY

-- 브랜드 F
insert into product values (40, 5, 0, 11200); -- TOP
insert into product values (41, 5, 1, 7200);  -- OUTER
insert into product values (42, 5, 2, 4000);  -- PANTS
insert into product values (43, 5, 3, 9300);  -- SNEAKERS
insert into product values (44, 5, 4, 2100);  -- BAG
insert into product values (45, 5, 5, 1600);  -- HAT
insert into product values (46, 5, 6, 2300);  -- SOCKS
insert into product values (47, 5, 7, 1900);  -- ACCESSORY

-- 브랜드 G
insert into product values (48, 6, 0, 10500); -- TOP
insert into product values (49, 6, 1, 5800);  -- OUTER
insert into product values (50, 6, 2, 3900);  -- PANTS
insert into product values (51, 6, 3, 9000);  -- SNEAKERS
insert into product values (52, 6, 4, 2200);  -- BAG
insert into product values (53, 6, 5, 1700);  -- HAT
insert into product values (54, 6, 6, 2100);  -- SOCKS
insert into product values (55, 6, 7, 2000);  -- ACCESSORY

-- 브랜드 H
insert into product values (56, 7, 0, 10800); -- TOP
insert into product values (57, 7, 1, 6300);  -- OUTER
insert into product values (58, 7, 2, 3100);  -- PANTS
insert into product values (59, 7, 3, 9700);  -- SNEAKERS
insert into product values (60, 7, 4, 2100);  -- BAG
insert into product values (61, 7, 5, 1600);  -- HAT
insert into product values (62, 7, 6, 2000);  -- SOCKS
insert into product values (63, 7, 7, 2000);  -- ACCESSORY

-- 브랜드 I
insert into product values (64, 8, 0, 11400); -- TOP
insert into product values (65, 8, 1, 6700);  -- OUTER
insert into product values (66, 8, 2, 3200);  -- PANTS
insert into product values (67, 8, 3, 9500);  -- SNEAKERS
insert into product values (68, 8, 4, 2400);  -- BAG
insert into product values (69, 8, 5, 1700);  -- HAT
insert into product values (70, 8, 6, 1700);  -- SOCKS
insert into product values (71, 8, 7, 2400);  -- ACCESSORY

insert into min_price_ranking(id, product_id, brand_id, category_id, price) values (0, 16, 2, 0, 10000);
insert into min_price_ranking(id, product_id, brand_id, category_id, price) values (1, 33, 4, 1, 5000);
insert into min_price_ranking(id, product_id, brand_id, category_id, price) values (2, 26,3, 2, 3000);
insert into min_price_ranking(id, product_id, brand_id, category_id, price) values (3, 3, 6, 3, 9000);
insert into min_price_ranking(id, product_id, brand_id, category_id, price) values (4, 51,6, 3, 9000);
insert into min_price_ranking(id, product_id, brand_id, category_id, price) values (5, 4, 0, 4, 2000);
insert into min_price_ranking(id, product_id, brand_id, category_id, price) values (6, 29 ,3, 5, 1500);
insert into min_price_ranking(id, product_id, brand_id, category_id, price) values (7, 70,8, 6, 1700);
insert into min_price_ranking(id, product_id, brand_id, category_id, price) values (8, 47 ,5, 7, 1900);

-- max price ranking
insert into max_price_ranking(id, product_id, brand_id, category_id, price) values (1, 64, 8, 0, 11400);
insert into max_price_ranking(id, product_id, brand_id, category_id, price) values (2, 41, 5, 1, 7200);
insert into max_price_ranking(id, product_id, brand_id, category_id, price) values (3, 2, 0, 2, 4200);
insert into max_price_ranking(id, product_id, brand_id, category_id, price) values (4, 35, 4, 3, 9900);
insert into max_price_ranking(id, product_id, brand_id, category_id, price) values (5, 28, 3, 4, 2500);
insert into max_price_ranking(id, product_id, brand_id, category_id, price) values (6, 13, 1, 5, 2000);
insert into max_price_ranking(id, product_id, brand_id, category_id, price) values (7, 30, 3, 6, 2400);
insert into max_price_ranking(id, product_id, brand_id, category_id, price) values (8, 71, 8, 7, 2400);
