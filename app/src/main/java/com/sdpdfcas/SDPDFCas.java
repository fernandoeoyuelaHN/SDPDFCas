package com.sdpdfcas;

import android.content.Context;
import android.content.Intent;

import com.sdpdfcas.core.SDPDFConstants;
import com.sdpdfcas.core.SDPDFVersion;
import com.sdpdfcas.options.SDPDFOptions;
import com.sdpdfcas.viewer.PdfViewerActivity;

public class SDPDFCas {

    /**
     * Método compatible con versiones anteriores.
     */
    public static void open(Context context, String url, String titulo) {

        open(
                context,
                url,
                titulo,
                new SDPDFOptions()
        );

    }

    /**
     * Nuevo método con opciones.
     */
    public static void open(
            Context context,
            String url,
            String titulo,
            SDPDFOptions options) {

        Intent intent = new Intent(context, PdfViewerActivity.class);

        intent.putExtra(
                SDPDFConstants.EXTRA_PDF_URL,
                url);

        intent.putExtra(
                SDPDFConstants.EXTRA_PDF_TITLE,
                titulo);

        intent.putExtra(
                SDPDFConstants.EXTRA_OPTIONS,
                options);

        context.startActivity(intent);

    }

    public static String getVersion() {

        return SDPDFVersion.VERSION;

    }

}