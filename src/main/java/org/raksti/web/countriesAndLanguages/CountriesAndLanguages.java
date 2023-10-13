package org.raksti.web.countriesAndLanguages;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("deprecation")
@Component
public class CountriesAndLanguages {

    public List<String> returnCountries() {
        List<String> list = new ArrayList<>();
        String[] countryCodes = Locale.getISOCountries();
        Locale obj;
        for (String countryCode: countryCodes) {
            obj = new Locale("", countryCode);
            list.add(obj.getDisplayCountry());
        }
        Collections.sort(list);
        return list;
    }

    public List<String> returnLanguages() {
        List<String> list = new ArrayList<>();
        String[] languages = Locale.getISOLanguages();
        Locale obj;
        for (String language: languages) {
            obj = new Locale(language);
            list.add(obj.getDisplayLanguage());
        }
        Collections.sort(list);
        return list;
    }


}
