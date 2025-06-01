package org.example.com;

import java.util.*;

public class DisplayLocales {
    public void execute(Locale currentLocale) {
        ResourceBundle messages = ResourceBundle.getBundle("res.Messages", currentLocale);
        System.out.println(messages.getString("locales"));

        for (Locale locale : Locale.getAvailableLocales()) {
            System.out.println("  " + locale.toString() + ": " +
                    locale.getDisplayLanguage(currentLocale) + " (" +
                    locale.getDisplayCountry(currentLocale) + ")");
        }
    }
}