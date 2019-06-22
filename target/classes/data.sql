-- USER
-- non-encrypted password: jwtpass
INSERT INTO users (id, name, password, email, role) VALUES (40, 'Publisher', '$2a$10$qFyMHxCA3mgsX7YocCDT3.OPyWCY4N/Mtm8U6A.M4fwpwD/YKvc.K', 'publisher@gmail.com', 'PUBLISHER');
INSERT INTO users (id, name, password, email, role) VALUES (41, 'Adops', '$2a$10$qFyMHxCA3mgsX7YocCDT3.OPyWCY4N/Mtm8U6A.M4fwpwD/YKvc.K', 'adops@gmail.com', 'ADOPS');
INSERT INTO users (id, name, password, email, role) VALUES (42, 'Admin', '$2a$10$qFyMHxCA3mgsX7YocCDT3.OPyWCY4N/Mtm8U6A.M4fwpwD/YKvc.K', 'admin@gmail.com', 'ADMIN');

INSERT INTO app(id, name, type) VALUES (100, 'Apple TV', 'ANDROID');
INSERT INTO app_content_types(App_id, content_type) VALUES (100, 'IMAGE');
INSERT INTO app_content_types(App_id, content_type) VALUES (100, 'HTML');
