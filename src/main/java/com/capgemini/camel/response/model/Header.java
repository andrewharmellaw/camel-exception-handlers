package com.capgemini.camel.response.model;

import java.math.BigInteger;

/**
 * @author Andrew Harmel-Law
 */
public class Header {

    protected int statusCode;
    protected String responseDate;
    protected int apiVersion;
    protected BigInteger faultCode;
    protected String faultMessage;

    public Header() {
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int value) {
        this.statusCode = value;
    }

    public String getResponseDate() {
        return this.responseDate;
    }

    public void setResponseDate(String value) {
        this.responseDate = value;
    }

    public int getApiVersion() {
        return this.apiVersion;
    }

    public void setApiVersion(int value) {
        this.apiVersion = value;
    }

    public BigInteger getFaultCode() {
        return this.faultCode;
    }

    public void setFaultCode(BigInteger value) {
        this.faultCode = value;
    }

    public String getFaultMessage() {
        return this.faultMessage;
    }

    public void setFaultMessage(String value) {
        this.faultMessage = value;
    }

}
