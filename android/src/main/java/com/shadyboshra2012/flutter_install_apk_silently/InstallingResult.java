package com.shadyboshra2012.flutter_install_apk_silently;

public class InstallingResult {
    private boolean isSuccess;
    private String error;
    private String errorMessage;
    private String errorDetails;

    InstallingResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    InstallingResult(boolean isSuccess, String error, String errorMessage, String errorDetails) {
        this.isSuccess = isSuccess;
        this.error = error;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorDetails() {
        return errorDetails;
    }
}
