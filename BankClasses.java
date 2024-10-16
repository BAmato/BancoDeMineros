public class BankClasses{

    BankClasses(){
        System.out.println("Bank class object created");
    }
};

class Customer{
    
    
    private String firstName;
    private String lastName;
    private String dob;
    
    int checkingAccountNumber;
    int savingsAccountNumber;
    
    public Customer(){
        System.out.println("Customer object created");
    }

    public void setName(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setCheckingAccountNumber(int accountNumber){
        this.checkingAccountNumber = accountNumber;
    }

    public void setSavingsAccountNumber(int accountNumber){
        this.savingsAccountNumber = accountNumber;
    }

    public String getName(){
        return this.firstName +" "+ this.lastName;
    }

    public int getCheckingAccountNumber(){
        return this.checkingAccountNumber;
    }

    public int getSavingsAccountNumber(){
        return this.savingsAccountNumber;
    }

    public void displayAccountInformation(Customer account){
        System.out.println("\n-------------Account Information--------------");
        System.out.println("Checking Account Balance: "+account.getCheckingAccountNumber());
        System.out.println("Savings Account Balance: "+account.savingsAccountNumber);
    }
};
