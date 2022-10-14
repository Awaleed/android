package maksab.sd.customer.network.appnetwork

import maksab.sd.customer.models.address.AddressInput
import maksab.sd.customer.models.address.AddressModel
import maksab.sd.customer.models.chat.OrderChatViewModel
import maksab.sd.customer.models.faq.FaqTypeViewModel
import maksab.sd.customer.models.faq.FaqViewModel
import maksab.sd.customer.models.lookup.SelectCouponModel
import maksab.sd.customer.models.main.*
import maksab.sd.customer.models.orders.details.*
import maksab.sd.customer.models.profile.BalanceViewModel
import maksab.sd.customer.models.profile.ProfileModel
import maksab.sd.customer.models.profile.UpdateProfileModel
import maksab.sd.customer.models.providers.*
import maksab.sd.customer.models.speciality.SpecialityQuestionModel
import maksab.sd.customer.models.speciality.SpecialitySubscriptionInputModel
import maksab.sd.customer.models.tickets.*
import maksab.sd.customer.notifications.helpers.NotificationModel
import maksab.sd.customer.ui.balance.models.BalanceTransactionViewModel
import maksab.sd.customer.ui.chat.chats.ChatInputModel
import maksab.sd.customer.util.general.FileModel
import maksab.sd.customer.util.general.FirebaseTokenModel
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by dev2 on 12/10/2017.
 */

interface ICustomersService {
    // Customer API
    @POST("customers/profile")
    fun updateProfile(@Body updateProfileModel: UpdateProfileModel): Call<ProfileModel>
    @GET("customers/profile")
    fun getProfile(): Call<ProfileModel>
    @POST("customers/profile/image")
    fun updateProfileImage(@Query("filePath") filePath: String?): Call<FileModel>
    @POST("customers/profile/source")
    fun updateSource(@Body updateSourceModel: UpdateSourceModel) : Call<ResponseBody>
    @POST("customers/onboarding")
    fun updateOnboardingStatus(@Query("onBoardingStatus") onBoardingStatus:Int) : Call<ResponseBody>
    @GET("customers/home")
    fun getHome() : Call<HomeModel>

    // Transactions
    @GET("customers/balances")
    fun getBalance(): Call<BalanceViewModel>
    @GET("customers/transactions")
    fun getBalanceTransactions(@Query("page") pagenum: Int): Call<List<BalanceTransactionViewModel?>?>?

    // Faqs
    @GET("faqs")
    fun getFaqs(@Query("faqTypeId") faqTypeId: Int,@Query("page") page: Int): Call<MutableList<FaqViewModel>>
    @GET("faqs/types")
    fun getFaqTypes(@Query("userTypeEnum") userTypeEnum: Int): Call<List<FaqTypeViewModel>>
    @GET("faqs/{faqId}")
    fun getFaqById(@Path("faqId") faqId: Int): Call<FaqViewModel>
    @POST("faqs/{faqId}/like")
    fun likeFaq(@Path("faqId") faqId: Int): Call<Boolean>
    @POST("faqs/{faqId}/dislike")
    fun dislikeFaq(@Path("faqId") faqId: Int): Call<Boolean>
    @POST("faqs/{faqId}/viewcount")
    fun increaseViewCount(@Path("faqId") faqId: Int): Call<Boolean>

    // Questions APIs
    @GET("customers/specialities/{id}/questions?specialtyQuestionForType=1")
    fun getSpecialtyQuestions(@Path("id") specialityId: Int): Call<MutableList<SpecialityQuestionModel?>?>?

    // Gallery APIs
    @GET("customers/galleries")
    fun getGalleries(@Query("page") page: Int, @Query("userId") userid: String): Call<List<GalleryModel>>
    @POST("customers/galleries/viewcount/{providerGalleryId}")
    fun viewCount(@Path("providerGalleryId") providerGalleryId: Long): Call<ResponseBody>
    @GET("customers/galleries/public/{id}?page=1&pageCount=12")
    fun getPublicGallery(@Path("id") id : Int) : Call<List<GalleryModel>>
    @GET("customers/galleries/public/{id}")
    fun getGalleryItemsbyCatId(@Path("id") id : Int, @Query("page") page: Int) : Call<List<GalleryModel>>
    @GET("customers/galleries/public/speciality/{specialityId}")
    fun getGalleryItemsbySepcId(@Path("specialityId") id : Int, @Query("page") page: Int) : Call<List<GalleryModel>>

    // Offers API
    @GET("customers/offers")
    fun getOffers(@Query("categoryId") categoryId : Int) : Call<List<HomeSpecialOfferModel>>
    @GET("customers/offers/active")
    fun getOffers(@Query("userId") userid: String) : Call<List<HomeSpecialOfferModel>>
    @GET("customers/offers/{offerId}")
    fun getOfferById(@Path("offerId") offerId: Int) : Call<HomeSpecialOfferModel>

    // Orders APIs
    @GET("customers/orders")
    fun GetOrders(@Query("page") page: Int, @Query("combinedStatusId") orderstate: Int): Call<List<OrderModel>>
    @POST("Customers/orders/{orderId}/cancel")
    fun cancelOrder(@Path("orderId") orderId: Long): Call<ResponseBody>
    @GET("customers/orders/{orderId}")
    fun getorderbyId(@Path("orderId") id: Long): Call<OrderModel>
    @POST("customers/orders")
    fun addNewOrder(@Body orderModel: OrderInputModel): Call<OrderModel>
    @GET("Customers/orders/{orderId}/chat")
    fun getOrderChatMessages(@Path("orderId") orderId: Long, @Query("page") page: Int): Call<List<OrderChatViewModel>>
    @POST("Customers/orders/{orderId}/chat")
    fun addOrderChatComment(@Path("orderId") orderId: Long,@Body addCommentMode: ChatInputModel): Call<OrderChatViewModel>
    @POST("Customers/orders/rate")
    fun ratingProvider(@Body rateOrder: RateOrder): Call<ResponseBody>
    @GET("Customers/orders/rate/pending")
    fun pendingRate(): Call<OrderModel>
    @POST("customers/orders/{orderId}/status")
    fun changeOrderStatus(@Path("orderId") orderid: Long, @Body model: OrderStatusUpdateModel): Call<ResponseBody>
    @POST("customers/orders")
    fun addQuotations(@Body orderInputModel: OrderInputModel): Call<ResponseBody>
    @POST("customers/orders/direct")
    fun addDirctQuotations(@Body orderInputModel: OrderInputModel): Call<ResponseBody>
    @Multipart
    @POST("customers/orders/{orderId}/images")
    fun uploadQuotationImage(@Part file: MultipartBody.Part): Call<ResponseBody>
    @GET("customers/orders")
    fun getQuotations(@Query("newOnly") newOnly: Boolean): Call<List<QuotationViewModel>>
    @GET("customers/orders/{orderId}/offers")
    fun getOrderOffers(@Path("orderId") orderId: Long, @Query("page") page: Int): Call<List<OrderOffer>>
    @POST("customers/orders/{quotationid}/close")
    fun cancelQuotation(@Path("quotationid") quotationid: Long, @Body model: CancelReasonModel): Call<ResponseBody>
    @GET("customers/orders/{quotationid}")
    fun getquotation(@Path("quotationid") quotationid: Long): Call<QuotationViewModel>
    @POST("customers/orders/{quotationid}/offers/{offerid}")
    fun acceptOffer(@Path("quotationid") quotationid: Long, @Path("offerid") offerid: Long): Call<OrderModel>
    @GET("customers/orders")
    fun getOrders(@Query("page") page: Int, @Query("combinedStatusId") combinedStatusId: Int): Call<List<OrderModel>>
    @POST("customers/orders/{orderId}/chat")
    fun addComment(@Path("orderId") quotationid : Long , @Body addCommentModel: AddCommentModel) : Call<CommentModel>
    @GET("customers/orders/{orderId}/chat")
    fun getComments(@Path("orderId") orderId: Long) : Call<List<CommentModel>>
    @GET("customers/orders/opened")
    fun checkOpendOrders() : Call<Boolean>

    // System API
    @GET("customers/appversion/{code}")
    fun updateVersionCode(@Path("code") versionCode:Int) : Call<VersionReslutModel>
    @POST("customers/timing_badge")
    fun addUserTimingAndBadge() : Call<Int>
    @POST("Site/feedbacks")
    fun postForm(@Body supportFormModel: SupportFormModel): Call<ResponseBody>
    @GET("Site/otp")
    fun resendOTP(@Query("mobile") mobile: String, @Query("clientType") clientType: String): Call<Boolean>

    // Firebase
    @POST("FireBaseTokens")
    fun addToken(@Body token: FirebaseTokenModel): Call<ResponseBody>
    @GET("FireBaseTokens")
    fun token() : Call<String>

    // Notifications
    @GET("notifications")
    fun getNotifications(@Query("notificationCombinedQuery") notificationCombinedQuery : Int, @Query("page") page: Int) : Call<List<NotificationModel>>
    @POST("notifications/read_all")
    fun setReadAllNotifications(): Call<Boolean?>?
    @POST("notifications/status")
    fun updateNotificationStatus(@Query("notificationId") notificationId: Int, @Query("type") type: Int): Call<Boolean?>?
    @POST("notifications/broadcast/status")
    fun updateBroadcastNotificationStatus(@Query("notificationId") notificationId: Int, @Query("type") type: Int): Call<Boolean?>?

    // Providers APIs
    @GET("customers/providers/{userid}")
    fun getProviderProfile(@Path("userid") userid: String): Call<ProviderDetailsModel>
//    @GET("customers/providers/speciality/nearby/{specialtyid}")
//    fun GetProvidersNearBy(@Path("specialtyid") speciltyId: Int, @Query("latitude") latitude: Double, @Query("longitude") longitude: Double, @Query("page") page: Int): Call<List<ProviderDetailsModel>>
    @GET("customers/providers/speciality/{specialtyid}")
    fun GetProvidersBySpeciality(@Path("specialtyid") speciltyId: Int , @Query("addressId")addressId:Int, @Query("page")page:Int): Call<List<ProviderDetailsModel>>
    @GET("customers/providers/rate")
    fun getProviderReviews(@Query("userid") providerUserId: String, @Query("page") page:Int) : Call<List<ProviderRateModel>>
    @GET("customers/providers/{userid}/favorite")
    fun getfavorite(@Path("userid") userid: String): Call<CheckFavoriteModel>

    // Address api
    @GET("customers/address")
    fun getAddress() : Call<List<AddressModel>>
    @POST("customers/address")
    fun addNewAddress(@Body addressInput: AddressInput) : Call<ResponseBody>
    @DELETE("customers/address")
    fun deleteAddress(@Query("addressId") addressId:Int) : Call<ResponseBody>

    // Feedback / Tickets APIs
    @POST("tickets")
    fun addTicket(@Body ticketInputModel: TicketInputModel) : Call<ResponseBody>
    @GET("tickets")
    fun getTickets(@Query("page") page:Int, @Query("ticketInputStatus") ticketStatus :Int) : Call<List<TicketModel>>
    @GET("tickets/{ticketId}")
    fun getTicketById(@Path("ticketId") id: Long) : Call<TicketModel>
    @POST("tickets/{ticketId}/chat")
    fun addTicketMessage(@Path("ticketId") id: Long,@Body ticketMessageInput: ChatInputModel?): Call<TicketMessageModel?>?
    @GET("tickets/{ticketId}/chat")
    fun getTicketMessages(@Path("ticketId") ticketId : Int , @Query("page")page:Int) : Call<List<TicketMessageModel>>
    @GET("tickets/ticket_categories")
    fun getTicketCategoriesType(@Query("userTypeEnum") userTypeEnum:Int) : Call<List<TicketCategoryTypeModel>>
    @GET("tickets/ticket_subcategories")
    fun getTicketsItemsType(@Query("ticketCategoryId") categoryId:Int) : Call<List<TicketItemTypeModel>>
    @POST("tickets/{id}")
    fun addFeedbackMessage(@Path("id") id:Int, @Body ticketMessageInput: TicketMessageInput) : Call<TicketMessageModel>
    @GET("tickets/{id}")
    fun getFeedbackMessage(@Path("id") id:Int) : Call<List<TicketMessageModel>>

    // Favorites
    @GET("Customers/favorites")
    fun gtFavouriteProviders(): Call<List<ProviderDetailsModel>>
    @POST("Customers/favorites")
    fun setToFavorite(@Body setFavoriteModel: SetFavoriteModel): Call<ResponseBody>

    // Coupon APIs
    @POST("Customers/coupon")
    fun checkCupon(@Body checkCuopon: CheckCuopon): Call<CouponModel>
    @GET("customers/coupon/{specailityId}")
    fun getActiveCoupons(@Path("specailityId") specailityId: Int) : Call<List<SelectCouponModel>>

    // DeepLinks & Credits
    @POST("customers/deeplink/credits")
    fun saveCredit(@Query("credits") credit: Long): Call<ResponseBody>
    @POST("customers/referral")
    fun addRefferal(@Query("userId") userId: String) : Call<Boolean>

    @POST("customers/subscription")
    fun subscribeToSpeciality(@Body subscriptionInputModel: SpecialitySubscriptionInputModel) : Call<Boolean>
    //specialities
}
