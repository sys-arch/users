/*INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2) 
VALUES ('c084162a-133f-4046-8866-fe5b8f43f6c9', 'juan.delgado@ejemplo.com', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'Juan', 'Delgado', 'Pérez', 'Hospital Central', true, 'ROLE_ADMIN');

INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('4b95832f-685e-4116-b4ca-e681d7c03542', 'maria.sanchez85@gmail.com', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'María', 'Sánchez', 'López', 'Clínica Universitaria', true, 'ROLE_ADMIN');

INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('7a6f309b-091e-4c23-9c5a-2b06d079361b', 'pedro.martinez@salud.gob.es', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'Pedro', 'Martínez', 'González', 'Centro de Salud Rural', true, 'ROLE_ADMIN');

INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('9f384d71-22ee-4b54-9467-5c2a4856b42f', 'juan.perez@ejemplo.com', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'Juan', 'Pérez', 'González', 'Universidad de Ejemplo', true, 'ROLE_EMPLOYEE');

INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('3a72b9f5-864c-40fe-821b-7d5ca1234567', 'maria.rodriguez@ejemplo.com', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'María', 'Rodríguez', 'López', 'Hospital Central', true, 'ROLE_EMPLOYEE');

INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('b2c3d4e5-f6g7-h8i9-j0k1-l2m3n4o5p6q7', 'pedro.martinez@ejemplo.com', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'Pedro', 'Martínez', 'Hernández', 'Empresa XYZ', true, 'ROLE_EMPLOYEE');

INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('5a6b7c8d-9e0f-1g2h-3i4j-5k6l7m8n9o0', 'ana.garcia@ejemplo.com', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'Ana', 'García', 'Jiménez', 'Gimnasio Fit', true, 'ROLE_EMPLOYEE');

INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('7d8e9f0a-1b2c-3d4e-5f6g-7h8i9j0k1l2', 'luis.fernandez@ejemplo.com', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'Luis', 'Fernández', 'Ruiz', 'Restaurante La Cocina', true, 'ROLE_EMPLOYEE');

-- Usuarios adicionales con campo 'role'
INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('f25517b5-3304-4fa2-9133-bcb53e07ea18', 'luise.fdezmedina@gmail.com', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'Luis Eduardo', 'Fernández-Medina', 'Cimas', 'ESI UCLM', true, 'ROLE_ADMIN');

INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('5803dc40-eec9-46c6-a308-b32588f294b6', 'guillermo.espejo@alu.uclm.es', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'Guillermo', 'Espejo', 'Palomeque', 'ESI UCLM', true, 'ROLE_ADMIN');

INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('86a54ba8-5c2d-496a-9250-ce7d35aabb11', 'yolanda.galvan@alu.uclm.es', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'Yolanda', 'Galván', 'Redondo', 'ESI UCLM', true, 'ROLE_ADMIN');

INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('22fad7d4-4746-4bdf-9606-6a5659615b11', 'adrian.gomez14@alu.uclm.es', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'Adrian', 'Gomez del Moral', 'Rodriguez-Madridejos', 'ESI UCLM', true, 'ROLE_ADMIN');

INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('1cff7cf2-4c6f-463f-b671-256d7c534fd6', 'ivan.jimenez4@alu.uclm.es', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'Iván', 'Jimenez', 'Quintana', 'ESI UCLM', true, 'ROLE_ADMIN');

INSERT INTO Usuarios (id, email, pwd, nombre, apellido1, apellido2, centro, twoFA, role) 
VALUES ('b6c34fef-9d99-423d-82cd-daa178f05f11', 'antonio.sanchez36@alu.uclm.es', '066b38460608c7c7745116cfa7b000d14fccdf34cd639a73180c845bca796c2c1817ff694bec90bee6f8dbca8d0f5c07ae09a3abf7711e77993328e456b894bb', 'Antonio', 'Sanchez', 'Sanchez', 'ESI UCLM', true, 'ROLE_ADMIN');*/