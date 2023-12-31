CREATE TABLE IF NOT EXISTS fruit_order (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    fruit_list JSONB NOT NULL DEFAULT '[]',
    total_value FLOAT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

ALTER TABLE fruit_order ADD CONSTRAINT value_amount_check CHECK (
    total_value >= 0
);
