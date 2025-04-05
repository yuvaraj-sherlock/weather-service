package com.klm.weather.util;

/*
Program to display the column index of an Excel sheet based on a given column label.
The Excel column naming follows a sequence like: A to Z, then AA to AZ, BA to BZ, ..., and so on up to ZZ.

For example:

If the input is "T", the output will be 20.

If the input is "DG", the output will be 111.
*/

/*
Purpose of the Program
This program converts a string representing an Excel-style column label (e.g., "A", "Z", "AA", "DJ") into its
corresponding numeric index (e.g., "A" = 1, "Z" = 26, "AA" = 27, "DJ" = 114).
It simulates the way Excel assigns column numbers.
*/

public class ExcelProgram {

    public static void main(String[] args) {
        String excelAlphaInput = "DJ"; // Example: Excel column-like input
        System.out.println("Given Excel Alphabet Input: " + excelAlphaInput);

        int inputLength = excelAlphaInput.length();
        System.out.println("Length of Input: " + inputLength);

        int result = 0;

        if (inputLength == 1) {
            // Single letter (A-Z)
            result = findAlphabetIndex(excelAlphaInput.charAt(0));
        } else if (inputLength == 2) {
            // Two-letter input like AA, AB, DJ etc.
            int firstCharIndex = findAlphabetIndex(excelAlphaInput.charAt(0));
            int secondCharIndex = findAlphabetIndex(excelAlphaInput.charAt(1));
            result = (firstCharIndex * 26) + secondCharIndex;
        } else {
            // Optional: extend this logic for 3-letter Excel columns like "AAA"
            throw new IllegalArgumentException("Input length not supported. Only 1 or 2 characters allowed.");
        }

        System.out.println("Index of Input in Excel Format: " + result);
    }

    /**
     * Returns the position of the given alphabet in A-Z (1 to 26).
     * Accepts both uppercase and lowercase letters.
     */
    private static int findAlphabetIndex(char input) {
        if (!Character.isLetter(input)) {
            throw new IllegalArgumentException("Invalid character. Only A-Z or a-z allowed.");
        }

        // Normalize to uppercase and compute position (A=1, B=2, ..., Z=26)
        return Character.toUpperCase(input) - 'A' + 1;
    }
}

