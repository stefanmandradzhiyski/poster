CREATE TABLE IF NOT EXISTS post (
    id VARCHAR(36) NOT NULL,
    username VARCHAR(32) NOT NULL,
    title VARCHAR(128) NOT NULL,
    short_description VARCHAR(512),
    content TEXT NOT NULL,
    view_count INT4 NOT NULL DEFAULT 0,
    like_count INT4 NOT NULL DEFAULT 0,
    comment_count INT4 NOT NULL DEFAULT 0,
    total_rating_points INT4 NOT NULL DEFAULT 0,
    overall_rating DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    display BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    CONSTRAINT post_pk PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS post_username_idx ON post (username);
CREATE INDEX IF NOT EXISTS post_title_idx ON post (title);
CREATE INDEX IF NOT EXISTS post_rating_idx ON post (overall_rating);
CREATE INDEX IF NOT EXISTS post_created_at_idx ON post (created_at);