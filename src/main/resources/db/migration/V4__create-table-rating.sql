CREATE TABLE rating (
    id BIGINT NOT NULL AUTO_INCREMENT,
    registration_id BIGINT NOT NULL,
    rating INT,
    rating_nps INT,
    feedback VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_registration
        FOREIGN KEY (registration_id)
        REFERENCES registration (id)
);