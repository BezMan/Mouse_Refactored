package com.comrax.mouseappandroid.http;

public interface RequestTaskDelegate {
    void onTaskPOSTCompleted(String result, RequestTask task);
    void onTaskGETCompleted(String result, RequestTask task);
}
