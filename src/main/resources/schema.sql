--
-- Table structure for table `scores`, for AI application ranking
--

DROP TABLE IF EXISTS `scores`;
CREATE TABLE `scores` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `application_id` int DEFAULT NULL,
                          `requirement_fit_score` int NOT NULL DEFAULT 0,
                          `qualifications_score` int NOT NULL DEFAULT 0,
                          `soft_skills_score` int NOT NULL DEFAULT 0,
                          `clarity_score` int NOT NULL DEFAULT 0,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
CREATE TABLE `application` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `user_id` int DEFAULT NULL,
                               `job_id` int DEFAULT NULL,
                               `date_applied` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                               `cover_letter` text,
                               `custom_resume` text,
                               `application_status` varchar(45) DEFAULT NULL,
                                `candidate_id` int NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
                       `id` int NOT NULL AUTO_INCREMENT,
                       `manager_id` int DEFAULT NULL,
                       `listing_title` varchar(100) DEFAULT NULL,
                       `date_listed` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                       `date_closed` timestamp NULL DEFAULT NULL,
                       `job_title` varchar(45) DEFAULT NULL,
                       `job_description` text,
                       `additional_information` text,
                       `listing_status` varchar(25) DEFAULT NULL,
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `user_id` int DEFAULT NULL,
                           `full_name` varchar(50) DEFAULT NULL,
                           `email` varchar(50) DEFAULT NULL,
                           `department` varchar(25) DEFAULT NULL,
                           `phone` varchar(25) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `candidate`
--

DROP TABLE IF EXISTS `candidate`;
CREATE TABLE `candidate` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `user_id` int DEFAULT NULL,
                             `full_name` varchar(50) DEFAULT NULL,
                             `email` varchar(50) DEFAULT NULL,
                             `address` varchar(100) DEFAULT NULL,
                             `phone` varchar(25) DEFAULT NULL,
                            `first_application_date` DATETIME DEFAULT NULL,
                             `resume` text,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `username` varchar(20) NOT NULL UNIQUE,
                        `password` varchar(20) DEFAULT NULL,
                        `type` ENUM('USER', 'CANDIDATE', 'MANAGER', 'ADMIN') DEFAULT 'USER',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;