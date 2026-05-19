-- ============================================================
-- Seed data from archive/handlers.ts (mock API spec)
-- ============================================================

-- Filieres
INSERT INTO filiere (id, name) VALUES ('f1', 'Génie Informatique');
INSERT INTO filiere (id, name) VALUES ('f2', 'Génie Industriel');
INSERT INTO filiere (id, name) VALUES ('f3', 'Génie Civil');
INSERT INTO filiere (id, name) VALUES ('f4', 'Génie Électrique');
INSERT INTO filiere (id, name) VALUES ('f5', 'Management');

-- Levels
INSERT INTO level (id, name) VALUES ('n1', 'Licence');
INSERT INTO level (id, name) VALUES ('n2', 'Master');
INSERT INTO level (id, name) VALUES ('n3', 'Doctorat');

-- Grades
INSERT INTO grade (id, name) VALUES ('g1', 'PES');
INSERT INTO grade (id, name) VALUES ('g2', 'PH');
INSERT INTO grade (id, name) VALUES ('g3', 'PA');

-- Departments
INSERT INTO department (id, name, code, head_id) VALUES ('1', 'Informatique', 'INFO', '4');
INSERT INTO department (id, name, code, head_id) VALUES ('2', 'Mathématiques', 'MATH', '3');
INSERT INTO department (id, name, code, head_id) VALUES ('3', 'Physique', 'PHYS', '3');

-- Users (base table)
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('User', '1', 'admin@univ.com', '1234', 'ADMIN', 'Ahmadi', 'Mohamed', true);
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('User', '2', 'coord@univ.com', '1234', 'COORDINATOR', 'Ouchen', 'Yassin', true);
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('TEACHER', '3', 'teacher@univ.com', '1234', 'TEACHER', 'Ali', 'Ben Ali', true);
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('TEACHER', '4', 'moussa@univ.com', '1234', 'TEACHER', 'Alami', 'Moussa', true);
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-demo', 'student@univ.com', '1234', 'STUDENT', 'Mohamed', 'Khalid', true);

-- Teachers (subclass table)
INSERT INTO teacher (id, grade_id, department_id) VALUES ('3', 'g1', '1');
INSERT INTO teacher (id, grade_id, department_id) VALUES ('4', 'g2', '2');

-- Demo student (subclass table)
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-demo', 'E13000999', 'f1', 'n2');

-- ============================================================
-- 50 generated students (std-1 to std-50)
-- Names cycle through 20 firstNames + 20 lastNames
-- Filieres cycle: f1..f5, Levels cycle: n1..n3
-- ============================================================
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-1', 'student1@univ.com', '1234', 'STUDENT', 'Alami', 'Amine', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-1', 'E130001', 'f1', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-2', 'student2@univ.com', '1234', 'STUDENT', 'Benali', 'Salma', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-2', 'E130002', 'f2', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-3', 'student3@univ.com', '1234', 'STUDENT', 'Fassi', 'Yassine', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-3', 'E130003', 'f3', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-4', 'student4@univ.com', '1234', 'STUDENT', 'Tazi', 'Fatima', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-4', 'E130004', 'f4', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-5', 'student5@univ.com', '1234', 'STUDENT', 'Mansouri', 'Mehdi', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-5', 'E130005', 'f5', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-6', 'student6@univ.com', '1234', 'STUDENT', 'Radi', 'Sofia', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-6', 'E130006', 'f1', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-7', 'student7@univ.com', '1234', 'STUDENT', 'Idrissi', 'Omar', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-7', 'E130007', 'f2', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-8', 'student8@univ.com', '1234', 'STUDENT', 'Bennani', 'Hajar', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-8', 'E130008', 'f3', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-9', 'student9@univ.com', '1234', 'STUDENT', 'Kettani', 'Khalid', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-9', 'E130009', 'f4', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-10', 'student10@univ.com', '1234', 'STUDENT', 'Amrani', 'Layla', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-10', 'E1300010', 'f5', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-11', 'student11@univ.com', '1234', 'STUDENT', 'Lahlou', 'Zakaria', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-11', 'E1300011', 'f1', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-12', 'student12@univ.com', '1234', 'STUDENT', 'Sekkat', 'Nadia', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-12', 'E1300012', 'f2', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-13', 'student13@univ.com', '1234', 'STUDENT', 'Guessous', 'Hamza', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-13', 'E1300013', 'f3', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-14', 'student14@univ.com', '1234', 'STUDENT', 'Filali', 'Zineb', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-14', 'E1300014', 'f4', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-15', 'student15@univ.com', '1234', 'STUDENT', 'Skalli', 'Anas', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-15', 'E1300015', 'f5', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-16', 'student16@univ.com', '1234', 'STUDENT', 'Kadiri', 'Meryem', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-16', 'E1300016', 'f1', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-17', 'student17@univ.com', '1234', 'STUDENT', 'Belkora', 'Reda', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-17', 'E1300017', 'f2', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-18', 'student18@univ.com', '1234', 'STUDENT', 'Mernissi', 'Chaimae', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-18', 'E1300018', 'f3', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-19', 'student19@univ.com', '1234', 'STUDENT', 'Berrada', 'Ayoub', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-19', 'E1300019', 'f4', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-20', 'student20@univ.com', '1234', 'STUDENT', 'El Hachimi', 'Ibtissam', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-20', 'E1300020', 'f5', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-21', 'student21@univ.com', '1234', 'STUDENT', 'Alami', 'Amine', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-21', 'E1300021', 'f1', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-22', 'student22@univ.com', '1234', 'STUDENT', 'Benali', 'Salma', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-22', 'E1300022', 'f2', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-23', 'student23@univ.com', '1234', 'STUDENT', 'Fassi', 'Yassine', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-23', 'E1300023', 'f3', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-24', 'student24@univ.com', '1234', 'STUDENT', 'Tazi', 'Fatima', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-24', 'E1300024', 'f4', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-25', 'student25@univ.com', '1234', 'STUDENT', 'Mansouri', 'Mehdi', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-25', 'E1300025', 'f5', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-26', 'student26@univ.com', '1234', 'STUDENT', 'Radi', 'Sofia', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-26', 'E1300026', 'f1', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-27', 'student27@univ.com', '1234', 'STUDENT', 'Idrissi', 'Omar', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-27', 'E1300027', 'f2', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-28', 'student28@univ.com', '1234', 'STUDENT', 'Bennani', 'Hajar', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-28', 'E1300028', 'f3', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-29', 'student29@univ.com', '1234', 'STUDENT', 'Kettani', 'Khalid', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-29', 'E1300029', 'f4', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-30', 'student30@univ.com', '1234', 'STUDENT', 'Amrani', 'Layla', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-30', 'E1300030', 'f5', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-31', 'student31@univ.com', '1234', 'STUDENT', 'Lahlou', 'Zakaria', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-31', 'E1300031', 'f1', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-32', 'student32@univ.com', '1234', 'STUDENT', 'Sekkat', 'Nadia', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-32', 'E1300032', 'f2', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-33', 'student33@univ.com', '1234', 'STUDENT', 'Guessous', 'Hamza', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-33', 'E1300033', 'f3', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-34', 'student34@univ.com', '1234', 'STUDENT', 'Filali', 'Zineb', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-34', 'E1300034', 'f4', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-35', 'student35@univ.com', '1234', 'STUDENT', 'Skalli', 'Anas', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-35', 'E1300035', 'f5', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-36', 'student36@univ.com', '1234', 'STUDENT', 'Kadiri', 'Meryem', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-36', 'E1300036', 'f1', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-37', 'student37@univ.com', '1234', 'STUDENT', 'Belkora', 'Reda', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-37', 'E1300037', 'f2', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-38', 'student38@univ.com', '1234', 'STUDENT', 'Mernissi', 'Chaimae', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-38', 'E1300038', 'f3', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-39', 'student39@univ.com', '1234', 'STUDENT', 'Berrada', 'Ayoub', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-39', 'E1300039', 'f4', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-40', 'student40@univ.com', '1234', 'STUDENT', 'El Hachimi', 'Ibtissam', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-40', 'E1300040', 'f5', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-41', 'student41@univ.com', '1234', 'STUDENT', 'Alami', 'Amine', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-41', 'E1300041', 'f1', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-42', 'student42@univ.com', '1234', 'STUDENT', 'Benali', 'Salma', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-42', 'E1300042', 'f2', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-43', 'student43@univ.com', '1234', 'STUDENT', 'Fassi', 'Yassine', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-43', 'E1300043', 'f3', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-44', 'student44@univ.com', '1234', 'STUDENT', 'Tazi', 'Fatima', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-44', 'E1300044', 'f4', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-45', 'student45@univ.com', '1234', 'STUDENT', 'Mansouri', 'Mehdi', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-45', 'E1300045', 'f5', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-46', 'student46@univ.com', '1234', 'STUDENT', 'Radi', 'Sofia', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-46', 'E1300046', 'f1', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-47', 'student47@univ.com', '1234', 'STUDENT', 'Idrissi', 'Omar', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-47', 'E1300047', 'f2', 'n2');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-48', 'student48@univ.com', '1234', 'STUDENT', 'Bennani', 'Hajar', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-48', 'E1300048', 'f3', 'n3');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-49', 'student49@univ.com', '1234', 'STUDENT', 'Kettani', 'Khalid', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-49', 'E1300049', 'f4', 'n1');
INSERT INTO users (dtype, id, email, password, role, last_name, first_name, is_active) VALUES ('STUDENT', 'std-50', 'student50@univ.com', '1234', 'STUDENT', 'Amrani', 'Layla', true);
INSERT INTO student (id, cne, filiere_id, level_id) VALUES ('std-50', 'E1300050', 'f5', 'n2');
