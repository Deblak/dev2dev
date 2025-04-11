DELETE FROM t_accounts;
DELETE FROM t_roles;

INSERT INTO t_roles (name, role_default) VALUES 
('MEMBER', true),
('INTEGRATOR', false);

INSERT INTO t_accounts (username, password, email_validate, id_role)
VALUES (
  'riri@mail.com', '$2a$12$S6f2tsM9xQv51P1zwlvSDuVLuGgHj4/T4ez6RTdTbgzpB3FnQAek.', '1', 
  (SELECT id FROM t_roles WHERE name = 'MEMBER'));

INSERT INTO t_accounts (username, password, email_validate, id_role)
VALUES (
  'fifi@mail.com', '$2a$12$S6f2tsM9xQv51P1zwlvSDuVLuGgHj4/T4ez6RTdTbgzpB3FnQAek.', '1', 
  (SELECT id FROM t_roles WHERE name = 'MEMBER'));

INSERT INTO t_accounts (username, password, email_validate, id_role)
VALUES (
  'loulou@mail.com', '$2a$12$S6f2tsM9xQv51P1zwlvSDuVLuGgHj4/T4ez6RTdTbgzpB3FnQAek.', '1', 
  (SELECT id FROM t_roles WHERE name = 'INTEGRATOR'));