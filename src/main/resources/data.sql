DROP TABLE IF EXISTS membre;

CREATE TABLE membre (
  mbr_id SERIAL PRIMARY KEY,
  prenom VARCHAR(250) NOT NULL,
  nom VARCHAR(250) NOT NULL,
  addresse VARCHAR(250),
  date_integration DATE,
  telephone VARCHAR(250),
  password VARCHAR(250) NOT NULL,
  role VARCHAR(250) NOT NULL,
  mbr_disabled BOOLEAN DEFAULT FALSE
);

INSERT INTO membre (prenom, nom, addresse, date_integration, telephone, password, role, mbr_disabled) 
VALUES 
('Laity', 'Faye', 'Thies', '2025-02-03', '780186229', 
'$2y$10$kp1V7UYDEWn17WSK16UcmOnFd1mPFVF6UkLrOOCGtf24HOYt8p1iC', 'ADMIN', FALSE);