package com.capgemini.camel.response.model;

/**
 * This class models an error response returned by a REST call
 *
 * @author Gayathri Thiyagarajan
 */
public class RestClientErrorResponse {

    private String errorType;
    private String code;
    private String message;

    public RestClientErrorResponse() {
    }

    public RestClientErrorResponse(String code, String message, String errorType) {
        this.code = code;
        this.message = message;
        this.errorType = errorType;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override public String toString() {
        return "RestClientErrorResponse{" +
                "errorType=" + errorType +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
