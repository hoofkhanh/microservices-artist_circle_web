CREATE TABLE music_genre (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255)
);

INSERT INTO music_genre (name) VALUES 
('POP'),
('ROCK'),
('HIP HOP'),
('JAZZ'),
('CLASSICAL'),
('R&B'),
('EDM'),
('FOLK'),
('BLUES'),
('OTHER');