package cn.tedu.note.util;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * p6spy打印日志输出格式修改
 * 1.只打印最终执行的sql.
 * 2.sql换到下一行
 * 3.结尾处增加分号,以标示sql结尾
 * @author liuwenlin
 * @version v1.0
 * @date 2019/8/21 9:27
 */
public class P6SpyLogger implements MessageFormattingStrategy {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");


    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        if (!sql.trim().equals("")) {
            return format.format(new Date()) + " | took " + elapsed + "ms | "
                    + category + " | connection " + connectionId + "\n " + sql + ";";
        }
        return "";
    }
}
