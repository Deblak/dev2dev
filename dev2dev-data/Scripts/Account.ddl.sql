-- Second execution --
DROP TABLE IF EXISTS t_accounts;

CREATE TABLE t_accounts (
	id BIGINT GENERATED ALWAYS AS IDENTITY,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(72) NOT NULL,
	email_validate BOOLEAN NOT NULL,
	id_role INT NOT NULL,
	CONSTRAINT t_id_accounts_pkey PRIMARY KEY(id), 
	CONSTRAINT t_accounts_ukey UNIQUE(username),
	CONSTRAINT fkey_role_name FOREIGN KEY(id_role) REFERENCES t_roles(id)
);