drop database FutbolRetro;
create database FutbolRetro;
use FutbolRetro;

-- ---------------------------------------------------------------------------
-- CREACION DE LAS TABLAS
-- ---------------------------------------------------------------------------
CREATE TABLE usuario (
    idUsuario INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    dni INT(15) NOT NULL,
    usuario VARCHAR(255) NOT NULL,
    clave VARCHAR(255) NOT NULL,
    rol VARCHAR(255) NOT NULL
);

CREATE TABLE clientes (
	idCliente int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    dni int (11) NOT NULL,
	nombre varchar(100) NOT NULL,
	apellido varchar(100) NOT NULL,
	telefono int(11) NOT NULL,
	correo varchar(100) NOT NULL
);

CREATE TABLE productos (
  idProd int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  descripcion varchar(255) NOT NULL,
  precio decimal(11,2) NOT NULL,
  stock int(15) NOT NULL,
  categoria varchar(255) NOT NULL
);

CREATE TABLE ventas(
	idVenta int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    idCliente int(11) NOT NULL,
    idUsuario INT(11) NOT NULL,
    numeroSerie varchar(255) NOT NULL,
    fecha DATETIME NOT NULL,
    precioTotal decimal(15,2) NOT NULL
);

CREATE TABLE detalleVenta (
	idDetalleVenta int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    idVenta int(11) NOT NULL,
    idProd int(11) NOT NULL,
    descripcionProd varchar(255) NOT NULL,
    cantidadProd int(11) NOT NULL,
    precio decimal(11,2) NOT NULL,
    igv decimal(11,2) NOT NULL,
    precioFinal decimal(11,2) NOT NULL,
    subTotal decimal(15,2) NOT NULL
);


CREATE TABLE comprobantePago(
	idComprobantePago int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tipoComprobantePago varchar(255) NOT NULL,
    numComprobantePago varchar(255) NOT NULL,
    idVenta int(11) NOT NULL
);


CREATE TABLE informe (
	idInforme int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    informedescripcion varchar(10000),
    idUsuario INT(11) NOT NULL
);

-- ---------------------------------------------------------------------------
-- CLAVES FORANEAS PARA LAS TABLAS
-- ---------------------------------------------------------------------------

ALTER TABLE ventas
ADD CONSTRAINT FK_Ventas_Clientes FOREIGN KEY (idCliente) REFERENCES clientes(idCliente),
ADD CONSTRAINT FK_Ventas_Usuario FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario);

ALTER TABLE detalleVenta
ADD CONSTRAINT FK_DetalleVenta_Ventas FOREIGN KEY (idVenta) REFERENCES ventas(idVenta) ON DELETE CASCADE,
ADD CONSTRAINT FK_DetalleVenta_Productos FOREIGN KEY (idProd) REFERENCES productos(idProd);

ALTER TABLE comprobantePago
ADD CONSTRAINT FK_ComprobantePago_Ventas FOREIGN KEY (idVenta) REFERENCES ventas(idVenta) ON DELETE CASCADE;

ALTER TABLE informe
ADD CONSTRAINT FK_Informe_Usuario FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario);


-- ---------------------------------------------------------------------------
-- INSERTAR DATOS DESDE EL DB
-- ---------------------------------------------------------------------------

insert into usuario(nombre, apellido, dni, usuario, clave, rol) values 
('Juan', 'Lloclla', 72488002, 'admin', '123', 'administrador'),
('Raul', 'Fernandez', 76594521, 'empleado', '159', 'empleado'); 

INSERT INTO productos (descripcion, precio, stock, categoria) VALUES
('Camiseta FC Barcelona 1999', 120, 20, 'CAMISETA CLUB ANTIGUA'),
('Camiseta Real Madrid 2002', 150, 25, 'CAMISETA CLUB ANTIGUA'),
('Camiseta AC Milan 1994', 130, 30, 'CAMISETA CLUB ANTIGUA'),
('Camiseta Manchester United 2024', 100, 24, 'CAMISETA CLUB ACTUAL'),
('Camiseta Liverpool 2024', 110, 35, 'CAMISETA CLUB ACTUAL'),
('Camiseta PSG 2024', 115, 40, 'CAMISETA CLUB ACTUAL'),
('Balon Adidas World Cup 2022', 80, 30, 'BALON'),
('Balon Nike Premier League 2023', 75, 29, 'BALON'),
('Balon Puma La Liga 2023', 70, 31, 'BALON'),
('Camiseta Juventus 1996', 140, 45, 'CAMISETA CLUB ANTIGUA');

INSERT INTO clientes (dni, nombre, apellido, telefono, correo) VALUES
(78945684 ,'Juan', 'Perez', 968545231, 'juanperez@gmail.com'),
(79564521, 'Raul', 'Lopez', 945849654, 'raullopez@gmail.com'),
(77951243, 'Selene', 'Silva', 994563786, 'selenesilva@gmail.com');

-- ---------------------------------------------------------------------------

select*from usuario;
select*from productos;
select*from clientes;
select*from ventas;
select*from detalleventa;
select*from comprobantePago;

-- Para ver las ventas en el sistema
SELECT v.idVenta, v.numeroSerie, v.fecha, c.nombre, c.apellido, v.precioTotal, dv.descripcionProd, p.categoria, dv.cantidadProd, dv.precioBruto, dv.subTotal
FROM ventas v INNER JOIN detalleVenta dv ON v.idVenta = dv.idVenta INNER JOIN clientes c ON v.idCliente = c.idCliente INNER JOIN productos p ON dv.idProd = p.idProd
ORDER BY dv.idDetalleVenta;


-- Para el jasper report Factura
-- Factura
select v.idVenta, c.nombre, c.apellido, c.dni, concat(c.nombre, ' ', c.apellido) as cliente , c.telefono, c.correo, cp.numComprobantePago, v.fecha, sum(dv.igv) ,v.precioTotal from ventas v 
inner join clientes c on v.idCliente = c.idCliente
inner join comprobantePago cp on v.idVenta = cp.idVenta
inner join detalleVenta dv on v.idVenta = dv.idVenta
where v.idVenta = 1
group by v.idVenta;

-- FacturaDetalle
select dv.cantidadProd, dv.descripcionProd, dv.precio, dv.igv, dv.precioFinal, dv.subTotal from ventas v
inner join detalleVenta dv on v.idVenta = dv.idVenta
where v.idVenta=1;

-- Para el jasper report Boleta
-- Boleta
select v.idVenta, c.nombre, c.apellido, c.dni, concat(c.nombre, ' ', c.apellido) as cliente , c.telefono, c.correo, cp.numComprobantePago, v.fecha, v.precioTotal from ventas v 
inner join clientes c on v.idCliente = c.idCliente
inner join comprobantePago cp on v.idVenta = cp.idVenta
where v.idVenta = 4;

-- BoletaDetalle
select dv.cantidadProd, dv.descripcionProd, dv.precioFinal, dv.subTotal from ventas v
inner join detalleVenta dv on v.idVenta = dv.idVenta
where v.idVenta=1;

-- Ver el correo del cliente
select correo from clientes where idCliente=?;

