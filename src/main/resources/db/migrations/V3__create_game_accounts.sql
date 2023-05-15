CREATE SEQUENCE IF NOT EXISTS gameacc_seq INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS game_accounts(
    id INT CHECK (id > 0) PRIMARY KEY NOT NULL DEFAULT NEXTVAL ('gameacc_seq'),
    region_id INT NOT NULL,
    login VARCHAR(20),
    password VARCHAR(20),
    name VARCHAR(20) NOT NULL,
    level INT,
    rank VARCHAR(20)
);