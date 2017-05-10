package com.qtsachit.helper.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

/**
 * The class is Created by Sachit on 02/February/2017
 * <p>
 * Description-
 * <p>
 * Additional notes-
 */

public final class DataBaseExportUtility {

    private static final boolean DEBUG = true;
    private static final String TAG = "DatabaseExportUtils";

    /**
     * Don't let anyone instantiate this class.
     */
    private DataBaseExportUtility() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * This method will start exporting data, as this is time consuming operation so do it in
     * background thread
     *
     * @param context      context of an activity
     * @param targetFile   target file
     * @param databaseName database file name
     * @return true/false
     */
    public boolean startExportDatabase(Context context, String targetFile,
                                       String databaseName) {
        if (DEBUG) {
            LoggerUtility.debug(TAG, "start export ...");
        }
        if (!Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            if (DEBUG) {
                LoggerUtility.debug(TAG, "cannot find SDCard");
            }
            return false;
        }
        String sourceFilePath = Environment.getDataDirectory() + "/data/"
                + context.getPackageName() + "/databases/" + databaseName;
        String destFilePath = Environment.getExternalStorageDirectory()
                + (TextUtils.isEmpty(targetFile) ? (context.getPackageName() + ".db")
                : targetFile);
        boolean isCopySuccess = IOUtility
                .copyFile(sourceFilePath, destFilePath);
        if (DEBUG) {
            if (isCopySuccess) {
                LoggerUtility.debug(TAG, "copy database file success. target file : "
                        + destFilePath);
            } else {
                LoggerUtility.debug(TAG, "copy database file failure");
            }
        }
        return isCopySuccess;
    }
}