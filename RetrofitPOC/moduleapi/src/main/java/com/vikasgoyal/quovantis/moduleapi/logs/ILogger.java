package com.vikasgoyal.quovantis.moduleapi.logs;

/**
 * Logger class to save logs on server for
 */
public interface ILogger {

    /**
     * Log simple message
     *
     * @param msg logger message
     */
    public void log(String msg);

    /**
     * Log message with description, tag and priorty based
     *  @param tag      Tag for the log
     * @param msg      Message to print
     */
    public void log(String tag, String msg);

}
