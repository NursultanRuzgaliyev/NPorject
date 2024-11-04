CREATE TABLE lessons (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         description VARCHAR(255),
                         content TEXT,
                         "order" INT NOT NULL,
                         created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Пример вставки данных в таблицу lessons
INSERT INTO lessons (name, description, content, "order", created_time, updated_time) VALUES
                                                                                          ('Introduction to Java', 'An overview of Java programming', 'Content about Java basics', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                          ('OOP Concepts', 'Explanation of Object-Oriented Programming principles', 'Content about OOP in Java', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                          ('Exception Handling', 'Techniques to handle exceptions', 'Content about try-catch in Java', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
