--liquibase formatted sql

--changeset Alexey Korotin:0001

CREATE TABLE IF NOT EXISTS user_account
(
    id       UUID PRIMARY KEY,
    user_id  TEXT         NOT NULL,
    currency VARCHAR(10)  NOT NULL,
    balance  INTEGER      NOT NULL,
    name     VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS income_source
(
    id       UUID PRIMARY KEY,
    user_id  TEXT         NOT NULL,
    name     VARCHAR(100) NOT NULL,
    currency VARCHAR(10)  NOT NULL,
    amount   INTEGER      NOT NULL
);

CREATE TABLE IF NOT EXISTS expense_category
(
    id       UUID PRIMARY KEY,
    user_id  TEXT         NOT NULL,
    name     VARCHAR(100) NOT NULL,
    currency VARCHAR(10)  NOT NULL,
    amount   INTEGER      NOT NULL
);

CREATE TABLE IF NOT EXISTS income_transaction
(
    id        UUID PRIMARY KEY,
    user_id   TEXT        NOT NULL,
    type      VARCHAR(20) NOT NULL,
    status    VARCHAR(20) NOT NULL,
    comment   VARCHAR(100),
    timestamp TIMESTAMP   NOT NULL,
    currency  VARCHAR(10) NOT NULL,
    amount    INTEGER     NOT NULL,
    source_id UUID NOT NULL REFERENCES income_source(id),
    destination_id UUID NOT NULL REFERENCES user_account(id)
);

CREATE TABLE IF NOT EXISTS expense_transaction
(
    id        UUID PRIMARY KEY,
    user_id   TEXT        NOT NULL,
    type      VARCHAR(20) NOT NULL,
    status    VARCHAR(20) NOT NULL,
    comment   VARCHAR(100),
    timestamp TIMESTAMP   NOT NULL,
    currency  VARCHAR(10) NOT NULL,
    amount    INTEGER     NOT NULL,
    source_id UUID NOT NULL REFERENCES user_account(id),
    category_id UUID NOT NULL REFERENCES expense_category(id)
);

CREATE TABLE IF NOT EXISTS transfer_transaction
(
    id        UUID PRIMARY KEY,
    user_id   TEXT        NOT NULL,
    type      VARCHAR(20) NOT NULL,
    status    VARCHAR(20) NOT NULL,
    comment   VARCHAR(100),
    timestamp TIMESTAMP   NOT NULL,
    currency  VARCHAR(10) NOT NULL,
    amount    INTEGER     NOT NULL,
    source_id UUID NOT NULL REFERENCES user_account(id),
    destination_id UUID NOT NULL REFERENCES user_account(id)
);
