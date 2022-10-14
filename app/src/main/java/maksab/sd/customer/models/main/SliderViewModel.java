package maksab.sd.customer.models.main;

public class SliderViewModel {
   private int id;
   private String imagePath;
   private String arabicName;
   private String englishName;
   private String arabicDescription;
   private String englishDescription;
   private int userTypeEnum;
   private int orderPriority;
   private int sliderTypeEnum;
   private int categoryId;
   private String itemId;

   public int getId() {
      return id;
   }

   public String getImagePath() {
      return imagePath;
   }

   public String getArabicName() {
      return arabicName;
   }

   public String getEnglishName() {
      return englishName;
   }

   public String getArabicDescription() {
      return arabicDescription;
   }

   public String getEnglishDescription() {
      return englishDescription;
   }

   public int getUserTypeEnum() {
      return userTypeEnum;
   }

   public int getOrderPriority() {
      return orderPriority;
   }

   public int getSliderTypeEnum() {
      return sliderTypeEnum;
   }

   public int getCategoryId() {
      return categoryId;
   }

   public String getItemId() {
      return itemId;
   }
}
