package com.pizzaria.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class SenhaUtil {

    private static final String SALT = "pizzaria-natan-2026";

    private SenhaUtil() {
    }

    public static String criptografar(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Senha não pode ser vazia.");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((SALT + senha).getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo de criptografia indisponível.", e);
        }
    }

    public static boolean verificar(String senhaInformada, String senhaArmazenada) {
        if (senhaInformada == null || senhaArmazenada == null) {
            return false;
        }
        return criptografar(senhaInformada).equals(senhaArmazenada);
    }
}
