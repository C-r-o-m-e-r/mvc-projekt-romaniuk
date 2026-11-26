package pl.merito.project.controller;

// Pomocnicza klasa kodująca znaki specjalne HTML w celu ochrony przed XSS.
final class HtmlUtils {

    private HtmlUtils() {
    }

    static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
