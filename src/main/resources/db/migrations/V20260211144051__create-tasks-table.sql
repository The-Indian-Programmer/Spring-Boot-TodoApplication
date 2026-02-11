-- Migration: create-tasks-table

CREATE table tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,

    title TEXT NOT NULL,
    description LONGTEXT NOT NULL,

    status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED')
        NOT NULL DEFAULT 'PENDING',
    due_date TIMESTAMP NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_tasks_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE

);

CREATE INDEX idx_tasks_user_id ON tasks(user_id);
CREATE INDEX idx_tasks_status ON tasks(status);