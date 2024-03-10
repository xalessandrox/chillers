CREATE SCHEMA IF NOT EXISTS chillers;

-- SET CLIENT_ENCODING TO 'UTF8';
-- SET TIMEZONE = 'EUROPE/BERLIN';
SET TIME ZONE 'UTC';

-- SET SEARCH_PATH TO chillers;

DROP TABLE IF EXISTS games_players;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS players;


CREATE TABLE IF NOT EXISTS players
(
    id            BIGSERIAL    NOT NULL PRIMARY KEY,
    nickname      VARCHAR(100) NOT NULL,
    image_url     VARCHAR(255) DEFAULT 'https://cdn-icons-png.flaticon.com/128/149/149071.png',
    player_points INTEGER      DEFAULT 100
--     CONSTRAINT uq_players_nickname UNIQUE (nickname)
);

CREATE TABLE IF NOT EXISTS games
(
    id                BIGSERIAL PRIMARY KEY,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    finished_at       TIMESTAMP DEFAULT NULL,
    game_format       CHAR(1), -- 2v2, 3v3 etc
    game_state        CHAR(1) DEFAULT 0, -- started / finished
    outcome           CHAR(1), -- TEAM1_WINS, TEAM2_WINS, DRAW, SCRAP
    mvp               INTEGER   DEFAULT NULL,
    number_of_players CHAR(1),
    FOREIGN KEY (mvp) REFERENCES players (id)
);

CREATE TABLE IF NOT EXISTS games_players
(
    id        BIGSERIAL PRIMARY KEY,
    game_id   SERIAL  NOT NULL,
    player_id SERIAL  NOT NULL,
    team      CHAR(1) NOT NULL,
    FOREIGN KEY (game_id) REFERENCES games (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (player_id) REFERENCES players (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- DROP TABLE IF EXISTS events;
-- CREATE TABLE events
-- (
--     id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
--     type        VARCHAR(50)  NOT NULL CHECK ( type IN (
--                                                        'LOGIN_ATTEMPT',
--                                                        'LOGIN_ATTEMPT_FAILURE',
--                                                        'LOGIN_ATTEMPT_SUCCESS',
--                                                        'PROFILE_UPDATE',
--                                                        'PROFILE_PICTURE_UPDATE',
--                                                        'ROLE_UPDATE',
--                                                        'ACCOUNT_SETTINGS_UPDATE',
--                                                        'PASSWORD_UPDATE',
--                                                        'MFA_UPDATE'
--         ) ),
--     description VARCHAR(255) NOT NULL,
--     CONSTRAINT uq_events_type UNIQUE (type)
-- );
--
-- -- DROP TABLE IF EXISTS users_events;
-- CREATE TABLE users_events
-- (
--     id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
--     user_id    BIGINT UNSIGNED NOT NULL,
--     event_id   BIGINT UNSIGNED NOT NULL,
--     device     VARCHAR(100) DEFAULT NULL,
--     ip_address VARCHAR(100) DEFAULT NULL,
--     created_at DATETIME     DEFAULT CURRENT_TIMESTAMP,
--     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
--     FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE RESTRICT ON UPDATE CASCADE
-- );
--
-- -- DROP TABLE IF EXISTS account_verifications;
-- CREATE TABLE account_verifications
-- (
--     id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
--     user_id BIGINT UNSIGNED NOT NULL,
--     url     VARCHAR(255) NOT NULL,
--     -- date     DATETIME NOT NULL,
--     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
--     CONSTRAINT uq_account_verifications_user_id UNIQUE (user_id),
--     CONSTRAINT uq_account_verifications_url UNIQUE (url)
-- );
--
-- -- DROP TABLE IF EXISTS reset_password_verifications;
-- CREATE TABLE reset_password_verifications
-- (
--     id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
--     user_id         BIGINT UNSIGNED NOT NULL,
--     url             VARCHAR(255) NOT NULL,
--     expiration_date DATETIME     NOT NULL,
--     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
--     CONSTRAINT uq_reset_password_verifications_user_id UNIQUE (user_id),
--     CONSTRAINT uq_reset_password_verifications_url UNIQUE (url)
-- );
--
-- -- DROP TABLE IF EXISTS two_factors_verifications;
-- CREATE TABLE two_factors_verifications
-- (
--     id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
--     user_id         BIGINT UNSIGNED NOT NULL,
--     code            VARCHAR(10) NOT NULL,
--     expiration_date DATETIME    NOT NULL,
--     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
--     CONSTRAINT uq_two_factors_verifications_user_id UNIQUE (user_id),
--     CONSTRAINT uq_two_factors_verifications_code UNIQUE (code)
-- );

-- This inserts the events
--
-- INSERT INTO events (type, description)
-- VALUES ('LOGIN_ATTEMPT', 'You tried to login'),
--        ('LOGIN_ATTEMPT_FAILURE', 'Failed attempt to login'),
--        ('LOGIN_ATTEMPT_SUCCESS', 'Successful attempt to login'),
--        ('PROFILE_UPDATE', 'Profile informations updated'),
--        ('PROFILE_PICTURE_UPDATE', 'Profile picture updated'),
--        ('ROLE_UPDATE', 'Role and permissions updated'),
--        ('ACCOUNT_SETTINGS_UPDATE', 'Account setting updated'),
--        ('MFA_UPDATE', 'Multi-Factor Authentication updated'),
--        ('PASSWORD_UPDATE', 'Password updated');
