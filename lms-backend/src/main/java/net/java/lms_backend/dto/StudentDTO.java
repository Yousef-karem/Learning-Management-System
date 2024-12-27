package net.java.lms_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class StudentDTO {
    private Long id;
    private String username;
    private String email;
    public StudentDTO(Long id, String username,String email){
        this.id=id;
        this.email=email;
        this.username=username;
    }

    public StudentDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public  StudentDTO(){
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
