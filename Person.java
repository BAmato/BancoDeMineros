
/**
 * This is the abstract Person class
 * A person's personal information will be collected here
 * @author: Christin Moreno & Team 14
 * @version: 
 */
public abstract class Person {
    private String name;
    private String dob;
    private String address;
    private String phoneNumber; 

    /**
     * Constructor
     * Initializes the customer with a name, address, and unique ID.
     * @param name name for Person
     * @param dob date of birth for Person
     * @param address address for Person
     * @param phoneNumber phone number for Person
     * 
     */

    public Person(String name, String dob, String address, String phoneNumber){
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setDob(String dob){
        this.dob = dob;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns Person's name
     * @return String name 
     */
    public String getName(){
        return name;
    }
    /**
     * Returns Person's date of birth
     * @return String date of birth
     */
    public String getDob(){
        return dob;
    }
    /**
     * Returns Person's address
     * @return String address
     */
    public String getAddress(){
        return address;
    }
    /**
     * Returns Person's phone number
     * @return String phone number
     */
    public String getPhoneNumber(){
        return phoneNumber;
    }
}
