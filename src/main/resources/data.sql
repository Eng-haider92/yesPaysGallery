INSERT INTO role (role_id, role) VALUES (1,'USER'), (2,'ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO users (user_id, active, username, password, email) VALUES (1, 1, 'admin', '$2a$10$b2aSAz0Nz1X6DJRshh/8oOM1em32xSC3q1kIuhiawNqKS.uR2.nyO', 'admin@mail.com') ON CONFLICT DO NOTHING;
INSERT INTO user_role (user_id, role_id) VALUES (1, 2) ON CONFLICT DO NOTHING;
INSERT INTO category (category_id, category_name) VALUES (1, 'living thing'), (2, 'machine'), (3, 'nature') ON CONFLICT DO NOTHING;
