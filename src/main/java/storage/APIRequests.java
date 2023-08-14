package storage;

import launcher.MainUI;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;

public class APIRequests {

    private static final String mainURL = "https://192.168.100.4:8443";
    private static final String cert = "crt.cer";
    // https://3.122.195.12:8443:    ||   https://192.168.100.4:8443
    // cerAWS.cer    ||    crt.cer

    public static int register(String email, String firstName, String lastName, String password) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL(mainURL + "/register");
        File crtFile = new File("src/main/resources/" + cert);
        Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(crtFile));
        // Or if the crt-file is packaged into a jar file:
        // CertificateFactory.getInstance("X.509").generateCertificate(this.class.getClassLoader().getResourceAsStream("crt.cer"));
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("server", certificate);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        connection.connect();
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(jsonObject.toString());
        osw.flush();
        osw.close();
        return connection.getResponseCode();
    }

    public static String getUserContent() throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, KeyManagementException {
        URL url = new URL(mainURL + "/passwords");
        File crtFile = new File("src/main/resources/" + cert);
        Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(crtFile));
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("server", certificate);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        String encoding = Base64.getEncoder().encodeToString((MainUI.user.getEmail() + ":" + MainUI.user.getPassword()).getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = bufferedReader.readLine();
        bufferedReader.close();
        return response;
    }

    public static void setUserContent(String content) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL(mainURL + "/passwords");
        File crtFile = new File("src/main/resources/" + cert);
        Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(crtFile));
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("server", certificate);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("encrypted_data", content);
        String encoding = Base64.getEncoder().encodeToString((MainUI.user.getEmail() + ":" + MainUI.user.getPassword()).getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(jsonObject.toString());
        osw.flush();
        osw.close();
        System.out.println(connection.getResponseCode() + " Set User stuff");
        connection.getResponseCode();
    }

    public static int setMFAKey(String mfaKey) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL(mainURL + "/mfakey");
        File crtFile = new File("src/main/resources/" + cert);
        Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(crtFile));
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("server", certificate);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mfaKey", mfaKey);
        jsonObject.put("keymail", MainUI.user.getEmail());
        String encoding = Base64.getEncoder().encodeToString((MainUI.user.getEmail() + ":" + MainUI.user.getPassword()).getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(jsonObject.toString());
        osw.flush();
        osw.close();
        System.out.println(connection.getResponseCode() + " Set User MFA stuff");
        return connection.getResponseCode();
    }

    public static String getMFAKey() throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL(mainURL + "/mfakey");
        File crtFile = new File("src/main/resources/" + cert);
        Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(crtFile));
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("server", certificate);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        String encoding = Base64.getEncoder().encodeToString((MainUI.user.getEmail() + ":" + MainUI.user.getPassword()).getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = bufferedReader.readLine();
        bufferedReader.close();
        System.out.println(connection.getResponseCode() + " Get User MFA stuff");
        System.out.println(response);
        return response;
    }

    public static int setUserBioContent(String bioKey) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, KeyManagementException {
        URL url = new URL(mainURL + "/biokey");
        File crtFile = new File("src/main/resources/" + cert);
        Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(crtFile));
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("server", certificate);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bioKey", bioKey);
        jsonObject.put("biomail", MainUI.user.getEmail());
        String encoding = Base64.getEncoder().encodeToString((MainUI.user.getEmail() + ":" + MainUI.user.getPassword()).getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(jsonObject.toString());
        osw.flush();
        osw.close();
        System.out.println(connection.getResponseCode() + " Set User Bio stuff");
        return connection.getResponseCode();
    }

    public static String getUserBioContent() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL(mainURL + "/biokey/" + MainUI.user.getEmail());
        File crtFile = new File("src/main/resources/" + cert);
        Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(crtFile));
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("server", certificate);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = bufferedReader.readLine();
        bufferedReader.close();
        System.out.println(connection.getResponseCode() + " Get User Bio stuff");
        System.out.println(response);
        return response;
    }

}

