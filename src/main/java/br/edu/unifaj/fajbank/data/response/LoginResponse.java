package br.edu.unifaj.fajbank.data.response;

public class LoginResponse {
    private String token;
    private long expiresIn;

    public String getToken() {
        return token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
