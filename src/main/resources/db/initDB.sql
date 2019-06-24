DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS meals;
DROP SEQUENCE IF EXISTS global_seq;
DROP SEQUENCE IF EXISTS global_meal_seq;

CREATE SEQUENCE global_seq START WITH 100000;
CREATE SEQUENCE global_meal_seq START WITH 100000;

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL,
  calories_per_day INTEGER DEFAULT 2000    NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE meals
(
   user_id         INTEGER                 NOT NULL,
   id              INTEGER PRIMARY KEY DEFAULT nextval('global_meal_seq'),
   datetime        TIMESTAMP DEFAULT now() NOT NULL,
   description     VARCHAR                 NOT NULL,
   calories        INTEGER                 NOT NULL,
   CONSTRAINT user_time_idx UNIQUE (user_id, datetime),
   FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE INDEX meals_userid_datetime_idx ON meals (user_id, datetime);
CREATE INDEX meals_id_userid_idx ON meals (id, user_id);
