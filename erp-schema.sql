DROP SCHEMA IF EXISTS sopraerp;
CREATE SCHEMA sopraerp;
USE sopraerp;

create table empresa(
	id_empresa SMALLINT UNSIGNED NOT NULL,
    descripcion varchar(45) not null,
    seleccionada boolean default 0,
    primary key(id_empresa)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table familia(
	id_empresa smallint unsigned not null,
	id_familia varchar(3) not null,
    descuento decimal(2),
    primary key(id_empresa,id_familia),
	CONSTRAINT `fk_familia` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE
	
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE articulo (
id_empresa smallint unsigned not null,
  id_articulo SMALLINT UNSIGNED NOT NULL,
  descripcion VARCHAR(45) NOT NULL,
  precio_base decimal(6,2) NOT NULL,
  id_proveedor_principal SMALLINT UNSIGNED,
  id_familia varchar(3),
  imagen blob,
  tip_iva smallint unsigned not null,
  precio_coste decimal(2) not null,
  PRIMARY KEY  (id_empresa,id_articulo),
  -- CONSTRAINT `fk_empresa1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_empresa2` FOREIGN KEY (id_empresa,id_familia) REFERENCES familia (id_empresa,id_familia) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table tipo_cliente (
    id_tipo smallint unsigned not null,
    descripcion varchar(45) not null,
    primary key (id_tipo)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table cliente(
	id_empresa smallint unsigned not null,
	id_cliente smallint unsigned not null,
    direccion varchar(45) not null,
    nombre varchar(45) not null,
    telefono int(9),
    CIF varchar(9) not null,
    tipo_cliente smallint unsigned,
    email varchar(45),
    primary key(id_empresa,id_cliente),
    CONSTRAINT `fk_empresa_cliente` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (tipo_cliente) REFERENCES tipo_cliente (id_tipo) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table proveedor(
	id_empresa smallint unsigned not null,
	id_proveedor smallint unsigned not null,
    direccion varchar(45) not null,
    nombre varchar(45) not null,
    telefono int(9),
    CIF varchar(9) not null,
    tipo_proveedor smallint unsigned,
    email varchar(45),
    primary key(id_empresa,id_proveedor),
    CONSTRAINT `fk_empresa_prov` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table almacen(
	id_empresa smallint unsigned not null,
    id_almacen smallint unsigned not null,
    descripcion varchar(45) not null,
    primary key(id_empresa,id_almacen),
    CONSTRAINT `fk_empresa_almacen` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table articulos_alma(
	id_empresa smallint unsigned not null,
    id_articulo smallint unsigned not null,
    id_almacen smallint unsigned not null,
    catindad int unsigned not null,
    ultima_entrada date not null,
    primary key(id_empresa,id_articulo,id_almacen),
    -- CONSTRAINT `fk_art_alma1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_art_alma2` FOREIGN KEY (id_empresa,id_articulo) REFERENCES articulo (id_empresa,id_articulo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_art_alma3` FOREIGN KEY (id_empresa,id_almacen) REFERENCES almacen (id_empresa,id_almacen) ON DELETE RESTRICT ON UPDATE CASCADE

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table impuesto(
	id_impuesto smallint unsigned not null,
    valor decimal(6,2) not null,
    primary key(id_impuesto)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table descuento(
	id_descuento smallint unsigned not null,
    valor decimal(6,2) not null,
    primary key(id_descuento)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table pedcl_cab(
	id_numdoc smallint unsigned not null,
	id_cliente smallint unsigned not null,
    id_empresa smallint unsigned not null,
    fecha_pedido date not null,
    id_descuento smallint unsigned not null,
    estado smallint,
    importe decimal(6,2) not null default 0,
    importe_desc decimal(6,2) not null default 0,
    importe_total decimal(6,2) not null default 0,
    primary key (id_empresa,id_numdoc),
    -- CONSTRAINT `fk_pedido1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido2` FOREIGN KEY (id_empresa,id_cliente) REFERENCES cliente (id_empresa,id_cliente) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido3` FOREIGN KEY (id_descuento) REFERENCES descuento (id_descuento) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table pedcl_lin(
	id_numdoc smallint unsigned not null,
    id_empresa smallint unsigned not null,
    id_articulo smallint unsigned not null,
    cantidad int not null default 0,
    precio decimal(6,2) not null default 0,
    id_linea smallint unsigned not null,
    id_descuento smallint unsigned not null,
    id_impuesto smallint unsigned not null,
    importe decimal(6,2) not null default 0,
    id_almacen smallint unsigned not null,
    primary key (id_empresa,id_numdoc,id_linea),
    -- CONSTRAINT `fk_pedido_lin1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	-- CONSTRAINT `fk_pedido_lin2` FOREIGN KEY (id_empresa,id_articulo) REFERENCES articulo (id_empresa,id_articulo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_lin3` FOREIGN KEY (id_impuesto) REFERENCES impuesto (id_impuesto) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_lin4` FOREIGN KEY (id_descuento) REFERENCES descuento (id_descuento) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_lin5` FOREIGN KEY (id_empresa,id_numdoc) REFERENCES pedcl_cab (id_empresa,id_numdoc) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_lin6` FOREIGN KEY (id_empresa,id_articulo,id_almacen) REFERENCES articulos_alma (id_empresa,id_articulo,id_almacen) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table alcl_cab(
	id_numdoc smallint unsigned not null,
	id_cliente smallint unsigned not null,
    id_empresa smallint unsigned not null,
    fecha_pedido date not null,
    id_descuento smallint unsigned not null,
    estado smallint,
    importe decimal(6,2) not null default 0,
    importe_desc decimal(6,2) not null default 0,
    importe_total decimal(6,2) not null default 0,
    primary key (id_empresa,id_numdoc),
    -- CONSTRAINT `fk_pedido1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_al2` FOREIGN KEY (id_empresa,id_cliente) REFERENCES cliente (id_empresa,id_cliente) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_al3` FOREIGN KEY (id_descuento) REFERENCES descuento (id_descuento) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table alcl_lin(
	id_numdoc smallint unsigned not null,
    id_empresa smallint unsigned not null,
    id_articulo smallint unsigned not null,
    cantidad int not null default 0,
    precio decimal(6,2) not null default 0,
    id_linea smallint unsigned not null,
    id_descuento smallint unsigned not null,
    id_impuesto smallint unsigned not null,
    importe decimal(6,2) not null default 0,
    id_almacen smallint unsigned not null,
    primary key (id_empresa,id_numdoc,id_linea),
    -- CONSTRAINT `fk_pedido_lin1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	-- CONSTRAINT `fk_pedido_lin2` FOREIGN KEY (id_empresa,id_articulo) REFERENCES articulo (id_empresa,id_articulo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_al_lin3` FOREIGN KEY (id_impuesto) REFERENCES impuesto (id_impuesto) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_al_lin4` FOREIGN KEY (id_descuento) REFERENCES descuento (id_descuento) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_al_lin5` FOREIGN KEY (id_empresa,id_numdoc) REFERENCES alcl_cab (id_empresa,id_numdoc) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_al_lin6` FOREIGN KEY (id_empresa,id_articulo,id_almacen) REFERENCES articulos_alma (id_empresa,id_articulo,id_almacen) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table faccl_cab(
	id_numdoc smallint unsigned not null,
	id_cliente smallint unsigned not null,
    id_empresa smallint unsigned not null,
    fecha_pedido date not null,
    id_descuento smallint unsigned not null,
    estado smallint,
    primary key (id_empresa,id_numdoc),
    -- CONSTRAINT `fk_pedido1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_fac2` FOREIGN KEY (id_empresa,id_cliente) REFERENCES cliente (id_empresa,id_cliente) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_fac3` FOREIGN KEY (id_descuento) REFERENCES descuento (id_descuento) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table faccl_lin(
	id_numdoc smallint unsigned not null,
    id_empresa smallint unsigned not null,
    id_articulo smallint unsigned not null,
    cantidad int not null default 0,
    precio decimal(6,2) not null default 0,
    id_linea smallint unsigned not null,
    id_descuento smallint unsigned not null,
    id_impuesto smallint unsigned not null,
    importe decimal(6,2) not null default 0,
    id_almacen smallint unsigned not null,
    primary key (id_empresa,id_numdoc,id_linea),
    -- CONSTRAINT `fk_pedido_lin1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	-- CONSTRAINT `fk_pedido_lin2` FOREIGN KEY (id_empresa,id_articulo) REFERENCES articulo (id_empresa,id_articulo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_fact_lin3` FOREIGN KEY (id_impuesto) REFERENCES impuesto (id_impuesto) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_fact_lin4` FOREIGN KEY (id_descuento) REFERENCES descuento (id_descuento) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_fact_lin5` FOREIGN KEY (id_empresa,id_numdoc) REFERENCES faccl_cab (id_empresa,id_numdoc) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_fact_lin6` FOREIGN KEY (id_empresa,id_articulo,id_almacen) REFERENCES articulos_alma (id_empresa,id_articulo,id_almacen) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table faccl_imp(
	id_numdoc smallint unsigned not null,
    id_empresa smallint unsigned not null,
	id_impuesto smallint unsigned not null,
    importe_lins decimal(6,2) not null default 0,
    importe_desc decimal(6,2) not null default 0,
    importe_total decimal(6,2) not null default 0,
    primary key (id_empresa,id_numdoc),
    CONSTRAINT `fk_fact_imp1` FOREIGN KEY (id_impuesto) REFERENCES impuesto (id_impuesto) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_fact_imp3` FOREIGN KEY (id_empresa,id_numdoc) REFERENCES faccl_cab (id_empresa,id_numdoc) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table pedprov_cab(
	id_numdoc smallint unsigned not null,
	id_proveedor smallint unsigned not null,
    id_empresa smallint unsigned not null,
    fecha_pedido date not null,
    id_descuento smallint unsigned not null,
    estado smallint,
    importe decimal(6,2) not null default 0,
    importe_desc decimal(6,2) not null default 0,
    importe_total decimal(6,2) not null default 0,
    primary key (id_empresa,id_numdoc),
    -- CONSTRAINT `fk_pedido1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_prov2` FOREIGN KEY (id_empresa,id_proveedor) REFERENCES proveedor (id_empresa,id_proveedor) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_prov3` FOREIGN KEY (id_descuento) REFERENCES descuento (id_descuento) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table pedprov_lin(
	id_numdoc smallint unsigned not null,
    id_empresa smallint unsigned not null,
    id_articulo smallint unsigned not null,
    cantidad int not null default 0,
    precio decimal(6,2) not null default 0,
    id_linea smallint unsigned not null,
    id_descuento smallint unsigned not null,
    id_impuesto smallint unsigned not null,
    importe decimal(6,2) not null default 0,
    id_almacen smallint unsigned not null,
    primary key (id_empresa,id_numdoc,id_linea),
    -- CONSTRAINT `fk_pedido_lin1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	-- CONSTRAINT `fk_pedido_lin2` FOREIGN KEY (id_empresa,id_articulo) REFERENCES articulo (id_empresa,id_articulo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedidoprov_lin3` FOREIGN KEY (id_impuesto) REFERENCES impuesto (id_impuesto) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedidoprov_lin4` FOREIGN KEY (id_descuento) REFERENCES descuento (id_descuento) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedidoprov_lin5` FOREIGN KEY (id_empresa,id_numdoc) REFERENCES pedprov_cab (id_empresa,id_numdoc) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedidoprov_lin6` FOREIGN KEY (id_empresa,id_articulo,id_almacen) REFERENCES articulos_alma (id_empresa,id_articulo,id_almacen) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table alprov_cab(
	id_numdoc smallint unsigned not null,
	id_proveedor smallint unsigned not null,
    id_empresa smallint unsigned not null,
    fecha_pedido date not null,
    id_descuento smallint unsigned not null,
    estado smallint,
    importe decimal(6,2) not null default 0,
    importe_desc decimal(6,2) not null default 0,
    importe_total decimal(6,2) not null default 0,
    primary key (id_empresa,id_numdoc),
    -- CONSTRAINT `fk_pedido1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedidoprov2` FOREIGN KEY (id_empresa,id_proveedor) REFERENCES proveedor (id_empresa,id_proveedor) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedidoprov3` FOREIGN KEY (id_descuento) REFERENCES descuento (id_descuento) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table alprov_lin(
	id_numdoc smallint unsigned not null,
    id_empresa smallint unsigned not null,
    id_articulo smallint unsigned not null,
    cantidad int not null default 0,
    precio decimal(6,2) not null default 0,
    id_linea smallint unsigned not null,
    id_descuento smallint unsigned not null,
    id_impuesto smallint unsigned not null,
    importe decimal(6,2) not null default 0,
    id_almacen smallint unsigned not null,
    primary key (id_empresa,id_numdoc,id_linea),
    -- CONSTRAINT `fk_pedido_lin1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	-- CONSTRAINT `fk_pedido_lin2` FOREIGN KEY (id_empresa,id_articulo) REFERENCES articulo (id_empresa,id_articulo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_linprov3` FOREIGN KEY (id_impuesto) REFERENCES impuesto (id_impuesto) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_linprov4` FOREIGN KEY (id_descuento) REFERENCES descuento (id_descuento) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_linprov5` FOREIGN KEY (id_empresa,id_numdoc) REFERENCES alprov_cab (id_empresa,id_numdoc) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_pedido_linprov6` FOREIGN KEY (id_empresa,id_articulo,id_almacen) REFERENCES articulos_alma (id_empresa,id_articulo,id_almacen) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table facprov_cab(
	id_numdoc smallint unsigned not null,
	id_proveedor smallint unsigned not null,
    id_empresa smallint unsigned not null,
    fecha_pedido date not null,
    id_descuento smallint unsigned not null,
    estado smallint,
    primary key (id_empresa,id_numdoc),
    -- CONSTRAINT `fk_pedido1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_fac_pedidoprov2` FOREIGN KEY (id_empresa,id_proveedor) REFERENCES proveedor (id_empresa,id_proveedor) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_fac_pedidoprov3` FOREIGN KEY (id_descuento) REFERENCES descuento (id_descuento) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table facprov_lin(
	id_numdoc smallint unsigned not null,
    id_empresa smallint unsigned not null,
    id_articulo smallint unsigned not null,
    cantidad int not null default 0,
    precio decimal(6,2) not null default 0,
    id_linea smallint unsigned not null,
    id_descuento smallint unsigned not null,
    id_impuesto smallint unsigned not null,
    importe decimal(6,2) not null default 0,
    id_almacen smallint unsigned not null,
    primary key (id_empresa,id_numdoc,id_linea),
    -- CONSTRAINT `fk_pedido_lin1` FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa) ON DELETE RESTRICT ON UPDATE CASCADE,
	-- CONSTRAINT `fk_pedido_lin2` FOREIGN KEY (id_empresa,id_articulo) REFERENCES articulo (id_empresa,id_articulo) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_fact_linprov3` FOREIGN KEY (id_impuesto) REFERENCES impuesto (id_impuesto) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_fact_linprov4` FOREIGN KEY (id_descuento) REFERENCES descuento (id_descuento) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_fact_linprov5` FOREIGN KEY (id_empresa,id_numdoc) REFERENCES facprov_cab (id_empresa,id_numdoc) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_fact_linprov6` FOREIGN KEY (id_empresa,id_articulo,id_almacen) REFERENCES articulos_alma (id_empresa,id_articulo,id_almacen) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table facprov_imp(
	id_numdoc smallint unsigned not null,
    id_empresa smallint unsigned not null,
	id_impuesto smallint unsigned not null,
    importe_lins decimal(6,2) not null default 0,
    importe_desc decimal(6,2) not null default 0,
    importe_total decimal(6,2) not null default 0,
    primary key (id_empresa,id_numdoc),
    CONSTRAINT `fk_fact_impprov1` FOREIGN KEY (id_impuesto) REFERENCES impuesto (id_impuesto) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_fact_impprov3` FOREIGN KEY (id_empresa,id_numdoc) REFERENCES faccl_cab (id_empresa,id_numdoc) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
