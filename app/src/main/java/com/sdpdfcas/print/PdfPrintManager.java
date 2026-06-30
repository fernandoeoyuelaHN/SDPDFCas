package com.sdpdfcas.print;

import android.content.Context;

import java.io.File;

public class PdfPrintManager {

    private PdfPrintManager() {
        // Evita instanciar esta clase.
    }

    /**
     * Imprime utilizando el adaptador indicado.
     */
    public static void print(
            Context context,
            File pdfFile,
            PrinterAdapter adapter) {

        if (context == null) {
            return;
        }

        if (pdfFile == null) {
            return;
        }

        if (adapter == null) {
            return;
        }

        adapter.print(context, pdfFile);

    }

    /**
     * Utiliza la impresión estándar de Android.
     */
    public static void print(
            Context context,
            File pdfFile) {

        print(
                context,
                pdfFile,
                new AndroidPrintAdapter()
        );

    }

}
