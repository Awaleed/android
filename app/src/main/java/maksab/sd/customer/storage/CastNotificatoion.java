package maksab.sd.customer.storage;

/**
 * Created by Dev on 2/21/2018.
 */

public class CastNotificatoion {

    public CastNotificatoion(String casttitle, String castbody) {
        this.casttitle = casttitle;
        this.castbody = castbody;
    }

    public String getCasttitle() {
        return casttitle;
    }

    public void setCasttitle(String casttitle) {
        this.casttitle = casttitle;
    }

    public String getCastbody() {
        return castbody;
    }

    public void setCastbody(String castbody) {
        this.castbody = castbody;
    }

    private String casttitle;
    private String castbody;
}
