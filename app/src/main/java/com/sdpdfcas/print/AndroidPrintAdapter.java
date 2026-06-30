package com.sdpdfcas.print;

import android.content.Context;
import android.print.PrintManager;

import androidx.print.PrintHelper;

import java.io.File;

public class AndroidPrintAdapter implements PrinterAdapter {

    @Override
    public void print(Context context, File pdfFile) {

        if (context == null || pdfFile == null) {
            return;
        }

        PrintManager printManager =
                (PrintManager) context.getSystemService(Context.PRINT_SERVICE);

        if (printManager == null) {
            return;
        }

        // La implementación real se agregará en el siguiente paso.

    }

}
