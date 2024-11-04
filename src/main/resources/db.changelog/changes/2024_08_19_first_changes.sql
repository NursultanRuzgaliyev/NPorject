CREATE TABLE chapters (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description VARCHAR(255),
                          "order" INT NOT NULL,
                          created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO chapters (name, description, "order", created_time, updated_time) VALUES
                                                                                  ('Introduction to Java', 'Basics of Java programming and setup', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Object-Oriented Programming', 'Understanding OOP concepts like classes, objects, inheritance, and polymorphism', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Data Structures in Java', 'Overview of data structures like lists, sets, maps, and trees in Java', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Exception Handling', 'Techniques for handling exceptions and errors in Java', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Multithreading', 'Working with threads and concurrency in Java applications', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Java Streams and Collections', 'Using Java Streams API and Collections framework for data manipulation', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Spring Framework Basics', 'Introduction to the Spring framework and its core features', 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Spring Boot', 'Setting up a Spring Boot application and building RESTful APIs', 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Database Integration', 'Using JPA and Hibernate for database integration in Java projects', 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Microservices Architecture', 'Understanding microservices and implementing them using Spring Boot', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Testing in Java', 'Unit testing and integration testing using JUnit and Mockito', 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Java Networking', 'Basics of networking in Java for building client-server applications', 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Advanced Java Features', 'Using advanced features like lambda expressions and the new Date and Time API', 13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Design Patterns', 'Understanding and implementing common design patterns in Java projects', 14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                  ('Deployment', 'Deploying Java applications on servers and using Docker for containerization', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
