package com.projetos.validadorboletos.repository;

public class BoletoUtils {

    public static String apenasDigitos(String s) {
        return s == null ? "" : s.replaceAll("\\D", "");
    }

    public static String identificarBanco(String linha) {
        String clean = apenasDigitos(linha);
        if (clean.length() < 3) return "Desconhecido";
        
        String codigo = clean.substring(0,3);

        return switch (codigo) {
            case "001" -> "Banco do Brasil";
            case "033" -> "Santander";
            case "104" -> "Caixa Econômica Federal";
            case "212" -> "Banco Original";
            case "237" -> "Bradesco";
            case "341" -> "Itaú";
            default -> "Código " + codigo;
        };
    }

    public static String linhaDigitavelParaCodigoBarras(String ld) {
        String clean = apenasDigitos(ld);

        if (clean.length() != 47) {
            throw new IllegalArgumentException("A linha digitável deve conter exatamente 47 números.");
        }

        String campo1 = clean.substring(0, 9);
        String campo2 = clean.substring(9, 19);
        String campo3 = clean.substring(19, 29);
        String campo4 = clean.substring(29, 30);
        String campo5 = clean.substring(30);

        return campo1.substring(0, 4) + campo4 + campo5 + campo1.substring(4) + campo2 + campo3;
    }

    public static String extrairVencimento(String codigoBarras) {
        String clean = apenasDigitos(codigoBarras);
        if (clean.length() != 44) return null;

        int fator = Integer.parseInt(clean.substring(5, 9));
        java.time.LocalDate base = java.time.LocalDate.of(1997,10,7);

        return base.plusDays(fator).toString();
    }

    public static String extrairValor(String codigoBarras) {
        String clean = apenasDigitos(codigoBarras);
        if (clean.length() != 44) return null;

        double valor = Double.parseDouble(clean.substring(9, 19)) / 100.0;

        return String.format("R$ %.2f", valor);
    }

    public static boolean validarLinha(String linha) {
        String clean = apenasDigitos(linha);
        return clean.length() == 47;
    }
}