package cn.tedu.note.service;

import java.util.concurrent.ExecutionException;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/19 15:27
 */
public interface IHoauScreenGeocodeService {

    /**
     * 大屏基础数据获取经纬度服务
     * @throws InterruptedException
     * @throws ExecutionException
     */
    void computeHoauScreenGeocode() throws InterruptedException, ExecutionException;
}
