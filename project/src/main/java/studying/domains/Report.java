package studying.domains;

import org.jetbrains.annotations.NotNull;

/**
 * Отчет о работе системы.
 *
 * @param title название отчета
 * @param content наполнение отчета
 */
public record Report(String title, String content) {
    @Override
    public @NotNull String toString() {
        return String.format("%s\n\n%s", title, content);
    }
}