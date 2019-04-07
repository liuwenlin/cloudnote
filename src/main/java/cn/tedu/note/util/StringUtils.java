package cn.tedu.note.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/7 20:49
 */
public class StringUtils {

    /**
     *
     * @param strArray
     * @return return sorted string array.
     */
    public static String sortStringArray(String... strArray){
        List<String> list = new ArrayList<String>();
        for(String temp:strArray){
            list.add(temp);
        }
        Collections.sort(list);
        StringBuffer strBuff = new StringBuffer();
        for(String result:list){
            strBuff.append(result);
        }
        return strBuff.toString();
    }

    /**
     * implemented by native
     * @param str
     * @return
     */
    public static String md5ByNative(String str){
        try {
            MessageDigest mdStr = MessageDigest.getInstance("MD5");
            mdStr.update(str.getBytes("UTF8"));
            byte[] bts = mdStr.digest();
            StringBuffer rsBuff = new StringBuffer();
            for(int i=0; i<bts.length; i++){
                rsBuff.append(Integer.toHexString((0x000000FF & bts[i]) | 0xFFFFFF00).substring(6));
            }
            return rsBuff.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String md5BySpring(String str){
        return DigestUtils.md5Hex(str);
    }

    public static void main(String[] args){
        String[] strArray = {"city=上海市","address=上海市闵行区华翔路2239号","output=JSON"};
        String resultString = sortStringArray(strArray)+"2b1b8768a5d61cfa19b2454f847cbf1f";
        System.out.println(resultString);
        System.out.println("-------------");
        System.out.println(md5ByNative(resultString));
        System.out.println(md5BySpring(resultString));
    }
}
