package main.java.storage;

import java.io.Serializable;

public class CredentialsElement implements Serializable {

    private int elementID;
    private String domain;
    private String username;
    private String email;
    private String password;
    private String additionalComments;

    CredentialsElement(int elementID, String domain, String username, String email, String password, String additionalComments) {
        this.elementID = elementID;
        this.domain = domain;
        this.username = username;
        this.email = email;
        this.password = password;
        this.additionalComments = additionalComments;
    }

    public int getElementID() {
        return this.elementID;
    }

    public void setElementID(int elementID) {
        this.elementID = elementID;
    }


    public String getDomain() { return domain; }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

}
