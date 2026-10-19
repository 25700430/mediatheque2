import java.util.List;

public class FormatCsv implements FormatRapport {
    @Override
    public String formater(List<Document> docs) {
        StringBuilder sb = new StringBuilder();
        for (Document document : docs) {
            sb.append(document.getTitre())
                    .append(';')
                    .append(document.getId())
                    .append('\n');
        }
        return sb.toString();
    }
}
