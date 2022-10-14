package maksab.sd.customer.ui.main.activties;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.adapters.CustomExpandableListAdapter;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;

public class TermsActivity extends BaseActivity {

    ILocalStorage localStorage;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @BindView(R.id.facebook_imageview)
    ImageView facebookImageView;

    @BindView(R.id.twitter_imageview)
    ImageView twitterImageView;

    @BindView(R.id.whatsapp_imageview)
    ImageView whatsapp_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        ButterKnife.bind(this);

        setUpToolBar();
        localStorage = new SharedPreferencesStorage(this);
//        ProviderService providerService = new ProviderService();

//        TextView terms = findViewById(R.id.terms);
//        showWaitDialog();
//        providerService.getTerms(tokenMaping(localStorage.getToken().getToken()), new Callback<TermsModel>() {
//            @Override
//            public void onResponse(Call<TermsModel> call, Response<TermsModel> response) {
//                dismissWaitDialog();
//                if (!response.isSuccessful()) {
//                    Toast.makeText(TermsActivity.this, R.string.errorInTermsfeatch, Toast.LENGTH_LONG).show();
//                    finish();
//                } else {
//                    terms.setText(response.body().getTerms());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TermsModel> call, Throwable t) {
//                dismissWaitDialog();
//                Toast.makeText(TermsActivity.this, R.string.internetError, Toast.LENGTH_LONG).show();
//            }
//        });

        buildTerms();
        initButtons();
    }

    private void initButtons() {
        final String facebook = "https://www.facebook.com/maksab.sd";
        final String twitter = "https://twitter.com/maksab_sd";

        facebookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebook)));
            }
        });

        twitterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitter)));
            }
        });

        whatsapp_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone="+ "+249113555535";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void buildTerms() {
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();
                return false;
            }
        });
    }


    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.terms_condations);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private String tokenMaping(String token) {
        return "bearer " + token;
    }

    private  HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> register = new ArrayList<String>();
        register.add(getString(R.string.term_1));
        register.add(getString(R.string.term_2));
        register.add(getString(R.string.term_3));

        List<String> orders = new ArrayList<String>();
        orders.add(getString(R.string.term_4));
        orders.add(getString(R.string.term_5));

        List<String> checking = new ArrayList<String>();
        checking.add(getString(R.string.term_6));
        checking.add(getString(R.string.term_7));
        checking.add(getString(R.string.term_8));
        checking.add(getString(R.string.term_9));

        List<String> advices = new ArrayList<String>();
        advices.add(getString(R.string.term_10));
        advices.add(getString(R.string.term_11));
        advices.add(getString(R.string.term_12));

        List<String> orderEnd = new ArrayList<String>();
        orderEnd.add(getString(R.string.term_13));
        orderEnd.add(getString(R.string.term_14));

        List<String> last = new ArrayList<String>();
        last.add(getString(R.string.term_15));
        last.add(getString(R.string.term_16));

        expandableListDetail.put(getString(R.string.term_title_1), register);
        expandableListDetail.put(getString(R.string.term_title_2), orders);
        expandableListDetail.put(getString(R.string.term_title_3), checking);
        expandableListDetail.put(getString(R.string.term_title_4), advices);
        expandableListDetail.put(getString(R.string.term_title_5), orderEnd);
        expandableListDetail.put(getString(R.string.term_title_6), last);

        return expandableListDetail;
    }
}