package maksab.sd.customer.ui.balance.models;

public class BalanceTransactionViewModel {
    private int id;
    private String userId;
    private int balanceTransactionTypeId;
    private int balanceTransactionChannelId;
    private int maksabOfficeId;
    private String addedByUserId;
    private String createdOn;
    private String createdOnString;
    private String description;
    private double amount;
    private BalanceTransactionTypeViewModel balanceTransactionType;
    private MaksabOfficeViewModel maksabOffice;
    private BalanceTransactionChannelViewModel balanceTransactionChannel;
    private String supportFullName;
    private String supportImage;
    private String expiredOn;
    private int userTypeEnum;
    private String expiredOnString;

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public int getBalanceTransactionTypeId() {
        return balanceTransactionTypeId;
    }

    public int getBalanceTransactionChannelId() {
        return balanceTransactionChannelId;
    }

    public int getMaksabOfficeId() {
        return maksabOfficeId;
    }

    public String getAddedByUserId() {
        return addedByUserId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getCreatedOnString() {
        return createdOnString;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public BalanceTransactionTypeViewModel getBalanceTransactionType() {
        return balanceTransactionType;
    }

    public MaksabOfficeViewModel getMaksabOffice() {
        return maksabOffice;
    }

    public BalanceTransactionChannelViewModel getBalanceTransactionChannel() {
        return balanceTransactionChannel;
    }

    public String getSupportFullName() {
        return supportFullName;
    }

    public String getSupportImage() {
        return supportImage;
    }

    public String getExpiredOn() {
        return expiredOn;
    }

    public int getUserTypeEnum() {
        return userTypeEnum;
    }

    public String getExpiredOnString() {
        return expiredOnString;
    }
}
