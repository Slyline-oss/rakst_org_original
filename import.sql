ALTER TABLE application_user ADD COLUMN IF NOT EXISTS allow_emails BOOLEAN;
UPDATE application_user SET allow_emails = TRUE;