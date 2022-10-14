package maksab.sd.customer.ui.chat.chats;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import maksab.sd.customer.BuildConfig;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.chat.OrderChatViewModel;
import maksab.sd.customer.models.tickets.TicketMessageModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.appnetwork.IUploadInterface;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.BitmapUtil;
import maksab.sd.customer.util.general.FileModel;
import maksab.sd.customer.util.general.FilePartUtil;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListFragment extends Fragment {
    @BindView(R.id.main_progress)
    ProgressBar progressBar;
    @BindView(R.id.no_data_layout)
    ViewGroup no_data_layout;
    @BindView(R.id.recycler_view)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.input_editext)
    EditText input_editext;
    @BindView(R.id.send_button)
    ImageButton send_button;
    @BindView(R.id.attach_button)
    ImageButton attach_button;
    @BindView(R.id.input_layout)
    View input_layout;

    private ILocalStorage localStorage;
    private List<ChatViewModel> itemModelList = new ArrayList<>();
    private ChatAdapter itemsAdapter;
    private ChatBottomSheetDialogFragment sheetDialog;
    private HubConnection hubConnection;
    private int itemId;
    private int currentPage = 1;
    private int PAGE_SIZE = 20;
    public enum ChatForType {Ticket, Order};

    // Change this based on App (Customer, Provider, Support)
    private Enums.UserTypeEnum currentUserType = Enums.UserTypeEnum.Customer;

    public static ChatListFragment newInstance(int itemId,
                                               ChatForType forType, boolean disabledInput) {
        ChatListFragment fragment = new ChatListFragment();
        Bundle arg = new Bundle();
        arg.putInt("ItemId", itemId);
        arg.putBoolean("DisabledInput", disabledInput);
        arg.putSerializable("ChatForType", forType);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (sheetDialog != null)
            sheetDialog.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {
        localStorage = new SharedPreferencesStorage(getActivity());
        itemId = getArguments().getInt("ItemId");
        boolean dispabledInput = getArguments().getBoolean("DisabledInput");

        if (dispabledInput) {
            input_layout.setVisibility(View.GONE);
        }
        else {
            input_layout.setVisibility(View.VISIBLE);
        }

        ChatForType chatForType = (ChatForType) getArguments().getSerializable("ChatForType");
        String orderType = chatForType == ChatForType.Order ? "order" : "ticket";

        initSockets(itemId, orderType);
        setRecyclerView(itemId, orderType);
        fetchData(itemId, orderType);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String body = input_editext.getText().toString();
                if (body == null || body.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.enter_all_data_validation), Toast.LENGTH_SHORT).show();
                    return;
                }

                ChatInputModel messageInput = new ChatInputModel(
                        itemId, Enums.MessageTypeEnum.Text.ordinal(), body, null);

                addMessage(messageInput);
            }
        });

        attach_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialog = new ChatBottomSheetDialogFragment();
                sheetDialog.setOnClickListener(new ChatBottomSheetDialogFragment.ClickListener() {
                    @Override
                    public void OnClickListener(File file, Enums.MessageTypeEnum messageTypeEnum) {
                        uploadFile(file, messageTypeEnum);
                    }
                }, (BaseActivity) getActivity());
                sheetDialog.show(getActivity().getSupportFragmentManager(), sheetDialog.getTag());
            }
        });
    }

    private void uploadFile(final File file, Enums.MessageTypeEnum messageTypeEnum) {
        try {
            if (getActivity() != null)
                ((BaseActivity) getActivity()).showWaitDialog();
            MultipartBody.Part filePart = FilePartUtil.GenerateMultiPart(file);

            String token = "bearer " + localStorage.getJwtToken().getStringToken();
            GetWayServiceGenerator.createService(IUploadInterface.class, token)
                    .uploadFile(filePart)
                    .enqueue(new Callback<FileModel>() {
                        @Override
                        public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                            if (getActivity() != null)
                                ((BaseActivity) getActivity()).dismissWaitDialog();

                            if (response.isSuccessful()) {
                                String path = response.body().getFilePath();
                                handleUploadedFile(file, messageTypeEnum, path);
                            }
                        }

                        @Override
                        public void onFailure(Call<FileModel> call, Throwable t) {
                            if (getActivity() != null)
                                ((BaseActivity) getActivity()).dismissWaitDialog();
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleUploadedFile(File file, Enums.MessageTypeEnum messageTypeEnum, String path) {
        if (messageTypeEnum.ordinal() == Enums.MessageTypeEnum.Text.ordinal() ||
                messageTypeEnum.ordinal() == Enums.MessageTypeEnum.Voice.ordinal() ||
                messageTypeEnum.ordinal() == Enums.MessageTypeEnum.Image.ordinal() ||
                messageTypeEnum.ordinal() == Enums.MessageTypeEnum.Document.ordinal() ||
                messageTypeEnum.ordinal() == Enums.MessageTypeEnum.Location.ordinal()) {
            ChatInputModel messageInput = new ChatInputModel(itemId,
                    messageTypeEnum.ordinal(), path, null);
            addMessage(messageInput);
        } else if (messageTypeEnum.ordinal() == Enums.MessageTypeEnum.Video.ordinal()) {
            ChatInputModel messageInput = new ChatInputModel(itemId,
                    messageTypeEnum.ordinal(), path, null);
            sendVideoThumbnailMessage(file, messageInput);
        }
    }

    private void sendVideoThumbnailMessage(File originalFile, ChatInputModel msg) {
        try {
            BitmapUtil bitmapUtil = new BitmapUtil(getActivity());
            Bitmap bitmap = bitmapUtil.generateThumbnailBitmap(originalFile);
            File file = bitmapUtil.saveThumbnailBitmap(bitmap);
            MultipartBody.Part filePart = FilePartUtil.GenerateMultiPart(file);

            String token = "bearer " + localStorage.getJwtToken().getStringToken();
            GetWayServiceGenerator.createService(IUploadInterface.class, token)
                    .uploadFile(filePart)
                    .enqueue(new Callback<FileModel>() {
                        @Override
                        public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                            if (response.isSuccessful()) {
                                String path = response.body().getFilePath();
                                msg.setAdditionalInfo(path);
                                addMessage(msg);
                            }
                        }

                        @Override
                        public void onFailure(Call<FileModel> call, Throwable t) {
                            //addMessage(msg);
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void fetchData(int itemId, String orderType) {
        progressBar.setVisibility(View.VISIBLE);
        clear();
        getMessages(itemId, orderType, currentPage);
    }

    private void clear() {
        itemModelList.clear();
        if (itemsAdapter != null)
            itemsAdapter.notifyDataSetChanged();
        currentPage = 1;
    }

    private void setRecyclerView(int orderId, String orderType) {
        itemModelList = new ArrayList<>();
        itemsAdapter = new ChatAdapter(itemModelList, getActivity(), currentUserType);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        itemsRecyclerView.setLayoutManager(mLayoutManager);
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.setAdapter(itemsAdapter);

        itemsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {

                    currentPage++;
                    getMessages(orderId, orderType, currentPage);
                }
            }
        });
    }

    private void getMessages(int itemId, String orderType, int page) {
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        ICustomersService providerService = GetWayServiceGenerator.createService(ICustomersService.class, token);

        ChatForType chatForType = (ChatForType) getArguments().getSerializable("ChatForType");
        if (chatForType == ChatForType.Order) {
            Call<List<OrderChatViewModel>> call = providerService.getOrderChatMessages(itemId, page);
            call.enqueue(new Callback<List<OrderChatViewModel>>() {
                @Override
                public void onResponse(Call<List<OrderChatViewModel>> call, Response<List<OrderChatViewModel>> response) {
                    progressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        List<OrderChatViewModel> items = response.body();
                        itemModelList.addAll(ChatMapper.mapOrders(items));
                        itemsAdapter.notifyItemRangeChanged(itemsAdapter.getItemCount(), items.size());

                        if (itemsAdapter.getItemCount() == 0) {
                            no_data_layout.setVisibility(View.VISIBLE);
                        } else {
                            no_data_layout.setVisibility(View.GONE);
                        }

                        if (page == 1) {
                            itemsRecyclerView.scrollToPosition(0);
                        }
                    } else {
                        //ErrorUtils.showError(getActivity(), response);
                    }
                }

                @Override
                public void onFailure(Call<List<OrderChatViewModel>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
        else {
            Call<List<TicketMessageModel>> call = providerService.getTicketMessages(itemId, page);
            call.enqueue(new Callback<List<TicketMessageModel>>() {
                @Override
                public void onResponse(Call<List<TicketMessageModel>> call, Response<List<TicketMessageModel>> response) {
                    progressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        List<TicketMessageModel> items = response.body();
                        itemModelList.addAll(ChatMapper.map(items));
                        itemsAdapter.notifyItemRangeChanged(itemsAdapter.getItemCount(), items.size());

                        if (itemsAdapter.getItemCount() == 0) {
                            no_data_layout.setVisibility(View.VISIBLE);
                        } else {
                            no_data_layout.setVisibility(View.GONE);
                        }

                        if (page == 1) {
                            itemsRecyclerView.scrollToPosition(0);
                        }
                    } else {
                        //ErrorUtils.showError(getActivity(), response);
                    }
                }

                @Override
                public void onFailure(Call<List<TicketMessageModel>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void addMessage(ChatInputModel messageInput) {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).showWaitDialog();
        String token = "bearer " + localStorage.getJwtToken().getStringToken();

        ChatForType chatForType = (ChatForType) getArguments().getSerializable("ChatForType");
        if (chatForType == ChatForType.Order) {
            GetWayServiceGenerator.createService(ICustomersService.class, token)
                    .addOrderChatComment(messageInput.getItemId(), messageInput)
                    .enqueue(new Callback<OrderChatViewModel>() {
                        @Override
                        public void onResponse(Call<OrderChatViewModel> call, Response<OrderChatViewModel> response) {
                            if (getActivity() != null)
                                ((BaseActivity) getActivity()).dismissWaitDialog();

                            if (response.isSuccessful()) {
                                OrderChatViewModel model = response.body();
                                ChatViewModel chatModel = ChatMapper.mapOrder(model);
                                sendMessageToSocket(chatModel, "order");
                                input_editext.setText("");
                                no_data_layout.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderChatViewModel> call, Throwable t) {
                            if (getActivity() != null)
                                ((BaseActivity) getActivity()).dismissWaitDialog();
                        }
                    });
        }
        else if (chatForType == ChatForType.Ticket) {
            GetWayServiceGenerator.createService(ICustomersService.class, token)
                    .addTicketMessage(messageInput.getItemId(), messageInput)
                    .enqueue(new Callback<TicketMessageModel>() {
                        @Override
                        public void onResponse(Call<TicketMessageModel> call, Response<TicketMessageModel> response) {
                            if (getActivity() != null)
                                ((BaseActivity) getActivity()).dismissWaitDialog();

                            if (response.isSuccessful()) {
                                TicketMessageModel model = response.body();
                                ChatViewModel chatModel = ChatMapper.map(model);
                                sendMessageToSocket(chatModel, "ticket");
                                input_editext.setText("");
                                no_data_layout.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<TicketMessageModel> call, Throwable t) {
                            if (getActivity() != null)
                                ((BaseActivity) getActivity()).dismissWaitDialog();
                        }
                    });
        }

        no_data_layout.setVisibility(View.GONE);
    }

    private void addMessageOnEnd(ChatViewModel chatModel) {
        itemModelList.add(0, chatModel);
        itemsAdapter.notifyItemInserted(0);
        itemsRecyclerView.scrollToPosition(0);
        if (!itemModelList.isEmpty())
            no_data_layout.setVisibility(View.GONE);
        runSound();
    }

    private void runSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getActivity(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SOCKET
    @Override
    public void onDestroy() {
        hubConnection.stop();
        super.onDestroy();
    }

    private void initSockets(int itemId, String itemType) {
        buildSocketConnection(itemId, itemType);
        listenForSocket();
    }

    private void sendMessageToSocket(ChatViewModel chatMessage, String itemType) {
        try {
            chatMessage.setItemTypeId(itemType);
            hubConnection.send("SendMessage", itemType, chatMessage.getId(),
                    chatMessage.getItemId(), chatMessage.getCreatedOn(),
                    chatMessage.getCreatedOnString(), chatMessage.getBody(),
                    chatMessage.getAdditionalInfo(), chatMessage.getUserId(),
                    chatMessage.getUserFullName(), chatMessage.getUserProfileLogo(),
                    chatMessage.getMessageType(), chatMessage.getUserTypeEnum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class HubConnectionTask extends AsyncTask<HubConnection, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(HubConnection... hubConnections) {
            try {
                HubConnection hubConnection = hubConnections[0];
                hubConnection.start().blockingAwait();
            }
            catch(Exception e) {

            }
            return null;
        }
    }

    private void buildSocketConnection(int itemId, String itemType) {
        SharedPreferencesStorage localStorage = new SharedPreferencesStorage(getActivity());
        hubConnection = HubConnectionBuilder
                .create(BuildConfig.ChatBaseUrl)
                .withHeader("ItemId", String.valueOf(itemId))
                .withHeader("ItemTypeId", itemType)
                .withAccessTokenProvider(Single.defer(() -> {
                    return Single.just(localStorage.getJwtToken().getStringToken().replace("Bearer ", ""));
                })).build();

        new HubConnectionTask().execute(hubConnection);
    }

    private void listenForSocket() {
        hubConnection.on("ReceiveMessage", (chatMessage) -> {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addMessageOnEnd(chatMessage);
                }
            });
        }, ChatViewModel.class);
    }
}

