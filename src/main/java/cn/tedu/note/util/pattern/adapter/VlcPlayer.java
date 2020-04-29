package cn.tedu.note.util.pattern.adapter;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/3 13:02
 */
public class VlcPlayer implements AdvancedMediaPlayer{
    @Override
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file. Name: "+ fileName);
    }

    @Override
    public void playMp4(String fileName) {
        //什么也不做
    }
}