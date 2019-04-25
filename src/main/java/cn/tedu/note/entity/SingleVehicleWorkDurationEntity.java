package cn.tedu.note.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Date;

/**
 * 单车工作时长测试类
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/18 9:29
 */
public class SingleVehicleWorkDurationEntity {
    /**
     * 单车工作时长表id主键
     */
    private String id;

    /**
     * 事业部
     */
    private String syb;

    /**
     * 大区
     */
    private String dq;

    /**
     * 是否单人单车
     */
    private String sfdrdc;

    /**
     * 车牌号
     */
    private String cph;

    /**
     * 车型编码
     */
    private String cx;

    /**
     * 上转移里程
     */
    private Integer szylc;

    /**
     * 下转移里程
     */
    private Integer xzylc;

    /**
     * 取件规划里程
     */
    private Integer qjghlc;

    /**
     * 派件规划里程
     */
    private Integer pjghlc;

    /**
     * 下转移吨位
     */
    private Double xzydw;

    /**
     * 上转移吨位
     */
    private Double szydw;

    /**
     * 取件吨位
     */
    private Double qjdw;

    /**
     * 派件吨位
     */
    private Double pjdw;

    /**
     * 取件体积
     */
    private Double qjtj;

    /**
     * 派件体积
     */
    private Double pjtj;


    /**
     * 取件件数
     */
    private Integer qjjs;

    /**
     * 派件件数
     */
    private Integer pjjs;

    /**
     * 统计日期
     */
    private Date tjrq;

    /**
     * 记录时间
     */
    private Date record_date;

    public SingleVehicleWorkDurationEntity(){}

    public SingleVehicleWorkDurationEntity(String id, String syb, String dq, String sfdrdc, String cph, String cx, Integer szylc, Integer xzylc, Integer qjghlc, Integer pjghlc, Double xzydw, Double szydw, Double qjdw, Double pjdw, Double qjtj, Double pjtj, Integer qjjs, Integer pjjs, Date tjrq, Date record_date) {
        this.id = id;
        this.syb = syb;
        this.dq = dq;
        this.sfdrdc = sfdrdc;
        this.cph = cph;
        this.cx = cx;
        this.szylc = szylc;
        this.xzylc = xzylc;
        this.qjghlc = qjghlc;
        this.pjghlc = pjghlc;
        this.xzydw = xzydw;
        this.szydw = szydw;
        this.qjdw = qjdw;
        this.pjdw = pjdw;
        this.qjtj = qjtj;
        this.pjtj = pjtj;
        this.qjjs = qjjs;
        this.pjjs = pjjs;
        this.tjrq = tjrq;
        this.record_date = record_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSyb() {
        return syb;
    }

    public void setSyb(String syb) {
        this.syb = syb;
    }

    public String getDq() {
        return dq;
    }

    public void setDq(String dq) {
        this.dq = dq;
    }

    public String getSfdrdc() {
        return sfdrdc;
    }

    public void setSfdrdc(String sfdrdc) {
        this.sfdrdc = sfdrdc;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public Integer getSzylc() {
        return szylc;
    }

    public void setSzylc(Integer szylc) {
        this.szylc = szylc;
    }

    public Integer getXzylc() {
        return xzylc;
    }

    public void setXzylc(Integer xzylc) {
        this.xzylc = xzylc;
    }

    public Integer getQjghlc() {
        return qjghlc;
    }

    public void setQjghlc(Integer qjghlc) {
        this.qjghlc = qjghlc;
    }

    public Integer getPjghlc() {
        return pjghlc;
    }

    public void setPjghlc(Integer pjghlc) {
        this.pjghlc = pjghlc;
    }

    public Double getXzydw() {
        return xzydw;
    }

    public void setXzydw(Double xzydw) {
        this.xzydw = xzydw;
    }

    public Double getSzydw() {
        return szydw;
    }

    public void setSzydw(Double szydw) {
        this.szydw = szydw;
    }

    public Double getQjdw() {
        return qjdw;
    }

    public void setQjdw(Double qjdw) {
        this.qjdw = qjdw;
    }

    public Double getPjdw() {
        return pjdw;
    }

    public void setPjdw(Double pjdw) {
        this.pjdw = pjdw;
    }

    public Double getQjtj() {
        return qjtj;
    }

    public void setQjtj(Double qjtj) {
        this.qjtj = qjtj;
    }

    public Double getPjtj() {
        return pjtj;
    }

    public void setPjtj(Double pjtj) {
        this.pjtj = pjtj;
    }

    public Integer getQjjs() {
        return qjjs;
    }

    public void setQjjs(Integer qjjs) {
        this.qjjs = qjjs;
    }

    public Integer getPjjs() {
        return pjjs;
    }

    public void setPjjs(Integer pjjs) {
        this.pjjs = pjjs;
    }

    public Date getTjrq() {
        return tjrq;
    }

    public void setTjrq(Date tjrq) {
        this.tjrq = tjrq;
    }

    public Date getRecord_date() {
        return record_date;
    }

    public void setRecord_date(Date record_date) {
        this.record_date = record_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SingleVehicleWorkDurationEntity entity = (SingleVehicleWorkDurationEntity) o;

        return new EqualsBuilder()
                .append(getId(), entity.getId())
                .append(getSyb(), entity.getSyb())
                .append(getDq(), entity.getDq())
                .append(getSfdrdc(), entity.getSfdrdc())
                .append(getCph(), entity.getCph())
                .append(getCx(), entity.getCx())
                .append(getSzylc(), entity.getSzylc())
                .append(getXzylc(), entity.getXzylc())
                .append(getQjghlc(), entity.getQjghlc())
                .append(getPjghlc(), entity.getPjghlc())
                .append(getXzydw(), entity.getXzydw())
                .append(getSzydw(), entity.getSzydw())
                .append(getQjdw(), entity.getQjdw())
                .append(getPjdw(), entity.getPjdw())
                .append(getQjtj(), entity.getQjtj())
                .append(getPjtj(), entity.getPjtj())
                .append(getQjjs(), entity.getQjjs())
                .append(getPjjs(), entity.getPjjs())
                .append(getTjrq(), entity.getTjrq())
                .append(getRecord_date(), entity.getRecord_date())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getSyb())
                .append(getDq())
                .append(getSfdrdc())
                .append(getCph())
                .append(getCx())
                .append(getSzylc())
                .append(getXzylc())
                .append(getQjghlc())
                .append(getPjghlc())
                .append(getXzydw())
                .append(getSzydw())
                .append(getQjdw())
                .append(getPjdw())
                .append(getQjtj())
                .append(getPjtj())
                .append(getQjjs())
                .append(getPjjs())
                .append(getTjrq())
                .append(getRecord_date())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SingleVehicleWorkDurationEntity{" +
                "id='" + id + '\'' +
                ", syb='" + syb + '\'' +
                ", dq='" + dq + '\'' +
                ", sfdrdc='" + sfdrdc + '\'' +
                ", cph='" + cph + '\'' +
                ", cx='" + cx + '\'' +
                ", szylc=" + szylc +
                ", xzylc=" + xzylc +
                ", qjghlc=" + qjghlc +
                ", pjghlc=" + pjghlc +
                ", xzydw=" + xzydw +
                ", szydw=" + szydw +
                ", qjdw=" + qjdw +
                ", pjdw=" + pjdw +
                ", qjtj=" + qjtj +
                ", pjtj=" + pjtj +
                ", qjjs=" + qjjs +
                ", pjjs=" + pjjs +
                ", tjrq=" + tjrq +
                ", record_date=" + record_date +
                '}';
    }
}
