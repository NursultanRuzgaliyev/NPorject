CREATE TABLE courses (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         description VARCHAR(255),
                         created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Пример вставки данных в таблицу courses
INSERT INTO courses (name, description, created_time, updated_time) VALUES
                                                                        ('Java Programming', 'Comprehensive course on Java programming', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                        ('Spring Framework', 'Introduction to building web applications using Spring', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                        ('Database Management', 'Course covering relational databases and SQL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                        ('Data Structures', 'Course on implementing data structures in Java', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
