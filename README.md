#Java Password Manager
Presenting to the world a solution for a basic password manager built using Oracle's java security framework and enhanced with Swing-like user interface.

###Prerequisites
* Java SE Runtime Environment 8 - Minimum Requirement;
* Java added to System PATH.

**Note:** Development was made on Java SE Development Kit 14.0.2, but the application was tested on Java SE Runtime Environment 8.

**Why Java?** To be able to run the application with no trouble on whatever operation system is provided. No matter if it is Windows, Linux or macOS machine.

###Usage
* Download the passwordManager.jar file located in the *production* package;
* Open the System Terminal and navigate to the location of the JAR;
* Run the below command:
```
java -jar passwordManager.jar
```

**Quick Debug:** If the command did not launch the application, please make sure java is installed and added to PATH by running one of the following commands:
```
java -version
```
which will output something similar to the following:
```
java version "14.0.1" 2020-04-14
Java(TM) SE Runtime Environment (build 14.0.1+7)
Java HotSpot(TM) 64-Bit Server VM (build 14.0.1+7, mixed mode, sharing)
```

###Explaining the Project Structure
The project is divided into two main ideas which are the *Login Page* and the *Entries Page*. Only the master key is required to access all the entries. 
Here is a list of the main algorithms and best practice logic used inside the application:
1. *SHA-512* with salts;
    ```java
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
    String finalPassword = SALT + password + SALT;
    return messageDigest.digest(finalPassword.getBytes());
    ```
1. *AES* in CBC Mode;
1. *PKCS5Padding*;
    ```java
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    SecretKey secretKey = KeyManager.getSecretKey();
    IvParameterSpec ivSpec = new IvParameterSpec(initialIV);
    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
    ```
1. The entries are stored inside an encrypted file whose content is read and decrypted at runtime;
    ```java
    ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(decryptContent(inputFilePath)));
    ArrayList<CredentialsElement> dataList = (ArrayList<CredentialsElement>) objectInputStream.readObject();
    objectInputStream.close();
    return dataList;
    ```
1. Do not forget the master key! - No *reset password* functionality is provided;
1. The key is stored using a *Java KeyStore Manager*.

###Contributing
Please report any issues by opening a ticket to discuss what you would like to fix or to implement.
We provide full access to the code - there is nothing to hide.

###Future Plans
The plan is to further extend the password manager idea into a browser extension, or maybe a fully functional web application. Check the repository for further updates.

###Contact
We are a small team of dedicated programmers and engineers which try to find our place into the world by building off-work, simple and user-friendly projects.

For any observations, comments, proposals, ideas or just some simple thoughts please contact us on our git page.

One project at a time... 