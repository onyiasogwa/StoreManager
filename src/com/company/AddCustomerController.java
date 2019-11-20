package com.company;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerController {
    public AddCustomerUI view;
    public IDataAccess dataAdapter;

    public AddCustomerController(AddCustomerUI view, IDataAccess adapter) {
        this.view = view;
        this.dataAdapter = adapter;

        view.btnAdd.addActionListener(new AddButtonController());
        view.btnCancel.addActionListener(new CancelButtonController());

    }

    class AddButtonController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();
            customer.mCustomerID = Integer.parseInt(view.txtCustomerID.getText());
            customer.mName = view.txtName.getText();
            customer.mAddress = view.txtAddress.getText();
            customer.mPhone = Integer.parseInt(view.txtPhone.getText());

            dataAdapter.saveCustomer(customer);
            JOptionPane.showMessageDialog(null, "Name = " + customer.mName);
        }
    }

    class CancelButtonController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            view.setVisible(false);
        }
    }
}

