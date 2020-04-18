package com.adobe.aem.guides.nirvana.core.practice;

public class StringPractice {
    public static void main(String[] args) {
        getHighlighted("Anchal");

        System.out.println(getHighlighted("Anchal"));

        getHighlightedText("My name is Anchal Garg", "Anchal");

        System.out.println(getHighlightedText("My name is Anchal Garg", "Anchal"));
    }

    public static String getHighlighted(String highlightedName) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<b>");
        stringBuilder.append(highlightedName);
        stringBuilder.append("</b>");

        return stringBuilder.toString();
    }

    public static String getHighlightedText(String toBeHighlighted, String highlighted) {

        StringBuilder str = new StringBuilder();

        if (toBeHighlighted.contains(highlighted)) {
            str.append("<b>");
            str.append(highlighted);
            str.append("</b>");

            toBeHighlighted.replace(highlighted, str);
        }

        return toBeHighlighted.replace(highlighted, str);
    }
}

