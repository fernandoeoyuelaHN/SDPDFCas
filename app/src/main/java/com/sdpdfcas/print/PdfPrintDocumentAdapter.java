package com.sdpdfcas.print;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintAttributes;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfPrintDocumentAdapter extends PrintDocumentAdapter {

    private final Context context;
    private final Uri pdfUri;

    public PdfPrintDocumentAdapter(Context context, Uri pdfUri) {

        this.context = context;
        this.pdfUri = pdfUri;

    }

    @Override
    public void onLayout(
            PrintAttributes oldAttributes,
            PrintAttributes newAttributes,
            CancellationSignal cancellationSignal,
            LayoutResultCallback callback,
            Bundle extras) {

        if (cancellationSignal.isCanceled()) {

            callback.onLayoutCancelled();
            return;

        }

        PrintDocumentInfo info =
                new PrintDocumentInfo.Builder("document.pdf")
                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                        .build();

        callback.onLayoutFinished(info, true);

    }

    @Override
    public void onWrite(
            PageRange[] pages,
            ParcelFileDescriptor destination,
            CancellationSignal cancellationSignal,
            WriteResultCallback callback) {

        try {

            FileInputStream input =
                    (FileInputStream) context.getContentResolver()
                            .openInputStream(pdfUri);

            FileOutputStream output =
                    new FileOutputStream(destination.getFileDescriptor());

            byte[] buffer = new byte[4096];

            int size;

            while ((size = input.read(buffer)) != -1) {

                output.write(buffer, 0, size);

            }

            input.close();
            output.close();

            callback.onWriteFinished(
                    new PageRange[]{PageRange.ALL_PAGES});

        } catch (IOException e) {

            callback.onWriteFailed(e.getMessage());

        }

    }

}