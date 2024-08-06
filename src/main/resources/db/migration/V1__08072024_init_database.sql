CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS permissao (
    id SERIAL PRIMARY KEY,
    nome text NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
    id SERIAL PRIMARY KEY,
    nome text NOT NULL,
    email text NOT NULL,
    senha text NOT NULL,
    uuid UUID DEFAULT uuid_generate_v4(),
    foto text,
    descricao text,
    permissao bigint REFERENCES permissao(id),
    data_cadastro timestamp with time zone DEFAULT now(),
    ativo boolean DEFAULT true
);


CREATE TABLE IF NOT EXISTS amizade (
    id SERIAL PRIMARY KEY,
    id_usuario1 bigint REFERENCES usuario(id),
    id_usuario2 bigint REFERENCES usuario(id),
    ativo boolean DEFAULT true,
    data_amizade timestamp with time zone DEFAULT now()
);


CREATE TABLE IF NOT EXISTS assunto (
   id SERIAL PRIMARY KEY,
   descricao text,
   ativo boolean DEFAULT true
);


CREATE TABLE IF NOT EXISTS postagem (
    id SERIAL PRIMARY KEY,
    conteudo text NOT NULL,
    data_postagem timestamp with time zone DEFAULT now(),
    uuid UUID DEFAULT uuid_generate_v4(),
    anexo boolean,
    valor_anexo text,
    id_usuario bigint REFERENCES usuario(id),
    id_assunto bigint REFERENCES assunto(id)
);


CREATE TABLE IF NOT EXISTS usuario_assunto (
    id SERIAL PRIMARY KEY,
    id_usuario bigint REFERENCES usuario(id),
    id_assunto bigint REFERENCES assunto(id),
    ativo boolean
);

INSERT INTO permissao (nome) VALUES ('ADMIN');
INSERT INTO permissao (nome) VALUES ('USUARIO');

INSERT INTO usuario (nome, email, senha, permissao, ativo)
    VALUES ('admin', 'admin@admin', '$2a$12$D34yoj4ZnMg.tWILXNp8qeMwEMjFaDJJ.G2z2ZT855/v4xupISE3q', 1, true);