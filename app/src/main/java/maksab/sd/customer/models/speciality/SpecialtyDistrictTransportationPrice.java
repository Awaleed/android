package maksab.sd.customer.models.speciality;

import maksab.sd.customer.models.address.District;

public class SpecialtyDistrictTransportationPrice {
    private int id;
    private int specialtyId;
    private SpecialityModel specialty;
    private int districtId;
    private District district;
    private double price;

    public int getId() {
        return id;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public SpecialityModel getSpecialty() {
        return specialty;
    }

    public int getDistrictId() {
        return districtId;
    }

    public District getDistrict() {
        return district;
    }

    public double getPrice() {
        return price;
    }
}
