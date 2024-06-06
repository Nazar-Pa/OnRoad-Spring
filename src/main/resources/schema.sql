CREATE SCHEMA IF NOT EXISTS onroad;

SET TIME ZONE 'WET';
SET TIME ZONE 'Europe/Berlin';

DROP TABLE IF EXISTS Users CASCADE;

CREATE TABLE Users (
    id bigint NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(255) DEFAULT NULL,
    phone character varying(30) DEFAULT NULL,
    enabled boolean DEFAULT FALSE,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    image_url character varying(255) DEFAULT 'https://cdn-icons-png.flaticon.com/512/149/149071.png',
    CONSTRAINT UQ_Users_Email UNIQUE (email)
);

DROP TABLE IF EXISTS Routes CASCADE;

CREATE TABLE Routes (
    id bigint NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    user_id bigint NOT NULL,
    driver_name character varying(50) NOT NULL,
    from_city character varying(20) NOT NULL,
    to_city character varying(20) NOT NULL,
    route_date timestamp without time zone NOT NULL,
    numb_of_pass smallint NOT NULL,
    price numeric NOT NULL,
    note character varying(255) DEFAULT NULL,
    car_model character varying(50) NOT NULL,
    phone character varying(30) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT users_routes_id_fk FOREIGN KEY (user_id)
            REFERENCES users(id)
               ON UPDATE CASCADE ON DELETE CASCADE NOT VALID
);