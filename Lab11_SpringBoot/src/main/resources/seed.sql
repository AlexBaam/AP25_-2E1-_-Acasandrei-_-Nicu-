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
    ('Romania', 'RO', 1),         -- id = 1
    ('Germany', 'DE', 1),         -- id = 2
    ('Japan', 'JP', 2),           -- id = 3
    ('Brazil', 'BR', 4),          -- id = 4
    ('USA', 'US', 3),             -- id = 5
    ('France', 'FR', 1),          -- id = 6
    ('UK', 'GB', 1),              -- id = 7
    ('Canada', 'CA', 3),          -- id = 8
    ('Australia', 'AU', 6),       -- id = 9
    ('Egypt', 'EG', 5);           -- id = 10

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