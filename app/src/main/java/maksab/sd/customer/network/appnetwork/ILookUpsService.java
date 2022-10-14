package maksab.sd.customer.network.appnetwork;

import java.util.List;

import maksab.sd.customer.models.faq.AboutUsModel;
import maksab.sd.customer.models.lookup.LookupModel;
import maksab.sd.customer.models.main.SliderViewModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dev2 on 12/10/2017.
 */

public interface ILookUpsService {
    @GET("lookups/cities")
    Call<List<LookupModel>> GetCities();
    @GET("lookups/districts")
    Call<List<LookupModel>> GetDistricts(@Query("cityId") Integer cityId , @Query("keyword") String keyword);
    @GET("lookups/address_types")
    Call<List<LookupModel>> GetAddressTypes();
    @GET("lookups/floor_types")
    Call<List<LookupModel>> GetFloors();
    @GET("lookups/dynamicconfig")
    Call<AboutUsModel> getDynamicConfig();
    @GET("lookups/sliders")
    Call<List<SliderViewModel>> getWizardSliders(@Query("userTypeEnum") int userTypeEnum);
}
