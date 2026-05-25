-- ============================================================
-- Seed data from archive/handlers.ts (mock API spec)
-- ============================================================

-- Majors
INSERT INTO major (id, name) VALUES ('m1', 'Génie Informatique');
INSERT INTO major (id, name) VALUES ('m2', 'Génie Industriel');
INSERT INTO major (id, name) VALUES ('m3', 'Génie Civil');
INSERT INTO major (id, name) VALUES ('m4', 'Génie Électrique');
INSERT INTO major (id, name) VALUES ('m5', 'Management');

-- Levels
INSERT INTO level (id, name) VALUES ('n1', 'Licence');
INSERT INTO level (id, name) VALUES ('n2', 'Master');
INSERT INTO level (id, name) VALUES ('n3', 'Doctorat');

-- Grades
INSERT INTO grade (id, name) VALUES ('g1', 'PES');
INSERT INTO grade (id, name) VALUES ('g2', 'PH');
INSERT INTO grade (id, name) VALUES ('g3', 'PA');

-- Departments (head_id set after teachers exist to avoid circular FK)
INSERT INTO department (id, name, code) VALUES ('1', 'Informatique', 'INFO');
INSERT INTO department (id, name, code) VALUES ('2', 'Mathématiques', 'MATH');
INSERT INTO department (id, name, code) VALUES ('3', 'Physique', 'PHYS');

-- Users (base table) — passwords are BCrypt-hashed '1234'
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('User', '1', 'admin@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'ADMIN', 'Ahmadi', 'Mohamed', true, null);
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('COORDINATOR', '2', 'coord@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'COORDINATOR', 'Ouchen', 'Yassin', true, null);
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('TEACHER', '3', 'teacher@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'TEACHER', 'Ali', 'Ben Ali', true, null);
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('TEACHER', '4', 'moussa@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'TEACHER', 'Alami', 'Moussa', true, null);
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-demo', 'student@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Mohamed', 'Khalid', true, null);

-- Coordinator subclass table
INSERT INTO coordinator (id) VALUES ('2');

-- Teachers (subclass table)
INSERT INTO teacher (id, grade_id, department_id) VALUES ('3', 'g1', '1');
INSERT INTO teacher (id, grade_id, department_id) VALUES ('4', 'g2', '2');

-- Set department heads (circular FK resolved by separate update)
UPDATE department SET head_id = '4' WHERE id = '1';
UPDATE department SET head_id = '3' WHERE id = '2';
UPDATE department SET head_id = '3' WHERE id = '3';

-- Demo student (subclass table)
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-demo', 'E13000999', 'm1', 'n2');

-- Sessions
INSERT INTO global_session (id, name, type, status, start_date, end_date) VALUES ('s1', 'Session Printemps 2026', 'NORMALE', 'ACTIVE', '2026-02-01', '2026-07-15');

-- Jury Role Templates
INSERT INTO jury_role_template (id, name, defense_type) VALUES ('jrt1', 'Template standard', 'PFE');
INSERT INTO jury_role_template_roles (jury_role_template_id, name, count, coefficient)
VALUES ('jrt1', 'Président', 1, 30),
       ('jrt1', 'Rapporteur', 1, 35),
       ('jrt1', 'Examinateur', 1, 35);

-- Defense Sessions
INSERT INTO defense_session (id, name, defense_type, status, max_group_size, defense_duration, break_duration, submission_deadline, global_session_id, jury_role_template_id, start_date, end_date) VALUES ('ds1', 'Soutenance PFE 2026', 'PFE', 'DRAFT', 3, 30, 10, '2026-06-01', 's1', 'jrt1', '2026-06-15', '2026-07-10');
INSERT INTO defense_session_coefficients (defense_session_id, role_name, coefficient) VALUES ('ds1', 'Président', 30), ('ds1', 'Rapporteur', 35), ('ds1', 'Examinateur', 35);

-- Defense Settings
INSERT INTO defense_settings (id, start_time, end_time, defense_duration, break_duration, group_creation_start_date, group_creation_end_date) VALUES ('default', '08:00', '18:00', 30, 15, '2026-03-01', '2026-05-01');

-- General Settings
INSERT INTO general_settings (id, institution_name, institution_logo_url, timezone, date_format, setup_completed) VALUES ('default', 'Université Hassan II', '', 'Africa/Casablanca', 'DD/MM/YYYY', false);

-- Defense Type Config
INSERT INTO defense_type_config (id, enabled, label, label_plural, default_duration, default_break) VALUES ('pfe', true, 'Projet de Fin d''Études', 'PFE', 30, 15);
INSERT INTO defense_type_config (id, enabled, label, label_plural, default_duration, default_break) VALUES ('memoire', true, 'Mémoire', 'Mémoires', 45, 15);
INSERT INTO defense_type_config (id, enabled, label, label_plural, default_duration, default_break) VALUES ('these', true, 'Thèse', 'Thèses', 60, 20);

-- Document Config
INSERT INTO document_config (id, max_file_size_mb, allowed_extensions, version_limit) VALUES ('default', 10, 'pdf,doc,docx', 5);

-- Email Config
INSERT INTO email_config (id, host, port, username, password, sender_name, sender_email, encryption) VALUES ('default', '', 587, '', '', 'Soutenance Université', 'noreply@soutenance-univ.ma', 'tls');

-- ============================================================
-- 50 generated students (std-1 to std-50)
-- Names cycle through 20 firstNames + 20 lastNames
-- Majors cycle: m1..m5, Levels cycle: n1..n3
-- ============================================================
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-1', 'student1@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Alami', 'Amine', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-1', 'E130001', 'm1', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-2', 'student2@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Benali', 'Salma', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-2', 'E130002', 'm2', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-3', 'student3@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Fassi', 'Yassine', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-3', 'E130003', 'm3', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-4', 'student4@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Tazi', 'Fatima', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-4', 'E130004', 'm4', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-5', 'student5@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Mansouri', 'Mehdi', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-5', 'E130005', 'm5', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-6', 'student6@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Radi', 'Sofia', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-6', 'E130006', 'm1', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-7', 'student7@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Idrissi', 'Omar', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-7', 'E130007', 'm2', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-8', 'student8@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Bennani', 'Hajar', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-8', 'E130008', 'm3', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-9', 'student9@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Kettani', 'Khalid', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-9', 'E130009', 'm4', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-10', 'student10@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Amrani', 'Layla', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-10', 'E1300010', 'm5', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-11', 'student11@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Lahlou', 'Zakaria', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-11', 'E1300011', 'm1', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-12', 'student12@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Sekkat', 'Nadia', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-12', 'E1300012', 'm2', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-13', 'student13@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Guessous', 'Hamza', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-13', 'E1300013', 'm3', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-14', 'student14@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Filali', 'Zineb', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-14', 'E1300014', 'm4', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-15', 'student15@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Skalli', 'Anas', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-15', 'E1300015', 'm5', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-16', 'student16@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Kadiri', 'Meryem', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-16', 'E1300016', 'm1', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-17', 'student17@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Belkora', 'Reda', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-17', 'E1300017', 'm2', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-18', 'student18@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Mernissi', 'Chaimae', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-18', 'E1300018', 'm3', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-19', 'student19@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Berrada', 'Ayoub', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-19', 'E1300019', 'm4', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-20', 'student20@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'El Hachimi', 'Ibtissam', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-20', 'E1300020', 'm5', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-21', 'student21@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Alami', 'Amine', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-21', 'E1300021', 'm1', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-22', 'student22@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Benali', 'Salma', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-22', 'E1300022', 'm2', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-23', 'student23@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Fassi', 'Yassine', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-23', 'E1300023', 'm3', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-24', 'student24@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Tazi', 'Fatima', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-24', 'E1300024', 'm4', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-25', 'student25@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Mansouri', 'Mehdi', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-25', 'E1300025', 'm5', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-26', 'student26@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Radi', 'Sofia', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-26', 'E1300026', 'm1', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-27', 'student27@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Idrissi', 'Omar', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-27', 'E1300027', 'm2', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-28', 'student28@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Bennani', 'Hajar', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-28', 'E1300028', 'm3', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-29', 'student29@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Kettani', 'Khalid', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-29', 'E1300029', 'm4', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-30', 'student30@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Amrani', 'Layla', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-30', 'E1300030', 'm5', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-31', 'student31@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Lahlou', 'Zakaria', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-31', 'E1300031', 'm1', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-32', 'student32@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Benali', 'Salma', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-32', 'E1300032', 'm2', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-33', 'student33@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Fassi', 'Yassine', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-33', 'E1300033', 'm3', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-34', 'student34@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Tazi', 'Fatima', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-34', 'E1300034', 'm4', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-35', 'student35@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Skalli', 'Anas', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-35', 'E1300035', 'm5', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-36', 'student36@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Kadiri', 'Meryem', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-36', 'E1300036', 'm1', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-37', 'student37@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Belkora', 'Reda', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-37', 'E1300037', 'm2', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-38', 'student38@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Mernissi', 'Chaimae', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-38', 'E1300038', 'm3', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-39', 'student39@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Berrada', 'Ayoub', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-39', 'E1300039', 'm4', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-40', 'student40@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'El Hachimi', 'Ibtissam', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-40', 'E1300040', 'm5', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-41', 'student41@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Alami', 'Amine', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-41', 'E1300041', 'm1', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-42', 'student42@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Benali', 'Salma', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-42', 'E1300042', 'm2', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-43', 'student43@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Fassi', 'Yassine', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-43', 'E1300043', 'm3', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-44', 'student44@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Tazi', 'Fatima', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-44', 'E1300044', 'm4', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-45', 'student45@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Mansouri', 'Mehdi', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-45', 'E1300045', 'm5', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-46', 'student46@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Radi', 'Sofia', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-46', 'E1300046', 'm1', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-47', 'student47@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Idrissi', 'Omar', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-47', 'E1300047', 'm2', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-48', 'student48@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Bennani', 'Hajar', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-48', 'E1300048', 'm3', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-49', 'student49@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Kettani', 'Khalid', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-49', 'E1300049', 'm4', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active, verification_token) VALUES ('STUDENT', 'std-50', 'student50@univ.com', '$2b$10$AY8qLutbqcPipSj1wCrt8.kqsfsezlmzkPa3YXE5Y7blECJqNj0ei', 'STUDENT', 'Amrani', 'Layla', true, null);
INSERT INTO student (id, cne, major_id, level_id) VALUES ('std-50', 'E1300050', 'm5', 'n2');
