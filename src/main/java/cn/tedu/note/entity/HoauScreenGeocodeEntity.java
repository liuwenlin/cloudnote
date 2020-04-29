package cn.tedu.note.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

/**
 * 华宇大屏规划基础数据表地理编码(经纬度)获取包装类
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/19 14:54
 */
public class HoauScreenGeocodeEntity {
    private String id;

    private String shortName;

    private String city;

    private String address;

    private BigDecimal longitude;

    private BigDecimal latitude;

    public HoauScreenGeocodeEntity() {
    }

    public HoauScreenGeocodeEntity(String id, String shortName, String city, String address, BigDecimal longitude, BigDecimal latitude) {
        this.id = id;
        this.shortName = shortName;
        this.city = city;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        HoauScreenGeocodeEntity that = (HoauScreenGeocodeEntity) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(getShortName(), that.getShortName())
                .append(getCity(), that.getCity())
                .append(getAddress(), that.getAddress())
                .append(getLongitude(), that.getLongitude())
                .append(getLatitude(), that.getLatitude())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getShortName())
                .append(getCity())
                .append(getAddress())
                .append(getLongitude())
                .append(getLatitude())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "HoauScreenGeocodeEntity{" +
                "id='" + id + '\'' +
                ", shortName='" + shortName + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
