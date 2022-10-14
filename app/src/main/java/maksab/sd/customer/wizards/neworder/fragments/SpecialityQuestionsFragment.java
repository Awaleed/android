package maksab.sd.customer.wizards.neworder.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.basecode.utility.QuestionControlTypes;
import maksab.sd.customer.models.orders.details.SpecialtyQuestionAnswers;
import maksab.sd.customer.models.speciality.SpecialityQuestionModel;
import maksab.sd.customer.models.speciality.WidgetTagStorage;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.OrderInMemoryStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.general.activities.ChooseLocationActivity;
import maksab.sd.customer.ui.lookups.activities.SelectImageActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialityQuestionsFragment extends Fragment implements FragmentsContract {
    @BindView(R.id.container)
    ViewGroup container;

    List<View> widgets;

    private static final int FORGPSINTENTREQUESTCODE = 176;
    private static final int FORSELECTIMAGEREQUESTCODE = 96;

    public static SpecialityQuestionsFragment newInstance(int specialityId) {
        SpecialityQuestionsFragment  fragment = new SpecialityQuestionsFragment();
        Bundle args = new Bundle();
        args.putInt("speciality.id" , specialityId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speciality_questions, container, false);
        initViews(view);

        return view;
    }

    void initViews(View view){
        ButterKnife.bind(this , view);
        widgets = new ArrayList<>();
        getItems();
    }

    private void getItems(){
        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , "bearer " + localStorage.getJwtToken().getStringToken());
        customersService.getSpecialtyQuestions(getArguments().getInt("speciality.id"))
                .enqueue(new Callback<List<SpecialityQuestionModel>>() {
            @Override
            public void onResponse(Call<List<SpecialityQuestionModel>> call, Response<List<SpecialityQuestionModel>> response) {
                if(response.isSuccessful()){
                    parseJsonToControls(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SpecialityQuestionModel>> call, Throwable t) {

            }
        });

    }

    private void parseJsonToControls(List<SpecialityQuestionModel> specialityQuestionModels){

        for (SpecialityQuestionModel specialityQuestionModel : specialityQuestionModels){
            if(specialityQuestionModel.getSpecialtyQuestionControlType() == QuestionControlTypes.FreeText.ordinal()  || specialityQuestionModel.getSpecialtyQuestionControlType() ==
             QuestionControlTypes.FreeTextLong.ordinal()){
                addFreeText(new WidgetTagStorage(specialityQuestionModel.getId(), specialityQuestionModel.getIsRequired()) , specialityQuestionModel.getArabicQuestion() ,getContext());
            }else if(specialityQuestionModel.getSpecialtyQuestionControlType() == QuestionControlTypes.SingleChoice.ordinal()){
               String[]  answers = specialityQuestionModel.getArabicAnswer().split(specialityQuestionModel.getAnswerSeparator());
                addSingleChoice(new WidgetTagStorage(specialityQuestionModel.getId(), specialityQuestionModel.getIsRequired()) , specialityQuestionModel.getArabicQuestion() , getContext() , answers);
            }
            else if(specialityQuestionModel.getSpecialtyQuestionControlType() == QuestionControlTypes.MultipleChoice.ordinal()){
                String[]  answers = specialityQuestionModel.getArabicAnswer().split(specialityQuestionModel.getAnswerSeparator());
                addMultiChoices(new WidgetTagStorage(specialityQuestionModel.getId(), specialityQuestionModel.getIsRequired()) , specialityQuestionModel.getArabicQuestion() , getContext() , answers);
            }else if(specialityQuestionModel.getSpecialtyQuestionControlType() == QuestionControlTypes.GPS.ordinal()){
                addGps(new WidgetTagStorage(specialityQuestionModel.getId(), specialityQuestionModel.getIsRequired()) , specialityQuestionModel.getArabicQuestion() , getContext());
            }else if(specialityQuestionModel.getSpecialtyQuestionControlType() == QuestionControlTypes.Image.ordinal()){
                addImage(new WidgetTagStorage(specialityQuestionModel.getId(), specialityQuestionModel.getIsRequired()) , specialityQuestionModel.getArabicQuestion() , getContext());
            }
        }

    }

    private int getSpaceInPixels(int dpi){
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (dpi * scale + 0.5f);
        return pixels;
    }

   private void addFreeText(WidgetTagStorage widgetTagStorage, String placeHolder , Context context){

       Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.din_regular);
       LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.WRAP_CONTENT);
       layoutParams.setMargins(0,getSpaceInPixels(6),0,0);

       TextView textView = new TextView(context);
       textView.setText(placeHolder);
       textView.setLayoutParams(layoutParams);
       textView.setTypeface(typeface);
       textView.setTextColor(Color.parseColor("#1e73be"));
       textView.setPadding(0,9,0,0);


        EditText editText = new EditText(context);
        editText.setTag(widgetTagStorage);
        editText.setLayoutParams(layoutParams);
        editText.setTypeface(typeface);
        container.addView(textView);
        container.addView(editText);
       widgets.add(editText);

   }

   private void addSingleChoice(WidgetTagStorage widgetTagStorage,String placeHolder , Context context , String[] answers){
       Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.din_regular);
       LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.WRAP_CONTENT);
       layoutParams.setMargins(0,getSpaceInPixels(13),0,0);
       LinearLayout.LayoutParams onlyWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT ,ViewGroup.LayoutParams.WRAP_CONTENT , 1.0f);


       TextView textView = new TextView(context);
       textView.setText(placeHolder);
       textView.setLayoutParams(layoutParams);
       textView.setTypeface(typeface);
       textView.setTextColor(Color.parseColor("#1e73be"));

       Spinner spinner = new Spinner(context);
       spinner.setLayoutParams(layoutParams);
       spinner.setTag(widgetTagStorage);
       spinner.setPadding(0,9,0,0);
       ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, answers);
       spinner.setAdapter(arrayAdapter);

       container.addView(textView);
       container.addView(spinner);
       widgets.add(spinner);
   }

    private void addMultiChoices(WidgetTagStorage widgetTagStorage,String placeHolder , Context context , String[] answers){
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.din_regular);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,getSpaceInPixels(13),0,0);
        LinearLayout.LayoutParams onlyWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT ,ViewGroup.LayoutParams.WRAP_CONTENT , 1.0f);

        TextView textView = new TextView(context);
        textView.setText(placeHolder);
        textView.setLayoutParams(layoutParams);
        textView.setTypeface(typeface);
        textView.setTextColor(Color.parseColor("#1e73be"));


        ChipGroup chipGroup = new ChipGroup(context);
        chipGroup.setLayoutParams(layoutParams);
        chipGroup.setPadding(0,9,0,0);

        for(String answer : answers){
            Chip chip = new Chip(context);
            chip.setText(answer);
            chip.setTag(widgetTagStorage);
            chip.setTypeface(typeface);
            chip.setCheckable(true);
            chipGroup.addView(chip);
            widgets.add(chip);
        }

        container.addView(textView);
        container.addView(chipGroup);
    }

    private void addGps(WidgetTagStorage widgetTagStorage,String placeHolder , Context context){

        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.din_regular);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,getSpaceInPixels(13),0,0);

        TextView question_text = new TextView(context);
        question_text.setLayoutParams(layoutParams);
        question_text.setTypeface(typeface);
        question_text.setText(placeHolder);
        question_text.setTextColor(Color.parseColor("#1e73be"));
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getSpaceInPixels(140)));
        imageView.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_location));
        imageView.setTag(widgetTagStorage);

        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(getContext() , ChooseLocationActivity.class);
            intent.putExtra("questionId" , widgetTagStorage.getQuestionId());
            startActivityForResult(intent , FORGPSINTENTREQUESTCODE);
        });

        container.addView(question_text);
        container.addView(imageView);
        widgets.add(imageView);

    }

    private void addImage(WidgetTagStorage widgetTagStorage,String placeHolder , Context context){
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.din_regular);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,getSpaceInPixels(13),0,0);

        TextView question_text = new TextView(context);
        question_text.setLayoutParams(layoutParams);
        question_text.setTypeface(typeface);
        question_text.setText(placeHolder);
        question_text.setTextColor(Color.parseColor("#1e73be"));


        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getSpaceInPixels(140)));
        imageView.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_menu_gallery));
        imageView.setTag(widgetTagStorage);

        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity() , SelectImageActivity.class);
            intent.putExtra("questionId" , widgetTagStorage.getQuestionId());
            startActivityForResult(intent , FORSELECTIMAGEREQUESTCODE);
        });

        container.addView(question_text);
        container.addView(imageView);
        widgets.add(imageView);

    }

    private void getQuestionsResults(){

        for (int i = 0; i < widgets.size(); i++) {
            View widget = widgets.get(i);
            WidgetTagStorage widgetTagStorage = (WidgetTagStorage) widget.getTag();
            if(widget instanceof EditText){
                if(!TextUtils.isEmpty(((EditText) widget).getText().toString())){
                    OrderInMemoryStorage.addSpecialityAnswer(new SpecialtyQuestionAnswers(((EditText) widget).getText().toString(), widgetTagStorage.getQuestionId()));
                }
            }else if(widget instanceof Spinner){
                Spinner spinner = (Spinner) widget;
               if(spinner.getSelectedItemId() > 0){
                   OrderInMemoryStorage.addSpecialityAnswer(new SpecialtyQuestionAnswers((String) spinner.getSelectedItem(), widgetTagStorage.getQuestionId()));
               }
            }else if(widget instanceof Chip){
                CheckBox checkBox = (CheckBox) widget;
                if(checkBox.isChecked()){
                    OrderInMemoryStorage.addSpecialityAnswer(new SpecialtyQuestionAnswers(checkBox.getText().toString() , widgetTagStorage.getQuestionId()));
                }

            }
        }
        Log.d("answers is : " , new Gson().toJson(OrderInMemoryStorage.getOrderInputs().getSpecialtyQuestionAnswers()));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == FORGPSINTENTREQUESTCODE){
            double longitude = data.getDoubleExtra("longitude" ,0);
            double latitude = data.getDoubleExtra("latitude" ,0);
            int questionId = data.getIntExtra("questionId",0);

            if(longitude != 0 && latitude != 0){

                OrderInMemoryStorage.addSpecialityAnswer(new SpecialtyQuestionAnswers(longitude + ","+latitude , questionId ));
                View view = getWidgetByTage(questionId);

                if(view !=null){
                    ImageView imageView = (ImageView) view;
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    String map_url = "https://maps.googleapis.com/maps/api/staticmap?markers=color:red|" + latitude+"," +longitude+"&size=400x400&zoom=15&key=AIzaSyCi-TxJLcmswJylE6ULJ3cIT1IHtWHwV-c&language=ar";
                    Log.d("map_url" , map_url);
                    Picasso.with(getContext()).load(map_url).placeholder(R.drawable.ic_location).into(imageView);
                }
            }

        }else if(resultCode == RESULT_OK && requestCode == FORSELECTIMAGEREQUESTCODE){

            int questionId = data.getIntExtra("questionId" , 0);
            String image_url = data.getStringExtra("image_url");

            if(image_url != null && !TextUtils.isEmpty(image_url)){
                OrderInMemoryStorage.addSpecialityAnswer(new SpecialtyQuestionAnswers(image_url , questionId));
                View view = getWidgetByTage(questionId);
                if(view !=null){
                    ImageView imageView = (ImageView) view;
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Picasso.with(getContext()).load(image_url).placeholder(R.drawable.ic_location).into(imageView);
                }
            }

        }
    }

    private View getWidgetByTage(int tageName){
        for (View view : widgets) {
            WidgetTagStorage widgetTagStorage = (WidgetTagStorage)view.getTag();
            if(widgetTagStorage.getQuestionId() == tageName){
                return view;
            }
        }
        return null;
    }

    private boolean isValidWidget(){
        for (View widget : widgets) {
            WidgetTagStorage widgetTagStorage = (WidgetTagStorage) widget.getTag();
            if(widgetTagStorage.isRequired() && OrderInMemoryStorage.getQuestionAnswerById(widgetTagStorage.getQuestionId()) ==null){
                Log.e("required is " , widgetTagStorage.getQuestionId() + "");
                return false;
            }
        }
        return  true;
    }

    @Override
    public boolean isValidForm() {
        getQuestionsResults();
        isValidWidget();
        return true;
    }

    @Override
    public String getErrorMessage() {
        return getString(R.string.answer_the_qestion_first);
    }

    @Override
    public String getStepTitle(Context context) {
        return getString(R.string.answer_following_question);
    }

    @Override
    public void saveChange() {
        getQuestionsResults();
    }
}