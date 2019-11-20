package com.company;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAccess {
    Connection conn = null;
    int errorCode = 0;

    public boolean connect(String path) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + path;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = CONNECTION_OPEN_FAILED;
            return false;
        }

    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        switch (errorCode) {
            case CONNECTION_OPEN_FAILED: return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED: return "Cannot load the product!";
        };
        return "OK";
    }

    public ProductModel loadProduct(int productID) {
        try {
            ProductModel product = new ProductModel();

            String sql = "SELECT ProductId, Name, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");
            return product;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = PRODUCT_LOAD_FAILED;
            return null;
        }
    }

    public PurchaseModel loadPurchase(int mPurchaseID) {
        try {
            PurchaseModel purchase = new PurchaseModel();

            String sql = "SELECT * FROM orders WHERE PurchaseId = " + mPurchaseID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            purchase.mProductID = rs.getInt("ProductId");
            purchase.mCustomerID = rs.getInt("CustomerId");
            purchase.mCost = rs.getInt("cost");
            purchase.mTotalCost = rs.getInt("totalcost");
            purchase.mTax = rs.getInt("tax");
            purchase.mQuantity = rs.getInt("quantity");
            purchase.mPrice = rs.getDouble("Price");
            return purchase;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = PRODUCT_LOAD_FAILED;
            return null;
        }
    }

    @Override
    public CustomerModel loadCustomer(int id) {
        CustomerModel customer = new CustomerModel();

        try {
            String sql = "SELECT * FROM Customers WHERE CustomerID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customer.mCustomerID = rs.getInt("CustomerID");
            customer.mName = rs.getString("Name");
            customer.mAddress = rs.getString("Address");
            customer.mPhone = rs.getInt("Phone");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }


    public Integer saveProduct(ProductModel product) {
        int success = 0;
        try {
            String sql = "INSERT INTO Products (ProductId, Name, Price, Quantity, Vendor) " +
                    "VALUES('"+product.mProductID+"','"+product.mName+"','"+product.mPrice+"','"+product.mQuantity+"','"+product.mVendor+"')";
            Statement stmt = conn.createStatement();
            success = stmt.executeUpdate(sql);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    public Integer saveCustomer(CustomerModel customer) {
        int success = 0;
        try {
            String sql = "INSERT INTO Customers (CustomerId, Name, Phone, Address) " +
                    "VALUES('"+customer.mCustomerID+"','"+customer.mName+"','"+customer.mPhone+"','"+customer.mAddress+"')";
            Statement stmt = conn.createStatement();
            success = stmt.executeUpdate(sql);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    public Integer savePurchase(PurchaseModel purchaseModel) {
        int success = 0;
        try {
            String query = "SELECT OrderId from Orders where OrderId = '"+purchaseModel.mPurchaseID+"'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return success;
            }
            String sql = "INSERT INTO Orders (OrderId, CustomerId, ProductId, Quantity, Price, TotalTax, TotalCost, Date) " +
                    "VALUES('"+purchaseModel.mPurchaseID+"','"+purchaseModel.mCustomerID+"','"+purchaseModel.mProductID+"','"+purchaseModel.mQuantity+"','"+purchaseModel.mPrice+"','"+purchaseModel.mTax+"','"+purchaseModel.mTotalCost+"','"+purchaseModel.mDate+"')";
            Statement stmt1 = conn.createStatement();
            success = stmt1.executeUpdate(sql);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

}
