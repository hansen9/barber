-- Customer Table
CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_no VARCHAR(20) NOT NULL,
    birthday DATE NOT NULL,
    point INTEGER NOT NULL DEFAULT 0
);

-- Treatment Table
CREATE TABLE treatment (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10,2) NOT NULL
);

-- Order Table
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customer(id),
    treatment_id BIGINT NOT NULL REFERENCES treatment(id),
    order_date TIMESTAMP NOT NULL,
    is_redeemed BOOLEAN NOT NULL DEFAULT FALSE,
    is_birthday_discount BOOLEAN NOT NULL DEFAULT FALSE
);

-- Sale Table
CREATE TABLE sale (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id),
    amount NUMERIC(10,2) NOT NULL,
    sale_date TIMESTAMP NOT NULL
);

