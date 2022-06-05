package com.project.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logging {
    private static String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        return formatter.format(date);
    }

    public static void errorLog(String error) throws IOException {
        File res = new File("errors.txt");
        boolean success = res.createNewFile();
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(res, true), StandardCharsets.UTF_8)) {
            writer.write(getDate() + ": " + error + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
