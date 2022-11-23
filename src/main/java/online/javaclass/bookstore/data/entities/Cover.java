package online.javaclass.bookstore.data.entities;

public enum Cover {
    SOFT("SOFT"),
    HARD("HARD"),
    SPECIAL("SPECIAL");

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
