CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS locations
(
    "id"                 SERIAL PRIMARY KEY,
    "city"               varchar(10)      NOT NULL,
    "geolocation"        geography(point) NOT NULL
);
