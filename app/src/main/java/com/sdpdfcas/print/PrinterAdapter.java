package com.sdpdfcas.print;

import android.content.Context;

import java.io.File;

public interface PrinterAdapter {

    void print(Context context, File pdfFile);

}
