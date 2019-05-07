package cn.tedu.note.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/5/6 18:09
 */
public class VehicleBillEntity implements Serializable {

    /**
     * 车牌号
     */
    private String cph;

    /**
     * 车牌号对应的货物单编号
     */
    private List<VehiclePlanLineEntity> vehiclePlanLineEntityList;

    public VehicleBillEntity() {
    }

    public VehicleBillEntity(String cph, List<VehiclePlanLineEntity> vehiclePlanLineEntityList) {
        this.cph = cph;
        this.vehiclePlanLineEntityList = vehiclePlanLineEntityList;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public List<VehiclePlanLineEntity> getVehiclePlanLineEntityList() {
        return vehiclePlanLineEntityList;
    }

    public void setVehiclePlanLineEntityList(List<VehiclePlanLineEntity> vehiclePlanLineEntityList) {
        this.vehiclePlanLineEntityList = vehiclePlanLineEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VehicleBillEntity that = (VehicleBillEntity) o;

        return new EqualsBuilder()
                .append(getCph(), that.getCph())
                .append(getVehiclePlanLineEntityList(), that.getVehiclePlanLineEntityList())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getCph())
                .append(getVehiclePlanLineEntityList())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "VehicleBillEntity{" +
                "cph='" + cph + '\'' +
                ", vehiclePlanLineEntityList=" + vehiclePlanLineEntityList +
                '}';
    }
}
