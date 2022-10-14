package maksab.sd.customer.models.speciality;

public class SpecialitySubscriptionInputModel {

    private Integer specialtyId;
    private Integer addressId;

    public SpecialitySubscriptionInputModel(Integer specialtyId, Integer addressId) {
        this.specialtyId = specialtyId;
        this.addressId = addressId;
    }

}
