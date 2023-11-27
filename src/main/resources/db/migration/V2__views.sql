CREATE VIEW invoice_detail_view AS
SELECT
    d.id AS detail_id,
    d.quantity,
    d.price AS detail_price,
    i.id AS invoice_id,
    i.code AS invoice_code,
    i.create_at AS invoice_create_at,
    c.full_name_client,
    p.id AS product_id,
    p.description AS product_description,
    p.brand AS product_brand,
    p.price AS product_price,
    p.stock AS product_stock
FROM detail d
    JOIN invoice i ON d.invoice_id = i.id
    JOIN client c ON i.client_id = c.id
    JOIN product p ON d.product_id = p.id;