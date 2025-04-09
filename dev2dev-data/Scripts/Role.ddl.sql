-- First execution --
DROP TABLE IF EXISTS t_roles;

CREATE TABLE t_roles(
	id int GENERATED ALWAYS AS IDENTITY,
	name varchar(10),
	role_default boolean,
	CONSTRAINT t_role_pkey PRIMARY KEY (id),
	CONSTRAINT t_role_name_ukey UNIQUE (name)
);