package online.javaclass.bookstore.data.entities;

public enum Cover {
    SOFT("soft"),
    HARD("hard"),
    SPECIAL("special");

    private final String title;

    Cover(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
