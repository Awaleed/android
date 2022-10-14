package maksab.sd.customer.models.profile;

public class BalanceViewModel {
    private String userId;
    private double balance;
    private double consumed;
    private double added;

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public double getConsumed() {
        return consumed;
    }

    public double getAdded() {
        return added;
    }
}
