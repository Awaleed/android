package maksab.sd.customer.models.speciality;

public class SubSpecialtyViewModel {

   private int id;
   private int specialtyId;
   private String arabicName;
   private String englishName;
   private boolean isActive;
   private int orderPriority;
   private String createdOn;
   private int subSpecialtyBy;
   private String providerUserId;
   private int providerId;
   private SpecialityModel specialtyViewModel;

   public int getId() {
      return id;
   }


   public int getSpecialtyId() {
      return specialtyId;
   }

   public String getArabicName() {
      return arabicName;
   }

   public String getEnglishName() {
      return englishName;
   }

   public boolean isActive() {
      return isActive;
   }

   public int getOrderPriority() {
      return orderPriority;
   }

   public String getCreatedOn() {
      return createdOn;
   }

   public int getSubSpecialtyBy() {
      return subSpecialtyBy;
   }

   public String getProviderUserId() {
      return providerUserId;
   }

   public int getProviderId() {
      return providerId;
   }

   public SpecialityModel getSpecialtyViewModel() {
      return specialtyViewModel;
   }
}
