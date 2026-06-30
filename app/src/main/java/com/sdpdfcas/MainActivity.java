package com.sdpdfcas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sdpdfcas.options.SDPDFOptions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SDPDFOptions options = new SDPDFOptions();

        options.setShowShareButton(false);
        options.setShowOpenWithButton(false);
        options.setShowDownloadButton(true);
        options.setShowPrintButton(false);

        SDPDFCas.open(
                this,
                "https://apps.appfactoryhn.com/ComandaRest.cas/seafoodSoloMariscos.pdf",
                "Seafood Solo Mariscos",
                options
        );

        finish();

    }

}