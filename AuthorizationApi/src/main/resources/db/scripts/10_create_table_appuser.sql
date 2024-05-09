CREATE TABLE IF NOT EXISTS APP_USER (
                        id          BIGINT PRIMARY KEY AUTO_INCREMENT,
                        uuid        VARCHAR(36) NOT NULL,
                        login   VARCHAR(25) NOT NULL,
                        email       VARCHAR(50) NOT NULL,
                        password    VARCHAR(70) NOT NULL,
                        role        ENUM('USER', 'ADMIN'),
                        is_lock      BOOLEAN DEFAULT TRUE,
                        is_enable   BOOLEAN DEFAULT FALSE
);