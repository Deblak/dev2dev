DROP TABLE IF EXISTS t_articles;

CREATE TABLE t_articles(
	id INT GENERATED ALWAYS AS IDENTITY,
	url varchar(2300) NOT NULL, --The maximum length of a URL in the address bar is 2048
	title varchar(225) NOT NULL, --recommand 55-60
	description varchar(1000),		--recommand 200
	image varchar(2300),
	shared_at TIMESTAMP,
	published_date TIMESTAMP,
	author varchar(225)
	--user_id INT NOT NULL
);