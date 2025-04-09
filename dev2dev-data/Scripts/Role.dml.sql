DELETE FROM t_roles;

INSERT INTO t_roles (name, role_default) VALUES ('Developer','1');
INSERT INTO t_roles (name, role_default) VALUES ('Member','1');
INSERT INTO t_roles (name, role_default) VALUES ('Integrator','1');

INSERT INTO t_roles (name, role_default) VALUES 
('MEMBER', true),
('INTEGRATOR', false);