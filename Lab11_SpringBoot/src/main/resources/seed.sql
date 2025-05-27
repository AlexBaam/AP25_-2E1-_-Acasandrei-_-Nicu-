DROP TABLE IF EXISTS sister_cities;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS continents CASCADE;

CREATE TABLE continents (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO continents (name) VALUES
    ('Europe'),
    ('Asia'),
    ('North America'),
    ('South America'),
    ('Africa'),
    ('Australia'),
    ('Antarctica');

CREATE TABLE countries (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL,
    continent INTEGER NOT NULL,
    CONSTRAINT fk_continent FOREIGN KEY (continent) REFERENCES continents(id)
);

INSERT INTO countries (name, code, continent) VALUES
    ('Romania', 'RO', 1),
    ('Germany', 'DE', 1),
    ('Brazil', 'BR', 4),
    ('Japan', 'JP', 2);

CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country INTEGER NOT NULL,
    capital BOOLEAN,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    is_fake BOOLEAN,
    CONSTRAINT fk_country FOREIGN KEY (country) REFERENCES countries(id)
);

CREATE TABLE sister_cities (
    city1_id INTEGER NOT NULL,
    city2_id INTEGER NOT NULL,
    CONSTRAINT fk_city1 FOREIGN KEY (city1_id) REFERENCES cities(id),
    CONSTRAINT fk_city2 FOREIGN KEY (city2_id) REFERENCES cities(id)
);