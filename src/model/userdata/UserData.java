package model.userdata;

public class UserData {
    private final String domain;
    private final String apiDomain;
    private final String path;
    private final String clientId;
    private final String secretId;
    private final String redirectUri;

    private UserData(String domain, String apiDomain, String path, String clientId, String secretId, String redirectUri) {
        this.apiDomain = apiDomain;
        this.domain = domain;
        this.path = path;
        this.clientId = clientId;
        this.secretId = secretId;
        this.redirectUri = redirectUri;
    }

    public static String concatKeyVal(String key, String val) {
        return new StringBuilder().append(key).append("=").append(val).toString();
    }

    public String getDomain() {
        return this.domain;
    }

    public String getApiDomain() {
        return this.apiDomain;
    }

    public String getPath() {
        return this.path;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getSecretId() {
        return this.secretId;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public static class Builder {
        private String apiDomain;
        private String domain;
        private String path;
        private String clientId;
        private String secretId;
        private String redirectUri;

        public Builder addApiDomain(String apiDomain) {
            this.apiDomain = apiDomain;
            return this;
        }

        public Builder addDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder addPath(String path) {
            this.path = path;
            return this;
        }

        public Builder addClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder addSecretId(String secret) {
            this.secretId = secret;
            return this;
        }

        public Builder addRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public UserData build() {
            return new UserData(domain, apiDomain, path, clientId, secretId, redirectUri);
        }
    }
}
