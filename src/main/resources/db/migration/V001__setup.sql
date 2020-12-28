CREATE TABLE owner_account (
    id bigint NOT NULL,
    birth date NOT NULL,
    cpf character varying(255) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    email character varying(255) NOT NULL,
    full_name character varying(255) NOT NULL,
    updated_at timestamp without time zone,
    CONSTRAINT owner_account_pkey PRIMARY KEY (id),
    CONSTRAINT uk_cpf UNIQUE (cpf),
    CONSTRAINT uk_email UNIQUE (email)
);

CREATE TABLE account (
    id bigint NOT NULL,
    agency character varying(255) NOT NULL,
    bank_code character varying(255) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    external_id uuid NOT NULL,
    account_number character varying(255) NOT NULL,
    security_code character varying(255) NOT NULL,
    account_type integer NOT NULL,
    updated_at timestamp without time zone,
    expiration_date timestamp without time zone NOT NULL,
    owner_account_id bigint NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT uk_external_id UNIQUE (external_id),
    CONSTRAINT fk_owner_account_id FOREIGN KEY (owner_account_id) REFERENCES owner_account (id)
);