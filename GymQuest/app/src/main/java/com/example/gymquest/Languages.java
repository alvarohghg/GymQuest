package com.example.gymquest;

import java.util.ArrayList;
import java.util.List;

public class Languages {
    public static List<Language> getLanguages(){
        List<Language> languages = new ArrayList<>();

        Language english = new Language();
        english.setName("English");
        english.setImage(R.drawable.en_flag);
        languages.add(english);

        Language french = new Language();
        french.setName("Francais");
        french.setImage(R.drawable.fr_flag);
        languages.add(french);

        return languages;
    }

}
