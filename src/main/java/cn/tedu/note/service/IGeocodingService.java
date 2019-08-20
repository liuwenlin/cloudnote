package cn.tedu.note.service;

import java.util.concurrent.ExecutionException;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/8/20 15:25
 */
public interface IGeocodingService {
    void getGeocoding() throws InterruptedException, ExecutionException;
}
