CREATE TABLE IF NOT EXISTS likes (
    id VARCHAR(36) NOT NULL,
    post_id VARCHAR(36) NOT NULL,
    username VARCHAR(32) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    CONSTRAINT likes_pk PRIMARY KEY (id),
    CONSTRAINT likes_fk_post_id FOREIGN KEY (post_id) REFERENCES post (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS comment_username_post_idx ON comment (username, post_id);
CREATE INDEX IF NOT EXISTS likes_username_idx ON likes (username);