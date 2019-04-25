package cn.tedu.note.service;

import cn.tedu.note.entity.GoodsPlanLineEntity;
import cn.tedu.note.entity.TransferPlanLineEntity;
import cn.tedu.note.entity.VehiclePlanLineEntity;

import java.util.concurrent.ExecutionException;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/11 15:29
 */
public interface IAmapService {

    VehiclePlanLineEntity captureGeocoding(VehiclePlanLineEntity vehiclePlanLineEntity) throws InterruptedException, ExecutionException;

    VehiclePlanLineEntity captureRoutePlanning(String startGeocode, VehiclePlanLineEntity vehiclePlanLineEntity) throws InterruptedException, ExecutionException;

    TransferPlanLineEntity captureTransferGeocoding(TransferPlanLineEntity transferPlanLineEntity) throws InterruptedException, ExecutionException;

    TransferPlanLineEntity captureTransferRoutePlanning(TransferPlanLineEntity transferPlanLineEntity) throws InterruptedException, ExecutionException;

}
