package com.capgemini.camel.bean.exceptionhandler;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.camel.Exchange;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.camel.response.model.Header;

/**
 * An implementation of an Exception Handler that returns a JSON response with
 * error message within the faultMessage and the appropriate faultCode.
 *
 * @author Abbas Attarwala
 * @author Andrew Harmel-Law
 */
public class JsonResponseExceptionHandler implements ExceptionHandler {

    private int apiVersion;
    private String dateFormat;
    
    final int BAD_REQUEST_ERROR_CODE  = HttpStatus.SC_BAD_REQUEST;
    final int UNAUTHORIZED_ERROR_CODE = HttpStatus.SC_UNAUTHORIZED;
    final int INTERNAL_SERVER_ERROR_CODE = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonResponseExceptionHandler.class);

    /**
     * Should be called in the case of a Recoverable Exception that fails after all its retries.
     * Sets the JSON message in the response body with faultCode and faultMessage for INTERNAL SERVER ERROR
     * 
     * @param exchange 
     */
    @Override
    public void handleRecoverableException(Exchange exchange) {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String faultMessage = cause.getMessage();
        String failureRouteId = exchange.getProperty(Exchange.FAILURE_ROUTE_ID, String.class);
        LOGGER.error("Exception occurred in the route {}. Exception details are: {}", failureRouteId, faultMessage);
        setResponse(exchange, INTERNAL_SERVER_ERROR_CODE, faultMessage);
    }

    /**
     * Should be called in the case of an irrecoverable exception
     * Sets the JSON message in the response body with faultCode and faultMessage for INTERNAL SERVER ERROR
     * 
     * @param exchange 
     */
    @Override
    public void handleIrrecoverableException(Exchange exchange) {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String faultMessage = cause.getMessage();
        String failureRouteId = exchange.getProperty(Exchange.FAILURE_ROUTE_ID, String.class);
        LOGGER.error("Exception occurred in the route {}. Exception details are: {}", failureRouteId, faultMessage);
        setResponse(exchange, INTERNAL_SERVER_ERROR_CODE, faultMessage);
    }
    
    /**
     * Should be called in the case of a transformation exception
     * Sets the JSON message in the response body with faultCode and faultMessage for INTERNAL SERVER ERROR
     * 
     * @param exchange 
     */
    @Override
    public void handleTransformationException(Exchange exchange) {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String faultMessage = cause.getMessage();
        String failureRouteId = exchange.getProperty(Exchange.FAILURE_ROUTE_ID, String.class);
        LOGGER.error("Exception occurred during transformation in the route {}. Exception details are: {}", failureRouteId, faultMessage);
        setResponse(exchange, INTERNAL_SERVER_ERROR_CODE, faultMessage);
    }

    /**
     * Should be called in the case of an invalid request
     * Sets the JSON message in the response body with faultCode and faultMessage for BAD REQUEST
     * 
     * @param exchange 
     */
    @Override
    public void handleValidationException(Exchange exchange) {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String faultMessage = cause.getMessage();
        LOGGER.error("A validation exception occured because : {}", faultMessage);
        setResponse(exchange, BAD_REQUEST_ERROR_CODE, faultMessage);
    }

    /**
     * Should be called in the case of an unauthorized request
     * Sets the JSON message in the response body with faultCode and faultMessage for UNAUTHORIZED
     * 
     * @param exchange 
     */
    @Override
    public void handleAuthorizationException(Exchange exchange) {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String faultMessage = cause.getMessage();
        LOGGER.error("An Authorization Exception  occured because : {}", faultMessage);
        setResponse(exchange, UNAUTHORIZED_ERROR_CODE, faultMessage);
    }
    
    /**
     * Sets the appropriate response to the exchange header and body.
     * 
     * @param exchange
     * @param statusCode
     * @param faultMessage
     */
    private void setResponse(Exchange exchange, int statusCode, String faultMessage) {
        Header header = new Header();
        header.setApiVersion(getApiVersion());
        header.setResponseDate(getCurrentDate(getDateFormat()));
        header.setStatusCode(statusCode);
        header.setFaultCode(new BigInteger(String.valueOf(statusCode)));
        header.setFaultMessage(faultMessage);
        
        exchange.getIn().removeHeaders("*");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, statusCode);
        exchange.getIn().setBody(header);
    }

    private String getCurrentDate(String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    public int getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
