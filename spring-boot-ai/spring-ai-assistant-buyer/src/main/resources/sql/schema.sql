DROP TABLE IF EXISTS products;
CREATE TABLE IF NOT EXISTS products
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    category    VARCHAR(100) NULL,
    price       NUMERIC(10, 2) NOT NULL CHECK (price >= 0)
);