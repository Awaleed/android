package maksab.sd.customer.network.appnetwork;

import java.util.List;

import maksab.sd.customer.models.categories.CategoryDetailsModel;
import maksab.sd.customer.models.lookup.DaySlotModel;
import maksab.sd.customer.models.lookup.SpecialityTimingModel;
import maksab.sd.customer.models.lookup.TimeSlotModel;
import maksab.sd.customer.models.main.CategoriesModel;
import maksab.sd.customer.models.services.ServiceModel;
import maksab.sd.customer.models.speciality.SpecialityModel;
import maksab.sd.customer.models.speciality.SpecialtyDistrictCoverage;
import maksab.sd.customer.models.speciality.SpecialtyDistrictTransportationPrice;
import maksab.sd.customer.models.speciality.SubSpecialtyViewModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ICatalogService {
    @GET("catalog/categories")
    Call<List<CategoriesModel>> GetCategories();
    @GET("catalog/categories/{id}")
    Call<CategoryDetailsModel> getCategoryDetails(@Path("id") int id, @Query("addressId") int addressId);
    @GET("catalog/categories/specialities/{id}")
    Call<List<SpecialityModel>> getSpecialitiesOnCategory(@Path("id") int id);
    @GET("catalog/specialities/{specialtyId}/dates")
    Call<List<DaySlotModel>> getSpecialityDates(@Path("specialtyId") int specialtyId);

    @GET("catalog/specialities/{specialtyId}/timing")
    Call<List<TimeSlotModel>> getSpecialityTimeSlot(@Path("specialtyId") int specialtyId , @Query("timestamp") String timestamp);

    @GET("catalog/specialities/{specialtyId}")
    Call<SpecialityModel> getSpecialityById(@Path("specialtyId") int specialtyId);

    // List of Transportations & Coverage District
    @GET("catalog/specialties/transportation_prices/{specialtyId}")
    Call<List<SpecialtyDistrictTransportationPrice>> getDistrictTranposrationPrices(@Path("specialtyId") int specialtyId, @Query("page") int page);
    @GET("catalog/specialties/coverage/{specialtyId}")
    Call<List<SpecialtyDistrictCoverage>> getDistrictCoverages(@Path("specialtyId") int specialtyId, @Query("page") int page);

    // Chips
    @GET("catalog/specialities/{specialtyId}/subspecialities")
    Call<List<SubSpecialtyViewModel>> getSubSpecialties(@Path("specialtyId") int specialtyId);
    @GET("catalog/providers/{userId}/subspecialities/{specialtyId}")
    Call<List<SubSpecialtyViewModel>> getProviderSubSpecialties(@Path("userId") String userId, @Path("specialtyId") int specialtyId);

    // Services On Chip
    @GET("catalog/specialities/{specialtyId}/subspecialities/{subspecailtyId}/services")
    Call<List<ServiceModel>> getActiveMaksabServices(@Path("specialtyId") int specialtyId, @Path("subspecailtyId") int subspecailtyId, @Query("page") int page);
    @GET("catalog/providers/{userId}/subspecialities/{subspecailtyId}/services")
    Call<List<ServiceModel>> getActiveProviderServices(@Path("userId") String userId, @Path("subspecailtyId") int subspecailtyId, @Query("page") int page);
}
