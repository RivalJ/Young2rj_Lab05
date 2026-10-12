CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          category_id INT NOT NULL,
                          CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id)
);