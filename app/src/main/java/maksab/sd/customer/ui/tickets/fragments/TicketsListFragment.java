package maksab.sd.customer.ui.tickets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.events.EndlessRecyclerViewScrollListener;
import maksab.sd.customer.models.tickets.TicketModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.tickets.activities.AddTicketWizardActivity;
import maksab.sd.customer.ui.tickets.activities.TicketDetailsActivity;
import maksab.sd.customer.ui.tickets.adapters.TicketsAdapter;
import maksab.sd.customer.util.constants.Enums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketsListFragment extends Fragment {
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_data_layout)
    ViewGroup no_data_layout;
    @BindView(R.id.main_progress)
    ProgressBar main_progressbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.open_tickets_btn)
    Chip open_tickets_btn;
    @BindView(R.id.closed_tickets_btn)
    Chip closed_tickets_btn;
    @BindView(R.id.add_ticket_btn)
    Chip add_ticket_btn;

    private TicketsAdapter ticketsAdapter;
    private List<TicketModel> ticketModels;
    private EndlessRecyclerViewScrollListener scrollListener;

    private int ticket_status;
    ICustomersService customersService;

    public static TicketsListFragment newInstance(int status) {
        TicketsListFragment fragment = new TicketsListFragment();
        Bundle args = new Bundle();
        args.putInt("ticket.status", status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_tickets_list, container, false);;
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        ButterKnife.bind(this, view);

        initNetwork();
        initChips();
        setupRecyclerView();

        ticket_status = getArguments().getInt("ticket.status");
        getTickets(1, ticket_status);

        if (ticket_status == Enums.TicketStatusEnum.OPENED.ordinal())
            open_tickets_btn.setChecked(true);
        else if (ticket_status == Enums.TicketStatusEnum.CLOSED.ordinal())
            closed_tickets_btn.setChecked(true);
    }

    private void clear() {
        ticketModels.clear();
        ticketsAdapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    private void setupRecyclerView() {
        ticketModels = new ArrayList<>();
        ticketsAdapter = new TicketsAdapter(ticketModels, getActivity(), view -> {
            TicketModel ticketModel = ticketModels.get(recyclerView.getChildAdapterPosition(view));
            Intent intent = new Intent(getContext(), TicketDetailsActivity.class);
            intent.putExtra("Ticket", ticketModel);
            intent.putExtra("ticket.id" , ticketModel);
            startActivity(intent);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ticketsAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getTickets(page, ticket_status);
            }
        };

        recyclerView.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                getTickets(1, ticket_status);
            }
        });
    }

    private void getTickets(int page, int ticketStatus) {
        main_progressbar.setVisibility(View.VISIBLE);
        no_data_layout.setVisibility(View.GONE);

        customersService.getTickets(page, ticketStatus)
                .enqueue(new Callback<List<TicketModel>>() {
            @Override
            public void onResponse(Call<List<TicketModel>> call, Response<List<TicketModel>> response) {

                if (response.isSuccessful()) {
                    main_progressbar.setVisibility(View.GONE);

                    ticketModels.addAll(response.body());
                    ticketsAdapter.notifyItemRangeChanged(ticketsAdapter.getItemCount(), response.body().size());

                    swipeRefreshLayout.setRefreshing(false);

                    if (ticketsAdapter.getItemCount() == 0) {
                        no_data_layout.setVisibility(View.VISIBLE);
                    } else {
                        no_data_layout.setVisibility(View.GONE);
                    }
                }
                else {
                    main_progressbar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<TicketModel>> call, Throwable t) {
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

    private void initNetwork() {
        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        String authToken = "bearer " + localStorage.getJwtToken().getStringToken();
        customersService = GetWayServiceGenerator.createService(ICustomersService.class, authToken);
    }

    private void initChips() {
        int page = 1;
        open_tickets_btn.setOnClickListener(view -> {
            closed_tickets_btn.setChecked(false);
            clear();
            ticket_status = Enums.TicketStatusEnum.OPENED.ordinal();
            getTickets(page, ticket_status);
        });

        closed_tickets_btn.setOnClickListener(view -> {
            open_tickets_btn.setChecked(false);
            clear();
            ticket_status = Enums.TicketStatusEnum.CLOSED.ordinal();
            getTickets(page, ticket_status);
        });

        add_ticket_btn.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), AddTicketWizardActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        clear();
        getTickets(1, ticket_status);
    }
}