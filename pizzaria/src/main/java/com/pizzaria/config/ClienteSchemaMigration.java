package com.pizzaria.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Remove índices UNIQUE legados de CPF e usuário para permitir reutilização
 * após exclusão lógica da conta (ativo = false).
 */
@Component
public class ClienteSchemaMigration implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ClienteSchemaMigration.class);

    private final JdbcTemplate jdbc;

    public ClienteSchemaMigration(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(ApplicationArguments args) {
        removerIndicesUnicos("cliente", "cpf");
        removerIndicesUnicos("cliente", "usuario");
    }

    private void removerIndicesUnicos(String tabela, String coluna) {
        List<String> indices = jdbc.queryForList("""
                SELECT DISTINCT INDEX_NAME
                FROM INFORMATION_SCHEMA.STATISTICS
                WHERE TABLE_SCHEMA = DATABASE()
                  AND TABLE_NAME = ?
                  AND COLUMN_NAME = ?
                  AND NON_UNIQUE = 0
                  AND INDEX_NAME <> 'PRIMARY'
                """, String.class, tabela, coluna);

        for (String indice : indices) {
            jdbc.execute("ALTER TABLE `" + tabela + "` DROP INDEX `" + indice + "`");
            log.info("Índice único removido: {}.{} ({})", tabela, coluna, indice);
        }
    }
}
