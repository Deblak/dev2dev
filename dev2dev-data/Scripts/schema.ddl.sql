DROP TABLE IF EXISTS t_account_notification_type;
DROP TABLE IF EXISTS t_verification_tokens;
DROP TABLE IF EXISTS t_articles_accounts;
DROP TABLE IF EXISTS t_accounts;
DROP TABLE IF EXISTS t_roles;
DROP TABLE IF EXISTS t_notification_types;
DROP TABLE IF EXISTS t_articles;

CREATE TABLE t_roles(
	id int GENERATED ALWAYS AS IDENTITY,
	name varchar(10),
	role_default boolean,
	CONSTRAINT t_role_pkey PRIMARY KEY (id),
	CONSTRAINT t_role_name_ukey UNIQUE (name)
);

CREATE TABLE t_accounts (
	id BIGINT GENERATED ALWAYS AS IDENTITY,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(72) NOT NULL,
	email_validate BOOLEAN NOT NULL,
	uuid_token VARCHAR(255),
	expiration_token TIMESTAMP,
	id_role INT NOT NULL,
	CONSTRAINT t_id_accounts_pkey PRIMARY KEY(id), 
	CONSTRAINT t_accounts_ukey UNIQUE(username),
	CONSTRAINT fkey_role_name FOREIGN KEY(id_role) REFERENCES t_roles(id)
);

CREATE TABLE t_verification_tokens (
	id BIGINT GENERATED ALWAYS AS IDENTITY,
	id_username INT NOT NULL,
	code_pin CHAR(4),
	verification_exp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT t_verification_tokens_pkey PRIMARY KEY(id), 
	CONSTRAINT fkey_account_username FOREIGN KEY(id_username) REFERENCES t_accounts(id)
);

CREATE TABLE t_articles(
	id INT GENERATED ALWAYS AS IDENTITY,
	link varchar(2300) NOT NULL, --The maximum length of a URL in the address bar is 2048
	title varchar(225) NOT NULL, --recommand 55-60
	description TEXT,			--recommand 200
	image varchar(2300),
	published_date TIMESTAMP,
	author varchar(225)
	--user_id INT NOT NULL
);

CREATE TABLE t_notification_types(
	id bigint GENERATED ALWAYS AS IDENTITY,
	type_name varchar(50) NOT NULL,
	CONSTRAINT notification_type_pkey PRIMARY KEY(id),
	CONSTRAINT notification_type_ukey UNIQUE(type_name)
);

CREATE TABLE t_account_notification_type(
	account_id bigint,
	notification_type_id bigint,
	CONSTRAINT account_notification_pkey PRIMARY KEY(account_id,notification_type_id),
	CONSTRAINT account_fkey FOREIGN KEY(account_id) REFERENCES t_accounts(id) ON DELETE CASCADE,
	CONSTRAINT notification_type_fkey FOREIGN KEY(notification_type_id) REFERENCES t_notification_types(id) ON DELETE CASCADE
	CONSTRAINT t_articles_pkey PRIMARY KEY (id),
	CONSTRAINT t_articles_link_unique UNIQUE (link) 
);

CREATE TABLE t_articles_accounts(
		id INT GENERATED ALWAYS AS IDENTITY,
		article_id int NOT NULL,
		account_id int NOT NULL,
		shared_at TIMESTAMP NOT null,
		CONSTRAINT t_articles_accounts_articles_fkey FOREIGN KEY (article_id) REFERENCES t_articles(id),
		CONSTRAINT t_articles_accounts_accounts_fkey FOREIGN KEY (account_id) REFERENCES t_accounts(id),
		CONSTRAINT t_articles_accounts_unique UNIQUE (article_id, account_id)
);