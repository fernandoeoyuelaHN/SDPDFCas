package com.sdpdfcas.options;

import java.io.Serializable;

public class SDPDFOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean showShareButton = true;
    private boolean showOpenWithButton = true;
    private boolean showDownloadButton = true;
    private boolean showPrintButton = true;

    public SDPDFOptions() {
    }

    public boolean isShowShareButton() {
        return showShareButton;
    }

    public void setShowShareButton(boolean showShareButton) {
        this.showShareButton = showShareButton;
    }

    public boolean isShowOpenWithButton() {
        return showOpenWithButton;
    }

    public void setShowOpenWithButton(boolean showOpenWithButton) {
        this.showOpenWithButton = showOpenWithButton;
    }

    public boolean isShowDownloadButton() {
        return showDownloadButton;
    }

    public void setShowDownloadButton(boolean showDownloadButton) {
        this.showDownloadButton = showDownloadButton;
    }

    public boolean isShowPrintButton() {
        return showPrintButton;
    }

    public void setShowPrintButton(boolean showPrintButton) {
        this.showPrintButton = showPrintButton;
    }

}