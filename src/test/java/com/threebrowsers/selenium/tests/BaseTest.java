package com.threebrowsers.selenium.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import java.io.File;

public abstract class BaseTest {
    protected static ExtentReports extent;
    protected static ExtentTest test;

    /**
     * Elimina la carpeta indicada del root del proyecto si existe.
     * @param folderName Nombre de la carpeta a eliminar
     */
    protected static void cleanFolder(String folderName) {
        File folder = new File(System.getProperty("user.dir"), folderName);
        if (folder.exists()) {
            deleteFolderRecursively(folder);
            System.out.println("[INFO] Carpeta '" + folderName + "' eliminada correctamente.");
        } else {
            System.out.println("[INFO] Carpeta '" + folderName + "' no existe, nada que eliminar.");
        }
    }

    /**
     * Elimina un archivo o carpeta recursivamente.
     * @param file Archivo o carpeta a eliminar
     */
    private static void deleteFolderRecursively(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteFolderRecursively(f);
                }
            }
        }
        file.delete();
    }
}
