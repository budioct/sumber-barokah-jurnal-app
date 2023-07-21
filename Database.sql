show databases;

create database sumber_barokah_jurnal_app;

use sumber_barokah_jurnal_app;

show tables;

create table customers
(
    customer_id        varchar(100) not null,
    name               varchar(100) not null,
    company            varchar(100),
    saldo              bigint,
    no_handphone       varchar(20)  not null,
    email              varchar(100) not null,
    address            varchar(255),
    create_at          timestamp,
    update_modified_at timestamp,
    primary key (customer_id)
) engine = InnoDB;

create table suppliers
(
    supplier_id        varchar(100) not null,
    name               varchar(100) not null,
    company            varchar(100),
    saldo              bigint,
    no_handphone       varchar(20)  not null,
    email              varchar(100) not null,
    address            varchar(255),
    create_at          timestamp,
    update_modified_at timestamp,
    primary key (supplier_id)
) engine = InnoDB;

create table categories
(
    category_id        varchar(100) not null,
    name               varchar(100) not null,
    create_at          timestamp,
    update_modified_at timestamp,
    primary key (category_id)
) engine = InnoDB;

create table products
(
    product_id          varchar(100) not null,
    product_code        varchar(100),
    name                varchar(100) not null,
    quantity            int,
    minimum_limit       int,
    unit                int,
    average_price       bigint,
    last_purchase_price bigint,
    purchase_price      bigint,
    selling_price       bigint,
    item_type           varchar(100),
    description         varchar(255),
    category_id         varchar(100) not null,
    create_at           timestamp,
    update_modified_at  timestamp,
    primary key (product_id),
    foreign key fk_products_categories (category_id) REFERENCES categories (category_id)
) engine = InnoDB;

alter table products modify unit varchar(100);

show tables;
describe customers;
describe suppliers;
desc categories;
desc products;

select * from customers;
select * from suppliers;
select * from categories;
select * from products;

create table jurnal_pembelian
(
    jurnal_pembelian_id varchar(100) not null,
    no_faktur           varchar(100),
    tanggal_transaksi   TIMESTAMP,
    tanggal_jatuh_tempo TIMESTAMP,
    status              varchar(100),
    sisa_tagihan        bigint,
    jumlah_total        bigint,
    no_transaksi        varchar(100),
    tags                varchar(100),
    supplier_id         varchar(100) not null,
#     product_id          varchar(100) not null,
    create_at           timestamp,
    update_modified_at  timestamp,
    primary key (jurnal_pembelian_id),
    foreign key fk_jurnalumum_suppliers (supplier_id) REFERENCES suppliers (supplier_id)
#     foreign key fk_jurnalumum_product (product_id) REFERENCES products (product_id)
) engine = InnoDB;

drop table jurnal_pembelian;

create table jurnal_pembelian_like_product
(
    jurnal_pembelian_id varchar(100) not null,
    product_id          varchar(100) not null,
    foreign key fk_jurnalpembelian_like_product (jurnal_pembelian_id) references jurnal_pembelian (jurnal_pembelian_id),
    foreign key fk_jurnalpembelian_pembelian_like_pembayaran (product_id) references products (product_id),
    primary key (jurnal_pembelian_id, product_id)
) engine InnoDB;

# catatan: constraint tidak bisa di modifikasi, jadi pertam kita harus hapus constraint, lalu tambhkan dengna constrain yang baru
alter table jurnal_pembelian_like_product
    drop constraint jurnal_pembelian_like_product_ibfk_2;

alter table jurnal_pembelian_like_product
    add constraint jurnal_pembelian_like_product_ibfk_2
        FOREIGN KEY (product_id)
            REFERENCES products (product_id)
            on delete no action on update no action;

show create table jurnal_pembelian_like_product;

select * from suppliers;
select * from jurnal_pembelian jp
                  left join suppliers s on s.supplier_id = jp.supplier_id
                    where s.name = 'cv sayuri';


create table jurnal_penjualan
(
    jurnal_penjualan_id varchar(100) not null,
    no_faktur           varchar(100),
    tanggal_transaksi   TIMESTAMP,
    tanggal_jatuh_tempo TIMESTAMP,
    status              varchar(100),
    sisa_tagihan        bigint,
    jumlah_total        bigint,
    no_transaksi        varchar(100),
    tags                varchar(100),
    customers_id        varchar(100) not null,
    product_id          varchar(100) not null,
    create_at           timestamp,
    update_modified_at  timestamp,
    primary key (jurnal_penjualan_id),
    foreign key fk_jurnalumum_customers (customers_id) REFERENCES customers (customer_id),
    foreign key fk_jurnalumum_product (product_id) REFERENCES products (product_id)
) engine = InnoDB;

select * from jurnal_penjualan jp
         left join customers c on (c.customer_id = jp.customers_id)
where c.email = 'danu@rabinza.com';
select * from customers c
         left join jurnal_penjualan jp on c.customer_id = jp.customers_id
where c.email = 'danu@rabinza.com';

create table pembayaran
(
    pembayaran_id      varchar(100) not null,
    tanggal_pembayaran timestamp,
    nominal            bigint,
    status             varchar(100),
    create_at          timestamp,
    update_modified_at timestamp,
    primary key (pembayaran_id)
) engine = InnoDB;

desc pembayaran;

create table jurnal_pembelian_like_pembayaran
(
    jurnal_pembelian_id varchar(100) not null,
    pembayaran_id       varchar(100) not null,
    foreign key fk_jurnalpembelian_like_pembayaran (jurnal_pembelian_id) references jurnal_pembelian (jurnal_pembelian_id),
    foreign key fk_jurnalpembelian_pembayaran_like_pembayaran (pembayaran_id) references pembayaran (pembayaran_id),
    primary key (jurnal_pembelian_id, pembayaran_id)
) engine InnoDB;

create table jurnal_penjualan_like_pembayaran
(
    jurnal_penjualan_id varchar(100) not null,
    pembayaran_id       varchar(100) not null,
    foreign key fk_jurnalpenjualan_like_pembayaran (jurnal_penjualan_id) references jurnal_penjualan (jurnal_penjualan_id),
    foreign key fk_jurnalpenjualan_pembayaran_like_pembayaran (pembayaran_id) references pembayaran (pembayaran_id),
    primary key (jurnal_penjualan_id, pembayaran_id)
) engine InnoDB;

show tables;
drop table pembayaran;
drop table jurnal_pembelian_like_pembayaran;
drop table jurnal_penjualan;
drop table jurnal_penjualan_like_pembayaran;

select * from jurnal_pembelian;
select * from jurnal_pembelian_like_product;
select * from products;

select * from customers order by create_at limit 0, 10;

select count(p.product_id) as count from products p;

# many to many delete from jurnal pada kunci luar tanpa merusak table refernce relasi
delete  from jurnal_pembelian where jurnal_pembelian_id in (select jurnal_pembelian_id from jurnal_pembelian_like_product where jurnal_pembelian.jurnal_pembelian_id = '9c35e3cf-cb5e-46e2-b8f2-35bbc07ef1f5');

describe jurnal_pembelian;
describe jurnal_pembelian_like_product;
describe products;

select * from jurnal_pembelian;
select * from jurnal_pembelian_like_product;
select * from products;

























