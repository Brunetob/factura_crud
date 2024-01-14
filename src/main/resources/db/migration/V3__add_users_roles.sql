CREATE TABLE IF NOT EXISTS users (
    id SERIAL,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(200) NOT NULL,
    email VARCHAR(50) NOT NULL,
    locked BOOLEAN NOT NULL,
    disabled BOOLEAN NOT NULL,
    UNIQUE (username),
    UNIQUE (email),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS role (
    id SERIAL,
    role VARCHAR(25) NOT NULL,
    user_id INTEGER NOT NULL,
    UNIQUE (role, user_id),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);