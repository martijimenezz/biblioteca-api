-- Datos de prueba para la base de datos Biblioteca API
-- Ejecutar después de que la aplicación haya creado las tablas

-- Autores (10 registros)
INSERT INTO authors (name, country, birth_year) VALUES
('Gabriel García Márquez', 'Colombia', 1927),
('Jorge Luis Borges', 'Argentina', 1899),
('Isabel Allende', 'Chile', 1942),
('Miguel de Cervantes', 'España', 1547),
('Pablo Neruda', 'Chile', 1904),
('Juan Carlos Onetti', 'Uruguay', 1909),
('Julio Cortázar', 'Argentina', 1914),
('Laura Esquivel', 'México', 1950),
('Carlos Fuentes', 'México', 1928),
('Margarita Rosa García Isaza', 'Colombia', 1958);

-- Libros (12 registros)
INSERT INTO books (title, isbn, author_id, publication_year, description, copies, available_copies) VALUES
('One Hundred Years of Solitude', '978-0060883287', 1, 1967, 'Novela de realismo mágico', 5, 3),
('Love in the Time of Cholera', '978-0571209263', 1, 1985, 'Historia de amor en Colombia', 4, 2),
('Ficciones', '978-0553283357', 2, 1944, 'Colección de relatos cortos', 3, 1),
('The Aleph', '978-0553283357', 2, 1949, 'Ensayo y ficción', 3, 2),
('The House of the Spirits', '978-0553283357', 3, 1982, 'Saga familiar', 4, 3),
('Paula', '978-0451196729', 3, 1994, 'Novela autobiográfica', 2, 1),
('Don Quixote', '978-0140449495', 4, 1605, 'Clásico de la literatura', 6, 4),
('Twenty Love Poems and a Song of Despair', '978-0143104094', 5, 1924, 'Poesía romántica', 3, 2),
('The House on Mango Street', '978-0553283357', 8, 1984, 'Novela corta juvenil', 5, 4),
('Rayuela', '978-8437604046', 7, 1963, 'Novela experimental', 2, 1),
('Chronicle of a Death Foretold', '978-0140297638', 1, 1981, 'Novela de misterio', 4, 2),
('The Master and Margarita', '978-0140449495', 1, 1966, 'Novelá fantástica', 3, 2);

-- Usuarios (10 registros)
INSERT INTO users (name, email, member_id, city, active) VALUES
('John Doe', 'john@example.com', 'M001', 'Barcelona', true),
('Jane Smith', 'jane@example.com', 'M002', 'Madrid', true),
('Carlos Miguel', 'carlos@example.com', 'M003', 'Valencia', true),
('María García', 'maria@example.com', 'M004', 'Sevilla', true),
('Antonio López', 'antonio@example.com', 'M005', 'Bilbao', true),
('Elena Rodríguez', 'elena@example.com', 'M006', 'Málaga', true),
('Pedro Fernández', 'pedro@example.com', 'M007', 'Alicante', true),
('Rosa Martínez', 'rosa@example.com', 'M008', 'Córdoba', true),
('Juan Diego', 'juan@example.com', 'M009', 'Murcia', true),
('Sofía González', 'sofia@example.com', 'M010', 'Palma', true);

-- Préstamos (12 registros)
INSERT INTO loans (book_id, user_id, loan_date, due_date, return_date, status) VALUES
(1, 1, '2024-01-15', '2024-01-29', NULL, 'ACTIVE'),
(2, 2, '2024-01-10', '2024-01-24', '2024-01-20', 'RETURNED'),
(3, 3, '2024-01-12', '2024-01-26', NULL, 'ACTIVE'),
(4, 4, '2024-01-08', '2024-01-22', '2024-01-22', 'RETURNED'),
(5, 5, '2024-01-14', '2024-01-28', NULL, 'ACTIVE'),
(6, 6, '2024-01-11', '2024-01-25', '2024-01-25', 'RETURNED'),
(7, 7, '2024-01-16', '2024-01-30', NULL, 'ACTIVE'),
(8, 8, '2024-01-09', '2024-01-23', '2024-01-23', 'RETURNED'),
(9, 9, '2024-01-13', '2024-01-27', NULL, 'ACTIVE'),
(10, 10, '2024-01-07', '2024-01-21', '2024-01-21', 'RETURNED'),
(11, 1, '2024-01-15', '2024-01-29', NULL, 'ACTIVE'),
(12, 2, '2024-01-17', '2024-01-31', NULL, 'ACTIVE');
