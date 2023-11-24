/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Menu;
import controller.ProductList;
import controller.Utils;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ta Minh Duc
 */
public class ViewManager {

    public static void main(String[] args) {
        ProductList productList = new ProductList();
        Scanner sc = new Scanner(System.in);
        String choiceLoadFile = Utils.getString("Do you want to load file? [Y:yes/ N:no]");;
        if ("Y".equalsIgnoreCase(choiceLoadFile)) {
            System.out.print("Enter file name want to load: ");
            String prListFilename = sc.nextLine();
            if (!productList.loadFromFile(prListFilename)) {
                System.out.println("Can not load file \"" + prListFilename + "\"!!!");
                return;
            } else {
                System.out.println("File \"" + prListFilename + "\" is loaded successfully!");
            }
        }

        //-- ArrayList options
        ArrayList<String> ops = new ArrayList<>();
        ops.add("Create a Product.");
        ops.add("Check exits Product.");
        ops.add("Search Product information by Name.");
        ops.add("Update Product.");
        ops.add("Save Products to file.");
        ops.add("Print list Products from the file.");
        ops.add("");

        //-- create menu
        Menu menu = new Menu();
        int choice = 0;
        do {
            System.out.println("\n--------------MENU--------------");
            choice = menu.int_getChoice(ops);
            switch (choice) {
                case 1:
                    productList.addProduct();
                    break;
                case 2:
                    productList.checkProductID();
                    break;
                case 3:
                    productList.searchProductName();
                    break;
                case 4:
                    //-- menu for update or delete
                    System.out.println("1. Update Product.\n"
                            + "2. Delete Product.\n"
                            + "Other. Exit.");
                    do {
                        int choice2 = sc.nextInt();
                        switch (choice2) {
                            case 1:
                                productList.updateProduct();
                                break;
                            case 2:
                                productList.deleteProduct();
                                break;
                            default:
                                break;
                        }
                    } while (choice > 0 && choice < 3);
                    break;
                case 5:
                    //-- thêm lựa chọn hỏi có load file tiếp hay ko
                    String saveFileName = Utils.getString("Enter file name want to save: ");
                    productList.saveToFile(saveFileName);
                    break;
                case 6:
                    productList.listAllProduct();
                    break;
                default:
                    System.out.println("-------END-------");
                    break;
            }

        } while (choice > 0 && choice < ops.size());

    }

}
