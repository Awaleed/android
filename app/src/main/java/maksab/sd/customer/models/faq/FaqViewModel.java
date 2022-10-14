package maksab.sd.customer.models.faq;

public class FaqViewModel {
    private int id;
    private int faqTypeId;
    private String arabicTitle;
    private String englishTitle;
    private String arabicHtmlBody;
    private String englishHtmlBody;
    private boolean isActive;
    private int viewCounts;
    private int likesCount;
    private int dislikesCount;
    private String createdOn;
    private FaqTypeViewModel faqType;

    public int getId() {
        return id;
    }

    public int getFaqTypeId() {
        return faqTypeId;
    }

    public String getArabicTitle() {
        return arabicTitle;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public String getArabicHtmlBody() {
        return arabicHtmlBody;
    }

    public String getEnglishHtmlBody() {
        return englishHtmlBody;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getViewCounts() {
        return viewCounts;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getDislikesCount() {
        return dislikesCount;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public FaqTypeViewModel getFaqType() {
        return faqType;
    }
}
