package main.java.gui.storage;

import main.java.storage.CredentialsElement;
import main.java.storage.DataOperations;
import main.java.storage.FileOperations;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class storageMain extends JFrame {
    private JButton addNewButton;
    private JButton saveButton;
    private JButton deleteButton;
    private JTextField domainTextField;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JTextField emailTextField;
    private JTextField additionalCommentsTextField;
    private JList<String> list;
    private JPanel mainPanel;
    private JPanel listPanel;
    private JPanel elementPanel;
    private JPanel titlePanel;
    private JPanel elementPanel2;

    private DataOperations dataOperations = new DataOperations();
    DefaultListModel<String> defaultListModel = new DefaultListModel<>();
    boolean deleteItem = false;

    public storageMain(String filePath) {
        File file = new File(filePath);
        deleteButton.setEnabled(false);
        saveButton.setEnabled(false);
        elementPanel.setVisible(false);
        if(!file.exists()) {
            firstAppBoot(filePath);
        } else {
            loadListPanel(filePath);
        }
        list.setModel(defaultListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSave(
                        list.getSelectedIndex(),
                        domainTextField.getText(),
                        usernameTextField.getText(),
                        emailTextField.getText(),
                        passwordTextField.getText(),
                        additionalCommentsTextField.getText(),
                        filePath
                );
            }
        });

        addNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAddNew();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteElement(list.getSelectedIndex(), filePath);
                deleteItem = false;
                deleteButton.setEnabled(false);
                saveButton.setEnabled(false);
            }
        });

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                saveButton.setEnabled(true);
                deleteButton.setEnabled(true);
                if(!e.getValueIsAdjusting() && list.getSelectedIndex() >= 0) {
                    elementPanel.setVisible(true);
                    setFields(list.getSelectedIndex());
                }
            }
        });
    }

    private void firstAppBoot(String filePath) {
        CredentialsElement firstElement = new CredentialsElement("Welcome!", "", "", "", "");
        ArrayList<CredentialsElement> testList = new ArrayList<>();
        testList.add(firstElement);
        dataOperations.setDataList(testList);
        try {
            FileOperations.writeAllElementsIntoFile(dataOperations, filePath);
        } catch(Exception e) {
            e.printStackTrace();
        }
        defaultListModel.addElement(dataOperations.getDataList().get(0).getDomain());
    }

    private void loadListPanel(String filePath) {
        //dataOperations = new DataOperations();
        try {
            dataOperations = FileOperations.loadAllElementsIntoArrayList(filePath);
        } catch(Exception e) {
            e.printStackTrace();
        }
        for (CredentialsElement credentialsElement : dataOperations.getDataList()) {
            defaultListModel.addElement(credentialsElement.getDomain());
        }
    }

    private void writeListPanel(String filePath) {
        try {
            FileOperations.writeAllElementsIntoFile(dataOperations, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFields(int listIndex) {
        domainTextField.setText(dataOperations.getDataList().get(listIndex).getDomain());
        usernameTextField.setText(dataOperations.getDataList().get(listIndex).getUsername());
        emailTextField.setText(dataOperations.getDataList().get(listIndex).getEmail());
        passwordTextField.setText(dataOperations.getDataList().get(listIndex).getPassword());
        additionalCommentsTextField.setText(dataOperations.getDataList().get(listIndex).getAdditionalComments());
    }

    private void onSave(int listIndex, String domain, String username, String email, String password, String additionalComments, String filePath) {
        dataOperations.getDataList().get(listIndex).setDomain(domain);
        dataOperations.getDataList().get(listIndex).setUsername(username);
        dataOperations.getDataList().get(listIndex).setEmail(email);
        dataOperations.getDataList().get(listIndex).setPassword(password);
        dataOperations.getDataList().get(listIndex).setAdditionalComments(additionalComments);
        if(!domain.equals(defaultListModel.get(listIndex))) {
            defaultListModel.set(listIndex, domain);
        }
        writeListPanel(filePath);
    }

    private void onAddNew() {
        CredentialsElement credentialsElement = new CredentialsElement();
        dataOperations.addNewElement(credentialsElement);
        defaultListModel.addElement(credentialsElement.getDomain());
    }

    private void deleteElement(int listIndex, String filePath) {
        if(listIndex >= 0) {
            elementPanel.setVisible(false);
            domainTextField.setText("");
            usernameTextField.setText("");
            emailTextField.setText("");
            passwordTextField.setText("");
            additionalCommentsTextField.setText("");
            System.out.println("Indexul este: " + listIndex);
            defaultListModel.removeElementAt(listIndex);
            //list.remove(listIndex);
            dataOperations.getDataList().remove(listIndex);
            list.clearSelection();
            writeListPanel(filePath);
        }
    }

    public static void main(String[] args) {
        String filePath = "test.encrypt";
        storageMain storageGUI = new storageMain(filePath);
        JFrame frame = new JFrame("Password Manager");
        frame.setContentPane(storageGUI.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
