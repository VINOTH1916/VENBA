package com.javaproject.venbakural;

//import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Venba {
    public static int j = 0;
    public static int k=0;
    public String[] nernirai = new String[1000000];
    public static int[] tonepattern = new int[1000000];
    private static final Map<String, Double> mathiraiMap = new HashMap<>();



    static {
        List<String> tamilLetters = new ArrayList<>(Arrays.asList(
                "ஃ", "அ", "ஆ", "இ", "ஈ", "உ", "ஊ", "எ", "ஏ", "ஒ", "ஓ",
                "க", "கா", "கி", "கீ", "கு", "கூ", "கெ", "கே", "கொ", "கோ",
                "ங", "ஙா", "ஙி", "ஙீ", "ஙு", "ஙூ", "ஙெ", "ஙே", "ஙொ", "ஙோ",
                "ச", "சா", "சி", "சீ", "சு", "சூ", "செ", "சே", "சொ", "சோ",
                "ஞ", "ஞா", "ஞி", "ஞீ", "ஞு", "ஞூ", "ஞெ", "ஞே", "ஞொ", "ஞோ",
                "ட", "டா", "டி", "டீ", "டு", "டூ", "டெ", "டே", "டொ", "டோ",
                "ண", "ணா", "ணி", "ணீ", "ணு", "ணூ", "ணெ", "ணே", "ணொ", "ணோ",
                "த", "தா", "தி", "தீ", "து", "தூ", "தெ", "தே", "தொ", "தோ",
                "ந", "நா", "நி", "நீ", "நு", "நூ", "நெ", "நே", "நொ", "நோ",
                "ப", "பா", "பி", "பீ", "பு", "பூ", "பெ", "பே", "பொ", "போ",
                "ம", "மா", "மி", "மீ", "மு", "மூ", "மெ", "மே", "மொ", "மோ",
                "ய", "யா", "யி", "யீ", "யு", "யூ", "யெ", "யே", "யொ", "யோ",
                "ர", "ரா", "ரி", "ரீ", "ரு", "ரூ", "ரெ", "ரே", "ரொ", "ரோ",
                "ல", "லா", "லி", "லீ", "லு", "லூ", "லெ", "லே", "லொ", "லோ",
                "வ", "வா", "வி", "வீ", "வு", "வூ", "வெ", "வே", "வொ", "வோ",
                "ழ", "ழா", "ழி", "ழீ", "ழு", "ழூ", "ழெ", "ழே", "ழொ", "ழோ",
                "ள", "ளா", "ளி", "ளீ", "ளு", "ளூ", "ளெ", "ளே", "ளொ", "ளோ",
                "ற", "றா", "றி", "றீ", "று", "றூ", "றெ", "றே", "றொ", "றோ",
                "ன", "னா", "னி", "னீ", "னு", "னூ", "னெ", "னே", "னொ", "னோ",
                "ஐ", "கை", "ஙை", "சை", "ஞை", "டை", "ணை", "தை", "நை",
                "பை", "மை", "யை", "ரை", "லை", "வை", "ழை", "ளை", "றை", "னை",
                "ஔ", "கௌ", "ஙௌ", "சௌ", "ஞௌ", "டௌ", "ணௌ", "தௌ", "நௌ", "பௌ",
                "மௌ", "யௌ", "ரௌ", "லௌ", "வௌ", "ழௌ", "ளௌ", "றௌ", "னௌ",
                "க்", "ங்", "ச்", "ஞ்", "ட்", "ண்", "த்", "ந்", "ப்", "ம்",
                "ய்", "ர்", "ல்", "வ்", "ழ்", "ள்", "ற்", "ன்"
        ));



        mathiraiMap.put("ஃ", 0.0);
        int i = 1;
        while (tamilLetters.get(i) != "ஐ") {
            if (i % 2 == 0) {
                mathiraiMap.put(tamilLetters.get(i), 2.0);
            } else {
                mathiraiMap.put(tamilLetters.get(i), 1.0);
            }
            i++;
        }
        while (tamilLetters.get(i) != "க்") {
            mathiraiMap.put(tamilLetters.get(i), 2.0);
            i++;
        }
        while (tamilLetters.get(i) != "ன்") {
            mathiraiMap.put(tamilLetters.get(i), 0.5);
            i++;
        }
        mathiraiMap.put("ன்", 0.5);
    }

    public static List<String> splitToSyllables(String input) {
        List<String> syllables = new ArrayList<>();
        int length = input.length();

        for (int i = 0; i < length; i++) {
            char currentChar = input.charAt(i);

            if (i < length - 1 && isTamilCompound(input.charAt(i + 1))) {
                syllables.add(input.substring(i, i + 2));
                i++;
            } else {
                syllables.add(String.valueOf(currentChar));
            }
        }
        return syllables;
    }

    private static boolean isTamilCompound(char ch) {
        return (ch >= '\u0BBE' && ch <= '\u0BCD');
    }

    public static List<Double> getMathiraiValues(String input) {
        List<String> syllables = splitToSyllables(input);
        List<Double> result = new ArrayList<>();

        for (String syllable : syllables) {
            Double mathirai = mathiraiMap.get(syllable);
            if (syllable.equals(" ")) {
                mathirai = -1.0;
            }
            if (mathirai != null) {
                result.add(mathirai);
            } else {
                System.out.println("Unknown syllable: " + syllable);
            }
        }
        return result;
    }

    // Method to apply Nirai Ner rules to a list of values
    public static void applyNiraiNerRules(List<Double> values, List<String> analysis, Venba venbaInstance) {
        int i = 0;
        venbaInstance.nernirai[j] = "";
        while (i < values.size()) {

            if (i + 2 < values.size() && values.get(i) == 1.0 && values.get(i + 1) == 1.0 && values.get(i + 2) == 0.5) {
                analysis.add("நிரை");
                i += 3;
                venbaInstance.nernirai[j] += '1';
                tonepattern[k++]=1;
            } else if (i + 2 < values.size() && values.get(i) == 1.0 && values.get(i + 1) == 2.0 && values.get(i + 2) == 0.5) {
                analysis.add("நிரை");
                i += 3;
                venbaInstance.nernirai[j] += '1';
                tonepattern[k++]=1;
            } else if (i + 1 < values.size() && values.get(i) == 1.0 && values.get(i + 1) == 0.5) {
                analysis.add("நேர்");
                i += 2;
                venbaInstance.nernirai[j] += '0';
                tonepattern[k++]=0;
            } else if (i + 1 < values.size() && values.get(i) == 2.0 && values.get(i + 1) == 0.5) {
                analysis.add("நேர்");
                i += 2;
                venbaInstance.nernirai[j] += '0';
                tonepattern[k++]=0;
            } else if (i + 1 < values.size() && values.get(i) == 1.0 && values.get(i + 1) == 1.0) {
                analysis.add("நிரை");
                i += 2;
                venbaInstance.nernirai[j] += '1';
                tonepattern[k++]=1;
            } else if (i + 1 < values.size() && values.get(i) == 1.0 && values.get(i + 1) == 2.0) {
                analysis.add("நிரை");
                i += 2;
                venbaInstance.nernirai[j] += '1';
                tonepattern[k++]=1;
            } else if (i < values.size() && values.get(i) == 2.0) {
                analysis.add("நேர்");
                i++;
                venbaInstance.nernirai[j] += '0';
                tonepattern[k++]=0;
            } else if (i < values.size() && values.get(i) == 1.0) {
                analysis.add("நேர்");
                i++;
                venbaInstance.nernirai[j] += '0';
                tonepattern[k++]=0;
            } else {
                i++; // Move to the next value if no pattern matches
            }
        }
        j++;
    }



    public static boolean isVenba(List<String> nerniraiList) {

        int size = nerniraiList.size();

        // Ensure that the size is one of the valid Venba lengths
        if (!(size == 7 || size == 15 || size == 31 || size == 47)) {
            return false;
        }

        // Get the last word and its length
        String lastSir = nerniraiList.get(size - 1);
        int length = lastSir.length();

        // Check if the length of the last word is 1 or 2
        if (!(length == 1 || length == 2)) {
            return false;
        }
        if(lastSir.equals("01") || lastSir.equals("11") )
        {
            return false;
        }

        for (int i = 0; i < nerniraiList.size() - 1; i++) {
            String current = nerniraiList.get(i);
            String next = nerniraiList.get(i + 1);

            // If current string has length 3, it should end with '0' and next should start with '0'
            if (current.length() == 3) {
                if (current.charAt(2) != '0' || next.charAt(0) != '0') {
                    return false; // Condition for Venba not met
                }
            }
            // If current string has length 2, it should end with (0 or 1) and next should start with the opposite
            else if (current.length() == 2) {
                char lastChar = current.charAt(1);
                char expectedStartChar = (lastChar == '0') ? '1' : '0';

                if (next.charAt(0) != expectedStartChar) {
                    return false; // Condition for Venba not met
                }
            }
        }
        return true; // All conditions for Venba met
    }

    public static String printVenbaWords(List<String> nerniraiList) {
        StringBuilder venbaString = new StringBuilder(); // Use StringBuilder for efficient string concatenation

        for (String current : nerniraiList) {
            switch (current) {
                case "00":
                    venbaString.append("தேமா ");
                    break;
                case "10":
                    venbaString.append("புளிமா ");
                    break;
                case "01":
                    venbaString.append("கூவிளம் ");
                    break;
                case "11":
                    venbaString.append("கருவிளம் ");
                    break;
                case "000":
                    venbaString.append("தேமாங்காய் ");
                    break;
                case "100":
                    venbaString.append("புளிமாங்காய் ");
                    break;
                case "010":
                    venbaString.append("கூவிளங்காய் ");
                    break;
                case "110":
                    venbaString.append("கருவிளங்காய் ");
                    break;
            }
        }

        // Check the last string for specific rules
        String lastSir = nerniraiList.get(nerniraiList.size() - 1);
        switch (lastSir) {
            case "0":
                venbaString.append("நாள்");
                break;
            case "1":
                venbaString.append("மலர்");
                break;
            case "00":
                venbaString.append("காசு");
                break;
            case "10":
                venbaString.append("பிறப்பு");
                break;
        }

        return venbaString.toString(); // Return the final string
    }

}