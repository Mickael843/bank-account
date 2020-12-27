CREATE TABLE owner (
    id bigint NOT NULL,
    birth date NOT NULL,
    cpf character varying(255) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    email character varying(255) NOT NULL,
    external_id uuid NOT NULL,
    full_name character varying(255) NOT NULL,
    updated_at timestamp without time zone,
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT uk_external_id UNIQUE (external_id),
    CONSTRAINT uk_cpf UNIQUE (cpf),
    CONSTRAINT uk_email UNIQUE (email)
)