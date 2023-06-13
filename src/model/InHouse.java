package model;

/** This class is a subclass of 'Part' class. It inherits the members of 'Part' class and it gets and sets the machine ID for in house parts. */
public class InHouse extends Part{

    private int machineId;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);

        this.machineId = machineID;
    }

    /** This is the machine ID getter
     * @return the machine id
     */
    public int getMachineId() {

        return machineId;
    }

    /** This is the machine ID setter
     * @param machineId
     */
    public void setMachineId(int machineId) {

        this.machineId = machineId;
    }
}