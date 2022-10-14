package maksab.sd.customer.storage;
   public class OrderSummaryDetails {

       private String name;
       private int quantity;
       private double price;
       private int id;

       public OrderSummaryDetails(String name, int quantity, double price, int id) {
           this.name = name;
           this.quantity = quantity;
           this.price = price;
           this.id = id;
       }

       public String getName() {
           return name;
       }

       public void setName(String name) {
           this.name = name;
       }

       public int getQuantity() {
           return quantity;
       }

       public void setQuantity(int quantity) {
           this.quantity = quantity;
       }

       public double getPrice() {
           return price;
       }

       public void setPrice(double price) {
           this.price = price;
       }

       public int getId() {
           return id;
       }

       public void setId(int id) {
           this.id = id;
       }


   }
