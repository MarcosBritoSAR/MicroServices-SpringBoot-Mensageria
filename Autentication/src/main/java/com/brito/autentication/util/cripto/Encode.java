package com.brito.autentication.util.cripto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Encode {

    public Encode(TheGreatFilter obj) {

        if (!obj.flag) {
            throw new IllegalArgumentException("Previous stage is not completed");
        }

    }

    public void run() {

        String origin = "src/main/java/com/brito/autentication/util/cripto/migration.sql";
        String destination = "src/main/java/com/brito/autentication/util/cripto/migration.sql";

        List<String> rawData = readFile(origin);
        List<List<String>> fragmentsOfData = segregate(rawData);
        List<String> readyData = encrypt(new BCryptPasswordEncoder(), fragmentsOfData);
        writeFile(readyData, destination);
    }

    private List<String> readFile(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {

            List<String> rawData = new ArrayList<>();
            String line = bufferedReader.readLine();

            while (line != null) {
                rawData.add(line);
                line = bufferedReader.readLine();
            }

            return rawData;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private List<List<String>> segregate(List<String> rawData) {

        List<String> username = new ArrayList<>();
        List<String> password = new ArrayList<>();
        List<String> cpf = new ArrayList<>();

        for (int i = 0, p1, p2; i < rawData.size(); i++) {

            p1 = rawData.get(i).indexOf('\'');
            p2 = rawData.get(i).indexOf('\'', p1 + 1);

            username.add(rawData.get(i).substring(p1 + 1, p2));

            p1 = rawData.get(i).indexOf('\'', p2 + 1);
            p2 = rawData.get(i).indexOf('\'', p1 + 1);

            password.add(rawData.get(i).substring(p1 + 1, p2));

            p1 = rawData.get(i).indexOf('\'', p2 + 1);
            p2 = rawData.get(i).indexOf('\'', p1 + 1);

            cpf.add(rawData.get(i).substring(p1 + 1, p2));

        }

        return new ArrayList<>(Arrays.asList(username, password, cpf));

    }

    private List<String> encrypt(PasswordEncoder encode, List<List<String>> fragmentsOfData) {

        List<String> result = new ArrayList<>();

        for (int i = 0; i < fragmentsOfData.get(0).size(); i++) {

            result.add(String.format("('%s','%s','%s'),",
                    fragmentsOfData.get(0).get(i),
                    encode.encode(fragmentsOfData.get(1).get(i)),
                    fragmentsOfData.get(2).get(i)));
        }


        int indexOfString = result.size() - 1;
        int indexOfLastCharacter = result.get(result.size() - 1).length() - 1;
        result.add(result.remove(indexOfString).substring(0, indexOfLastCharacter ) + ";");

        return result;
    }

    private void writeFile(List<String> readyData, String destination) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destination))) {

            bufferedWriter.write("insert into tb_user (username, password, cpf) values");
            bufferedWriter.newLine();

            readyData.forEach(
                    s -> {

                        try {
                            bufferedWriter.write(s);
                            bufferedWriter.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
