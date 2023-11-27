-- Orden de creación de tablas: client, invoice product y luego detail
-- Orden de creación de tablas: client, invoice, product y luego detail

CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    nui_client VARCHAR(10) UNIQUE,
    full_name_client VARCHAR(52),
    address_client VARCHAR(122)
);
--client se enlaza con invoice e invoice con detail
CREATE TABLE invoice (
    id SERIAL PRIMARY KEY,
    client_id INT,
    code VARCHAR(10),
    create_at VARCHAR(52),
    total FLOAT,
    FOREIGN KEY (client_id) REFERENCES client(id)
);

--product se enelaza condetail
CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    description VARCHAR(250),
    brand VARCHAR(50), -- Asumí que brand es una cadena de texto
    price FLOAT,
    stock INT
);

CREATE TABLE detail(
    id SERIAL PRIMARY KEY,
    quantity INT,
    price FLOAT,
    invoice_id INT,
    product_id INT,
    FOREIGN KEY (invoice_id) REFERENCES invoice(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);