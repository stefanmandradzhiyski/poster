CREATE TABLE IF NOT EXISTS comment (
    id VARCHAR(36) NOT NULL,
    post_id VARCHAR(36) NOT NULL,
    username VARCHAR(32) NOT NULL,
    rating INT4 NOT NULL,
    content VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    CONSTRAINT comment_pk PRIMARY KEY (id),
    CONSTRAINT comment_fk_post_id FOREIGN KEY (post_id) REFERENCES post (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS comment_username_post_idx ON comment (username, post_id);
CREATE INDEX IF NOT EXISTS comment_username_idx ON comment (username);
CREATE INDEX IF NOT EXISTS comment_created_at_idx ON comment (created_at);