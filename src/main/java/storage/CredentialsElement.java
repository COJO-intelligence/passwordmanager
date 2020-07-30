package main.java.storage;

import java.io.Serializable;

public class CredentialsElement implements Serializable {

    private int elementID;
    private static int elementCounter = 0;
    private String domain;
    private String username;
    private String email;
    private String password;
    private String additionalComments;

    public CredentialsElement(String domain, String username, String email, String password, String additionalComments) {
        elementCounter++;
        this.elementID = elementCounter;
        this.domain = domain;
        this.username = username;
        this.email = email;
        this.password = password;
        this.additionalComments = additionalComments;
    }

    public CredentialsElement() {
        elementCounter++;
        this.elementID = elementCounter;
        this.domain = "Password Manager" + elementCounter;
        this.username = null;
        this.email = null;
        this.password = null;
        this.additionalComments = null;
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
