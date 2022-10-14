package maksab.sd.customer.network.appnetwork;

import java.util.List;

import maksab.sd.customer.models.main.CategoriesModel;
import maksab.sd.customer.models.speciality.SpecialityModel;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import retrofit2.Callback;

/**
 * Created by dev2 on 12/10/2017.
 */

public class LookUpsService {

    public void GetCategories(Callback<List<CategoriesModel>> categoriesModelCallback){
        ICatalogService catalogService = GetWayServiceGenerator.createService(ICatalogService.class);
        catalogService.GetCategories().enqueue(categoriesModelCallback);
    }

    public void GetCatSpecialty(int catid , Callback<List<SpecialityModel>> callback) {
        ICatalogService catalogService = GetWayServiceGenerator.createService(ICatalogService.class);
        catalogService.getSpecialitiesOnCategory(catid).enqueue(callback);
    }
}
