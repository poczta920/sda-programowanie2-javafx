package com.sda.lib;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class ConsumedCalories {
    private static final String fileName = "./saves/consumed.calories";
    private static final String fieldName = "consumerCalories";

    public static void saveCalory(int consumedCalories) {
        Properties properties = new Properties();
        properties.setProperty(fieldName, String.valueOf(consumedCalories));
        properties.setProperty("date", LocalDate.now().toString());

        try {
            FileWriter fw = new FileWriter(fileName);
            properties.store(fw, "");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int getCalory() {
        Properties properties = new Properties();
        LocalDate date;
        try {
            FileReader fr = new FileReader(fileName);
            properties.load(fr);
            fr.close();

            date = LocalDate.parse(properties.getProperty("date"));

            //date = LocalDate.now();
            if(date.isBefore(LocalDate.now())){
                return 0;
            }

            return Integer.parseInt(properties.getProperty(fieldName));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
