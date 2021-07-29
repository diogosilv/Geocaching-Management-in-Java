public enum Tipo {
    BASIC("basic"),
    PREMIUM("premium"),
    ADMIN("admin");

    private String tipoString;

    Tipo(String tipo) {
        tipoString = tipo;
    }

    /**
     * Converter tipo de String para Tipo
     * @param tipoString
     * @return
     */
    public static Tipo fromString(String tipoString) {
        for (Tipo tipo : Tipo.values()) {
            if (tipo.tipoString.equalsIgnoreCase(tipoString)) {
                return tipo;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return tipoString;
    }
}
