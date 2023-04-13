package online.javaclass.bookstore.data.converters.preferenceConverters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Locale;

@Converter
public class LocaleConverter implements AttributeConverter<Locale, String> {
    @Override
    public String convertToDatabaseColumn(Locale locale) {
        return locale.getLanguage();
    }

    @Override
    public Locale convertToEntityAttribute(String s) {
        return new Locale(s);
    }
}
