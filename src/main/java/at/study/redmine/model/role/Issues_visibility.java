package at.study.redmine.model.role;

public enum Issues_visibility {
    DEFAULT("Только общие задачи"),
    OWN("Задачи созданные или назначенные пользователю"),
    ALL("Все задачи");

    private final String issues_description;

    Issues_visibility(String issues_description) {
        this.issues_description = issues_description;
    }
}
