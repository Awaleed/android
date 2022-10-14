package maksab.sd.customer.models.faq;

public class FaqTypeViewModel {
    private int id;
    private String arabicName;
    private String englishName;
    private int forUserType;
    private boolean isActive;

    public int getId() {
        return id;
    }

    public String getArabicName() {
        return arabicName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public int getForUserType() {
        return forUserType;
    }

    public boolean isActive() {
        return isActive;
    }
}
