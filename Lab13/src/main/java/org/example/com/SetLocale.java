package org.example.com;

import lombok.Getter;
import lombok.Setter;
import org.example.app.LocaleExplore;
import java.text.MessageFormat;
import java.util.*;

@Setter
@Getter
public class SetLocale {
    public void execute(String tag) {
        try {
            Locale newLocale = Locale.forLanguageTag(tag);
            LocaleExplore.setCurrentLocale(newLocale);
            ResourceBundle messages = ResourceBundle.getBundle("res.Messages", newLocale);
            System.out.println(MessageFormat.format(messages.getString("locale.set"), newLocale.toString()));
        } catch (Exception e) {
            System.out.println("Invalid locale tag.");
        }
    }
}