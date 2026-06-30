package com.pizzaria.util;

public final class CpfUtil {

    private CpfUtil() {
    }

    public static String normalizar(String cpf) {
        if (cpf == null) {
            return "";
        }
        return cpf.replaceAll("\\D", "");
    }

    public static String formatar(String cpf) {
        String numeros = normalizar(cpf);
        if (numeros.length() != 11) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        return numeros.substring(0, 3) + "."
                + numeros.substring(3, 6) + "."
                + numeros.substring(6, 9) + "-"
                + numeros.substring(9);
    }

    public static boolean mesmoCpf(String cpf1, String cpf2) {
        return normalizar(cpf1).equals(normalizar(cpf2));
    }
}
