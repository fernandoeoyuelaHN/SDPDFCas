package com.sdpdfcas.print;

import android.content.Context;
import android.net.Uri;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;

import androidx.core.content.FileProvider;

import java.io.File;

public class AndroidPrintAdapter implements PrinterAdapter {

    @Override
    public void print(Context context, File pdfFile) {

        if (context == null || pdfFile == null || !pdfFile.exists()) {
            return;
        }

        PrintManager printManager =
                (PrintManager) context.getSystemService(Context.PRINT_SERVICE);

        if (printManager == null) {
            return;
        }

        Uri pdfUri = FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".provider",
                pdfFile
        );

        PrintDocumentAdapter adapter =
                new PdfPrintDocumentAdapter(context, pdfUri);

        printManager.print(
                pdfFile.getName(),
                adapter,
                null
        );

    }

}
