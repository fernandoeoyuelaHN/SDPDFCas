package com.sdpdfcas.download;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class PdfDownloadManager {

    public static boolean saveToDownloads(Context context, File pdfFile) {

        if (pdfFile == null || !pdfFile.exists()) {
            return false;
        }

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                ContentValues values = new ContentValues();
                values.put(MediaStore.Downloads.DISPLAY_NAME, pdfFile.getName());
                values.put(MediaStore.Downloads.MIME_TYPE, "application/pdf");
                values.put(MediaStore.Downloads.IS_PENDING, 1);

                Uri uri = context.getContentResolver().insert(
                        MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                        values
                );

                if (uri == null) {
                    return false;
                }

                OutputStream output =
                        context.getContentResolver().openOutputStream(uri);

                InputStream input =
                        new FileInputStream(pdfFile);

                copy(input, output);

                input.close();

                if (output != null) {
                    output.close();
                }

                values.clear();
                values.put(MediaStore.Downloads.IS_PENDING, 0);

                context.getContentResolver().update(uri, values, null, null);

            } else {

                File downloads =
                        Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS
                        );

                if (!downloads.exists()) {
                    downloads.mkdirs();
                }

                File destination =
                        new File(downloads, pdfFile.getName());

                copy(
                        new FileInputStream(pdfFile),
                        new FileOutputStream(destination)
                );

            }

            Toast.makeText(
                    context,
                    "PDF guardado en Descargas",
                    Toast.LENGTH_LONG
            ).show();

            return true;

        } catch (Exception e) {

            Toast.makeText(
                    context,
                    "No fue posible guardar el PDF",
                    Toast.LENGTH_LONG
            ).show();

            return false;

        }

    }

    private static void copy(InputStream input, OutputStream output)
            throws Exception {

        byte[] buffer = new byte[8192];

        int length;

        while ((length = input.read(buffer)) > 0) {

            output.write(buffer, 0, length);

        }

        output.flush();

    }

}
