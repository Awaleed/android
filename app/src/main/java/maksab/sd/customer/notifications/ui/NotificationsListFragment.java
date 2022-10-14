package maksab.sd.customer.notifications.ui;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.events.EndlessRecyclerViewScrollListener;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.notifications.helpers.NotificationModel;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.main.activties.MainActivity;
import maksab.sd.customer.util.constants.Enums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsListFragment extends Fragment {
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_data_layout)
    ViewGroup no_data_layout;
    @BindView(R.id.main_progress)
    ProgressBar main_progressbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.public_button)
    Chip public_button;
    @BindView(R.id.private_button)
    Chip private_button;
    @BindView(R.id.read_all_button)
    TextView read_all_button;

    private NotificationAdapter notificationAdapter;
    private List<NotificationModel> _notifications;
    private EndlessRecyclerViewScrollListener scrollListener;

    private ICustomersService customersService;
    private ILocalStorage localStorage;
    private int currentStatus = Enums.NotificationCombinedQueryEnum.All.ordinal();
    private final int OPEN_DETAIL_REQUEST_CODE = 2344;

    public static NotificationsListFragment newInstance() {
        NotificationsListFragment fragment = new NotificationsListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        localStorage = new SharedPreferencesStorage(getActivity());
        customersService = GetWayServiceGenerator.createService(ICustomersService.class , "bearer " + localStorage.getJwtToken().getStringToken());

        setupRecyclerView();
        initChips();
        getNotifications(1, currentStatus);

        read_all_button.setPaintFlags(read_all_button.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        read_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setMessage(getString(R.string.are_you_sure))
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setReadAllNotifications();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void initChips() {
        int page = 1;
        public_button.setOnClickListener(view -> {
            private_button.setChecked(false);
            clear();
            currentStatus = Enums.NotificationCombinedQueryEnum.PublicNotifications.ordinal();
            getNotifications(page, currentStatus);
        });

        private_button.setOnClickListener(view -> {
            public_button.setChecked(false);
            clear();
            currentStatus = Enums.NotificationCombinedQueryEnum.PrivateNotifications.ordinal();
            getNotifications(page, currentStatus);
        });
    }

    public void setReadAllNotifications() {
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .setReadAllNotifications()
                .enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful()) {
                            ((MainActivity)getActivity()).refreshBadge();
                            clear();
                            getNotifications(1, currentStatus);
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(getActivity(), getString(R.string.internetError), Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
    }

    private void clear() {
        _notifications.clear();
        notificationAdapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    private void setupRecyclerView() {
        _notifications = new ArrayList<>();

        notificationAdapter = new NotificationAdapter(_notifications, view -> {
            int index = recyclerview.getChildAdapterPosition(view);
            NotificationModel notificationModel = _notifications.get(index);
            Intent intent = new Intent(getActivity(), NotificationDetailActivity.class);
            intent.putExtra("Notification", notificationModel);
            intent.putExtra("NotificationIndex", index);
            startActivityForResult(intent, OPEN_DETAIL_REQUEST_CODE);
            ((MainActivity)getActivity()).refreshBadge();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(notificationAdapter);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getNotifications(page, currentStatus);
            }
        };

        recyclerview.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            clear();
            getNotifications(1, currentStatus);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == OPEN_DETAIL_REQUEST_CODE) {
                int index = data.getIntExtra("NotificationIndex", 0);
               try {
                   NotificationModel notificationModel = _notifications.get(index);
                   notificationModel.setRead(true);
                   notificationAdapter.notifyItemChanged(index);
               }catch (IndexOutOfBoundsException outOfBoundsException){
                   //just ignore it when it happen
               }
            }
        }
    }

    private void getNotifications(int page, int status) {
        main_progressbar.setVisibility(View.VISIBLE);
        no_data_layout.setVisibility(View.GONE);

        customersService.getNotifications(status, page)
                .enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {
                swipeRefreshLayout.setRefreshing(false);
                main_progressbar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    List<NotificationModel> results = response.body();
                    _notifications.addAll(results);
                    notificationAdapter.notifyItemRangeChanged(notificationAdapter.getItemCount(), results.size());

                    swipeRefreshLayout.setRefreshing(false);

                    if (notificationAdapter.getItemCount() == 0) {
                        no_data_layout.setVisibility(View.VISIBLE);
                    } else {
                        no_data_layout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                main_progressbar.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
                if(getContext() != null){
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }else if(getActivity() != null){
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
        private List<NotificationModel> notifications;
        private View.OnClickListener _clickListener;

        public NotificationAdapter(List<NotificationModel> notifications,
                                   View.OnClickListener clickListener) {
            this.notifications = notifications;
            this._clickListener = clickListener;
        }

        @NonNull
        @Override
        public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            NotificationViewHolder notificationViewHolder = new NotificationViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_notification, viewGroup, false));
            notificationViewHolder.itemView.setOnClickListener(_clickListener);
            return notificationViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {
            NotificationModel notificationModel = notifications.get(i);

            notificationViewHolder.notification_type.setText(Enums.getNotificationCategoryEnum(notificationModel.getNotificationCategoryId()));
            notificationViewHolder.notification_date.setText(notificationModel.getCreatedOnString());
            notificationViewHolder.notification_body.setText(notificationModel.getMessage());
            notificationViewHolder.notification_title.setText(notificationModel.getTitle());

            if (!TextUtils.isEmpty(notificationModel.getImageLink())) {
                Picasso.with(getActivity()).load(notificationModel.getImageLink())
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(notificationViewHolder.notification_image);

                notificationViewHolder.notification_image.setVisibility(View.VISIBLE);
            } else {
                notificationViewHolder.notification_image.setVisibility(View.GONE);
            }

            if (!notificationModel.isRead()) {
                notificationViewHolder.itemView.setBackgroundColor(Color.parseColor("#e7eecc"));
            } else {
                notificationViewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }

        @Override
        public int getItemCount() {
            return notifications.size();
        }
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_type)
        TextView notification_type;
        @BindView(R.id.notification_date)
        TextView notification_date;
        @BindView(R.id.notification_body)
        TextView notification_body;
        @BindView(R.id.notification_title)
        TextView notification_title;
        @BindView(R.id.notification_image)
        ImageView notification_image;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        clear();
        getNotifications(1, currentStatus);
    }
}
