package com.litvinova.tools;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class EmailCreator {

    private static final String EMAIL_MASK = "+wpt@wriketask.qaa";
    private static final int MAX_LENGTH_RANDOM_EMAIL_PART = 60;

    public static String getRandomEmail() {

        int lengthRandomEmailPart = new Random().nextInt(MAX_LENGTH_RANDOM_EMAIL_PART) + 1;

        String generatedString = RandomStringUtils.randomAlphanumeric(lengthRandomEmailPart);

        return generatedString + EMAIL_MASK;
    }

}
