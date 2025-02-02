package cn.tedu.note.test;

import cn.tedu.note.dao.HoauScreenGeocodeDao;
import cn.tedu.note.dao.SingleVehicleWorkDurationMapper;
import cn.tedu.note.entity.*;
import cn.tedu.note.service.IAmapService;
import cn.tedu.note.service.IGeocodingService;
import cn.tedu.note.service.IHoauScreenGeocodeService;
import cn.tedu.note.service.impl.AmapService;
import cn.tedu.note.service.impl.GeocodingServiceImpl;
import cn.tedu.note.service.impl.HoauScreenGeocodeService;
import cn.tedu.note.service.impl.SingleVehicleWorkDurationService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/18 9:41
 */
public class SingleVehicleWorkDurationTest extends BaseTest {

    private static Logger LOG = Logger.getLogger(SingleVehicleWorkDurationTest.class);

    SingleVehicleWorkDurationMapper dao;

    SingleVehicleWorkDurationService singleVehicleWorkDurationService;

    IAmapService amapService;

    IGeocodingService geocodingService;

    HoauScreenGeocodeDao hoauScreenGeocodeDao;

    IHoauScreenGeocodeService hoauScreenGeocodeService;

    @Before
    public void init(){
//        dao = ctx.getBean("singleVehicleWorkDurationMapper",SingleVehicleWorkDurationMapper.class);
//        amapService = ctx.getBean("amapService",AmapService.class);
//        singleVehicleWorkDurationService = ctx.getBean("singleVehicleWorkDurationService",SingleVehicleWorkDurationService.class);
//        geocodingService = ctx.getBean("geocodingServiceImpl", GeocodingServiceImpl.class);
        hoauScreenGeocodeDao = ctx.getBean("hoauScreenGeocodeDao",HoauScreenGeocodeDao.class);
        hoauScreenGeocodeService = ctx.getBean("hoauScreenGeocodeService", HoauScreenGeocodeService.class);
    }

    @Test
    public void testHoauScreenGeocode(){
        LOG.info("------测试开始-------");
        try {
            hoauScreenGeocodeService.computeHoauScreenGeocode();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        LOG.info("------测试结束------");
    }

    @Test
    public void testSingleVehicleWorkDurationMap(){
        Map<String,SingleVehicleWorkDurationEntity> list = dao.getSingleVehicleWorkDurationMap();
        for(Object entity : list.values()){
            System.out.println(entity);
        }
    }


    @Test
    public void testSingleVehicleWorkDurationList(){

    }

    @Test
    public void testGeocoding(){
        LOG.info("---测试开始---");
        long beginTime = System.currentTimeMillis();
        try {
            geocodingService.getGeocoding();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        LOG.info("---测试结束---");
        System.out.println("解析总耗时: " + (System.currentTimeMillis() - beginTime)/1000 + "秒");
    }

    @Test
    public void testGetTransferPlanLineList(){
        List<TransferPlanLineEntity> list = dao.getTransferPlanLineList();
        for(TransferPlanLineEntity transferPlan : list){
            System.out.println(transferPlan);
        }
    }

    @Test
    public void testGetGoodsPlanLineList(){
        List<GoodsPlanLineEntity> list = dao.getGoodsPlanLineList();
        for(GoodsPlanLineEntity goodsPlan : list){
            System.out.println(goodsPlan);
        }
    }

    @Test
    public void testGetSingleVehicleWorkDurationList(){
        List<SingleVehicleWorkDurationEntity> list = dao.getSingleVehicleWorkDurationList();
        for(SingleVehicleWorkDurationEntity entity : list){
            System.out.println(entity);
        }
    }

    @Test
    public void testDeliverGoodsPlanMap(){
        LOG.info("---测试开始---");
        Map<String, VehicleBillEntity> list = dao.getDeliverVehicleGoodsPlanLineMap();
        for(VehicleBillEntity vehicleBillEntity : list.values()){
            System.out.println(vehicleBillEntity);
//            for(VehiclePlanLineEntity entity : vehicleBillEntity.getVehiclePlanLineEntityList()){
//                System.out.println(entity);
//            }
        }
        LOG.info("---测试结束---");
    }

    @Test
    public void testPickupGoodsPlanMap(){
        LOG.info("---测试开始---");
        Map<String,VehiclePlanLineEntity> list = dao.getPickupGoodsPlanLineMap();

        for(String cph : list.keySet()){
            System.out.println("车牌号: " + cph + " "+ list.get(cph));
        }

//        for(VehiclePlanLineEntity entity : list.values()){
//            System.out.println(entity);
//        }
        LOG.info("---测试结束---");
    }


    @Test
    public void testSingleVehicleWorkDurationStatisticInfo() throws ExecutionException, InterruptedException {
        LOG.info("---测试开始---");
        singleVehicleWorkDurationService.computeSingleVehicleWorkDuration();
        LOG.info("---测试结束---");
    }

}
