package cn.tedu.note.test;

import cn.tedu.note.service.IDrivingCarDistanceCalService;
import cn.tedu.note.service.impl.DrivingCarDistanceCalService;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/4/22 1:04
 */
public class DrivingCarDistanceTest extends BaseTest {

    IDrivingCarDistanceCalService drivingCarDistanceCalService;

    @Before
    public void init(){
        drivingCarDistanceCalService
                = ctx.getBean("drivingCarDistanceCalService", DrivingCarDistanceCalService.class);
    }

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        String filePath = "\\D:\\report.xls";
        drivingCarDistanceCalService.calDistanceByExcel(filePath);
    }

}
