package maksab.sd.customer.util.general;

public class SelectedSpecialityModel {
    int catid;
    int specialtyId;
    String specialtyName;
    String specialtydecription;

    public SelectedSpecialityModel(int catid, int specialtyId, String specialtyName, String specialtydecription) {
        this.catid = catid;
        this.specialtyId = specialtyId;
        this.specialtyName = specialtyName;
        this.specialtydecription = specialtydecription;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public String getSpecialtydecription() {
        return specialtydecription;
    }

    public void setSpecialtydecription(String specialtydecription) {
        this.specialtydecription = specialtydecription;
    }


}
