CREATE TABLE IF NOT EXISTS RESET_OPERATIONS (
                                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                                users BIGINT,
                                                create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                                uid VARCHAR(255),
                                                FOREIGN KEY (users) REFERENCES APP_USER(id)
);
