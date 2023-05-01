package com.example.gymquest.Language;

import com.example.gymquest.R;

import java.util.ArrayList;
import java.util.List;

public class Languages {
    //new languages will be added here to appear on the spinner!
    public static List<Language> getLanguages(){
        List<Language> languages = new ArrayList<>();

        Language english = new Language();
        english.setName("English");
        english.setImage(R.drawable.en_flag);
        english.setLanguage_code("en");
        languages.add(english);

        Language french = new Language();
        french.setName("Francais");
        french.setImage(R.drawable.fr_flag);
        french.setLanguage_code("fr");
        languages.add(french);

        return languages;
    }

}
