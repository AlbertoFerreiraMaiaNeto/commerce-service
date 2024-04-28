DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS tb_order;
DROP TABLE IF EXISTS order_product;

CREATE TABLE category (
        name varchar(255) PRIMARY KEY not null,
        description varchar(255),
        active boolean
    );

CREATE TABLE product (
            name varchar(255) not null,
            category varchar(255) not null,
            description varchar(255),
            amount integer,
            unitPrice numeric(38,2),
            PRIMARY KEY (name, category)
        );

CREATE TABLE tb_order (
        order_id bigserial PRIMARY KEY not null,
        order_owner varchar(255),
        order_status varchar(100)
    );

    create table order_product (
        order_id bigint,
        product_name varchar(255),
        product_category varchar(255),
        amount integer not null,
        PRIMARY KEY (order_id, product_name, product_category)
    );

-- CATEGORIES
insert into category (active,description,name) values (true,'Categoria para produtos de cozinha', 'COZINHA');
insert into category (active,description,name) values (true,'Categoria para produtos de informática', 'INFORMATICA');
insert into category (active,description,name) values (true,'Categoria para produtos esportivos', 'ESPORTIVOS');

-- PRODUCTS
insert into product (amount,description,unitPrice,category,name) values (40,'Colher inox tramontina',20.00,'COZINHA','Colher Inox');
insert into product (amount,description,unitPrice,category,name) values (60,'Faca inox tramontina',20.00,'COZINHA','Faca Inox');
insert into product (amount,description,unitPrice,category,name) values (20,'SSD NVME 250GB',400.00,'INFORMATICA','SSD');
insert into product (amount,description,unitPrice,category,name) values (10,'MOUSE RED DRAGON',140.00,'INFORMATICA','MOUSE');
insert into product (amount,description,unitPrice,category,name) values (5,'Bola de Futebol da Copa de 1998',300.00,'ESPORTIVOS','Bola de Futebol');