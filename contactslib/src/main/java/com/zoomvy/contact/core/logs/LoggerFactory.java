package com.zoomvy.contact.core.logs;


public class LoggerFactory {

    public static Logger createLogger(Class<?> clazz) {
        return new Logger(clazz.getSimpleName());
    }
}
