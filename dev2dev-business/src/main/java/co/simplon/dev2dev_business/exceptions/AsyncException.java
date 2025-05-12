package co.simplon.dev2dev_business.exceptions;

import org.apache.logging.log4j.Level;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class AsyncException implements AsyncUncaughtExceptionHandler {

    Logger logger = LogManager.getLogger();

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        logger.log(Level.ERROR, "[ASYNC-ERROR] method: {} exception: {}", method.getName(), ex);
    }
}
