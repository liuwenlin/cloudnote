package cn.tedu.note.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/4/21 23:50
 */
public class StandardPlanDistanceEntity implements Serializable {

    private String id;

    private String startCity;

    private String startAddress;

    private String startGeocode;

    private String endCity;

    private String endAddress;

    private String endGeocode;

    private String remarkText;

    private Double distance;

    public StandardPlanDistanceEntity() {
    }

    public StandardPlanDistanceEntity(String id, String startCity, String startAddress, String startGeocode, String endCity, String endAddress, String endGeocode, String remarkText, Double distance) {
        this.id = id;
        this.startCity = startCity;
        this.startAddress = startAddress;
        this.startGeocode = startGeocode;
        this.endCity = endCity;
        this.endAddress = endAddress;
        this.endGeocode = endGeocode;
        this.remarkText = remarkText;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStartGeocode() {
        return startGeocode;
    }

    public void setStartGeocode(String startGeocode) {
        this.startGeocode = startGeocode;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getEndGeocode() {
        return endGeocode;
    }

    public void setEndGeocode(String endGeocode) {
        this.endGeocode = endGeocode;
    }

    public String getRemarkText() {
        return remarkText;
    }

    public void setRemarkText(String remarkText) {
        this.remarkText = remarkText;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StandardPlanDistanceEntity that = (StandardPlanDistanceEntity) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(getStartCity(), that.getStartCity())
                .append(getStartAddress(), that.getStartAddress())
                .append(getStartGeocode(), that.getStartGeocode())
                .append(getEndCity(), that.getEndCity())
                .append(getEndAddress(), that.getEndAddress())
                .append(getEndGeocode(), that.getEndGeocode())
                .append(getRemarkText(), that.getRemarkText())
                .append(getDistance(), that.getDistance())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getStartCity())
                .append(getStartAddress())
                .append(getStartGeocode())
                .append(getEndCity())
                .append(getEndAddress())
                .append(getEndGeocode())
                .append(getRemarkText())
                .append(getDistance())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "StandardPlanDistanceEntity{" +
                "id='" + id + '\'' +
                ", startCity='" + startCity + '\'' +
                ", startAddress='" + startAddress + '\'' +
                ", startGeocode='" + startGeocode + '\'' +
                ", endCity='" + endCity + '\'' +
                ", endAddress='" + endAddress + '\'' +
                ", endGeocode='" + endGeocode + '\'' +
                ", remarkText='" + remarkText + '\'' +
                ", distance=" + distance +
                '}';
    }
}
