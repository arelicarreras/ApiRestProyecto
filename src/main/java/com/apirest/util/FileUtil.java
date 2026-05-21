package com.apirest.util;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtil {
    private static final String UPLOAD_DIR = "uploads/";
    public static String guardarArchivo(InputStream input, String nombreArchivo) {
        try {
            Path ruta = Paths.get(UPLOAD_DIR + nombreArchivo);
            Files.createDirectories(ruta.getParent());
            Files.copy(input, ruta, StandardCopyOption.REPLACE_EXISTING);
            return ruta.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
