package online.javaclass.bookstore.service.dto;

import java.util.Locale;

public class UserPreferencesDto {
    private Long id;
    private Long userId;
    private Locale preferredLocale;

    public UserPreferencesDto() {
    }

    public Locale getPreferredLocale() {
        return preferredLocale;
    }

    public void setPreferredLocale(Locale preferredLocale) {
        this.preferredLocale = preferredLocale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserPreferencesDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", preferredLocale=" + preferredLocale +
                '}';
    }
}
