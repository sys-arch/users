/* La contraseña es: juan.delgado */
INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2) 
VALUES ('c084162a-133f-4046-8866-fe5b8f43f6c9', 'juan.delgado@ejemplo.com', 'ff86ec82a5e1bdc881a3cdde8ab87fbf28a4731b59875a2757046d900e062e416e3ef08c34d2775bcefc39f0313927c6d53c3507c2c05d3392a7b7ad2179a4d9', 'Juan', 'Delgado', 'Pérez');

/* La contraseña es: luise.fdezmedina */
INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2) 
VALUES ('34f1c5b6-8f9b-4e67-8cf2-8945e8c1e4b2', 'luise.fdezmedina@gmail.com', 'ff86ec82a5e1bdc881a3cdde8ab87fbf28a4731b59875a2757046d900e062e416e3ef08c34d2775bcefc39f0313927c6d53c3507c2c05d3392a7b7ad2179a4d9', 'Luise', 'Fernández', 'Medina');

/* Créditos iniciales */
INSERT INTO Credits (id, user_id, credits) 
VALUES (20, 'c084162a-133f-4046-8866-fe5b8f43f6c9', 200);
VALUES (21, '34f1c5b6-8f9b-4e67-8cf2-8945e8c1e4b2', 200);

