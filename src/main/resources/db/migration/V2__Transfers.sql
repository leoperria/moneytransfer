CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS transfers
(
  id TEXT PRIMARY KEY DEFAULT uuid_generate_v4(),
  source_account_id  TEXT NOT NULL REFERENCES accounts(id),
  destination_account_id TEXT NOT NULL REFERENCES accounts(id),
  amount int NOT NULL DEFAULT 0 CHECK (amount > 0)
);

CREATE INDEX transfers_source_account_id_index ON transfers (source_account_id);
CREATE INDEX transfers_destination_account_id_index ON transfers (destination_account_id);