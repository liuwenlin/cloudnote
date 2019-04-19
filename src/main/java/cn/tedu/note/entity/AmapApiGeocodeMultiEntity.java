package cn.tedu.note.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.concurrent.FutureTask;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/13 23:40
 */
public class AmapApiGeocodeMultiEntity implements Serializable {

    private String geocode;

    private FutureTask<String> futureGeocode;

    public AmapApiGeocodeMultiEntity(String geocode){
        this.geocode = geocode;
        this.futureGeocode = null;
    }

    public AmapApiGeocodeMultiEntity(FutureTask<String> futureGeocode){
        this.geocode = null;
        this.futureGeocode = futureGeocode;
    }

    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }

    public FutureTask<String> getFutureGeocode() {
        return futureGeocode;
    }

    public void setFutureGeocode(FutureTask<String> futureGeocode) {
        this.futureGeocode = futureGeocode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AmapApiGeocodeMultiEntity that = (AmapApiGeocodeMultiEntity) o;

        return new EqualsBuilder()
                .append(getGeocode(), that.getGeocode())
                .append(getFutureGeocode(), that.getFutureGeocode())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getGeocode())
                .append(getFutureGeocode())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "AmapApiGeocodeMultiEntity{" +
                "geocode='" + geocode + '\'' +
                ", futureGeocode=" + futureGeocode +
                '}';
    }
}
