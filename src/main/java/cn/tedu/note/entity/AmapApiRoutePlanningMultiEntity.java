package cn.tedu.note.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.concurrent.FutureTask;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/11 17:17
 */
public class AmapApiRoutePlanningMultiEntity implements Serializable {

    private Integer amapResultDistance;

    private FutureTask<Integer> futureDistance;

    public AmapApiRoutePlanningMultiEntity(Integer amapResultDistance) {
        this.amapResultDistance = amapResultDistance;
        this.futureDistance = null;
    }

    public AmapApiRoutePlanningMultiEntity(FutureTask<Integer> futureDistance) {
        this.futureDistance = futureDistance;
        this.amapResultDistance = null;
    }

    public Integer getAmapResultDistance() {
        return amapResultDistance;
    }

    public void setAmapResultDistance(Integer amapResultDistance) {
        this.amapResultDistance = amapResultDistance;
    }

    public FutureTask<Integer> getFutureDistance() {
        return futureDistance;
    }

    public void setFutureDistance(FutureTask<Integer> futureDistance) {
        this.futureDistance = futureDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AmapApiRoutePlanningMultiEntity that = (AmapApiRoutePlanningMultiEntity) o;

        return new EqualsBuilder()
                .append(getAmapResultDistance(), that.getAmapResultDistance())
                .append(getFutureDistance(), that.getFutureDistance())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getAmapResultDistance())
                .append(getFutureDistance())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "AmapApiRoutePlanningResultEntity{" +
                "amapResultDistance=" + amapResultDistance +
                ", futureDistance=" + futureDistance +
                '}';
    }
}
