package maksab.sd.customer.models.orders.details

data class CommentModel (var id : Long , var quotationId : Long ,var userId : String
                         ,var userFullName : String , var userImage : String , var body : String ,
                         var createdOnString : String , var isMedia : Boolean , var isCustomer : Boolean ) {
}