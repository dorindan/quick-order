ALTER TABLE menu_item
 ALTER COLUMN preparation_duration Type int USING Extract(MINUTE FROM preparation_duration)::integer;