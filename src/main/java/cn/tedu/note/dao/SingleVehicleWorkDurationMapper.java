package cn.tedu.note.dao;

import cn.tedu.note.entity.GoodsPlanLineEntity;
import cn.tedu.note.entity.SingleVehicleWorkDurationEntity;
import cn.tedu.note.entity.TransferPlanLineEntity;
import cn.tedu.note.entity.VehiclePlanLineEntity;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/18 9:35
 */
@Repository
public interface SingleVehicleWorkDurationMapper {

    /**
     * 返回单车工作时长统计基础数据Map集合
     * @return
     */
    @MapKey("cph")
    Map<String,SingleVehicleWorkDurationEntity> getSingleVehicleWorkDurationMap();

    /**
     * 返回单车工作时长统计基础数据Map集合
     * @return
     */
    List<SingleVehicleWorkDurationEntity> getSingleVehicleWorkDurationList();

    /**
     * 批量插入单车工作时长数据
     * @param list
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void batchInsertSingleVehicleWorkDurationInfo(List<SingleVehicleWorkDurationEntity> list);


    /**
     * 返回当前时间前一天上下转移线路数据list集合
     * @return
     */
    List<TransferPlanLineEntity> getTransferPlanLineList();

    /**
     * 返回当前时间前一天提送货线路数据list集合
     * @return
     */
    List<GoodsPlanLineEntity> getGoodsPlanLineList();

    /**
     * 返回当前时间前一天每个车辆送货线路数据list集合
     * @return
     */
    @MapKey("cph")
    Map<String,VehiclePlanLineEntity> getDeliverGoodsPlanLineMap();

    /**
     * 返回当前时间前一天每个车辆提货线路数据list集合
     * @return
     */
    @MapKey("cph")
    Map<String,VehiclePlanLineEntity> getPickupGoodsPlanLineMap();
}
