CREATE TABLE IF NOT EXISTS product (
   sku TEXT PRIMARY KEY,
   price REAL NOT NULL,
   description TEXT NOT NULL,
   category TEXT NOT NULL
);