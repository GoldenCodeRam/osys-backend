CREATE TABLE IF NOT EXISTS fruit (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    type VARCHAR(255) NOT NULL UNIQUE,
    value FLOAT NOT NULL DEFAULT 0,
    stock INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

ALTER TABLE fruit ADD CONSTRAINT stock_amount_check CHECK (
    stock >= 0
);