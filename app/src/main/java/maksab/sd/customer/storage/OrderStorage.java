package maksab.sd.customer.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import maksab.sd.customer.models.orders.details.OrderInputModel;
import maksab.sd.customer.util.general.SelectedSpecialityModel;

public class OrderStorage {

    private final Context _context;

    private final SharedPreferences _SharedPreferences;

    private final SharedPreferences.Editor _editor;

    private final String fileName = "temp_quotation.xml";
    private final String quotationFiled = "quotation_filed";
    private final String selected_specialyfiled = "selected_specialityFiled";


    public OrderStorage(Context context) {
        _context = context;
        _SharedPreferences = _context.getSharedPreferences(fileName , Context.MODE_PRIVATE);
        _editor = _SharedPreferences.edit();
    }

    public void saveQuotation(OrderInputModel orderInputModel){
        Gson gson = new Gson();
        _editor.putString(quotationFiled,gson.toJson(orderInputModel));
        _editor.commit();
    }

    public OrderInputModel getQuotation(){
        Gson gson = new Gson();
        return gson.fromJson(_SharedPreferences.getString(quotationFiled , "") , OrderInputModel.class);
    }



    public void saveSelectedSpecaility(SelectedSpecialityModel selectedSpecialityModel){
        Gson gson = new Gson();
        _editor.putString(selected_specialyfiled , gson.toJson(selectedSpecialityModel));
        _editor.commit();
    }

    public SelectedSpecialityModel getSelectedspeciality(){
        Gson gson = new Gson();
        return gson.fromJson(_SharedPreferences.getString(selected_specialyfiled , "") , SelectedSpecialityModel.class);
    }

    public void clearSavedData(){
        _editor.remove(quotationFiled);
        _editor.remove(selected_specialyfiled);
        _editor.commit();
    }


}
