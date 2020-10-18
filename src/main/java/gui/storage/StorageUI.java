package main.java.gui.storage;

import main.java.MainUI;
import main.java.storage.CredentialsElement;
import main.java.storage.DataOperations;
import main.java.storage.PasswordGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;

public class StorageUI extends JFrame {
    final DefaultListModel<String> defaultListModel = new DefaultListModel<>();
    private final DataOperations dataOperations = new DataOperations();
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
    private JTextField dateCreatedTextField;
    private JTextField dateModifiedTextField;
    private JComboBox<String> accountTypeComboBox;
    private JCheckBox deactivatedCheckBox;
    private JButton generatePasswordButton;

    public StorageUI() throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException, ClassNotFoundException {
        if (dataOperations.isFilePresent()) {
            loadListPanel();
        } else {
            firstAppBoot();
        }
        elementPanel.setVisible(false);

        list.setModel(defaultListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dateCreatedTextField.setEditable(false);
        dateModifiedTextField.setEditable(false);
        String[] accountTypeSelection = new String[]{"Education", "Entertainment", "Financial", "Health", "High Importance", "Sport", "Transport", "Utility", "Work", "None"};
        for (String string : accountTypeSelection) {
            accountTypeComboBox.addItem(string);
        }

        list.addListSelectionListener(e -> {
            saveButton.setEnabled(true);
            deleteButton.setEnabled(true);
            if (!e.getValueIsAdjusting() && list.getSelectedIndex() >= 0) {
                elementPanel.setVisible(true);
                accountTypeComboBox.setSelectedIndex(dataOperations.getDataList().get(list.getSelectedIndex()).getAccountType());
                domainTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getDomain());
                linkTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getLink());
                usernameTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getUsername());
                emailTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getEmail());
                passwordTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getPassword());
                dateCreatedTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getDateCreatedString());
                dateModifiedTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getDateModifiedString());
                additionalCommentsTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getAdditionalComments());
                deactivatedCheckBox.setSelected(dataOperations.getDataList().get(list.getSelectedIndex()).isDeactivated());
            }
        });

        addNewButton.addActionListener(e -> {
            CredentialsElement credentialsElement = new CredentialsElement();
            dataOperations.addNewElement(credentialsElement);
            defaultListModel.addElement(credentialsElement.getDomain());
            list.setSelectedIndex(list.getLastVisibleIndex());
        });

        deleteButton.setEnabled(false);
        deleteButton.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index >= 0) {
                elementPanel.setVisible(false);
                accountTypeComboBox.setSelectedIndex(9);
                domainTextField.setText("");
                usernameTextField.setText("");
                emailTextField.setText("");
                passwordTextField.setText("");
                dateCreatedTextField.setText("");
                dateModifiedTextField.setText("");
                additionalCommentsTextField.setText("");
                deactivatedCheckBox.setSelected(false);
                defaultListModel.removeElementAt(index);
                dataOperations.getDataList().remove(index);
            }
            deleteButton.setEnabled(false);
            saveButton.setEnabled(false);
            if (index == 0 || index <= list.getLastVisibleIndex()) {
                list.setSelectedIndex(index);
            } else {
                list.setSelectedIndex(index - 1);
            }
        });

        saveButton.setEnabled(false);
        saveButton.addActionListener(e -> {
            saveContent();
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(getMainPanel()), "Passwords saved!", "Success!", JOptionPane.INFORMATION_MESSAGE);
        });

        generatePasswordButton.addActionListener(e -> {
            PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                    .useDigits(true)
                    .useLower(true)
                    .useUpper(true)
                    .usePunctuation(true)
                    .build();
            passwordTextField.setText(passwordGenerator.generate(16));
        });

        linkTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported())
                {
                    try {
                        Desktop desktop = java.awt.Desktop.getDesktop();
                        URI uri = new URI(linkTextField.getText());
                        if (uri.isAbsolute()) {
                            desktop.browse(uri);
                        }
                    } catch (URISyntaxException | IOException uriSyntaxException) {
                        uriSyntaxException.printStackTrace();
                    }
                }
            }
        });
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void writeListPanel() {
        try {
            dataOperations.writeAllElementsIntoFile(dataOperations.getDataList());
        } catch (Exception exception) {
            MainUI.treatError(exception, (JFrame) SwingUtilities.getWindowAncestor(mainPanel), 2);
        }
    }

    private void firstAppBoot() {
        CredentialsElement firstElement = new CredentialsElement(9,"Welcome!", "", "", "", "", "");
        ArrayList<CredentialsElement> arrayList = new ArrayList<>();
        arrayList.add(firstElement);
        dataOperations.setDataList(arrayList);
        writeListPanel();
        defaultListModel.addElement(dataOperations.getDataList().get(0).getDomain());
    }

    private void loadListPanel() throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, KeyStoreException, ClassNotFoundException {
        dataOperations.setDataList(dataOperations.loadAllElementsIntoArrayList());
        for (CredentialsElement credentialsElement : dataOperations.getDataList()) {
            defaultListModel.addElement(credentialsElement.getDomain());
        }
    }

    private void saveContent()
    {
        dataOperations.getDataList().get(list.getSelectedIndex()).setAccountType(accountTypeComboBox.getSelectedIndex());
        dataOperations.getDataList().get(list.getSelectedIndex()).setDomain(domainTextField.getText());
        dataOperations.getDataList().get(list.getSelectedIndex()).setLink(linkTextField.getText());
        dataOperations.getDataList().get(list.getSelectedIndex()).setUsername(usernameTextField.getText());
        dataOperations.getDataList().get(list.getSelectedIndex()).setEmail(emailTextField.getText());
        dataOperations.getDataList().get(list.getSelectedIndex()).setPassword(passwordTextField.getText());
        dataOperations.getDataList().get(list.getSelectedIndex()).setDateModifiedString();
        dataOperations.getDataList().get(list.getSelectedIndex()).setAdditionalComments(additionalCommentsTextField.getText());
        dataOperations.getDataList().get(list.getSelectedIndex()).setDeactivated(deactivatedCheckBox.isSelected());
        if (!domainTextField.getText().equals(defaultListModel.get(list.getSelectedIndex()))) {
            defaultListModel.set(list.getSelectedIndex(), domainTextField.getText());
        }
        dateModifiedTextField.setText(dataOperations.getDataList().get(list.getSelectedIndex()).getDateModifiedString());
        writeListPanel();
    }
}
