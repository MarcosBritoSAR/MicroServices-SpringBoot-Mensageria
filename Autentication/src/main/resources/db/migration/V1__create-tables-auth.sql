CREATE TABLE tb_role
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    role VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_tb_role PRIMARY KEY (id)
);

CREATE TABLE tb_user
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username VARCHAR(255),
    cpf      VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT pk_tb_user PRIMARY KEY (id)
);

CREATE TABLE tb_user_role
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_tb_user_role PRIMARY KEY (role_id, user_id)
);

ALTER TABLE tb_role
    ADD CONSTRAINT uc_tb_role_role UNIQUE (role);

ALTER TABLE tb_user
    ADD CONSTRAINT uc_tb_user_cpf UNIQUE (cpf);

ALTER TABLE tb_user
    ADD CONSTRAINT uc_tb_user_username UNIQUE (username);

ALTER TABLE tb_user_role
    ADD CONSTRAINT fk_tbuserol_on_role FOREIGN KEY (role_id) REFERENCES tb_role (id);

ALTER TABLE tb_user_role
    ADD CONSTRAINT fk_tbuserol_on_user FOREIGN KEY (user_id) REFERENCES tb_user (id);