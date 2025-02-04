package com.brito.autentication.util.cripto;

public class Cripto {

    /**
     * Class responsible for encoding passwords from data generated by Mockaroo
     * and creating insertion scripts for the `tb_user` table in the database.
     *
     * Purpose:
     * Transform raw data into a secure format, aligned with the security model
     * adopted in the database.
     *
     * How it works:
     *
     * - Input:
     *   The file `mockarooData.txt` must contain CSV data generated by Mockaroo in the following format:
     *   - Only 3 fields: username (email), password, and CPF (preferably valid).
     *   - The fields must be enclosed in parentheses and separated by commas.
     *   Example: (user@example.com, password123, 12345678901)
     *
     * - Execution:
     *   Simply call the single method of this class to process the data.
     *
     * - Output:
     *   The file `migration.sql` will be generated with the formatted insertion script,
     *   ready to be executed in the database.
     */
    public static void main(String[] args) {

        TheGreatFilter algorithm = new TheGreatFilter();
        algorithm.run();

        Encode encode = new Encode(algorithm);
        encode.run();

    }
}
