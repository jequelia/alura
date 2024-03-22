CREATE TABLE course (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(10) NOT NULL UNIQUE,
    instructor_id BIGINT NOT NULL,
    description TEXT,
    status TINYINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    deactivated_at DATETIME,
    FOREIGN KEY (instructor_id) REFERENCES users(id)
);