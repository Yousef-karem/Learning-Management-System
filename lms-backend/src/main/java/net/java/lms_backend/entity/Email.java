package net.java.lms_backend.entity;

public class Email {
    private String link;
    private User user;
    private EmailType type;
    private String emailText;
    private String subject;
    private String from;
    private String content;

    public Email(String content, EmailType type, User user) {
        this.content = content;
        this.type = type;
        this.user = user;
        setEmailText();
    }

    public Email(String link, User user, EmailType type) {
        this.link = link;
        this.user = user;
        this.type = type;
        setEmailText();
    }

    public Email(User user, EmailType type) {
        this.user = user;
        this.type = type;
        setEmailText();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getEmailText() {
        return emailText;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEmailText() {
        this.from = "fcai@gmail.com";

        if (this.type == EmailType.Confirmation) {
            this.subject = "Confirm your email";
            this.emailText = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                    "    <p>Hi " + user.getUsername() + ",</p>\n" +
                    "    <p>Thank you for registering. Please click on the below link to activate your account:</p>\n" +
                    "    <a href=\"" + link + "\">Activate Now</a>\n" +
                    "    <p>Link will expire in 5 minutes.</p>\n" +
                    "    <p>See you soon!</p>\n" +
                    "</div>";
        } else if (this.type == EmailType.Notification) {
            this.subject = "Notification Email";
            this.emailText = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                    "    <p>Hi " + user.getUsername() + ",</p>\n" +
                            content+"\n"+
                    "    <p>Best regards,<br>FCAI Team</p>\n" +
                    "</div>";
        }
    }
}
