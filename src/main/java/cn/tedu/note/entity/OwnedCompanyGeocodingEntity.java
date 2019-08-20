package cn.tedu.note.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/8/20 15:23
 */
public class OwnedCompanyGeocodingEntity implements Serializable {
    private String companyName;

    private String city;

    private String address;

    private double longitude;

    private double latitude;

    private double longitudeA;

    private double latitudeA;

    public OwnedCompanyGeocodingEntity() {
    }

    public OwnedCompanyGeocodingEntity(String companyName, String city, String address, double longitude, double latitude, double longitudeA, double latitudeA) {
        this.companyName = companyName;
        this.city = city;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.longitudeA = longitudeA;
        this.latitudeA = latitudeA;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitudeA() {
        return longitudeA;
    }

    public void setLongitudeA(double longitudeA) {
        this.longitudeA = longitudeA;
    }

    public double getLatitudeA() {
        return latitudeA;
    }

    public void setLatitudeA(double latitudeA) {
        this.latitudeA = latitudeA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        OwnedCompanyGeocodingEntity that = (OwnedCompanyGeocodingEntity) o;

        return new EqualsBuilder()
                .append(getLongitude(), that.getLongitude())
                .append(getLatitude(), that.getLatitude())
                .append(getLongitudeA(), that.getLongitudeA())
                .append(getLatitudeA(), that.getLatitudeA())
                .append(getCompanyName(), that.getCompanyName())
                .append(getCity(), that.getCity())
                .append(getAddress(), that.getAddress())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getCompanyName())
                .append(getCity())
                .append(getAddress())
                .append(getLongitude())
                .append(getLatitude())
                .append(getLongitudeA())
                .append(getLatitudeA())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OwnedCompanyGeocodingEntity{" +
                "companyName='" + companyName + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", longitudeA=" + longitudeA +
                ", latitudeA=" + latitudeA +
                '}';
    }
}
