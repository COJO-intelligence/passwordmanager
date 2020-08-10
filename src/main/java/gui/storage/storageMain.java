package main.java.gui.storage;

import main.java.storage.CredentialsElement;
import main.java.storage.DataOperations;

import javax.swing.*;
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
    private JLabel Link;
    private JTextField linkTextField;

    private final DataOperations dataOperations = new DataOperations();
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
                        linkTextField.getText(),
                        usernameTextField.getText(),
                        emailTextField.getText(),
                        passwordTextField.getText(),
                        additionalCommentsTextField.getText(),
                        filePath
                );
            }
        });

        addNewButton.addActionListener(e -> {
            onAddNew();
            list.setSelectedIndex(list.getSelectedIndex() + 1);
        });

        deleteButton.addActionListener(e -> {
            int index = list.getSelectedIndex();
            deleteElement(list.getSelectedIndex());
            deleteItem = false;
            deleteButton.setEnabled(false);
            saveButton.setEnabled(false);
            if(index == 0 || index <= list.getLastVisibleIndex()){
                list.setSelectedIndex(index);
            } else {
                list.setSelectedIndex(index - 1);
            }
        });

        list.addListSelectionListener(e -> {
            saveButton.setEnabled(true);
            deleteButton.setEnabled(true);
            if(!e.getValueIsAdjusting() && list.getSelectedIndex() >= 0) {
                elementPanel.setVisible(true);
                setFields(list.getSelectedIndex());
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void firstAppBoot(String filePath) {
        CredentialsElement firstElement = new CredentialsElement("Welcome!", "", "", "", "", "");
        ArrayList<CredentialsElement> testList = new ArrayList<>();
        testList.add(firstElement);
        dataOperations.setDataList(testList);
        try {
            dataOperations.writeDataListToFile(filePath);
        } catch(Exception e) {
            e.printStackTrace();
        }
        defaultListModel.addElement(dataOperations.getDataList().get(0).getDomain());
    }

    private void loadListPanel(String filePath) {
        //dataOperations = new DataOperations();
        try {
            dataOperations.loadDataListToDataOperationsObject(filePath);
            //dataOperations = FileOperations.loadAllElementsIntoArrayList(filePath);
        } catch(Exception e) {
            e.printStackTrace();
        }
        for (CredentialsElement credentialsElement : dataOperations.getDataList()) {
            defaultListModel.addElement(credentialsElement.getDomain());
        }
    }

    public void writeListPanel(String filePath) {
        try {
            dataOperations.writeDataListToFile(filePath);
            //FileOperations.writeAllElementsIntoFile(dataOperations, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFields(int listIndex) {
        domainTextField.setText(dataOperations.getDataList().get(listIndex).getDomain());
        linkTextField.setText(dataOperations.getDataList().get(listIndex).getLink());
        usernameTextField.setText(dataOperations.getDataList().get(listIndex).getUsername());
        emailTextField.setText(dataOperations.getDataList().get(listIndex).getEmail());
        passwordTextField.setText(dataOperations.getDataList().get(listIndex).getPassword());
        additionalCommentsTextField.setText(dataOperations.getDataList().get(listIndex).getAdditionalComments());
    }

    private void onSave(int listIndex, String domain, String link, String username, String email, String password, String additionalComments, String filePath) {
        dataOperations.getDataList().get(listIndex).setDomain(domain);
        dataOperations.getDataList().get(listIndex).setLink(link);
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

    private void deleteElement(int listIndex) {
        if(listIndex >= 0) {
            elementPanel.setVisible(false);
            domainTextField.setText("");
            usernameTextField.setText("");
            emailTextField.setText("");
            passwordTextField.setText("");
            additionalCommentsTextField.setText("");
            System.out.println("The index is: " + listIndex);
            defaultListModel.removeElementAt(listIndex);
            dataOperations.getDataList().remove(listIndex);
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
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                storageGUI.writeListPanel(filePath);
                if (JOptionPane.showConfirmDialog(frame,
                        "The passwords were saved!", "Success!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);

                }
            }
        });
    }

}
