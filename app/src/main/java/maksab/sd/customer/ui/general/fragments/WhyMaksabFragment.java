package maksab.sd.customer.ui.general.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import maksab.sd.customer.R;

public class WhyMaksabFragment extends Fragment {
    public static WhyMaksabFragment newInstance() {
        WhyMaksabFragment fragment = new WhyMaksabFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_why_maksab, container, false);
        return view;
    }
}