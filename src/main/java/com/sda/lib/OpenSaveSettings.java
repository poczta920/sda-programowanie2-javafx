package com.sda.lib;

import jdk.nashorn.internal.objects.annotations.Property;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class OpenSaveSettings {
    static String fileName = "./saves/file.properties";

    public static void  saveConfig(ConfigDTO configDTO){
        Path path = Paths.get(fileName);

        Properties properties = new Properties();
        try {
            FileWriter fw = new FileWriter(fileName);

            properties.setProperty("userName", configDTO.getUserName());
            properties.setProperty("userLocalization", configDTO.getLocalization());
            properties.setProperty("userLimitCalories", String.valueOf(configDTO.getLimitCalories()));
            properties.store(fw, "");

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static ConfigDTO  openConfig(){
        ConfigDTO configDTO = new ConfigDTO();

        Properties properties = new Properties();
        try {
            FileReader fr = new FileReader(fileName);
            properties.load(fr);
            fr.close();

            configDTO.setUserName(properties.getProperty("userName"));
            configDTO.setLocalization(properties.getProperty("userLocalization"));
            configDTO.setLimitCalories(Integer.parseInt(properties.getProperty("userLimitCalories")));


        } catch (IOException e) {
            e.printStackTrace();
        }

        return configDTO;
    }



}
