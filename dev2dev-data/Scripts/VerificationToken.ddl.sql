DROP TABLE IF EXISTS t_verification_tokens;

CREATE TABLE t_verification_tokens (
	id BIGINT GENERATED ALWAYS AS IDENTITY,
	id_username INT NOT NULL,
	code_pin CHAR(4),
	verification_exp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT t_verification_tokens_pkey PRIMARY KEY(id), 
	CONSTRAINT fkey_account_username FOREIGN KEY(id_username) REFERENCES t_accounts(id)
);

