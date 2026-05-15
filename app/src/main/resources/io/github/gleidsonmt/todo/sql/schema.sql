CREATE TABLE list (
      id        INTEGER PRIMARY KEY,
      name      VARCHAR,
      icon_name VARCHAR,
      fixed     SMALLINT,
      size      INTEGER
);

INSERT INTO `list` (id, name, icon_name, fixed, size) VALUES (1, 'tasks',  'home', 1,0);
INSERT INTO `list` (id, name, icon_name, fixed, size) VALUES (2, 'my_day',  'sun', 1,0);
INSERT INTO `list` (id, name, icon_name, fixed, size) VALUES (3, 'important',  'star', 1,0);
INSERT INTO `list` (id, name, icon_name, fixed, size) VALUES (4, 'Dashboard',  'notebook', 0,0);
INSERT INTO `list` (id, name, icon_name, fixed, size) VALUES (5, 'To Buy',  'shopping', 0,0);
INSERT INTO `list` (id, name, icon_name, fixed, size) VALUES (6, 'Exercise',  'strong-arm', 0,0);

CREATE TABLE task (
      id INTEGER PRIMARY KEY,
      name VARCHAR,
      completed SMALLINT,
      important SMALLINT,
      my_day SMALLINT,
      due_date DATE,
      remind TEXT,
      created_at DATE,
      list_id INTEGER,
      FOREIGN KEY (list_id) REFERENCES list(id)
);