public class Saving extends Account{
    private double totalSavings;
    private double availableSavings; 

    public Saving(){}

    public void setTotalSavings(double totalSavingsIn){
        this.totalSavings = totalSavingsIn;
    }
    public void setAvailableSavings(double availableSavingsIn){
        this.availableSavings = availableSavingsIn;
    }
    public double getTotalSavings(){
        return totalSavings;
    }
    public double getavailableSavings(){
        return availableSavings;
    }
    
    public void printCurrStatement(){
        System.out.println("Current Statement: " + totalSavings);
    }

}
