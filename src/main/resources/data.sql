-- Insertion des utilisateurs
INSERT INTO `user` (username, password, type) VALUES
('johndoe', 'password123', 'CANDIDATE'),
('janedoe', 'password456', 'MANAGER'),
('adminuser', 'adminpass', 'ADMIN'),
('alice', 'alicepass', 'CANDIDATE'),
('bob', 'bobpass', 'CANDIDATE');

-- Insertion des candidats
INSERT INTO `candidate` (user_id, full_name, email, address, phone, resume, first_application_date) VALUES
(1, 'John Doe', 'johndoe@example.com', '123 Main St, Cityville', '555-1234', 'John\'s resume content here.', NOW()),
(4, 'Alice Smith', 'alice@example.com', '456 Maple St, Townsville', '555-5678', 'Alice\'s resume content here.', NOW()),
(5, 'Bob Johnson', 'bob@example.com', '789 Oak St, Villageville', '555-9101', 'Bob\'s resume content here.', NOW());

-- Insertion des managers
INSERT INTO `manager` (user_id, full_name, email, department, phone) VALUES
(2, 'Jane Doe', 'janedoe@example.com', 'Human Resources', '555-2222'),
(3, 'Bob Smith', 'bobsmith@example.com', 'Engineering', '555-1234');

-- Insertion des applications
INSERT INTO `application` (user_id, job_id, date_applied, cover_letter, custom_resume, application_status, candidate_id) VALUES
(1, 1, NOW(), 'I am very interested in this position.', 'Custom resume content for John.', 'submitted', 1),
(4, 1, NOW(), 'I believe I am a great fit for this job.', 'Custom resume content for Alice.', 'submitted', 2),
(5, 2, NOW(), 'Looking forward to the opportunity.', 'Custom resume content for Bob.', 'in_review', 3);

-- Insertion des emplois (si nécessaire)
INSERT INTO `job` (manager_id, listing_title, date_listed, date_closed, job_title, job_description, additional_information, listing_status) VALUES
(1, 'HR Specialist', '2024-02-01T11:00:00.000+00:00', '2024-02-01T12:00:00.000+00:00', 'Human Resources Specialist', 'Support HR functions, including recruitment, onboarding, and compliance with labor laws, while fostering a positive workplace culture.', 'Bachelor''s degree in HR or related field preferred; strong communication skills and knowledge of employment laws required.', 'Open'),
(2, 'Software Engineer', '2024-02-01T11:00:00.000+00:00', '2024-02-01T12:00:00.000+00:00', 'Software Engineer', 'Responsible for developing full-stack applications.', 'Must have experience with Java and Angular.', 'Closed'),
(1, 'Recruitment Coordinator', '2024-02-10T09:00:00.000+00:00', '2024-02-10T17:00:00.000+00:00', 'Recruitment Specialist', 'Oversee the recruitment process and manage applicant tracking.', 'Experience with ATS systems is a plus.', 'Open'),
(1, 'HR Generalist', '2024-03-01T10:00:00.000+00:00', '2024-03-01T18:00:00.000+00:00', 'HR Generalist', 'Support HR functions including employee relations and compliance.', 'Must be familiar with labor laws.', 'Open'),
(2, 'Backend Developer', '2024-02-15T11:00:00.000+00:00', '2024-02-15T15:00:00.000+00:00', 'Backend Engineer', 'Develop and maintain server-side applications and databases.', 'Experience with Node.js and SQL required.', 'Open');
