package br.com.simple.error;

//Padronização do corpo da mensagem de erro. Pode ser visto no Postman
public class ResourceNotFoundDetails extends ErrorDetails {

    private ResourceNotFoundDetails() {
    }

    public static final class Builder {
        private String title;
        private int status;
        private String details;
        private long timestamp;
        private String developerMessage;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder details(String details) {
            this.details = details;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ResourceNotFoundDetails build() {
            ResourceNotFoundDetails resourceNotFoundDetails = new ResourceNotFoundDetails();
            resourceNotFoundDetails.setStatus(this.status);
            resourceNotFoundDetails.setDetails(this.details);
            resourceNotFoundDetails.setTitle(this.title);
            resourceNotFoundDetails.setTimestamp(this.timestamp);
            resourceNotFoundDetails.setDeveloperMessage(this.developerMessage);
            return resourceNotFoundDetails;
        }
    }
}
