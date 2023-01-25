CREATE TABLE IF NOT EXISTS history (
    id VARCHAR(36) NOT NULL,
    post_id VARCHAR(36) NOT NULL,
    post_title VARCHAR(128) NOT NULL,
    username VARCHAR(32) NOT NULL,
    type VARCHAR(16) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    CONSTRAINT history_pk PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS history_username_type_created_comp_idx ON history (username, type, created_at);