package com.savitoh.demoqrcodeapi.exceptions;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public final class CustomApiErroResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", locale = "UTC-03", timezone="GMT-3")
    private LocalDateTime timestamp;
    private int statusCode;
    private String status;
    private String error;

    private CustomApiErroResponse(int statusCode, String status, String error) {
        this.timestamp = LocalDateTime.now();
        this.statusCode = statusCode;
        this.status = status;
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public static class Builder {
        private int statusCode;
        private String status;
        private String error;


        public Builder withStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder withError(String error) {
            this.error = error;
            return this;
        }

        public Builder withError(@NonNull BindingResult bindingResult) {
            Assert.notNull(bindingResult, "#bindingResult não pode ser nullo");
            this.error = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(" - "));
            return this;
        }  

        public CustomApiErroResponse build() {
            return new CustomApiErroResponse(this.statusCode, this.status, this.error);
        }
    }

    @Override
    public String toString() {
        return "ApiErroResponse{" +
                "timestamp=" + timestamp +
                ", codeStatus=" + statusCode +
                ", status='" + status + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
