CREATE TABLE list
(
    id        INTEGER PRIMARY KEY,
    name      VARCHAR,
    icon_name VARCHAR,
    fixed     SMALLINT,
    size      INTEGER
);

INSERT INTO `list` (id, name, icon_name, fixed, size)
VALUES (1, 'tasks', 'home', 1, 0);
INSERT INTO `list` (id, name, icon_name, fixed, size)
VALUES (2, 'my_day', 'sun', 1, 0);
INSERT INTO `list` (id, name, icon_name, fixed, size)
VALUES (3, 'important', 'star', 1, 0);
INSERT INTO `list` (id, name, icon_name, fixed, size)
VALUES (4, 'Dashboard', 'notebook', 0, 0);
INSERT INTO `list` (id, name, icon_name, fixed, size)
VALUES (5, 'To Buy', 'shopping', 0, 0);
INSERT INTO `list` (id, name, icon_name, fixed, size)
VALUES (6, 'Exercise', 'strong-arm', 0, 0);

CREATE TABLE task
(
    id         INTEGER PRIMARY KEY,
    name       VARCHAR,
    completed  TINYINT DEFAULT 0,
    important  TINYINT DEFAULT 0,
    my_day     TINYINT DEFAULT 0,
    due_date   TEXT,
    remind     TEXT,
    created_at TEXT,
    list_id    INTEGER,
    FOREIGN KEY (list_id) REFERENCES list (id)
);

CREATE TABLE recurrence
(
    id           INTEGER PRIMARY KEY,
    gap          INTEGER DEFAULT 1,
    days_of_week INTEGER NOT NULL CHECK (days_of_week BETWEEN 0 AND 127),
    type         TEXT    NOT NULL CHECK (type IN ('DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY', 'WEEKDAYS', 'CUSTOM')),
    task_id      INTEGER NOT NULL,
    FOREIGN KEY (task_id) REFERENCES task (id)
);

CREATE TABLE user
(
    id       INTEGER PRIMARY KEY,
    name     VARCHAR,
    username VARCHAR UNIQUE,
    password TEXT,
    salt BLOB
);