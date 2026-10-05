USE quiz_platform_db;
INSERT INTO users(name,email,password_hash,role) VALUES
('Admin User','admin@quiz.com',BCrypt_PLACEHOLDER,'ADMIN'),
('Quiz Creator','creator@quiz.com',BCrypt_PLACEHOLDER,'CREATOR'),
('Demo Participant','participant@quiz.com',BCrypt_PLACEHOLDER,'PARTICIPANT')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO system_settings(setting_key,setting_value) VALUES
('platform_name','QuizCraft'),('default_duration','15'),('leaderboard_enabled','true')
ON DUPLICATE KEY UPDATE setting_value=VALUES(setting_value);
