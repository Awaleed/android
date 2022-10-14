package maksab.sd.customer.ui.profile.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.constant.ImageProvider;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.models.profile.ProfileModel;
import maksab.sd.customer.models.profile.UpdateProfileModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.profile.activities.MobileOtpActivity;
import maksab.sd.customer.util.general.DateUtil;
import maksab.sd.customer.util.general.FileModel;
import maksab.sd.customer.util.general.StringUtils;
import maksab.sd.customer.viewmodels.profile.ProfileViewModel;
import maksab.sd.customer.viewmodels.profile.UploadFileViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements FragmentsContract {
    @BindView(R.id.full_name_input)
    TextInputEditText full_name_input;
    @BindView(R.id.mobile_number_input)
    TextInputEditText mobile_number_input;
    @BindView(R.id.radiomale)
    RadioButton radiomale;
    @BindView(R.id.radiofemale)
    RadioButton radiofemale;
    @BindView(R.id.provider_profile_image)
    ShapeableImageView profile_image;
    @BindView(R.id.personal_image_button)
    MaterialButton personal_image_button;
    @BindView(R.id.save_profile_button)
    Button save_profile_button;
    @BindView(R.id.indivisual_account)
    RadioButton indivisual_account;
    @BindView(R.id.company_account)
    RadioButton company_account;
    @BindView(R.id.main_progress)
    ProgressBar main_progress;
    @BindView(R.id.profile_layout)
    NestedScrollView profile_layout;
    @BindView(R.id.date_of_birth_input)
    TextInputEditText date_of_birth_input;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 2;
    private static final int IMAGE_REQUEST_CODE = 90;

    private String personalImageUrl;
    private ProgressDialog dialog;
    private String originalMobile;
    private ProfileViewModel profileViewModel;
    private UploadFileViewModel mUploadFileViewModel;
    private boolean showSaveButton = false;
    private final Calendar myCalendar= Calendar.getInstance();
    private DatePickerDialog datePickerDialog;

   public static ProfileFragment newInstance(boolean showSaveButton){
       ProfileFragment profileFragment = new ProfileFragment();
       Bundle args = new Bundle();
       args.putBoolean("showing_save_btn" , showSaveButton);
       profileFragment.setArguments(args);
       return  profileFragment;
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        ButterKnife.bind(this, view);

        chooseAccountTypesEvents();
        showSaveButton = getArguments().getBoolean("showing_save_btn");

        boolean isEditMode = showSaveButton;
        if (isEditMode){
            save_profile_button.setVisibility(View.VISIBLE);
            mobile_number_input.setEnabled(true);
        }
        else {
            save_profile_button.setVisibility(View.GONE);
            mobile_number_input.setEnabled(false);
            mobile_number_input.setBackgroundColor(ContextCompat.getColor(getActivity(),
                R.color.gray));
        }

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        mUploadFileViewModel = ViewModelProviders.of(this).get(UploadFileViewModel.class);

        profileDataObservable();
        updateProfileObservable();
        profileImageObservable();
        showLoading();
        profileViewModel.fetchProfileData();
        initDateTimePicker();
    }

    private Integer getSelectedAccountType(){
       if(indivisual_account.isChecked()) {
           return 0;
       }
       return 1;
    }

    private void setDataScreen(ProfileModel profileModel) {
        originalMobile = profileModel.getMobileNo();
        full_name_input.setText(profileModel.getFullName());
        mobile_number_input.setText(profileModel.getMobileNo());

        if (!StringUtils.isEmpty(profileModel.getBirthDate())) {
            DateUtil parser = DateUtil.newInstance();
            parser.parse(profileModel.getBirthDate());
            date_of_birth_input.setText(parser.getDateString());
        }

        if(profileModel.getGenderId().equals(1)){
            radiomale.setChecked(true);
        }
        else if(profileModel.getGenderId().equals(2)) {
            radiofemale.setChecked(true);
        }

        if(profileModel.getAccountType().equals(0)){
            indivisual_account.setChecked(true);
        }
        else if(profileModel.getAccountType().equals(1)){
            company_account.setChecked(true);
        }

        Picasso.with(getContext()).load(profileModel.getProfileImage())
                .placeholder(R.drawable.placeholder).into(profile_image);
    }

    private UpdateProfileModel getDataFromScreen() {
       UpdateProfileModel updateProfileModel = new UpdateProfileModel( radiomale.isChecked()? 1 : 2 , full_name_input.getText().toString() , "" ,
               mobile_number_input.getText().toString() ,"", getSelectedAccountType());
       updateProfileModel.setBirthDate(date_of_birth_input.getText().toString());
       updateProfileModel.setProfileImage(personalImageUrl);
       return updateProfileModel;
    }

    private void profileDataObservable(){
        profileViewModel.profileDataObservable().observe(getViewLifecycleOwner() , updateProfile -> {
            hideLoading();
            if(updateProfile !=null){
                setDataScreen(updateProfile);
            }
        });
    }

    private void updateProfileObservable(){
        profileViewModel.UpdateProfileObservable().observe(getViewLifecycleOwner() , isupdate -> {
           if(isupdate){
               Toast.makeText(getContext() , getString(R.string.profile_update) , Toast.LENGTH_LONG).show();
           }else{
               Toast.makeText(getContext() , getString(R.string.profile_update_failed) , Toast.LENGTH_LONG).show();
           }
        });
    }

    private void profileImageObservable(){
        mUploadFileViewModel.getFilePathObserver().observe(getViewLifecycleOwner() , url -> {
            dismissWaitDialog();
            if(url !=null){
                updateProfileImage(url);
            }
            else {
                Picasso.with(getContext()).load(R.drawable.placeholder).into(profile_image);
                Toast.makeText(getContext() , R.string.imageUploadError , Toast.LENGTH_LONG).show();
            }
        } );
    }

    @OnClick(R.id.save_profile_button)
    void onSaveProfile(){
       saveProfile();
    }

    @OnClick(R.id.date_of_birth_input)
    void openDatePicker(){
       datePickerDialog.show();
    }

    @OnClick(R.id.provider_profile_image)
    void onImageSelect() {
        ImagePicker.with(this).crop().provider(ImageProvider.BOTH).start(IMAGE_REQUEST_CODE);
    }

    @OnClick(R.id.personal_image_button)
    void onImageEditSelect() {
        ImagePicker.with(this).crop().maxResultSize(400, 300).provider(ImageProvider.BOTH).start(IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            File file = new File(data.getData().getPath());
            Picasso.with(getContext()).load(file).into(profile_image);
            showWaitDialog();
            mUploadFileViewModel.upload(file);
        }

        if (resultCode == RESULT_OK && requestCode == 100) {
            String mobile = data.getStringExtra("mobile");
            originalMobile = mobile;
            UpdateProfileModel profileModel = getDataFromScreen();
            profileModel.setMobileNo(mobile);
            profileViewModel.updateProfile(profileModel);

            if (showSaveButton) {
                getActivity().finish();
            }
        }

    }

    private void initDateTimePicker(){
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateDateOfBirthEditText();
        };
       datePickerDialog = new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void updateDateOfBirthEditText(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        date_of_birth_input.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void showWaitDialog() {
        dialog = ProgressDialog.show(getContext(), "",
                getString(R.string.loading_please_wait), true);
    }

    public void dismissWaitDialog(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    private void chooseAccountTypesEvents(){
       indivisual_account.setOnClickListener(view -> {
           full_name_input.requestFocus();
           full_name_input.setHint(R.string.your_full_name);
       });

        company_account.setOnClickListener(view -> {
            full_name_input.requestFocus();
            full_name_input.setHint(R.string.company_name);
        });
    }

    private void showLoading(){
        profile_layout.setVisibility(View.GONE);
        main_progress.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        profile_layout.setVisibility(View.VISIBLE);
        main_progress.setVisibility(View.GONE);
    }

    private void updateProfileImage(String path){
        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        String token = "bearer "+localStorage.getJwtToken().getStringToken();
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , token);
        customersService.updateProfileImage(path).enqueue(new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext() , R.string.imageUploaded , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
                Toast.makeText(getContext() , "Error: " +
                        t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean isValidForm() {
        return  !TextUtils.isEmpty(full_name_input.getText().toString())
                && isValidMobile()
                && !TextUtils.isEmpty(date_of_birth_input.getText().toString())
                && (radiofemale.isChecked() || radiomale.isChecked());
    }

    @Override
    public String getErrorMessage() {
        return getString(R.string.update_profile);
    }

    @Override
    public String getStepTitle(Context context) {
        return context.getString(R.string.enter_profile_info);
    }

    @Override
    public void saveChange() {
        saveProfile();
    }

    private boolean isValidMobile() {
        String mobile = mobile_number_input.getText().toString();
        boolean emptyMobile = TextUtils.isEmpty(mobile);
        boolean sudaniMobile = mobile.startsWith("09") || mobile.startsWith("01");
        boolean validLength = mobile.length() == 10;
        return !emptyMobile && sudaniMobile && validLength;
    }

    private void saveProfile(){
        boolean genderIsChecked = radiomale.isChecked() || radiofemale.isChecked();
        if(!TextUtils.isEmpty(full_name_input.getText().toString()) &&
                !TextUtils.isEmpty(date_of_birth_input.getText().toString()) &&
                isValidMobile() && genderIsChecked) {
            if (mobile_number_input.getText().toString().equals(originalMobile)) {
                profileViewModel.updateProfile(getDataFromScreen());
                if (showSaveButton) {
                    getActivity().finish();
                }
            }
            else {
                Intent intent = new Intent(getActivity(), MobileOtpActivity.class);
                intent.putExtra("mobile", mobile_number_input.getText().toString());
                intent.putExtra("originalMobile", originalMobile);
                startActivityForResult(intent, 100);
            }
        }
        else {
            Toast.makeText(getContext() , R.string.please_add_data , Toast.LENGTH_LONG).show();
        }
    }
}
