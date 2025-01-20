package com.brito.autentication.util.cripto;

import java.io.*;
import java.util.*;


public class TheGreatFilter {

    public boolean flag;

    public void run() {

        String origin = "src/main/java/com/brito/autentication/util/cripto/mockarooData.txt";
        String destination = "src/main/java/com/brito/autentication/util/cripto/migration.sql";


        //----

        List<String> list = readFile(origin);

        List<String> filteredList = segregated(list);

        flag = writeFile(filteredList, destination);


    }


    public List<String> readFile(String path) {

        try (BufferedReader fileReader = new BufferedReader(new FileReader(path))) {

            List<String> result = new ArrayList<>();

            String line = fileReader.readLine();

            while (line != null) {
                result.add(line);
                line = fileReader.readLine();
            }

            return result;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> segregated(List<String> list) {

        List<String> result = new ArrayList<>();

        list.forEach(
                l -> {

                    if (rules(l)) {
                        result.add(l);
                    }

                }
        );

        return result;
    }

    public boolean rules(String str) {

        int contador = 0;

        for (char c : str.toCharArray()) {
            if (c == '(' || c == ')') {
                contador++;
            }
        }

        if (contador != 2) {
            return false;
        }

        contador = 0;

        for (char c : str.toCharArray()) {
            if (c == ',') {
                contador++;
            }
        }

        if (contador != 3 && contador != 2) {
            return false;
        }

        contador = 0;

        for (char c : str.toCharArray()) {
            if (c == '\'') {
                contador++;
            }
        }

        if (contador != 6) {
            return false;
        }

        return true;
    }

    public boolean writeFile(List<String> data, String path) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path,true))) {

            data.forEach(
                    str -> {
                        try {

                            bw.write(str);
                            bw.newLine();

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;

    }
}
