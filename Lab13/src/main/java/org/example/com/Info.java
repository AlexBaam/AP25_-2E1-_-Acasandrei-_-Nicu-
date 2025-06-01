package org.example.com;

import org.example.app.LocaleExplore;
import java.text.*;
import java.util.*;

public class Info {
    public void execute(String tag) {
        // Folosim ca sa returnam ultimul tip de tag oferit (ro-RO)
        Locale locale = (tag == null) ? LocaleExplore.getCurrentLocale() : Locale.forLanguageTag(tag);
        ResourceBundle messages = ResourceBundle.getBundle("res.Messages", locale);

        System.out.println(MessageFormat.format(messages.getString("info"), locale.toString()));

        // Afisam tara si limba
        System.out.println("Country: " + locale.getDisplayCountry(locale) + " (" + locale.getDisplayCountry() + ")");
        System.out.println("Language: " + locale.getDisplayLanguage(locale) + " (" + locale.getDisplayLanguage() + ")");

        // Afisam valuta
        Currency currency = Currency.getInstance(locale);
        System.out.println("Currency: " + currency.getCurrencyCode() + " (" + currency.getDisplayName(locale) + ")");

        // Afisam informatii despre data si luna
        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        System.out.println("Week Days: " + String.join(", ", Arrays.copyOfRange(dfs.getWeekdays(), 1, 8)));
        System.out.println("Months: " + String.join(", ", Arrays.copyOfRange(dfs.getMonths(), 0, 12)));

        // Afisam data de astazi
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
        System.out.println("Today: " + dateFormat.format(new Date()));
    }
}