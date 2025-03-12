CREATE TABLE IF NOT EXISTS order_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- id field with auto increment (auto generates values)
    item VARCHAR(255),  -- item field, assuming a string up to 255 characters
    quantity INT  -- quantity field, integer
);
