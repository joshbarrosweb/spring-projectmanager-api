CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE project_user (
    user_id INT REFERENCES users(id),
    project_id INT REFERENCES projects(id),
    PRIMARY KEY(user_id, project_id)
);

INSERT INTO users (name, email, password)
VALUES
    ('Cloud Strife', 'cloud@example.com', 'password1'),
    ('Tifa Lockhart', 'tifa@example.com', 'password2'),
    ('Aerith Gainsborough', 'aerith@example.com', 'password3'),
    ('Barret Wallace', 'barret@example.com', 'password4'),
    ('Sephiroth', 'sephiroth@example.com', 'password5'),
    ('Yuffie Kisaragi', 'yuffie@example.com', 'password6'),
    ('Vincent Valentine', 'vincent@example.com', 'password7'),
    ('Cid Highwind', 'cid@example.com', 'password8'),
    ('Red XIII', 'red@example.com', 'password9'),
    ('Cait Sith', 'cait@example.com', 'password10');

INSERT INTO projects (name, description)
VALUES
    ('Call of Duty: Modern Warfare', 'Description for Call of Duty: Modern Warfare'),
    ('Call of Duty: Black Ops Cold War', 'Description for Call of Duty: Black Ops Cold War'),
    ('Call of Duty: Warzone', 'Description for Call of Duty: Warzone'),
    ('Call of Duty: WWII', 'Description for Call of Duty: WWII'),
    ('Call of Duty 4: Modern Warfare', 'Description for Call of Duty 4: Modern Warfare'),
    ('Call of Duty: Black Ops 4', 'Description for Call of Duty: Black Ops 4'),
    ('Call of Duty: Advanced Warfare', 'Description for Call of Duty: Advanced Warfare'),
    ('Call of Duty: Ghosts', 'Description for Call of Duty: Ghosts'),
    ('Call of Duty: Black Ops III', 'Description for Call of Duty: Black Ops III'),
    ('Call of Duty: Infinite Warfare', 'Description for Call of Duty: Infinite Warfare'),
    ('Call of Duty: Modern Warfare 2', 'Description for Call of Duty: Modern Warfare 2'),
    ('Call of Duty: Black Ops II', 'Description for Call of Duty: Black Ops II'),
    ('Call of Duty: Modern Warfare 3', 'Description for Call of Duty: Modern Warfare 3'),
    ('Call of Duty: Black Ops', 'Description for Call of Duty: Black Ops'),
    ('Call of Duty: Modern Warfare Remastered', 'Description for Call of Duty: Modern Warfare Remastered'),
    ('Call of Duty: World at War', 'Description for Call of Duty: World at War'),
    ('Call of Duty 2', 'Description for Call of Duty 2'),
    ('Call of Duty', 'Description for Call of Duty'),
    ('Call of Duty: United Offensive', 'Description for Call of Duty: United Offensive'),
    ('Call of Duty: Finest Hour', 'Description for Call of Duty: Finest Hour'),
    ('Call of Duty 2: Big Red One', 'Description for Call of Duty 2: Big Red One'),
    ('Call of Duty 3', 'Description for Call of Duty 3'),
    ('Call of Duty: Roads to Victory', 'Description for Call of Duty: Roads to Victory'),
    ('Call of Duty: World at War – Final Fronts', 'Description for Call of Duty: World at War – Final Fronts'),
    ('Call of Duty: Modern Warfare: Mobilized', 'Description for Call of Duty: Modern Warfare: Mobilized'),
    ('Call of Duty: Black Ops: Declassified', 'Description for Call of Duty: Black Ops: Declassified'),
    ('Call of Duty: Strike Team', 'Description for Call of Duty: Strike Team'),
    ('Call of Duty: Heroes', 'Description for Call of Duty: Heroes'),
    ('Call of Duty: Online', 'Description for Call of Duty: Online'),
    ('Call of Duty: Mobile', 'Description for Call of Duty: Mobile');

INSERT INTO project_user (user_id, project_id)
VALUES
    (1, 1), (1, 2),
    (2, 3), (2, 4),
    (3, 5), (3, 6),
    (4, 7), (4, 8),
    (5, 9), (5, 10),
    (6, 11), (6, 12),
    (7, 13), (7, 14),
    (8, 15), (8, 16),
    (9, 17), (9, 18),
    (10, 19), (10, 20);
