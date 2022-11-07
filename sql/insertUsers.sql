CREATE TABLE users (
user_id BIGSERIAL PRIMARY KEY,
firstname VARCHAR(128),
lastname VARCHAR(128),
email VARCHAR(128) UNIQUE NOT NULL,
user_password VARCHAR(128) NOT NULL,
user_role INTEGER,
rating NUMERIC
);

INSERT INTO users (firstname, lastname, email, user_password)
VALUES ('Vladimir', 'Ulyanov', 'bolshevik1870@mail.ussr', 'RSDRP4ever'),
        ('Bob', 'Marley', 'reggaeKing45@ja.maica', 'SunIsShining'),
        ('Tom', 'Marvolo Riddle', 'avadakedavra@hogwarts.uk', 'iAmLordVoldemort'),
        ('Adolph', 'Hitler', 'adolph89@reich.de', 'meinKampf'),
        ('Arnold', 'Schwarzenegger', 'steelMuscles@gym.com', 'IllBeBack'),
        ('Tim', 'Burton', 'tim_burton@mail.com', 'Frankenweenie'),
        ('Angelina', 'Jolie', 'angelina@hollywood.ru', 'HelpChildrenWorlwide'),
        ('Brad', 'Pitt', 'loveAngelina@ex-wife.com', 'ISecretlyStillLoveJennifer'),
        ('Marilyn', 'Monroe', 'normaBaker@yandex.by', 'pupidupu'),
        ('Marilyn', 'Manson', 'wannaBeSomebody@gmail.ru', 'sweetDreams69');

SELECT * FROM users;