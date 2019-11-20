package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductController {
    public AddProductUI view;
    public IDataAccess dataAdapter;

    public AddProductController(AddProductUI view, IDataAccess adapter) {
        this.view = view;
        this.dataAdapter = adapter;
        view.btnAdd.addActionListener(new AddButtonController());
        view.btnCancel.addActionListener(new CancelButtonController());
    }

    class AddButtonController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();
            product.mProductID = Integer.parseInt(view.txtProductID.getText());
            product.mName = view.txtName.getText();
            product.mPrice = Double.parseDouble(view.txtPrice.getText());
            product.mQuantity = Double.parseDouble(view.txtQuantity.getText());

            dataAdapter.saveProduct(product);

            JOptionPane.showMessageDialog(null, "Name = " + product.mName);
        }
    }

    class CancelButtonController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            view.setVisible(false);
        }
    }

}