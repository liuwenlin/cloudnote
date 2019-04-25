package cn.tedu.note.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/23 16:07
 */
public class VehicleOrderEntity implements Serializable {
    /**
     * 提送货车牌号
     */
    private String cph;

    /**
     * 运单编号
     */
    private String ydbh;

    /**
     * 运单所在城市
     */
    private String city;

    /**
     * 运单地址
     */
    private String address;

    /**
     * 地理编码
     */
    private String geocode;

    /**
     * 运单件数
     */
    private Integer countOfOrder;

    /**
     * 运单吨位
     */
    private Double ton;

    /**
     * 运单体积
     */
    private Double tj;

    /**
     * 运单提货顺序
     */
    private Integer goodsSequence;

    public VehicleOrderEntity() {
    }

    public VehicleOrderEntity(String cph, String ydbh, String city, String address, String geocode, Integer countOfOrder, Double ton, Double tj, Integer goodsSequence) {
        this.cph = cph;
        this.ydbh = ydbh;
        this.city = city;
        this.address = address;
        this.geocode = geocode;
        this.countOfOrder = countOfOrder;
        this.ton = ton;
        this.tj = tj;
        this.goodsSequence = goodsSequence;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getYdbh() {
        return ydbh;
    }

    public void setYdbh(String ydbh) {
        this.ydbh = ydbh;
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

    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }

    public Integer getCountOfOrder() {
        return countOfOrder;
    }

    public void setCountOfOrder(Integer countOfOrder) {
        this.countOfOrder = countOfOrder;
    }

    public Double getTon() {
        return ton;
    }

    public void setTon(Double ton) {
        this.ton = ton;
    }

    public Double getTj() {
        return tj;
    }

    public void setTj(Double tj) {
        this.tj = tj;
    }

    public Integer getGoodsSequence() {
        return goodsSequence;
    }

    public void setGoodsSequence(Integer goodsSequence) {
        this.goodsSequence = goodsSequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VehicleOrderEntity that = (VehicleOrderEntity) o;

        return new EqualsBuilder()
                .append(getCph(), that.getCph())
                .append(getYdbh(), that.getYdbh())
                .append(getCity(), that.getCity())
                .append(getAddress(), that.getAddress())
                .append(getGeocode(), that.getGeocode())
                .append(getCountOfOrder(), that.getCountOfOrder())
                .append(getTon(), that.getTon())
                .append(getTj(), that.getTj())
                .append(getGoodsSequence(), that.getGoodsSequence())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getCph())
                .append(getYdbh())
                .append(getCity())
                .append(getAddress())
                .append(getGeocode())
                .append(getCountOfOrder())
                .append(getTon())
                .append(getTj())
                .append(getGoodsSequence())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "VehicleOrderEntity{" +
                "cph='" + cph + '\'' +
                ", ydbh='" + ydbh + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", geocode='" + geocode + '\'' +
                ", countOfOrder=" + countOfOrder +
                ", ton=" + ton +
                ", tj=" + tj +
                ", goodsSequence=" + goodsSequence +
                '}';
    }
}
