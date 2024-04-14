CREATE TABLE IF NOT EXISTS APP_USER (
                        id          BIGINT PRIMARY KEY AUTO_INCREMENT,
                        UUID        VARCHAR(36) NOT NULL,
                        USER_NAME   VARCHAR(25) NOT NULL,
                        EMAIL       VARCHAR(50) NOT NULL,
                        PASSWORD    VARCHAR(70) NOT NULL,
                        ROLE        ENUM('USER', 'ADMIN'),
                        is_lock      BOOLEAN DEFAULT TRUE,
                        is_enable   BOOLEAN DEFAULT FALSE
);