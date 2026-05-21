CREATE DATABASE sistema_tickets;

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(120) UNIQUE NOT NULL,
    departamento VARCHAR(100),
    rol VARCHAR(50) NOT NULL
);

CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    descripcion TEXT NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'CREADO',
    creadoPor INT NOT NULL,
    asignadoA INT,
    fechaCreacion TIMESTAMP DEFAULT NOW(),
    fechaCierre TIMESTAMP,
    FOREIGN KEY (creadoPor) REFERENCES usuarios(id),
    FOREIGN KEY (asignadoA) REFERENCES usuarios(id)
);

ALTER TABLE ticket
ADD CONSTRAINT chk_estado_ticket
CHECK (
    estado IN (
        'CREADO',
        'ASIGNADO',
        'VALIDACION',
        'FINALIZADO',
        'DEVUELTO',
        'RECHAZADO'
    )
);

CREATE TABLE ticket_adjuntos (
    id SERIAL PRIMARY KEY,
    ticket INT NOT NULL,
    nombreOriginal VARCHAR(255),
    rutaArchivo TEXT NOT NULL,
    tipoMime VARCHAR(100),
    tamanio BIGINT,
    FOREIGN KEY (ticket) REFERENCES ticket(id) ON DELETE CASCADE
);

CREATE TABLE timeline_ticket (
    id SERIAL PRIMARY KEY,
    codigo_ticket VARCHAR(20) NOT NULL,
    fecha_hora TIMESTAMP DEFAULT NOW(),
    estado VARCHAR(20) NOT NULL,
    actor VARCHAR(100) NOT NULL,
    observacion TEXT,
    FOREIGN KEY (codigo_ticket) REFERENCES ticket(codigo) ON DELETE CASCADE
);

ALTER TABLE timeline_ticket
ADD CONSTRAINT chk_estado_timeline
CHECK (
    estado IN (
        'CREADO',
        'ASIGNADO',
        'VALIDACION',
        'FINALIZADO',
        'DEVUELTO',
        'RECHAZADO'
    )
);

SELECT * FROM usuarios;
SELECT * FROM ticket;
SELECT * FROM ticket_adjuntos;
SELECT * FROM timeline_ticket;