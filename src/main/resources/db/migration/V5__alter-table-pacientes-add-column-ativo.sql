ALTER TABLE pacientes ADD COLUMN ativo BOOLEAN;

UPDATE pacientes SET ativo = TRUE;

ALTER TABLE pacientes ALTER COLUMN ativo SET NOT NULL;
