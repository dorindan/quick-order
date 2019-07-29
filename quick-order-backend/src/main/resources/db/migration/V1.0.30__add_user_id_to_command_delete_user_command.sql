DROP TABLE user_command;
ALTER TABLE command ADD user_id VARCHAR(40);
ALTER TABLE command
ADD CONSTRAINT FK_command_user FOREIGN KEY (user_id) REFERENCES users (id);
