package maksab.sd.customer.ui.balance.models;

public class BalanceTransactionTypeViewModel  {
    private int id;
    private String arabicName;
    private String englishName;
    private String description;
    private int userTypeEnum;
    private boolean isPublicOperationBySupport;

    public int getId() {
        return id;
    }


    public String getArabicName() {
        return arabicName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getDescription() {
        return description;
    }

    public int getUserTypeEnum() {
        return userTypeEnum;
    }

    public boolean isPublicOperationBySupport() {
        return isPublicOperationBySupport;
    }
}
