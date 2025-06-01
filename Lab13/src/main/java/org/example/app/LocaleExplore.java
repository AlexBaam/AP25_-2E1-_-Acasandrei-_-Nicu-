package org.example.app;

import lombok.Getter;
import lombok.Setter;
import org.example.com.DisplayLocales;
import org.example.com.Info;
import org.example.com.SetLocale;
import java.util.*;

@Setter
@Getter
public class LocaleExplore {
    // Salvam default aplicatiei, exemplu engleza
    private static Locale currentLocale = Locale.getDefault();

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static void setCurrentLocale(Locale locale) {
        currentLocale = locale;
    }

    public static void main(String[] args) {
        // Scanner ptr scris in terminal in loop
        Scanner scanner = new Scanner(System.in);

        while (true) { // Un loop infinit
            ResourceBundle messages = ResourceBundle.getBundle("res.Messages", currentLocale);
            System.out.print(messages.getString("prompt") + " ");

            String input = scanner.nextLine().trim();
            String[] tokens = input.split("\\s+");

            if (tokens.length == 0) continue;

            switch (tokens[0].toLowerCase()) {
                case "display":
                    if (tokens.length > 1 && tokens[1].equalsIgnoreCase("locales")) {
                        new DisplayLocales().execute(currentLocale);
                    } else {
                        System.out.println(messages.getString("invalid"));
                    }
                    break;

                case "set":
                    if (tokens.length > 2 && tokens[1].equalsIgnoreCase("locale")) {
                        new SetLocale().execute(tokens[2]);
                    } else {
                        System.out.println(messages.getString("invalid"));
                    }
                    break;

                case "info":
                    String tag = (tokens.length > 1) ? tokens[1] : null;
                    new Info().execute(tag);
                    break;

                default:
                    System.out.println(messages.getString("invalid"));
            }
        }
    }
}