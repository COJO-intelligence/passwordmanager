# Java Password Manager
Presenting to the world a solution for a basic password manager built using Oracle's java security framework and enhanced with Swing-like user interface.

## Version 2.0.0 Released
Implemented new features and improved previous build:
* New entries aiming to improve the quality of the stored information (Account Type, Date Created, Date Modified and "Deactivated?" checkbox);
* New utility feature in the form of a random password generator;
* Inactivity for 15 minutes will lock the application;
* Various security improvements (Key derivation function for the login, Main password type switch from String to CharArray, Changed encryption algorithm to AES in Galois Counter Mode to provide a form of protection against data tampering).

**Warning!** Due to major modifications and additions in this version, files created with version 1.0.0 are not compatible with this version. Version 2.0.0 will have its files in a different directory, but on the same user's home path.

## Version 1.0.0
### Prerequisites
* Java SE Runtime Environment 8 - Minimum Requirement;
* Java added to System PATH.

**Note:** Development was made on Java SE Development Kit 14.0.1, but the application was tested on Java SE Runtime Environment 8 as well.

**Why Java?** To be able to run the application with no trouble on whatever operation system is provided. No matter if it is Windows or Linux machines.

### Usage
* Download the passwordManager.jar file located in the *app* package;
* Open the System Terminal and navigate to the location of the JAR;
* Run the below command:
```
java -jar passwordManager.jar
```

Alternatively, you can run the application by double clicking on the JAR file.
 
**Quick Debug:** If the command did not launch the application, please make sure java is installed and added to PATH by running one of the following commands:
```
java -version
javac -version
```
which will output something similar to the following:
```
java version "14.0.1" 2020-04-14
Java(TM) SE Runtime Environment (build 14.0.1+7)
Java HotSpot(TM) 64-Bit Server VM (build 14.0.1+7, mixed mode, sharing)
```

**Note:** The project was developed using IntelliJ IDEA Community Edition. For importing the source code into an IDE and compile it without any errors, please add the external JAR **flatlaf-0.38.jar** into your project. The JAR is located into the *src\main\resources* package.

### Explaining the Project Structure
The project is divided into two main ideas which are the *Login Page* and the *Entries Page*. Only the master key is required to access all the entries. The Password Manager works alongside 4 files: 
1. **pm.enc** - The main file which contains all the password inputs and all its related information. The file is always encrypted. Only the content is decrypted at runtime in order to display the values;
1. **pm.dat** - This file contains the SHA-512 hash of the master password combined with a couple of salts. This password is used only to login in the app;
1. **pm.p12** - This file is the keystore that contains the master key used for encrypting and decrypting; 
1. **pm.log** - The log file which we require you to send in order to debug a certain crash situation or malfunction. Please note that we do not collect any information about the system;

All these files are created at runtime and updated properly. Also, their location is set to the user's home directory path (under the directory PMCojoFiles).

**Very Important!** Current version does not provide a way to reset the main password. We strongly recommend to not forget it. If such a situation occurs, or you simply want to reset the app, delete all the 4 files described above. Be aware that such an action will erase all previously added entries!

Here is a list of the main algorithms and best practice logic used inside the application:
1. *SHA-512* with salts - used for hashing;
    ```java
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
    String finalPassword = SALT + password + SALT;
    return messageDigest.digest(finalPassword.getBytes());
    ```
1. *AES* in *CBC* Mode with *PKCS5Padding* - used for cryptographic operations;
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
   
1. The key is generated and stored using *Java KeyStore Manager*.
    ```java
    KeyGenerator keyGen = KeyGenerator.getInstance(algorithmEncrypt);
    SecureRandom secRandom = new SecureRandom();
    keyGen.init(secRandom);
    return keyGen.generateKey();
    ```

### Contributing
Please report any issues by sending an email to cojo.intelligence@gmail.com, on our Discord Server (https://discord.gg/cS7wBKu) or by opening a ticket on our git page to discuss what you would like to fix or to implement.
We provide full access to the code, even for the GUI/GUX - there is nothing to hide.

### Future Plans
For short term, the plan is to further extend the password manager idea into a browser extension, or maybe a web application. For long term, we aim to provide a full cloud password manager solution, including coverage over mobile devices.

Check the repository for further updates.

### Contact
We are a small team of dedicated programmers and engineers which try to find our place into the world by building off-work, simple and user-friendly projects. For any observations, comments, proposals, ideas or just some random thoughts please contact us at cojo.intelligence@gmail.com, on our Discord Server (https://discord.gg/cS7wBKu) or on our git page.

One project at a time... 

### DONATE

[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=T5VSHQCBRBAZC)

