package ru.otus.spring.exceptions;


public class ExceptionUtil {

    private static final String ENTITY_NOT_FOUND_EXCEPTION_MESSAGE_FORMAT = "%s with id %s not found";


    private ExceptionUtil() {

    }

    public static String entityNotFoundExceptionMessageFormat(String entityName, Long id) {
        return String.format(ENTITY_NOT_FOUND_EXCEPTION_MESSAGE_FORMAT, entityName, id);
    }

}
