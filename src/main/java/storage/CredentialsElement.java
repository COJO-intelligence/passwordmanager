package main.java.storage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CredentialsElement implements Serializable {

    private static int elementCounter = 0;
    private final int elementID;
    private String domain;
    private String link;
    private String username;
    private String email;
    private String password;
    private String additionalComments;
    private String dateCreatedString;
    private String dateModifiedString;

    public static String dateFormat = "EEEE, dd MMMM yyyy hh:mm:ss";

    public CredentialsElement(String domain, String link, String username, String email, String password, String additionalComments) {
        elementCounter++;
        this.elementID = elementCounter;
        this.domain = domain;
        this.link = link;
        this.username = username;
        this.email = email;
        this.password = password;
        this.additionalComments = additionalComments;
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat(dateFormat);
        this.dateCreatedString = DateFor.format(date);
        this.dateModifiedString = DateFor.format(date);
    }

    public CredentialsElement() {
        elementCounter++;
        this.elementID = elementCounter;
        this.domain = "New Entry " + getElementID();
        this.username = null;
        this.email = null;
        this.password = null;
        this.additionalComments = null;
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat(dateFormat);
        this.dateCreatedString = DateFor.format(date);
        this.dateModifiedString = DateFor.format(date);
    }

    public int getElementID() {
        return this.elementID;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String domain) {
        this.link = domain;
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

    public String getDateCreatedString() {
        return dateCreatedString;
    }

    public void setDateCreatedString() {
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat(dateFormat);
        this.dateCreatedString = DateFor.format(date);
    }

    public String getDateModifiedString() {
        return dateModifiedString;
    }

    public void setDateModifiedString() {
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat(dateFormat);
        this.dateModifiedString = DateFor.format(date);
    }
}
