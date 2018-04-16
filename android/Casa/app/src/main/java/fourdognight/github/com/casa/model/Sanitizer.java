package fourdognight.github.com.casa.model;

class Sanitizer {
    static String sanitize(String dbPath) {
        String sanitized = dbPath.replaceAll("[\\.#\\$\\[\\]]", "");
        if (!"".equals(sanitized)) {
            return sanitized;
        } else {
            return " ";
        }
    }
}
