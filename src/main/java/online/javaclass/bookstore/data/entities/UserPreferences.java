package online.javaclass.bookstore.data.entities;

import online.javaclass.bookstore.data.converters.preferenceConverters.LocaleConverter;

import javax.persistence.*;
import java.util.Locale;
import java.util.Objects;

@Entity
@Table(name = "users_preferences")
public class UserPreferences {
    @Id
    @Column(name = "id")
    private long id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "locale")
    @Convert(converter = LocaleConverter.class)
    private Locale preferredLocale;

    public UserPreferences() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Locale getPreferredLocale() {
        return preferredLocale;
    }

    public void setPreferredLocale(Locale preferredLocale) {
        this.preferredLocale = preferredLocale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPreferences that = (UserPreferences) o;
        return Objects.equals(user, that.user) && Objects.equals(preferredLocale, that.preferredLocale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, preferredLocale);
    }

    @Override
    public String toString() {
        return "UserPreferences{" +
                "user=" + user +
                ", locale=" + preferredLocale +
                '}';
    }
}
