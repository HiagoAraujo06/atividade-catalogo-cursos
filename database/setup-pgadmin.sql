-- Catálogo de Cursos - criação do banco pelo pgAdmin 4
--
-- Abra o Query Tool conectado a qualquer banco do servidor PostgreSQL local
-- usando um usuário com permissão para criar bancos.
-- Execute este arquivo sozinho e uma única vez (F5).
-- O PostgreSQL não permite CREATE DATABASE dentro de uma transação.

CREATE DATABASE catalogocursos_dev
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TEMPLATE = template0;

