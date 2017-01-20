package com.flyingsh4rk.org;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by pthanhtrung on 1/12/2017.
 */
public class IoMain {
    final static private String hello = "Hello java IO API";
    public static void main(String args[]){
        System.out.println("Java 8 IO examples");
        byte[] serializedString = hello.getBytes(StandardCharsets.UTF_8);

        File file = new File("./hello.txt");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file); BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)){
            bufferedOutputStream.write(serializedString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
