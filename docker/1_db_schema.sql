CREATE TABLE `users` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `username` varchar(30) DEFAULT NULL,
                         `email` varchar(255) DEFAULT NULL,
                         `password` varchar(255) DEFAULT NULL,
                         `role` varchar(30) DEFAULT NULL,
                         `first_name` varchar(255) DEFAULT NULL,
                         `last_name` varchar(255) DEFAULT NULL,
                         `city` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hibernate_sequence` (
    `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `demoapp`.`hibernate_sequence`
(`next_val`)
VALUES (7);

CREATE TABLE `friendship` (
                              `user_id_sender` int NOT NULL,
                              `user_id_receiver` int NOT NULL,
                              PRIMARY KEY (`user_id_sender`,`user_id_receiver`),
                              KEY `user_id_receiver` (`user_id_receiver`),
                              CONSTRAINT `friendship_ibfk_1` FOREIGN KEY (`user_id_sender`) REFERENCES `users` (`id`),
                              CONSTRAINT `friendship_ibfk_2` FOREIGN KEY (`user_id_receiver`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


