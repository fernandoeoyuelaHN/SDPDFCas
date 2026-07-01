package com.sdpdfcas.viewer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.appbar.MaterialToolbar;
import com.sdpdfcas.R;
import com.sdpdfcas.core.SDPDFConstants;
import com.sdpdfcas.options.SDPDFOptions;
import com.sdpdfcas.downloader.PdfDownloader;
import com.sdpdfcas.menu.PdfMenuManager;
import com.sdpdfcas.print.PdfPrintManager;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PdfViewerActivity extends AppCompatActivity {

    public static final String EXTRA_PDF_URL = SDPDFConstants.EXTRA_PDF_URL;
    public static final String EXTRA_PDF_TITLE = SDPDFConstants.EXTRA_PDF_TITLE;

    private MaterialToolbar toolbar;
    private PDFView pdfView;
    private ProgressBar progressBar;

    private File currentPdfFile;
    private SDPDFOptions options;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pdf_viewer);

        toolbar = findViewById(R.id.toolbar);
        pdfView = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.progressBar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String pdfUrl = getIntent().getStringExtra(SDPDFConstants.EXTRA_PDF_URL);
        String pdfTitle = getIntent().getStringExtra(SDPDFConstants.EXTRA_PDF_TITLE);

        options = (SDPDFOptions) getIntent().getSerializableExtra(
                SDPDFConstants.EXTRA_OPTIONS);

        if (options == null) {
            options = new SDPDFOptions();
        }

        if (pdfTitle == null || pdfTitle.trim().isEmpty()) {
            pdfTitle = "SDPDFCas";
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(pdfTitle);
        }

        if (pdfUrl == null || pdfUrl.trim().isEmpty()) {

            loadSamplePdf();

        } else {

            downloadAndShowPdf(pdfUrl);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.pdf_viewer_menu, menu);

        if (options != null) {

            MenuItem share = menu.findItem(R.id.action_share);
            if (share != null) {
                share.setVisible(options.isShowShareButton());
            }

            MenuItem open = menu.findItem(R.id.action_open);
            if (open != null) {
                open.setVisible(options.isShowOpenWithButton());
            }

            MenuItem save = menu.findItem(R.id.action_save);
            if (save != null) {
                save.setVisible(options.isShowDownloadButton());
            }
            MenuItem print = menu.findItem(R.id.action_print);
            if (print != null) {
                print.setVisible(options.isShowPrintButton());
            }
        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();
            return true;

        }

        if (id == R.id.action_share) {

            PdfMenuManager.share(this, currentPdfFile);
            return true;

        }

        if (id == R.id.action_open) {

            PdfMenuManager.openWith(this, currentPdfFile);
            return true;

        }

        if (id == R.id.action_save) {

            PdfMenuManager.save(this, currentPdfFile);
            return true;

        }
        if (id == R.id.action_print) {

            PdfPrintManager.print(this, currentPdfFile);
            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    private void loadSamplePdf() {

        pdfView.fromAsset("sample.pdf")
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .load();

    }

    private void downloadAndShowPdf(String url) {

        progressBar.setVisibility(View.VISIBLE);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {

            File pdfFile = PdfDownloader.download(this, url);

            runOnUiThread(() -> {

                progressBar.setVisibility(View.GONE);

                if (pdfFile == null) {

                    Toast.makeText(
                            this,
                            "No fue posible descargar el PDF",
                            Toast.LENGTH_LONG
                    ).show();

                    return;

                }

                currentPdfFile = pdfFile;

                pdfView.fromFile(currentPdfFile)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .load();

            });

        });

    }

    @Override
    public boolean onSupportNavigateUp() {

        finish();
        return true;

    }

}