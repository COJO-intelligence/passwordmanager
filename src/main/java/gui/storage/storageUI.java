package main.java.gui.storage;

import main.java.storage.CredentialsElement;
import main.java.storage.DataOperations;

import javax.swing.*;
import java.util.ArrayList;

public class storageUI extends JFrame {
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
    private JPanel elementPanel;
    private JTextField linkTextField;

    private final DataOperations dataOperations = new DataOperations();
    DefaultListModel<String> defaultListModel = new DefaultListModel<>();

    public storageUI() {
        if(dataOperations.isFilePresent()) {
            loadListPanel();
        } else {
            firstAppBoot();
        }
        elementPanel.setVisible(false);

        list.setModel(defaultListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(e -> {
            saveButton.setEnabled(true);
            deleteButton.setEnabled(true);
            if(!e.getValueIsAdjusting() && list.getSelectedIndex() >= 0) {
                elementPanel.setVisible(true);
                domainTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getDomain());
                linkTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getLink());
                usernameTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getUsername());
                emailTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getEmail());
                passwordTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getPassword());
                additionalCommentsTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getAdditionalComments());
            }
        });

        addNewButton.addActionListener(e -> {
            CredentialsElement credentialsElement = new CredentialsElement();
            dataOperations.addNewElement(credentialsElement);
            defaultListModel.addElement(credentialsElement.getDomain());
            list.setSelectedIndex(list.getSelectedIndex() + 1);
        });

        deleteButton.setEnabled(false);
        deleteButton.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if(index >= 0) {
                elementPanel.setVisible(false);
                domainTextField.setText("");
                usernameTextField.setText("");
                emailTextField.setText("");
                passwordTextField.setText("");
                additionalCommentsTextField.setText("");
                defaultListModel.removeElementAt(index);
                dataOperations.getDataList().remove(index);
            }
            deleteButton.setEnabled(false);
            saveButton.setEnabled(false);
            if(index == 0 || index <= list.getLastVisibleIndex()){
                list.setSelectedIndex(index);
            } else {
                list.setSelectedIndex(index - 1);
            }
        });

        saveButton.setEnabled(false);
        saveButton.addActionListener(e -> {
            dataOperations.getDataList().get(list.getSelectedIndex()).setDomain(domainTextField.getText());
            dataOperations.getDataList().get(list.getSelectedIndex()).setLink(linkTextField.getText());
            dataOperations.getDataList().get(list.getSelectedIndex()).setUsername(usernameTextField.getText());
            dataOperations.getDataList().get(list.getSelectedIndex()).setEmail(emailTextField.getText());
            dataOperations.getDataList().get(list.getSelectedIndex()).setPassword(passwordTextField.getText());
            dataOperations.getDataList().get(list.getSelectedIndex()).setAdditionalComments(additionalCommentsTextField.getText());
            if(!domainTextField.getText().equals(defaultListModel.get(list.getSelectedIndex()))) {
                defaultListModel.set(list.getSelectedIndex(), domainTextField.getText());
            }
            writeListPanel();
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    //TODO try catch
    public void writeListPanel() {
        try {
            dataOperations.writeDataListToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void firstAppBoot() {
        CredentialsElement firstElement = new CredentialsElement("Welcome!", "", "", "", "", "");
        ArrayList<CredentialsElement> arrayList = new ArrayList<>();
        arrayList.add(firstElement);
        dataOperations.setDataList(arrayList);
        writeListPanel();
        defaultListModel.addElement(dataOperations.getDataList().get(0).getDomain());
    }

    private void loadListPanel() {
        try {
            dataOperations.loadDataListToDataOperationsObject();
        } catch(Exception e) {
            e.printStackTrace();
        }
        for (CredentialsElement credentialsElement : dataOperations.getDataList()) {
            defaultListModel.addElement(credentialsElement.getDomain());
        }
    }

}
