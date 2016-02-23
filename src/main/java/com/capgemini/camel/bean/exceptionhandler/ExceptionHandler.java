package com.capgemini.camel.bean.exceptionhandler;

import org.apache.camel.Exchange;

/**
 * An abstraction of an exception handler
 *
 * @author Abbas Attarwala
 */
public interface ExceptionHandler {
    
    /**
     * This method should handle a recoverable exception
     * 
     * @param exchange 
     */
    void handleRecoverableException(Exchange exchange);
    
    /**
     * This method should handle an irrecoverable exception
     * 
     * @param exchange 
     */
    void handleIrrecoverableException(Exchange exchange);
    
    /**
     * This method should handle a validation exception.
     * 
     * @param exchange 
     */
    void handleValidationException(Exchange exchange);
    
    /**
     * This method should handle an Authorization Exception
     * 
     * @param exchange 
     */
    void handleAuthorizationException(Exchange exchange);
    
    /**
     * This method should handle a Transformation Exception
     * 
     * @param exchange 
     */
    void handleTransformationException(Exchange exchange);
}
