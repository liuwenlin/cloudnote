package cn.tedu.note.service;

import java.util.concurrent.ExecutionException;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/12 13:16
 */
public interface ISingleVehicleWorkDurationService {

    /**
     * 计算单车工作时长
     * @throws InterruptedException
     * @throws ExecutionException
     */
    void computeSingleVehicleWorkDuration() throws InterruptedException, ExecutionException;

}
