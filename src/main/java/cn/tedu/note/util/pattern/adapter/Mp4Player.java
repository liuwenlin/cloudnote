package cn.tedu.note.util.pattern.adapter;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/3 13:02
 */
public class Mp4Player implements AdvancedMediaPlayer{

    @Override
    public void playVlc(String fileName) {
        //什么也不做
    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file. Name: "+ fileName);
    }
}
