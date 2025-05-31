DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS borders;
DROP TABLE IF EXISTS sister_cities;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS continents CASCADE;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

INSERT INTO users (username, password) VALUES
    ('Alex', '0307'),
    ('Nicu', '0307'),
    ('Geo', '0404');

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
    code VARCHAR(255) NOT NULL UNIQUE,
    continent INTEGER NOT NULL,
    CONSTRAINT fk_continent FOREIGN KEY (continent) REFERENCES continents(id)
);

INSERT INTO countries (name, code, continent) VALUES
    ('Romania', 'RO', 1),         -- id = 1
    ('Germany', 'DE', 1),         -- id = 2
    ('Japan', 'JP', 2),           -- id = 3
    ('Brazil', 'BR', 4),          -- id = 4
    ('USA', 'US', 3),             -- id = 5
    ('France', 'FR', 1),          -- id = 6
    ('UK', 'GB', 1),              -- id = 7
    ('Canada', 'CA', 3),          -- id = 8
    ('Australia', 'AU', 6),       -- id = 9
    ('Egypt', 'EG', 5),           -- id = 10
    ('Hungary', 'HU', 1),
    ('Ukraine', 'UA', 1),
    ('Bulgaria', 'BG', 1),
    ('Serbia', 'RS', 1),
    ('Moldova', 'MD', 1),
    ('Greece', 'GR', 1),
    ('Poland', 'PL', 1),
    ('Austria', 'AT', 1),
    ('Slovakia', 'SK', 1),
    ('Czechia', 'CZ', 1),
    ('Italy', 'IT', 1),
    ('Spain', 'ES', 1),
    ('Portugal', 'PT', 1),
    ('Netherlands', 'NL', 1),
    ('Belgium', 'BE', 1),
    ('Switzerland', 'CH', 1),
    ('Norway', 'NO', 1),
    ('Sweden', 'SE', 1),
    ('Finland', 'FI', 1),
    ('Denmark', 'DK', 1),
    ('Croatia', 'HR', 1),
    ('Slovenia', 'SI', 1),
    ('Bosnia and Herzegovina', 'BA', 1),
    ('Albania', 'AL', 1),
    ('North Macedonia', 'MK', 1);

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

CREATE TABLE borders (
    id SERIAL PRIMARY KEY,
    country_code VARCHAR(3),
    neighbor_code VARCHAR(3)
);

INSERT INTO cities (name, country, capital, latitude, longitude, is_fake) VALUES
    ('Bucharest', 1, true, 44.4268, 26.1025, false),
    ('Berlin', 2, true, 52.5200, 13.4050, false),
    ('Tokyo', 3, true, 35.6895, 139.6917, false),
    ('Brasília', 4, true, -15.8267, -47.9218, false),
    ('Washington D.C.', 5, true, 38.9072, -77.0369, false),
    ('Paris', 6, true, 48.8566, 2.3522, false),
    ('London', 7, true, 51.5074, -0.1278, false),
    ('Ottawa', 8, true, 45.4215, -75.6972, false),
    ('Canberra', 9, true, -35.2809, 149.1300, false),
    ('Cairo', 10, true, 30.0444, 31.2357, false);

INSERT INTO borders (country_code, neighbor_code) VALUES ('AL', 'GR');
INSERT INTO borders (country_code, neighbor_code) VALUES ('AL', 'MK');
INSERT INTO borders (country_code, neighbor_code) VALUES ('AL', 'RS');
INSERT INTO borders (country_code, neighbor_code) VALUES ('AT', 'CZ');
INSERT INTO borders (country_code, neighbor_code) VALUES ('AT', 'DE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('AT', 'HU');
INSERT INTO borders (country_code, neighbor_code) VALUES ('AT', 'IT');
INSERT INTO borders (country_code, neighbor_code) VALUES ('AT', 'SK');
INSERT INTO borders (country_code, neighbor_code) VALUES ('AT', 'SI');
INSERT INTO borders (country_code, neighbor_code) VALUES ('AT', 'CH');
INSERT INTO borders (country_code, neighbor_code) VALUES ('BA', 'HR');
INSERT INTO borders (country_code, neighbor_code) VALUES ('BA', 'RS');
INSERT INTO borders (country_code, neighbor_code) VALUES ('BE', 'FR');
INSERT INTO borders (country_code, neighbor_code) VALUES ('BE', 'DE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('BE', 'NL');
INSERT INTO borders (country_code, neighbor_code) VALUES ('BG', 'GR');
INSERT INTO borders (country_code, neighbor_code) VALUES ('BG', 'MK');
INSERT INTO borders (country_code, neighbor_code) VALUES ('BG', 'RO');
INSERT INTO borders (country_code, neighbor_code) VALUES ('BG', 'RS');
INSERT INTO borders (country_code, neighbor_code) VALUES ('CH', 'AT');
INSERT INTO borders (country_code, neighbor_code) VALUES ('CH', 'FR');
INSERT INTO borders (country_code, neighbor_code) VALUES ('CH', 'DE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('CH', 'IT');
INSERT INTO borders (country_code, neighbor_code) VALUES ('CZ', 'AT');
INSERT INTO borders (country_code, neighbor_code) VALUES ('CZ', 'DE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('CZ', 'PL');
INSERT INTO borders (country_code, neighbor_code) VALUES ('CZ', 'SK');
INSERT INTO borders (country_code, neighbor_code) VALUES ('DE', 'AT');
INSERT INTO borders (country_code, neighbor_code) VALUES ('DE', 'BE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('DE', 'CZ');
INSERT INTO borders (country_code, neighbor_code) VALUES ('DE', 'DK');
INSERT INTO borders (country_code, neighbor_code) VALUES ('DE', 'FR');
INSERT INTO borders (country_code, neighbor_code) VALUES ('DE', 'NL');
INSERT INTO borders (country_code, neighbor_code) VALUES ('DE', 'PL');
INSERT INTO borders (country_code, neighbor_code) VALUES ('DE', 'CH');
INSERT INTO borders (country_code, neighbor_code) VALUES ('DK', 'DE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('ES', 'FR');
INSERT INTO borders (country_code, neighbor_code) VALUES ('ES', 'PT');
INSERT INTO borders (country_code, neighbor_code) VALUES ('FI', 'NO');
INSERT INTO borders (country_code, neighbor_code) VALUES ('FI', 'SE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('FR', 'BE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('FR', 'DE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('FR', 'IT');
INSERT INTO borders (country_code, neighbor_code) VALUES ('FR', 'ES');
INSERT INTO borders (country_code, neighbor_code) VALUES ('FR', 'CH');
INSERT INTO borders (country_code, neighbor_code) VALUES ('GR', 'AL');
INSERT INTO borders (country_code, neighbor_code) VALUES ('GR', 'BG');
INSERT INTO borders (country_code, neighbor_code) VALUES ('GR', 'MK');
INSERT INTO borders (country_code, neighbor_code) VALUES ('HR', 'BA');
INSERT INTO borders (country_code, neighbor_code) VALUES ('HR', 'HU');
INSERT INTO borders (country_code, neighbor_code) VALUES ('HR', 'RS');
INSERT INTO borders (country_code, neighbor_code) VALUES ('HR', 'SI');
INSERT INTO borders (country_code, neighbor_code) VALUES ('HU', 'AT');
INSERT INTO borders (country_code, neighbor_code) VALUES ('HU', 'HR');
INSERT INTO borders (country_code, neighbor_code) VALUES ('HU', 'RO');
INSERT INTO borders (country_code, neighbor_code) VALUES ('HU', 'RS');
INSERT INTO borders (country_code, neighbor_code) VALUES ('HU', 'SK');
INSERT INTO borders (country_code, neighbor_code) VALUES ('HU', 'SI');
INSERT INTO borders (country_code, neighbor_code) VALUES ('HU', 'UA');
INSERT INTO borders (country_code, neighbor_code) VALUES ('IT', 'AT');
INSERT INTO borders (country_code, neighbor_code) VALUES ('IT', 'FR');
INSERT INTO borders (country_code, neighbor_code) VALUES ('IT', 'SI');
INSERT INTO borders (country_code, neighbor_code) VALUES ('IT', 'CH');
INSERT INTO borders (country_code, neighbor_code) VALUES ('MD', 'RO');
INSERT INTO borders (country_code, neighbor_code) VALUES ('MD', 'UA');
INSERT INTO borders (country_code, neighbor_code) VALUES ('MK', 'AL');
INSERT INTO borders (country_code, neighbor_code) VALUES ('MK', 'BG');
INSERT INTO borders (country_code, neighbor_code) VALUES ('MK', 'GR');
INSERT INTO borders (country_code, neighbor_code) VALUES ('MK', 'RS');
INSERT INTO borders (country_code, neighbor_code) VALUES ('NL', 'BE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('NL', 'DE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('NO', 'FI');
INSERT INTO borders (country_code, neighbor_code) VALUES ('NO', 'SE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('PL', 'CZ');
INSERT INTO borders (country_code, neighbor_code) VALUES ('PL', 'DE');
INSERT INTO borders (country_code, neighbor_code) VALUES ('PL', 'SK');
INSERT INTO borders (country_code, neighbor_code) VALUES ('PL', 'UA');
INSERT INTO borders (country_code, neighbor_code) VALUES ('PT', 'ES');
INSERT INTO borders (country_code, neighbor_code) VALUES ('RO', 'BG');
INSERT INTO borders (country_code, neighbor_code) VALUES ('RO', 'HU');
INSERT INTO borders (country_code, neighbor_code) VALUES ('RO', 'MD');
INSERT INTO borders (country_code, neighbor_code) VALUES ('RO', 'RS');
INSERT INTO borders (country_code, neighbor_code) VALUES ('RO', 'UA');
INSERT INTO borders (country_code, neighbor_code) VALUES ('RS', 'AL');
INSERT INTO borders (country_code, neighbor_code) VALUES ('RS', 'BA');
INSERT INTO borders (country_code, neighbor_code) VALUES ('RS', 'BG');
INSERT INTO borders (country_code, neighbor_code) VALUES ('RS', 'HR');
INSERT INTO borders (country_code, neighbor_code) VALUES ('RS', 'HU');
INSERT INTO borders (country_code, neighbor_code) VALUES ('RS', 'MK');
INSERT INTO borders (country_code, neighbor_code) VALUES ('RS', 'RO');
INSERT INTO borders (country_code, neighbor_code) VALUES ('SE', 'FI');
INSERT INTO borders (country_code, neighbor_code) VALUES ('SE', 'NO');
INSERT INTO borders (country_code, neighbor_code) VALUES ('SI', 'AT');
INSERT INTO borders (country_code, neighbor_code) VALUES ('SI', 'HR');
INSERT INTO borders (country_code, neighbor_code) VALUES ('SI', 'HU');
INSERT INTO borders (country_code, neighbor_code) VALUES ('SI', 'IT');
INSERT INTO borders (country_code, neighbor_code) VALUES ('SK', 'AT');
INSERT INTO borders (country_code, neighbor_code) VALUES ('SK', 'CZ');
INSERT INTO borders (country_code, neighbor_code) VALUES ('SK', 'HU');
INSERT INTO borders (country_code, neighbor_code) VALUES ('SK', 'PL');
INSERT INTO borders (country_code, neighbor_code) VALUES ('SK', 'UA');
INSERT INTO borders (country_code, neighbor_code) VALUES ('UA', 'HU');
INSERT INTO borders (country_code, neighbor_code) VALUES ('UA', 'MD');
INSERT INTO borders (country_code, neighbor_code) VALUES ('UA', 'PL');
INSERT INTO borders (country_code, neighbor_code) VALUES ('UA', 'RO');
INSERT INTO borders (country_code, neighbor_code) VALUES ('UA', 'SK');