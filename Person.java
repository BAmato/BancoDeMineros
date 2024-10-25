public abstract class Person {
    public String name;
    public int dobYear;
    private String address;
    private int ssn; 

    public Person(){}

    public void setName(String nameIn){
        this.name = nameIn;
    }
    public void setDob(int dobYearIn){
        this.dobYear = dobYearIn;
    }
    public void setAddress(String addressIn){
        this.address = addressIn;
    }
    public void setSsn(int ssnIn){
        this.ssn = ssnIn;
    }
    public String getName(){
        return name;
    }
    public int getDobYear(){
        return dobYear;
    }
    public String getAddress(){
        return address;
    }
    public int getSsn(){
        return ssn;
    }
}
