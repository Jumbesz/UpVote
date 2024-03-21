CREATE SCHEMA IF NOT EXISTS upvote;
SET search_path TO upvote;

CREATE
EXTENSION IF NOT EXISTS pgcrypto SCHEMA upvote;

CREATE TABLE IF NOT EXISTS profile
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    profile_role VARCHAR(50)  NOT NULL
);


CREATE TABLE IF NOT EXISTS idea
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    is_approved BOOLEAN      NOT NULL,
    rating      INTEGER      NOT NULL,
    profile_id  INTEGER REFERENCES profile (id)
);

INSERT INTO profile (username, password, profile_role)
VALUES ('admin', crypt('admin', gen_salt('bf')), 'ROLE_ADMIN'),
       ('user', crypt('user', gen_salt('bf')), 'ROLE_USER');

INSERT INTO idea (name, description, is_approved, rating, profile_id)
SELECT 'Idea ' || n || ' by User',
       'This is idea ' || n || ' created by the user.',
       n % 2 = 0, -- Alternate between true and false
       FLOOR(random() * 5) + 1,
       2
FROM generate_series(4, 23) AS n;

UPDATE profile
SET profile_role = 'ROLE_USER'
WHERE id <> (SELECT id FROM profile WHERE profile_role = 'ROLE_ADMIN' LIMIT 1);

RESET
search_path
