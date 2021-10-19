package at.study.redmine.model.user;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum MailNotfication {
    ALL("О всех событиях во всех моих проектах"),
    ONLY_MY_EVENTS("Только для объектов которые я отслеживаю или в которых участвую"),
    ONLY_ASSIGNED("Только для объектов которые я отслеживаю или которые мне назначены"),
    ONLY_OWNER("Только для объектов которые я отслеживаю или для которых я владелец"),
    NONE("Нет событий");

    private final String descrpiton;

    public static MailNotfication of(String descrpiton) {

        return Stream.of(values())
                .filter(mailNotfication -> mailNotfication.descrpiton.equals(descrpiton))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найдем объект enum MailNotification"));

    }

}
