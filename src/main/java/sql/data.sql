CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       phone VARCHAR(20),
                       role VARCHAR(20) NOT NULL,
                       qualification VARCHAR(100)
);

CREATE TABLE trainings (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           description TEXT,
                           schedule VARCHAR(255),
                           client_id INT NOT NULL,
                           trainer_id INT NOT NULL,
                           FOREIGN KEY (trainer_id) REFERENCES users(id) ON DELETE CASCADE,
                           FOREIGN KEY (client_id) REFERENCES  users(id) ON DELETE CASCADE
);

CREATE TABLE exercises (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           description TEXT,
                           difficulty_level VARCHAR(50) CHECK (difficulty_level IN ('Easy', 'Medium', 'Hard'))
);

CREATE TABLE training_exercises (
                                    training_id INT NOT NULL,
                                    exercise_id INT NOT NULL,
                                    PRIMARY KEY (training_id, exercise_id),
                                    FOREIGN KEY (training_id) REFERENCES trainings(id) ON DELETE CASCADE,
                                    FOREIGN KEY (exercise_id) REFERENCES exercises(id) ON DELETE CASCADE
);

CREATE TABLE feedbacks (
                           id SERIAL PRIMARY KEY,
                           user_id INT NOT NULL,
                           trainer_id INT NOT NULL,
                           rating INT CHECK (rating >= 1 AND rating <= 5),
                           comment TEXT,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                           FOREIGN KEY (trainer_id) REFERENCES users(id) ON DELETE CASCADE
);