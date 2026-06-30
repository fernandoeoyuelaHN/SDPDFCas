package com.sdpdfcas.menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;

import com.sdpdfcas.download.PdfDownloadManager;

import java.io.File;

public class PdfMenuManager {

    /**
     * Compartir PDF
     */
    public static void share(Context context, File pdfFile) {

        if (pdfFile == null || !pdfFile.exists()) {
            return;
        }

        Uri uri = FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".provider",
                pdfFile
        );

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        context.startActivity(
                Intent.createChooser(intent, "Compartir PDF")
        );
    }

    /**
     * Abrir con otra aplicación
     */
    public static void openWith(Context context, File pdfFile) {

        if (pdfFile == null || !pdfFile.exists()) {
            return;
        }

        Uri uri = FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".provider",
                pdfFile
        );

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        context.startActivity(
                Intent.createChooser(intent, "Abrir PDF con")
        );
    }

    /**
     * Guardar PDF en Descargas
     */
    public static void save(Context context, File pdfFile) {

        PdfDownloadManager.saveToDownloads(context, pdfFile);

    }

}