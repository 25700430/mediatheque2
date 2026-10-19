import java.util.List;

public class FormatJson implements FormatRapport {
    @Override
    public String formater(List<Document> docs) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < docs.size(); i++) {
            if (i > 0) {
                json.append(',');
            }
            Document document = docs.get(i);
            json.append("{\"titre\":")
                    .append(chaineJson(document.getTitre()))
                    .append(",\"id\":")
                    .append(document.getId())
                    .append('}');
        }
        return json.append(']').toString();
    }

    private String chaineJson(String valeur) {
        if (valeur == null) {
            return "null";
        }

        StringBuilder json = new StringBuilder("\"");
        for (int i = 0; i < valeur.length(); i++) {
            char caractere = valeur.charAt(i);
            switch (caractere) {
                case '"' -> json.append("\\\"");
                case '\\' -> json.append("\\\\");
                case '\b' -> json.append("\\b");
                case '\f' -> json.append("\\f");
                case '\n' -> json.append("\\n");
                case '\r' -> json.append("\\r");
                case '\t' -> json.append("\\t");
                default -> {
                    if (caractere < 0x20) {
                        json.append(String.format("\\u%04x", (int) caractere));
                    } else {
                        json.append(caractere);
                    }
                }
            }
        }
        return json.append('"').toString();
    }
}