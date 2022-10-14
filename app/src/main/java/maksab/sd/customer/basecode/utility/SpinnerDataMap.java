package maksab.sd.customer.basecode.utility;

/**
 * Created by dev2 on 12/10/2017.
 */

public class SpinnerDataMap {
    private int id;
    private String name;
    private int catid;

    public SpinnerDataMap(int id, String name , int catid) {
        this.id = id;
        this.name = name;
        this.catid = catid;
    }

    public SpinnerDataMap(int id, String name ) {
        this.id = id;
        this.name = name;
    }

    public SpinnerDataMap(){

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }
}
