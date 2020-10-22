package main.java.storage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CredentialsElement implements Serializable {

    private static int elementCounter = 0;
    private final int elementID;
    private int accountType;
    private String domain;
    private String link;
    private String username;
    private String email;
    private String password;
    private final String dateCreatedString;
    private String dateModifiedString;
    private String additionalComments;
    private boolean isDeactivated;

    public static String dateFormat = "EEEE, dd MMMM yyyy hh:mm:ss";

    public CredentialsElement(int accountType, String domain, String link, String username, String email, String password, String additionalComments) {
        elementCounter++;
        this.elementID = elementCounter;
        this.accountType = accountType;
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
        isDeactivated = false;
    }

    public CredentialsElement() {
        elementCounter++;
        this.elementID = elementCounter;
        this.domain = "New Entry " + getElementID();
        this.accountType = 9;
        this.username = null;
        this.email = null;
        this.password = null;
        this.additionalComments = null;
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat(dateFormat);
        this.dateCreatedString = DateFor.format(date);
        this.dateModifiedString = DateFor.format(date);
        isDeactivated = false;
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

    public String getDateModifiedString() {
        return dateModifiedString;
    }

    public void setDateModifiedString() {
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat(dateFormat);
        this.dateModifiedString = DateFor.format(date);
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public boolean isDeactivated() {
        return isDeactivated;
    }

    public void setDeactivated(boolean deactivated) {
        isDeactivated = deactivated;
    }
}
