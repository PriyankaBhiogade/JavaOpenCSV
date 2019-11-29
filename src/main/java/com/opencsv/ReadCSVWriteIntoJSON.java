package com.opencsv;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ReadCSVWriteIntoJSON {
        private static final String CSV_FILE = "users-with-header.csv";
        private static final String GSON_FILE = "user.json";

        public static void main(String[] args) throws IOException {
            try {
                Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE));
                CsvToBeanBuilder<CSVUser> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                csvToBeanBuilder.withType(CSVUser.class);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBean<CSVUser> csvToBean = csvToBeanBuilder.build();
                List<CSVUser> csvUsers = csvToBean.parse();
                Gson gson= new Gson();
                String json =gson.toJson(csvUsers);
                FileWriter fileWriter = new FileWriter(GSON_FILE);
                fileWriter.write(json);
                fileWriter.close();
                BufferedReader bufferedReader = new BufferedReader(new FileReader(GSON_FILE));
                CSVUser[] userObj = gson.fromJson(bufferedReader,CSVUser[].class);
                List<CSVUser> csvUserList = Arrays.asList(userObj);
            } catch (IOException |IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

