package com.ad.block.osaadblock.utils;

import java.util.Locale;

public class LauguageUtil {



    public static boolean isTrkin() {
        String language = getLanguageEnv();

        if (language != null
                && (language.trim().equals("tr")))
            return true;
        else
            return false;
    }

    private static String getLanguageEnv() {
        Locale l = Locale.getDefault();
        String language = l.getLanguage();
        String country = l.getCountry().toLowerCase();
//        if ("zh".equals(language)) {
//            if ("cn".equals(country)) {
//                language = "zh-CN";
//            } else if ("tw".equals(country)) {
//                language = "zh-TW";
//            }
//        } else if ("pt".equals(language)) {
//            if ("br".equals(country)) {
//                language = "pt-BR";
//            } else if ("pt".equals(country)) {
//                language = "pt-PT";
//            }
//        }
        return language;
    }
}