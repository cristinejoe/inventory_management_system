package model;

/** This class is a subclass of 'Part' class. It inherits the members of 'Part' class and it gets and sets the company name for the outsourced parts. */
public class OutSourced extends Part{


    private String companyName;
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);

        this.companyName = companyName;
    }

    /** This is the companyName getter
     * @return the company name
     */
    public String getCompanyName() {

        return companyName;
    }

    /** This is the companyName setter
     * @param companyName
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
}
