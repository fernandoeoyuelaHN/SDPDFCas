package com.sdpdfcas.downloader;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfDownloader {

    public static File download(Context context, String urlString) {

        try {

            URL url = new URL(urlString);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream inputStream = connection.getInputStream();

            File pdfFile = new File(context.getCacheDir(), "temp.pdf");

            FileOutputStream outputStream = new FileOutputStream(pdfFile);

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.flush();
            outputStream.close();

            inputStream.close();

            connection.disconnect();

            return pdfFile;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }

    }

}