-- Catálogo de Cursos - conferência do banco pelo pgAdmin 4
--
-- Execute este arquivo somente depois de iniciar CatalogocursosApplication.
-- O Query Tool deve estar conectado ao banco "catalogocursos_dev".

SELECT current_database() AS banco_atual;

SELECT id, author, filename, dateexecuted, exectype
FROM databasechangelog
ORDER BY orderexecuted;

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
ORDER BY table_name;

SELECT
    c.conname AS restricao,
    pg_get_constraintdef(c.oid) AS definicao
FROM pg_constraint c
WHERE c.conrelid IN ('instrutor'::regclass, 'curso'::regclass)
ORDER BY c.conrelid::regclass::text, c.conname;

