CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  name VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  tel VARCHAR(20) NOT NULL,
  confirmation_code VARCHAR(20)
);

CREATE TABLE reminders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  message TEXT,
  scheduled_time DATETIME NOT NULL,
  status TINYINT(1) DEFAULT 0,
  user_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users (username, name, password, tel)
VALUES ('user1', 'Hossem', 'password1', '27515642'),
       ('user2', 'slim', 'password2', '27515642');

INSERT INTO reminders (title, message, scheduled_time, status, user_id)
VALUES ('Reminder 1', 'This is reminder 1', '2023-04-25 10:00:00', 0, 1),
       ('Reminder 2', 'This is reminder 2', '2023-04-26 15:30:00', 0, 2),
       ('Reminder 3', 'This is reminder 3', '2023-04-27 08:00:00', 0, 1),
       ('Reminder 4', 'This is reminder 4', '2023-04-28 13:45:00', 0, 2);