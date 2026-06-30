package com.pizzaria.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CardapioSchemaMigration implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(CardapioSchemaMigration.class);

    private final JdbcTemplate jdbc;

    public CardapioSchemaMigration(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(ApplicationArguments args) {
        alterarImagemParaLongText("pizza");
        alterarImagemParaLongText("bebida");
    }

    private void alterarImagemParaLongText(String tabela) {
        try {
            jdbc.execute("ALTER TABLE `" + tabela + "` MODIFY COLUMN `imagem` LONGTEXT");
            log.info("Coluna {}.imagem atualizada para LONGTEXT", tabela);
        } catch (Exception ex) {
            log.warn("Não foi possível alterar {}.imagem para LONGTEXT: {}", tabela, ex.getMessage());
        }
    }
}
