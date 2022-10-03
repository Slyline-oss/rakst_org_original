package org.raksti.web.countriesAndLanguages;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class CountriesAndLanguages {

    public List<String> returnCountries() {
        List<String> list = new ArrayList<>();
        String [] countryCodes = Locale.getISOCountries();
        Locale obj;
        for (String countryCode: countryCodes) {
            obj = new Locale("", countryCode);
            list.add(obj.getDisplayCountry());
        }
        return list;
    }

    public List<String> returnLanguages() {
        List<String> list = new ArrayList<>();
        String [] languages = Locale.getISOLanguages();
        Locale obj;
        for (String language: languages) {
            obj = new Locale(language);
            list.add(obj.getDisplayLanguage());
        }
        return list;
    }


}
