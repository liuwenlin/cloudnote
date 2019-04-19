package cn.tedu.note.test;

import cn.tedu.note.dao.SingleVehicleWorkDurationMapper;
import cn.tedu.note.entity.GoodsPlanLineEntity;
import cn.tedu.note.entity.SingleVehicleWorkDurationEntity;
import cn.tedu.note.entity.TransferPlanLineEntity;
import cn.tedu.note.service.impl.AmapService;
import cn.tedu.note.service.impl.SingleVehicleWorkDurationService;
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

    SingleVehicleWorkDurationMapper dao;

    SingleVehicleWorkDurationService singleVehicleWorkDurationService;

    AmapService amapService;

    @Before
    public void init(){
        dao = ctx.getBean("singleVehicleWorkDurationMapper",SingleVehicleWorkDurationMapper.class);
        amapService = ctx.getBean("amapService",AmapService.class);
        singleVehicleWorkDurationService = ctx.getBean("singleVehicleWorkDurationService",SingleVehicleWorkDurationService.class);
    }

    @Test
    public void testSingleVehicleWorkDurationMapper(){
        List<Map<String,Object>> list = dao.getSingleVehicleWorkDurationMap();
        for(Map<String,Object> map : list){
            System.out.println(map);
        }
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
    public void testSingleVehicleWorkDurationStatisticInfo() throws ExecutionException, InterruptedException {
        singleVehicleWorkDurationService.computeSingleVehicleWorkDuration();
    }

}
