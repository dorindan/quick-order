ALTER TABLE property DROP COLUMN start_program_time;
ALTER TABLE property DROP COLUMN end_program_time;
ALTER TABLE property DROP COLUMN nume_restaurant;

ALTER TABLE property ADD COLUMN name varchar(40);
ALTER TABLE property ADD COLUMN value varchar (40);