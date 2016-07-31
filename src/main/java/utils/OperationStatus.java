package utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OperationStatus {
    @JsonProperty
    public String statusCode;

    @JsonProperty
    public String statusMessage;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public OperationStatus(){}
    
    public OperationStatus(String statusCode, String statusMessage){
        this.statusCode =statusCode;
        this.statusMessage =statusMessage;
    }
}
