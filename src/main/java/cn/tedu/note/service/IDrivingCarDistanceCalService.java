package cn.tedu.note.service;

import java.util.concurrent.ExecutionException;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/4/21 23:39
 */
public interface IDrivingCarDistanceCalService {

    void calDistance() throws InterruptedException, ExecutionException;

    public void calDistanceByExcel(String filePath) throws InterruptedException, ExecutionException;
}
