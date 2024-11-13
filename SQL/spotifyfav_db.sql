create schema IF NOT EXISTS spotyfav;

USE spotyfav;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    image_user VARCHAR(255),
    password_user VARCHAR(255) NOT NULL,
    mail_user VARCHAR(255) NOT NULL UNIQUE,
    name_user VARCHAR(255) NOT NULL
);

CREATE TABLE favorite_song(
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    song_id INT,
    song_name NVARCHAR(30),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO user (id, image_user, password_user, mail_user, name_user) VALUES
(1, 'img1.jpg', 'password1', 'user1@example.com', 'User1'),
(2, 'img2.jpg', 'password2', 'user2@example.com', 'User2'),
(3, 'img3.jpg', 'password3', 'user3@example.com', 'User3'),
(4, 'img4.jpg', 'password4', 'user4@example.com', 'User4'),
(5, 'img5.jpg', 'password5', 'user5@example.com', 'User5');


INSERT INTO favorite_song (user_id, song_id, song_name) VALUES
(1, 101, 'Song 1 by User 1'),
(1, 102, 'Song 2 by User 1'),
(1, 103, 'Song 3 by User 1'),

(2, 201, 'Song 1 by User 2'),
(2, 202, 'Song 2 by User 2'),
(2, 203, 'Song 3 by User 2'),

(3, 301, 'Song 1 by User 3'),
(3, 302, 'Song 2 by User 3'),
(3, 303, 'Song 3 by User 3'),

(4, 401, 'Song 1 by User 4'),
(4, 402, 'Song 2 by User 4'),
(4, 403, 'Song 3 by User 4'),

(5, 501, 'Song 1 by User 5'),
(5, 502, 'Song 2 by User 5'),
(5, 503, 'Song 3 by User 5');

