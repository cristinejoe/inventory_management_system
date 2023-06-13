package model;


import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


/** This class was created to store all product's associated parts in the ObservableList. */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** This is the id getter
     * @return the id
     */
    public int getId() {

        return id;
    }

    /** This is the id setter
     * @param id
     */
    public void setId(int id) {

        this.id = id;
    }

    /** This is the name getter
     * @return the name
     */
    public String getName() {

        return name;
    }

    /** This is the name setter
     * @param name
     */
    public void setName(String name) {

        this.name = name;
    }

    /** This is the price getter
     * @return the price
     */
    public double getPrice() {

        return price;
    }

    /** This is the price setter
     * @param price the price to set
     */
    public void setPrice(double price) {

        this.price = price;
    }

    /** This is the stock getter
     * @return the stock
     */
    public int getStock() {

        return stock;
    }

    /** This is the stock setter
     * @param stock the stock to set
     */
    public void setStock(int stock) {

        this.stock = stock;
    }

    /** This is the min getter
     * @return the min
     */
    public int getMin() {

        return min;
    }

    /** This is the min setter
     * @param min the min to set
     */
    public void setMin(int min) {

        this.min = min;
    }

    /** This is the max getter
     * @return the max
     */
    public int getMax() {

        return max;
    }

    /** This is the max setter
     * @param max the max to set
     */
    public void setMax(int max) {

        this.max = max;
    }

    //addAssociatedPart(part:Part):void
    /** This method adds new part to the associatedParts ObservableList
     * @param part
     */
    public void addAssociatedPart(Part part){

        associatedParts.add(part);
    }

    // deleteAssociatedPart(selectedAssociatedPart:Part):boolean
    /** This method deletes a part from the associatedParts ObservableList
     * @param selectedAssociatedPart
     * @return true
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){

        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    // getAllAssociatedParts():ObservableList<Part>
    /** This method returns all associated parts of a product
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts(){

        return associatedParts;
    }

}

