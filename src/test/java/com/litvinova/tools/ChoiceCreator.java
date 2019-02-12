package com.litvinova.tools;

import java.util.Random;

public class ChoiceCreator {

    public static int getRandomChoice(int number) {

        int choice = new Random().nextInt(number);

        return choice;
    }
}
