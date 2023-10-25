use aaron_closet_shop;

-- Insert Sample Data into Products Table
INSERT INTO products (name, description, price, stock_quantity, image_url, manufacturer)
VALUES
    ('Product A', 'Description of Product A', 49.99, 100, 'product_a.jpg', 'Manufacturer X'),
    ('Product B', 'Description of Product B', 29.99, 50, 'product_b.jpg', 'Manufacturer Y'),
    ('Product C', 'Description of Product C', 99.99, 75, 'product_c.jpg', 'Manufacturer Z');

-- Insert Sample Data into Customers Table
INSERT INTO customers (first_name, last_name, email, password, shipping_address, billing_address, phone_number)
VALUES
    ('John', 'Doe', 'john@example.com', 'hashed_password', '123 Main St, City', '123 Billing St, City', '123-456-7890'),
    ('Jane', 'Smith', 'jane@example.com', 'hashed_password', '456 Elm St, Town', '456 Billing St, Town', '987-654-3210');

-- Insert Sample Data into Categories Table
INSERT INTO categories (category_name, description)
VALUES
    ('Electronics', 'Category for electronic products'),
    ('Clothing', 'Category for clothing and apparel'),
    ('Home & Garden', 'Category for home and garden products');

-- Insert Sample Data into Shipping Methods Table
INSERT INTO shipping_methods (name, description, cost)
VALUES
    ('Standard Shipping', 'Delivery within 5-7 business days', 9.99),
    ('Express Shipping', 'Next-day delivery', 19.99);

-- Insert Sample Data into Payment Methods Table
INSERT INTO payment_methods (name, description)
VALUES
    ('Credit Card', 'Payment using credit card'),
    ('PayPal', 'Payment using PayPal account');


-- Insert Sample Data into Orders Table
INSERT INTO orders (customer_id, order_date, order_status, total_amount, shipping_method, payment_method)
VALUES
    (1, '2023-10-15', 'Pending', 49.99, 'Standard Shipping', 'Credit Card'),
    (2, '2023-10-16', 'Processing', 79.98, 'Express Shipping', 'PayPal');

-- Insert Sample Data into Order Items Table
INSERT INTO order_items (order_id, product_id, quantity, subtotal)
VALUES
    (1, 1, 2, 99.98),  -- Order 1 includes 2 units of Product 1
    (1, 3, 1, 49.99),  -- Order 1 includes 1 unit of Product 3
    (2, 2, 3, 89.97);  -- Order 2 includes 3 units of Product 2


-- Insert Sample Data into Cart Table
INSERT INTO cart (customer_id, product_id, quantity)
VALUES
    (1, 2, 1),  -- Customer 1 adds 1 unit of Product 2 to their cart
    (2, 1, 3);  -- Customer 2 adds 3 units of Product 1 to their cart

-- Insert Sample Data into Cart Table
INSERT INTO cart (customer_id, product_id, quantity)
VALUES
    (1, 2, 1),  -- Customer 1 adds 1 unit of Product 2 to their cart
    (2, 1, 3);  -- Customer 2 adds 3 units of Product 1 to their cart

-- Insert Sample Data into Reviews and Ratings Table
INSERT INTO reviews_ratings (product_id, customer_id, rating, review_text)
VALUES
    (1, 1, 5, 'Great product! I''m very satisfied with it.'),
    (2, 2, 4, 'Good quality and fast delivery.'),
    (3, 1, 3, 'Decent product, but could be better.');


-- Insert Sample Data into Images Table
INSERT INTO images (product_id, image_url, caption)
VALUES
    (1, 'product_image1.jpg', 'Front view of the product'),
    (1, 'product_image2.jpg', 'Back view of the product'),
    (2, 'product_image3.jpg', 'Product image from a different angle');
    
insert into app_role (`name`) values
    ('USER'),
    ('ADMIN');

-- passwords are set to "P@ssw0rd!"
insert into app_user (username, password_hash, enabled)
    values
    ('sharoonjamal@gmail.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

insert into app_user_role
    values
    (3, 2);
