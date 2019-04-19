package cn.tedu.note.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/18 9:37
 */
public class GoodsPlanLineEntity {

    /**
     * 车牌号
     */
    private String cph;

    /**
     * 提送货单编号
     */
    private String goodsBill;

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
    private List<GoodsOrdersEntity> orderGeoCodeList;

    /**
     * 当前提送货单地图返回的规划距离
     */
    private Integer goodsDistance;

    public GoodsPlanLineEntity(){}

    public GoodsPlanLineEntity(String cph, String goodsBill, String billType, String city, String storeAddress, String storeGeoCode, List<GoodsOrdersEntity> orderGeoCodeList, Integer goodsDistance) {
        this.cph = cph;
        this.goodsBill = goodsBill;
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

    public String getGoodsBill() {
        return goodsBill;
    }

    public void setGoodsBill(String goodsBill) {
        this.goodsBill = goodsBill;
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

    public List<GoodsOrdersEntity> getOrderGeoCodeList() {
        return orderGeoCodeList;
    }

    public void setOrderGeoCodeList(List<GoodsOrdersEntity> orderGeoCodeList) {
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

        GoodsPlanLineEntity that = (GoodsPlanLineEntity) o;

        return new EqualsBuilder()
                .append(getCph(), that.getCph())
                .append(getGoodsBill(), that.getGoodsBill())
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
                .append(getGoodsBill())
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
        return "GoodsPlanLineEntity{" +
                "cph='" + cph + '\'' +
                ", goodsBill='" + goodsBill + '\'' +
                ", billType='" + billType + '\'' +
                ", city='" + city + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", storeGeoCode='" + storeGeoCode + '\'' +
                ", orderGeoCodeList=" + orderGeoCodeList +
                ", goodsDistance=" + goodsDistance +
                '}';
    }
}
