package com.paper.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    public static void zipFolder(String sourceFolder, String zipFilePath) {
        try {
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            addFolderToZip("", sourceFolder, zos);

            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addFolderToZip(String basePath, String folderPath, ZipOutputStream zos) throws IOException {
        File folder = new File(folderPath);
        for (String fileName : folder.list()) {
            if (basePath.equals("")) {
                addFileToZip(folder.getName(), folderPath + "/" + fileName, zos);
            } else {
                addFileToZip(basePath + "/" + folder.getName(), folderPath + "/" + fileName, zos);
            }
        }
    }

    private static void addFileToZip(String path, String srcFile, ZipOutputStream zos) throws IOException {
        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zos);
        } else {
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(srcFile);
            zos.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
            fis.close();
        }
    }
}
