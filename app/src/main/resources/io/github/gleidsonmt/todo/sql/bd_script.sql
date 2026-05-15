/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;


DROP DATABASE IF EXISTS `todo`;
CREATE DATABASE IF NOT EXISTS `todo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `todo`;

DROP TABLE IF EXISTS `list`;
CREATE TABLE IF NOT EXISTS `list`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `name`      varchar(50)                                                  DEFAULT NULL,
    `fixed`     tinyint(4)                                                   DEFAULT NULL,
    `icon_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `size`      int(11)                                                      DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `list` (`id`, `name`, `fixed`, `icon_name`, `size`)
VALUES (1, 'tasks', 1, 'home', 0),
       (2, 'my_day', 1, 'sun', 0),
       (3, 'important', 1, 'star', 0),
       (4, 'Dashboard', 0, 'notebook', 0),
       (5, 'To Buy', 0, 'shopping', 0),
       (6, 'Exercise', 0, 'strong-arm', 0);

-- FOREIGN KEY (list_id) REFERENCES list(id)

CREATE TABLE IF NOT EXISTS `task`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `completed`  tinyint(4)                                                   DEFAULT NULL,
    `important`  tinyint(4)                                                   DEFAULT NULL,
    `my_day`     tinyint(4)                                                   DEFAULT NULL,
    `due_date`   date                                                         DEFAULT NULL,
    `remind`     datetime                                                     DEFAULT NULL,
    `created_at` date                                                         DEFAULT NULL,
    `list_id`    int(11) NOT NULL                                             DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `FK_task_list` (`list_id`),
    CONSTRAINT `FK_task_list` FOREIGN KEY (`list_id`) REFERENCES `list` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `recurrence`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `gap`          int(11)                                                                                                                             DEFAULT NULL,
    `type`         enum ('DAILY','WEEKLY','MONTHLY','YEARLY') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                         DEFAULT 'DAILY',
    `days_of_week` set ('SUNDAY','MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','ANY') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'ANY',
    `task_id`      int(11)                                                                                                                             DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_recurrence_task` (`task_id`),
    CONSTRAINT `FK_recurrence_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

/*!40103 SET TIME_ZONE = IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES = IFNULL(@OLD_SQL_NOTES, 1) */;

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