DROP TABLE IF EXISTS t_account_notification_type;

CREATE TABLE t_account_notification_type(
	account_id bigint,
	notification_type_id bigint,
	CONSTRAINT account_notification_pkey PRIMARY KEY(account_id,notification_type_id),
	CONSTRAINT account_fkey FOREIGN KEY(account_id) REFERENCES t_accounts(id) ON DELETE CASCADE,
	CONSTRAINT notification_type_fkey FOREIGN KEY(notification_type_id) REFERENCES t_notification_types(id) ON DELETE CASCADE
);