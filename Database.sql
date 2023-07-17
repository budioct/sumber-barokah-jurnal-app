show
    databases;

create
    database sumber_barokah_jurnal_app;

use
    sumber_barokah_jurnal_app;

show
    tables;

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

show
    tables;
describe customers;
describe suppliers;
desc categories;
desc products;

select *
from customers;
select *
from suppliers;
select *
from categories;
select *
from products;

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
    product_id          varchar(100) not null,
    create_at           timestamp,
    update_modified_at  timestamp,
    primary key (jurnal_pembelian_id),
    foreign key fk_jurnalumum_suppliers (supplier_id) REFERENCES suppliers (supplier_id),
    foreign key fk_jurnalumum_product (product_id) REFERENCES products (product_id)
) engine = InnoDB;

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

desc jurnal_pembelian;
desc jurnal_penjualan;






































