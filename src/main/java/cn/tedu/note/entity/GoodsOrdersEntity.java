package cn.tedu.note.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/18 9:37
 */
public class GoodsOrdersEntity {
    /**
     * 提送货物单编号
     */
    private String goodsBill;

    /**
     * 运单编号
     */
    private String ydbh;

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
     * 运单提货顺序
     */
    private Integer goodsSequence;

    public GoodsOrdersEntity(){}

    public GoodsOrdersEntity(String goodsBill, String ydbh, String address, String geocode, Integer countOfOrder, Double ton, Integer goodsSequence) {
        this.goodsBill = goodsBill;
        this.ydbh = ydbh;
        this.address = address;
        this.geocode = geocode;
        this.countOfOrder = countOfOrder;
        this.ton = ton;
        this.goodsSequence = goodsSequence;
    }

    public String getGoodsBill() {
        return goodsBill;
    }

    public void setGoodsBill(String goodsBill) {
        this.goodsBill = goodsBill;
    }

    public String getYdbh() {
        return ydbh;
    }

    public void setYdbh(String ydbh) {
        this.ydbh = ydbh;
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

        GoodsOrdersEntity that = (GoodsOrdersEntity) o;

        return new EqualsBuilder()
                .append(getGoodsBill(), that.getGoodsBill())
                .append(getYdbh(), that.getYdbh())
                .append(getAddress(), that.getAddress())
                .append(getGeocode(), that.getGeocode())
                .append(getCountOfOrder(), that.getCountOfOrder())
                .append(getTon(), that.getTon())
                .append(getGoodsSequence(), that.getGoodsSequence())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getGoodsBill())
                .append(getYdbh())
                .append(getAddress())
                .append(getGeocode())
                .append(getCountOfOrder())
                .append(getTon())
                .append(getGoodsSequence())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "GoodsOrdersEntity{" +
                "goodsBill='" + goodsBill + '\'' +
                ", ydbh='" + ydbh + '\'' +
                ", address='" + address + '\'' +
                ", geocode='" + geocode + '\'' +
                ", countOfOrder=" + countOfOrder +
                ", ton=" + ton +
                ", goodsSequence=" + goodsSequence +
                '}';
    }
}
