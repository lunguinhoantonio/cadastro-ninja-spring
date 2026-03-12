CREATE TABLE tb_missoes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255),
    dificuldade VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE tb_cadastro (
    id BIGINT NOT NULL AUTO_INCREMENT,
    idade INTEGER,
    nome VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    img_url VARCHAR(255),
    nivel VARCHAR(255),
    missoes_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (missoes_id) REFERENCES tb_missoes(id)
);