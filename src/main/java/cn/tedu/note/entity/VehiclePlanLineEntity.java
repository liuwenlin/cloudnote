package cn.tedu.note.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/23 16:07
 */
public class VehiclePlanLineEntity implements Serializable {
    /**
     * 车牌号
     */
    private String cph;

    /**
     * 提送货单编号类型
     */
    private String billType;

    /**
     * 城市
     */
    private String city;

    /**
     * 提送货公司地址
     */
    private String storeAddress;

    /**
     * 提货公司地理编码
     */
    private String storeGeoCode;

    /**
     * 提货单运单
     */
    private List<VehicleOrderEntity> orderGeoCodeList;

    /**
     * 当前提送货单地图返回的规划距离
     */
    private Integer goodsDistance;

    public VehiclePlanLineEntity() {
    }

    public VehiclePlanLineEntity(String cph, String billType, String city, String storeAddress, String storeGeoCode, List<VehicleOrderEntity> orderGeoCodeList, Integer goodsDistance) {
        this.cph = cph;
        this.billType = billType;
        this.city = city;
        this.storeAddress = storeAddress;
        this.storeGeoCode = storeGeoCode;
        this.orderGeoCodeList = orderGeoCodeList;
        this.goodsDistance = goodsDistance;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreGeoCode() {
        return storeGeoCode;
    }

    public void setStoreGeoCode(String storeGeoCode) {
        this.storeGeoCode = storeGeoCode;
    }

    public List<VehicleOrderEntity> getOrderGeoCodeList() {
        return orderGeoCodeList;
    }

    public void setOrderGeoCodeList(List<VehicleOrderEntity> orderGeoCodeList) {
        this.orderGeoCodeList = orderGeoCodeList;
    }

    public Integer getGoodsDistance() {
        return goodsDistance;
    }

    public void setGoodsDistance(Integer goodsDistance) {
        this.goodsDistance = goodsDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VehiclePlanLineEntity that = (VehiclePlanLineEntity) o;

        return new EqualsBuilder()
                .append(getCph(), that.getCph())
                .append(getBillType(), that.getBillType())
                .append(getCity(), that.getCity())
                .append(getStoreAddress(), that.getStoreAddress())
                .append(getStoreGeoCode(), that.getStoreGeoCode())
                .append(getOrderGeoCodeList(), that.getOrderGeoCodeList())
                .append(getGoodsDistance(), that.getGoodsDistance())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getCph())
                .append(getBillType())
                .append(getCity())
                .append(getStoreAddress())
                .append(getStoreGeoCode())
                .append(getOrderGeoCodeList())
                .append(getGoodsDistance())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "VehiclePlanLineEntity{" +
                "cph='" + cph + '\'' +
                ", billType='" + billType + '\'' +
                ", city='" + city + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", storeGeoCode='" + storeGeoCode + '\'' +
                ", orderGeoCodeList=" + orderGeoCodeList +
                ", goodsDistance=" + goodsDistance +
                '}';
    }
}
