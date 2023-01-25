CREATE TABLE IF NOT EXISTS "user" (
    id VARCHAR(36) NOT NULL,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(512) NOT NULL,
    visible_post_count INT4 NOT NULL DEFAULT 0,
    total_post_count INT4 NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS user_username_idx ON "user" (username);
CREATE INDEX IF NOT EXISTS user_password_idx ON "user" (password);