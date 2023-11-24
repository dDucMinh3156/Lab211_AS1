/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import model.Products;
import model.SortQuantity;

/**
 *
 * @author Ta Minh Duc
 */
public class ProductList {

    private ArrayList<Products> listProducts;

    Scanner sc = new Scanner(System.in);

    public ProductList() {
        listProducts = new ArrayList<>();
    }

    //-- Load data of products from file
    public boolean loadFromFile(String filename) {
        try {
            File f = new File(filename);
            if (!f.exists()) {
                System.out.println("The file does not existed!!!");
                return false;
            } else {
                BufferedReader read = new BufferedReader(new FileReader(f));
                String line = read.readLine();

                //-- separate product information into categories
                while (line != null) {
                    String[] sP = line.split(", ");
                    if (sP.length <= 1) {
                        break;
                    }
                    String productID = sP[0].trim();
                    String productName = sP[1].trim();
                    float unitPrice = Float.parseFloat(sP[2].trim());
                    int Quantity = Integer.parseInt(sP[3].trim());
                    String Status = sP[4].trim();
                    Products loadProducts = new Products(productID, productName, unitPrice, Quantity, Status);
                    listProducts.add(loadProducts);
                    line = read.readLine();
                }
                read.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //-- Option 1: Csreate new data for product in list
    public void addProduct() {
        boolean ask;
        do {
            try {
                //-- enter productID, productName, unitPrice, Quantity, Status
                String aProductID = Utils.getString("Enter product ID: (need to capitalize all letters)").trim();
                //-- check exist of product ID
                int pos = searchID(aProductID);
                while (pos > -1) {
                    System.out.println("Product exist! Please enter another ID");
                    aProductID = Utils.getString("Enter the product ID want to add: ").toUpperCase();
                    pos = searchID(aProductID);
                }

                String aProductName = Utils.getString("Enter name of product (at least 5 characters without spaces): ").trim();
                while (aProductName.length() <= 4 || aProductName.contains(" ")) {
                    System.out.println("Wrong format!!! [Need at least 5 characters without spaces]");
                    aProductName = Utils.getString("Enter name of product (at least 5 characters without spaces): ").trim();
                }
                //-- add Unit Price
                float aUnitPrice = Utils.getFloat("Enter the unit price [0-10,000]: ", 0, 10000);

                //-- add Quantity
                int aQuantity = Utils.getInt("Enter the quantity [0-1,000]: ", 0, 1000);

                //-- add Status
                String aStatus;
                while (true) {
                    System.out.println("Enter the status of product [av:Available/ nav:Not Available]: ");
                    String choice = sc.nextLine().toLowerCase().trim();
                    if (!choice.contains("n") && choice.contains("av")) {
                        aStatus = "Available";
                        break;
                    } else if (choice.contains("nav")) {
                        aStatus = "Not Available";
                        break;
                    } else {
                        System.out.println("Invalid choice! Please enter again.");
                    }
                }

                Products nProducts = new Products(aProductID, aProductName, aUnitPrice, aQuantity, aStatus);
                listProducts.add(nProducts);
                System.out.println("Data product is added successfully!");

            } catch (Exception e) {
                System.out.println("Data product has not been added!!!");
            }

            ask = Utils.confirmYesNo("Do you want continue create? [Y:yes/ N:no]");
        } while (ask);

    }

    //-- Option 2: Check product exists or not
    public void checkProductID() {
        boolean ask;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the product ID want to search:");
            String prID = sc.nextLine().trim().toUpperCase();
            Products foundProduct = null;
            for (Products product : listProducts) {
                if (product.getProductID().equals(prID)) {
                    foundProduct = product;
                    break;
                }
            }
            if (foundProduct != null) {
                System.out.println("Exist Product");
                //System.out.println(foundProduct.toString());
            } else {
                System.out.println("No product found!!!");
            }

            ask = Utils.confirmYesNo("Do you want continue check? [Y:yes/ N:no]");
        } while (ask);

    }

    //-- Option 3: Search product following a part of name
    public void searchProductName() {
        boolean ask;
        do {
            Scanner sc = new Scanner(System.in);
            String aPartOfName = Utils.getString("Enter a part of product name: ").toLowerCase();

            if (listProducts.isEmpty()) {
                System.out.println("Have no any Product.");
            } else {
                System.out.println("|-----------------------------List of products--------------------------------------|");
                System.out.println("|------------|----------------------------|--------------|----------|---------------|");
                System.out.println("| Product ID |        Product Name        |  Unit Price  | Quantity |    Status     |");
                System.out.println("|------------|----------------------------|--------------|----------|---------------|");

                //-- sort data following the name product: ascending alphabet
                Collections.sort(listProducts, new Products());
                //-- show list name product
                for (Products name : listProducts) {
                    String namePart = name.getProductName().toLowerCase();
                    if (namePart.contains(aPartOfName)) {
                        System.out.println(name);
                    }
                }
                System.out.println("|-----------------------------------------------------------------------------------|");
            }

            ask = Utils.confirmYesNo("Do you want continue search? [Y:yes/ N:no]");
        } while (ask);

    }

    //-- option 4.1: Update new data for products in list
    public void updateProduct() {
        boolean ask;
        do {
            try {
                boolean askUpdate;
                Scanner sc = new Scanner(System.in);

                //-- check exist of product ID
                String uProductID = Utils.getString("Enter the product ID want to update: ").toUpperCase();
                int pos = searchID(uProductID);
                while (pos < 0) {
                    System.out.println("Product does not exist");
                    uProductID = Utils.getString("Enter the product ID want to update: ").toUpperCase();
                    pos = searchID(uProductID);
                }
                //-- show old info
                System.out.println("Old information product:");
                System.out.println(listProducts.get(pos).toString());
                //-- set new data to list product
                Products uProduct = listProducts.get(pos);

                //-- Case update new Name
                System.out.println("Enter the product name want to update (at least 5 characters without spaces): ");
                String uProductName = sc.nextLine().trim();
//                if (!uProductName.isEmpty()) {
//                    while (uProductName.length() <= 4 || uProductName.contains(" ")) {
//                        System.out.println("Wrong format!!! Need at least 5 characters without spaces");
//                        uProductName = Utils.getString("Enter name of product (at least 5 characters without spaces): ").trim();
//                    }
//                }
                while (uProductName.length() <= 4 || uProductName.contains(" ")) {
                    System.out.println("Wrong format!!! Need at least 5 characters without spaces");
                    uProductName = Utils.getString("Enter name of product (at least 5 characters without spaces): ").trim();
                }
                //-- ask check confirm update Name
                askUpdate = Utils.confirmYesNoUpdate("Do you want update old Name? [Y:yes/ N:no]");
                if (askUpdate == true) {
                    uProduct.setProductName(uProductName);
                    System.out.println("*** Name is updated successfully! ***");
                }

                //-- Case update new Unit Price
                float uUnitPrice = Utils.getFloatUpdate("Enter the unit price want to update [0-10,000]: ", 0, 10000);
                if (uUnitPrice > 1) {
                    //-- ask check confirm update Unit Price
                    askUpdate = Utils.confirmYesNoUpdate("Do you want update old Unit Price? [Y:yes/ N:no]");
                    if (askUpdate == true) {
                        uProduct.setUnitPrice(uUnitPrice);
                        System.out.println("*** Unit Price is updated successfully! ***");
                    }
                }

                //-- Case update new Quantity
                int uQuantity = Utils.getIntUpdate("Enter the quantity want to update [0-1,000]: ", 0, 1000);
                if (uQuantity > 1) {
                    //-- ask check confirm update Quantity
                    askUpdate = Utils.confirmYesNoUpdate("Do you want update old Quantity? [Y:yes/ N:no]");
                    if (askUpdate == true) {
                        uProduct.setQuantity(uQuantity);
                        System.out.println("*** Quantity is updated successfully! ***");
                    }
                }

                //-- Case update new Status
                String uStatus;
                System.out.println("Enter the status of product [av:Available/ nav:Not Available]: ");
                uStatus = sc.nextLine().trim();
                if (!uStatus.isEmpty()) {
                    while (true) {
                        if (!uStatus.contains("n") && uStatus.contains("av")) {
                            uStatus = "Available";
                            break;
                        } else if (uStatus.contains("nav")) {
                            uStatus = "Not Available";
                            break;
                        } else {
                            System.out.println("Invalid choice!!! Please enter again.");
                        }
                    }
                    //-- ask check confirm update Status
                    askUpdate = Utils.confirmYesNoUpdate("Do you want update old Status? [Y:yes/ N:no]");
                    if (askUpdate == true) {
                        uProduct.setStatus(uStatus);
                        System.out.println("*** Status is updated successfully! ***");
                    }
                }
            } catch (Exception e) {
                System.out.println("Data product has not been updated!!!");
                e.printStackTrace();
            }

            ask = Utils.confirmYesNo("Do you want continue update? [Y:yes/ N:no]");
        } while (ask);
    }

    //-- Option 4.2: Delete data of product in list
    public void deleteProduct() {
        boolean ask;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter product ID: ");
            String removeID = sc.nextLine().trim().toUpperCase();
            int pos = searchID(removeID);
            if (pos < 0) {
                System.out.println("Product does not exist!");
            } else {
                System.out.println("Information product:");
                System.out.println(listProducts.get(pos).toString());
                System.out.println("Are you sure you want to DELETE? [Y:yes/ N:no]");
                String askDelete = sc.nextLine().trim();
                if (askDelete.equalsIgnoreCase("Y")) {
                    listProducts.remove(pos);
                    System.out.println("Remove data product successfully!");
                }
            }

            ask = Utils.confirmYesNo("Do you want continue delete? [Y:yes/ N:no]");
        } while (ask);
    }

    //-- find position of product by ID
    public int searchID(String prID) {
        int N = listProducts.size();
        for (int i = 0; i <= N - 1; i++) {
//            if (listProducts.get(i).getProductID().equals(prID)) {
//                return i;
//            }
            if (listProducts.get(i).getProductID().equals(prID)) {
                return i;
            }
        }
        return -1;
    }

    //-- Option 5: Save data of products to file
    public boolean saveToFile(String filename) {
        try {
            PrintWriter pw = new PrintWriter(filename);
            for (Products product : listProducts) {
                pw.println(product.getProductID() + ", " + product.getProductName() + ", "
                        + product.getUnitPrice() + ", " + product.getQuantity() + ", " + product.getStatus());
            }
            pw.flush();
            pw.close();
            System.out.println("Data product is save successfully!");
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Data product has not been saved!!!");
            e.printStackTrace();
            return false;
        }
    }

    //-- Option 6
    public void listAllProduct() {

        boolean ask;
        do {
            System.out.println("|-----------------------------List of products--------------------------------------|");
            System.out.println("|------------|----------------------------|--------------|----------|---------------|");
            System.out.println("| Product ID |        Product Name        |  Unit Price  | Quantity |    Status     |");
            System.out.println("|------------|----------------------------|--------------|----------|---------------|");
            //-- sort product Quantity descending; same Quantity -> sort UnitPrice ascending

            Collections.sort(listProducts, new SortQuantity());
            //Collections.sort(listProducts, new sortUnitPrice());
            for (Products allproducts : listProducts) {
                System.out.println(allproducts);
            }
            System.out.println("|-----------------------------------------------------------------------------------|");

            ask = Utils.confirmYesNo("Do you want continue view list? [Y:yes/ N:no]");
        } while (ask);
    }

}
