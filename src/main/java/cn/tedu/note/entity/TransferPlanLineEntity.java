package cn.tedu.note.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/18 9:35
 */
public class TransferPlanLineEntity {
    /**
     * 车牌号
     */
    private String cph;

    /**
     * 转移线路
     */
    private String line;

    /**
     * 发车公司所在城市
     */
    private String startCity;

    /**
     * 串线发车公司地址
     */
    private String startAddress;

    /**
     * 串线发车公司地理编码
     */
    private String startGeoCode;

    /**
     * 到车公司所在城市
     */
    private String endCity;

    /**
     * 串线到车公司地理编码
     */
    private String endGeoCode;

    /**
     * 串线到货公司地址
     */
    private String endAddress;

    /**
     * 转移规划行驶距离
     */
    private Integer plannedDistance;

    /**
     * 上下转移吨位
     */
    private Double zydw;

    /**
     * 上下转移体积
     */
    private Double zytj;

    /**
     * 发车类型
     */
    private String type;

    public TransferPlanLineEntity() {
    }

    public TransferPlanLineEntity(String cph, String line, String startCity, String startAddress, String startGeoCode, String endCity, String endGeoCode, String endAddress, Integer plannedDistance, Double zydw, Double zytj, String type) {
        this.cph = cph;
        this.line = line;
        this.startCity = startCity;
        this.startAddress = startAddress;
        this.startGeoCode = startGeoCode;
        this.endCity = endCity;
        this.endGeoCode = endGeoCode;
        this.endAddress = endAddress;
        this.plannedDistance = plannedDistance;
        this.zydw = zydw;
        this.zytj = zytj;
        this.type = type;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getStartGeoCode() {
        return startGeoCode;
    }

    public void setStartGeoCode(String startGeoCode) {
        this.startGeoCode = startGeoCode;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public String getEndGeoCode() {
        return endGeoCode;
    }

    public void setEndGeoCode(String endGeoCode) {
        this.endGeoCode = endGeoCode;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public Integer getPlannedDistance() {
        return plannedDistance;
    }

    public void setPlannedDistance(Integer plannedDistance) {
        this.plannedDistance = plannedDistance;
    }

    public Double getZydw() {
        return zydw;
    }

    public void setZydw(Double zydw) {
        this.zydw = zydw;
    }

    public Double getZytj() {
        return zytj;
    }

    public void setZytj(Double zytj) {
        this.zytj = zytj;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TransferPlanLineEntity that = (TransferPlanLineEntity) o;

        return new EqualsBuilder()
                .append(getCph(), that.getCph())
                .append(getLine(), that.getLine())
                .append(getStartCity(), that.getStartCity())
                .append(getStartAddress(), that.getStartAddress())
                .append(getStartGeoCode(), that.getStartGeoCode())
                .append(getEndCity(), that.getEndCity())
                .append(getEndGeoCode(), that.getEndGeoCode())
                .append(getEndAddress(), that.getEndAddress())
                .append(getPlannedDistance(), that.getPlannedDistance())
                .append(getZydw(), that.getZydw())
                .append(getZytj(), that.getZytj())
                .append(getType(), that.getType())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getCph())
                .append(getLine())
                .append(getStartCity())
                .append(getStartAddress())
                .append(getStartGeoCode())
                .append(getEndCity())
                .append(getEndGeoCode())
                .append(getEndAddress())
                .append(getPlannedDistance())
                .append(getZydw())
                .append(getZytj())
                .append(getType())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "TransferPlanLineEntity{" +
                "cph='" + cph + '\'' +
                ", line='" + line + '\'' +
                ", startCity='" + startCity + '\'' +
                ", startAddress='" + startAddress + '\'' +
                ", startGeoCode='" + startGeoCode + '\'' +
                ", endCity='" + endCity + '\'' +
                ", endGeoCode='" + endGeoCode + '\'' +
                ", endAddress='" + endAddress + '\'' +
                ", plannedDistance=" + plannedDistance +
                ", zydw=" + zydw +
                ", zytj=" + zytj +
                ", type='" + type + '\'' +
                '}';
    }
}
