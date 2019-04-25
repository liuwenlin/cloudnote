package cn.tedu.note.util;

import cn.tedu.note.entity.GoodsPlanLineEntity;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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

    /**
     * implemented by spring
     * @param str
     * @return
     */
    public static String md5BySpring(String str){
        return DigestUtils.md5Hex(str);
    }

    /**
     * 格式化json
     *
     * @param jsonStr
     * @return
     * @author liuwenlin
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr))
            return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        boolean isInQuotationMarks = false;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '"':
                    if (last != '\\'){
                        isInQuotationMarks = !isInQuotationMarks;
                    }
                    sb.append(current);
                    break;
                case '{':
                case '[':
                    sb.append(current);
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent++;
                        addIndentBlank(sb, indent);
                    }
                    break;
                case '}':
                case ']':
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent--;
                        addIndentBlank(sb, indent);
                    }
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     * @author liuwenlin
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    public static boolean isZeroAfterRemaind(int a, int b){
        int c = a&(b-1);
        if(c==0){
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        String[] strArray = {"city=上海市","address=上海市闵行区华翔路2239号","output=JSON"};
        String resultString = sortStringArray(strArray)+"2b1b8768a5d61cfa19b2454f847cbf1f";
        System.out.println(resultString);
        System.out.println("-------------");
        System.out.println(md5ByNative(resultString));
        System.out.println(md5BySpring(resultString));
        System.out.println(isZeroAfterRemaind(47,16));

        GoodsPlanLineEntity entity = new GoodsPlanLineEntity();

        entity.setCph("沪A88888");

        List<GoodsPlanLineEntity> list = new LinkedList<GoodsPlanLineEntity>();

        list.add(entity);
        System.out.println(entity);

        entity.setCph("沪666666");
        System.out.println(entity);

    }
}
