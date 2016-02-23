package com.capgemini.camel.bean.exceptionhandler;

import org.apache.camel.Exchange;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of an Exception Handler that returns an HTTP response with
 * error message within the response body and the appropriate HTTP status code.
 *
 * @author Abbas Attarwala
 */
public class HttpResponseExceptionHandler implements ExceptionHandler {
    
    private final int BAD_REQUEST_ERROR_CODE  = HttpStatus.SC_BAD_REQUEST;
    private final int UNAUTHORIZED_ERROR_CODE = HttpStatus.SC_UNAUTHORIZED;
    private final int INTERNAL_SERVER_ERROR_CODE = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * Should be called in the case of a Recoverable Exception that fails after all its retries.
     * Sets the Error Message in the response body with an HTTP Status header of 500 - INTERNAL SERVER ERROR
     * 
     * @param exchange 
     */
    @Override
    public void handleRecoverableException(Exchange exchange) {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String failureRouteId = exchange.getProperty(Exchange.FAILURE_ROUTE_ID, String.class);
        String errorMessage = cause.getMessage();
        LOGGER.error("Exception occurred in the route {}. Exception details are: {}", failureRouteId, errorMessage);
        setResponse(exchange, INTERNAL_SERVER_ERROR_CODE, errorMessage);
    }

    /**
     * Should be called in the case of an Irrecoverable Exception.
     * Sets the Error Message in the response body with an HTTP Status header of 500 - INTERNAL SERVER ERROR
     * 
     * @param exchange 
     */
    @Override
    public void handleIrrecoverableException(Exchange exchange) {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String failureRouteId = exchange.getProperty(Exchange.FAILURE_ROUTE_ID, String.class);
        String errorMessage = cause.getMessage();
        LOGGER.error("Exception occurred in the route {}. Exception details are: {}", failureRouteId, errorMessage);
        setResponse(exchange, INTERNAL_SERVER_ERROR_CODE, errorMessage);
    }
    
    /**
     * Should be called in the case of an error during transformation
     * Sets the transformation error message in the response body with an HTTP Status header of 500 - INTERNAL SERVER ERROR
     * 
     * @param exchange 
     */
    @Override
    public void handleTransformationException(Exchange exchange) {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String failureRouteId = exchange.getProperty(Exchange.FAILURE_ROUTE_ID, String.class);
        String errorMessage = cause.getMessage();
        LOGGER.error("Exception occured during transformation in the route {}. Exception details are: {}", failureRouteId, errorMessage);
        setResponse(exchange, INTERNAL_SERVER_ERROR_CODE, errorMessage);
    }

    /**
     * Should be called in the case of an invalid request.
     * Sets the validation error message in the response body with an HTTP Status header of 400 - BAD REQUEST
     * 
     * @param exchange 
     */
    @Override
    public void handleValidationException(Exchange exchange) {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String errorMessage = cause.getMessage();
        LOGGER.error("A validation exception occured because : {}", errorMessage);
        setResponse(exchange, BAD_REQUEST_ERROR_CODE, errorMessage);
    }

    /**
     * Should be called in the case of an unauthorized request
     * Sets the unauthorized error message in the response body with an HTTP Status header of 401 - UNAUTHORIZED
     * 
     * @param exchange 
     */
    @Override
    public void handleAuthorizationException(Exchange exchange) {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String errorMessage = cause.getMessage();
        LOGGER.error("An Authorization Exception  occured because : {}", errorMessage);
        setResponse(exchange, UNAUTHORIZED_ERROR_CODE, errorMessage);
    }
    
    /**
     * Sets the appropriate response to the exchange header and body.
     * 
     * @param exchange
     * @param statusCode
     * @param errorMessage 
     */
    private void setResponse(Exchange exchange, int statusCode, String errorMessage) {
        exchange.getIn().removeHeaders("*");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, statusCode);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "text/plain");
        exchange.getIn().setBody(errorMessage);
    }
}
