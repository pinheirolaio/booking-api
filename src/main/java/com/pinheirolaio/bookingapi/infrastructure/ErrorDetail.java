package com.pinheirolaio.bookingapi.infrastructure;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail {

    private String timestamp;
    private String status;
    private String title;
    private List<String> message;

    public ErrorDetail() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public static final class Builder {
        private String timestamp;
        private String status;
        private String title;
        private List<String> message;

        public Builder() {
        }

        public static Builder anErrorDetail() {
            return new Builder();
        }

        public Builder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(List<String> message) {
            this.message = message;
            return this;
        }


        public ErrorDetail build() {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setTimestamp(timestamp);
            errorDetail.setStatus(status);
            errorDetail.setTitle(title);
            errorDetail.setMessage(message);
            return errorDetail;
        }
    }
}
