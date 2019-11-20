package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerUI extends JFrame {
    public JFrame view;
    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);
    public JTextField txtPhone = new JTextField(20);

    public JButton btnAdd = new JButton("Add Customer");
    public JButton btnCancel = new JButton("Cancel");

    public AddCustomerUI() {
        view = new JFrame();
        view.setTitle("Add Customer");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("CustomerID:"));
        line1.add(txtCustomerID);
        pane.add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name:"));
        line2.add(txtName);
        pane.add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Address:"));
        line3.add(txtAddress);
        pane.add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Phone:"));
        line4.add(txtPhone);
        pane.add(line4);

        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnAdd);
        buttonPane.add(btnCancel);
        pane.add(buttonPane);

        btnAdd.addActionListener(new AddButtonController());
        btnCancel.addActionListener((actionEvent) -> view.dispose());

    }

    public void run() {
        view.setVisible(true);
    }

    class AddButtonController implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customerModel = new CustomerModel();

            String s = txtCustomerID.getText();

            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Empty Customer ID");
                return;
            }
            try {
                customerModel.mCustomerID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "CustomerId is INVALID (not a number)!!!");
                return;
            }

            s = txtName.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Customer Name could not be EMPTY!!!");
                return;
            }
            customerModel.mName = s;

            s = txtAddress.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Address could not be EMPTY!!!");
                return;
            }

            customerModel.mAddress = s;

            s = txtPhone.getText();
            if (s.length() != 10) {
                JOptionPane.showMessageDialog(null,
                        "Invalid Phone Number (Format: 3347895678)");
                System.out.println(s);
                return;

            }
            System.out.println(s);

            customerModel.mPhone = Integer.parseInt(s);


            IDataAccess adapter = StoreManager.getInstance().getDataAccess();

            if (adapter.saveCustomer(customerModel).equals(1))
                JOptionPane.showMessageDialog(null,
                        "Customer is saved successfully!");
            else {
                System.out.println(adapter.getErrorMessage());
            }


        }
    }
}