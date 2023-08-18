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

CREATE TABLE `users_friends` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `user_id` int NOT NULL,
                                 `friends_id` int NOT NULL,
                                 PRIMARY KEY (`id`),
                                 FOREIGN KEY (`friends_id`) REFERENCES `users` (`id`),
                                 FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `users_friends_request` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `user_id` int NOT NULL,
                                         `friends_request_id` int NOT NULL,
                                         PRIMARY KEY (`id`),
                                         FOREIGN KEY (`friends_request_id`) REFERENCES `users` (`id`),
                                         FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



