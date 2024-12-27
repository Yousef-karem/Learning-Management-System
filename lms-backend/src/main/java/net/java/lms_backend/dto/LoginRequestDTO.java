package net.java.lms_backend.dto;

public class LoginRequestDTO {
    private String identifier; // email or username
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String password, String identifier) {
        this.password = password;
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return identifier;
    }

    public void setEmail(String mail) {
        this.identifier = mail;
    }

}
