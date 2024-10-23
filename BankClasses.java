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

    public void setDOB(String dob){
        this.dob = dob;
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

    public String getDOB(){
        return this.dob;
    }

    public void displayAccountInformation(Customer account){
        System.out.println("\n-------------Account Information--------------");
        System.out.println("Checking Account Balance: "+account.getCheckingAccountNumber());
        System.out.println("Savings Account Balance: "+account.savingsAccountNumber);
    }
};

class Credit{

    private float principle;
    private int paymentPeriod, creditLimit;

    Credit(){
        System.out.println("Default Credit object created ");
    }

    public void setPaymentPeriod(int paymentPeriod){
        this.paymentPeriod = paymentPeriod;
    }

    public void setPrinciple(float principle){
        this.principle = principle;
    }

    public float getPrinciple(Account account){
        return this.principle;
    }

    public int getPaymentPeriod(Account account){
        return this.paymentPeriod;
    }

    public int getCreditLimit(Account account){
        return this.creditLimit;
    }

    public void displayInformation(Account account){
        System.out.println("Display info callled under credit object");
    }

    private boolean creditTransaction(Account account, float amount){
        System.out.println("Credit transaction called under credit object");
        return true;
    }
};