

## INSTALACIÓN DOCKER

### 1. Habilitar características de windows

https://docs.docker.com/desktop/troubleshoot/topics/#virtualization

* Plataforma de máquina virtual
* Subsistema de windows para linux

### 2. Instalar WSL
https://learn.microsoft.com/en-us/windows/wsl/install

WSL es Windows Subsystem for Linux permite ejecutar Linux en Windows.

Abrir Powershell en Windows:

wsl --install 

wsl --list --online

wsl --install -d Ubuntu-22.04

wsl --list 

wsl.exe --update

### 3. Instalar Docker

https://docs.docker.com/desktop/troubleshoot/topics/#virtualization


## SQL 

* SELECT 

* INSERT 

* UPDATE 

* DELETE 

Base de datos demo: sakila.

Buscar en windows MySQL workbench

quien no le aparezca: 

C:\Program Files\MySQL\MySQL Workbench 8.0

buscar la aplicacion y con clic derecho enviar al escritorio, esto crea un acceso directo en el escritorio.

Alternativa a MySQL Workbench: DBeaver.


## VER BASES Y TABLAS

show databases;

show tables;

## SINTAXIS DML


### SELECT

#### PEDIR COLUMNAS

Consultar información. 

Especificar qué información queremos consultar: 

SELECT * FROM customer;
SELECT email FROM customer;
SELECT first_name, last_name FROM customer;

También se puede poner las cláusulas SELECT y FROM en minúsculas, pero es habitual ponerlas en MAYÚSCULAS para diferenciar de nombres de tablas y columnas.

select * from payment; 


#### FILTROS

WHERE

SELECT * FROM payment WHERE staff_id = 2;

-- payment amount menores que 5 euros: 
SELECT * FROM payment WHERE amount < 5;
-- payment amount mayores que 10 euros: 
SELECT * FROM payment WHERE amount > 10;
-- payment amount entre 5 y 10 euros
SELECT * FROM payment WHERE amount >= 5 AND amount <= 10;
SELECT * FROM payment WHERE amount BETWEEN 5 AND 10; -- incluye 5 y 10
SELECT * FROM payment WHERE payment_date BETWEEN '2005-07-01' AND '2005-07-15';

-- operador OR customer
SELECT * FROM address WHERE district = "california" OR district = 'Nagasaki';

-- Filtrar address que contengan la palabra Avenue
-- LIKE
select * from address WHERE address LIKE '%Aurora'; -- 0
select * from address WHERE address LIKE '%Aurora%'; -- 1
select * from customer where first_name LIKE 'A%'; -- 44
select * from customer where email LIKE '%@gmail.com';
select * from customer where email NOT LIKE '%@sakilacustomer.org';

## INSPECCIONAR TABLA

clic derecho sobre una tabla y seleccionar Table inspector > DDL.

## VER COLUMNAS DE UNA TABLA

show columns from film;

## INSERTAR DATOS EN UNA TABLA

SELECT * FROM city;
select * from country where country = 'Spain';
INSERT INTO city (city, country_id) VALUES('León', 87);
INSERT INTO city VALUES (602, 'Madrid', 87, '2023-01-01');

-- INSERT manualmente en film una nueva fila especificando columnas
insert into film 
(title, language_id, rental_duration, rental_rate, replacement_cost)
VALUES 
('ejemplo title', 1, 5, 2.99, 18.99);

