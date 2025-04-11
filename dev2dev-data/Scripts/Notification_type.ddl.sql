DROP TABLE IF EXISTS t_notification_types;

CREATE TABLE t_notification_types(
	id bigint GENERATED ALWAYS AS IDENTITY,
	type_name varchar(50) NOT NULL,
	CONSTRAINT notification_type_pkey PRIMARY KEY(id),
	CONSTRAINT notification_type_ukey UNIQUE(type_name)
);