CREATE TABLE usuarios (
    id BIGSERIAL NOT NULL,
    login VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);
