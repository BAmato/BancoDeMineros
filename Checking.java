/**
 * this class represent the  Checking Account
 * checking account allows deposits and withdrawals, with defined overdraft limit
 * @author: P.Lorena Renteria
 * @version: 2.0
 */

public class Checking {
    private double overDraftLimit;// overdraft limit  to the checking account

    /**
     * constructor for checking account
     * @param accountNumber unique identifier for the account
     * @param accountHolder the customer who owns the account
     * @param overDraftLimit the maximum overdraft limit allowed for the account
     */
    public void checking(int accountNumber, String accountHolder, double overDraftLimit) {
        super(accountNumber, accountHolder);//call the constructor of the parent class 
        this.overDraftLimit = overDraftLimit;// set the overdraft limit for the account
    }

    /**
     * deposit a specific amount into a checking account
     * this method add the amount to the balance
     * @param amount amount to deposit into the account
     */
    @override
    public void deposit(double amount){
        if(amount > 0){//if the amount is more than 0 deposit the amount
            super.deposit(amount);// call the deposit method of the parent class
            System.out.println("Deposit of $" + amount);// display the deposit amount
        }else{
            System.out.println("Deposit amount incorrect");// if the amount is less than 0 display "deposit amount incorrect"
        }
        }

        /**
         * withdraws a specific amount from the checking account,respecting overdraft limit
         *  this method subtract the amount from the balance
         * if the withdrawal exceeds the balance  and , or  the overdraft limit, the transaction will be denied
         * @param amount amount to withdraw
         * @return true if the withdrawal is successful, return false otherwise
         */
        @Override
        public boolean withdraw(double amount){
    if(amount <= 0){// if the amount is less or equal to 0 
        System.out.println("Amount must be more than 0");// display amount must be more than 0
        return false;//return false
    }
    if( amount > (super.getBalance() + overDraftLimit)){// check if the withdrawal is in the overdraft limit
        System.out.println("Insufficient funds for withdrawal");// display insufficient funds for withdrawal
        return false;// return false
    } else {
        super.withdraw(amount);// call the withdraw method of the parent class and reuse it
        System.out.println("Withdrawal of $" +amount); // display the withdrawal amount
        return true;// return true
    }
}

/**
 * returns the overdraft limit for the checking account
 * @return overDraftLimit ove draft limit
 */
public double getOverDraftLimit(){
    return overDraftLimit;// return the over draft limit
}

/**
 * sets a new overdraft limit for the checking account 
 * @param overDraftLimit the new overdraft limit
 */
public void setOverDraftLimit(double overDraftLimit){
    if(overDraftLimit >= 0){// if the overdraft limit is more than 0 or equal
        this.overDraftLimit = overDraftLimit;// set the overdraft limit
        System.out.println("Overdraft limit updated to $" + overDraftLimit);// display the updated overdraft limit
        }else{// if the overdraft is less  than 0 
            System.out.println("Overdraft limit have to be more than 0");// display overdraft have to be more than 0
}
}
}
