DELETE FROM t_roles;

--INSERT INTO t_roles (name, role_default) VALUES ('DEVELOPER','0');
--INSERT INTO t_roles (name, role_default) VALUES ('MEMBER','1');
--INSERT INTO t_roles (name, role_default) VALUES ('INTEGRATOR','0');

INSERT INTO t_roles (name, role_default) VALUES 
('MEMBER', true),
('INTEGRATOR', false);