package com.vds.account.config;

import com.vds.account.exception.AccountException;
import feign.FeignException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String DEFAULT_EXCEPTION_MESSAGE = "An exception has occurred in controller: ";
    private static final String LOCALE_MESSAGE = "The locale is: ";

    /**
     * AccountException exception handler.
     *
     * @param e             exception.
     * @param handlerMethod information about method.
     * @param locale        localization.
     * @return ResponseEntity with CareWaveServerException payload.
     */
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<AccountException> onRegularException(AccountException e, HandlerMethod handlerMethod, Locale locale) {
        String message = handleException(handlerMethod, locale);
        LOGGER.log(Level.SEVERE, message, e);
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));

        return new ResponseEntity<>(new AccountException(String.format("%s%s", message, stringWriter.toString()),
                e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * General exception handler.
     *
     * @param e             exception.
     * @param handlerMethod handler method.
     * @param locale        locale.
     * @return ResponseEntity with Object payload.
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> onException(Exception e, HandlerMethod handlerMethod, Locale locale) {
        String message = handleException(handlerMethod, locale);
        LOGGER.log(Level.SEVERE, message, e);

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return new ResponseEntity<>(String.format("An error has just occurred: %n %s %n%s", message, sw.toString()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String handleException(HandlerMethod handlerMethod, Locale locale){
        Class<?> controllerClass = handlerMethod.getMethod().getDeclaringClass();
        return String.format("%s%s . %s%s", DEFAULT_EXCEPTION_MESSAGE, controllerClass.toString(),
                LOCALE_MESSAGE, locale.toString());
    }

    /**
     * FeignClient exception handler.
     *
     * @param e             exception.
     * @param response        servlet response.
     * @return Map<String, Object> returns well-formatted JSON-wrapped exception.
     */

    @ExceptionHandler(FeignException.class)
    public Map<String, Object> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return new JSONObject(e.contentUTF8()).toMap();
    }

}