package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class was created to store all parts and all products in the ObservableLists. */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    //Methods for parts

    //addPart(newPart:Part):void
    /** This method add new parts to allParts ObservableList
     * @param newPart
     */
    public static void addPart(Part newPart){

        allParts.add(newPart);
    }

    //getAllParts():ObservableList<Part>
    /** This method returns all parts of the ObservableList
     * @return allParts
     */
    public static ObservableList<Part> getAllParts(){

        return allParts;
    }

    //lookupPart(partId:int):Part
    /** This method look up for parts in the ObservableList by typing the ID
     * @param partId
     * @return part
     */
    public static Part lookupPart(int partId){
        for(Part part: Inventory.getAllParts()){
            if(part.getId() == partId){
                return part;
            }
        }
        return null;
    }

    //lookupPart(partName:String):ObservableList<Part>
    /** This method look up for parts in the ObservableList by typing the part name
     * @param partName
     * @return answer
     */
    public static ObservableList<Part> lookupPart(String partName){

        ObservableList<Part> answer = FXCollections.observableArrayList();

        for (Part part: allParts){
            if (part.getName().toLowerCase().contains(partName.toLowerCase())){
                answer.add(part);
            }
        }
        return answer;
    }

    //updatePart(index:int, selectedPart:Part):void
    /** This method updates a selected part in the allParts ObservableList
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart){

        allParts.set(index, selectedPart);
    }

    //deletePart(selectedPart:Part):boolean
    /** This method deletes a part in the allParts ObservableList
     * @param selectedPart
     * @return true
     */
    public static boolean deletePart(Part selectedPart){
        if (allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }else{
            return false;
        }

    }

    // Methods for products

    //addProduct(newProduct:Product):void
    /** This method add new products to allProducts ObservableList
     * @param newProduct
     */
    public static void addProduct(Product newProduct){

        allProducts.add(newProduct);
    }

    //getAllProducts():ObservableList<Product>
    /** This method returns all products of the ObservableList
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }

    //lookupProduct(productId:int):Product
    /** This method look up for products of the ObservableList
     * @param productId
     * @return product
     */
    public static Product lookupProduct(int productId) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    //lookupProduct(productName:String):ObservableList<Product>
    /** This method look up for products in the ObservableList by typing the product name
     * @param productName
     * @return answer
     */
    public static ObservableList<Product> lookupProduct(String productName){

        ObservableList<Product> answer = FXCollections.observableArrayList();

        for (Product product: allProducts){
            if (product.getName().toLowerCase().contains(productName.toLowerCase())){
                answer.add(product);
            }
        }
        return answer;
    }

    //updateProduct(index:int, newProduct:Product):void
    /** This method updates a selected product in the allProducts ObservableList
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct){

        allProducts.set(index, newProduct);
    }

    //deleteProduct(selectedProduct:Product):boolean
    /** This method deletes a product in the allProducts ObservableList
     * @param selectedProduct
     * @return allProducts
     */
    public static boolean deleteProduct(Product selectedProduct){

        return allProducts.remove(selectedProduct);
    }
}
