package utils;

import goods.Good;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Administrator
 */
public class Utils {

    public static File GetImage(String theurl) throws Exception {
        //new一个URL对象  
        URL url = new URL(theurl);
        //打开链接  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方式为"GET"  
        conn.setRequestMethod("GET");
        //超时响应时间为5秒  
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据  
        InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
        byte[] data = readInputStream(inStream);
        //new一个文件对象用来保存图片，默认保存当前工程根目录  
        File imageFile = new File("BeautyGirl.jpg");
        //创建输出流  
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据  
        outStream.write(data);
        //关闭输出流  
        outStream.close();
        
        return imageFile;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来  
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);
        }
        //关闭输入流  
        inStream.close();
        //把outStream里的数据写入内存  
        return outStream.toByteArray();
    }

    public static HashMap<String, Good> sortGoodHashMap(HashMap<String, Good> map
    ) {
        //從HashMap中恢復entry集合，得到全部的鍵值對集合
        Set<Map.Entry<String, Good>> entey
                = map
                        .entrySet();
        //將Set集合轉為List集合，為了實用工具類的排序方法
        List<Map.Entry<String, Good>> list
                = new ArrayList<>(entey
                );

        //使用Collections工具類對list進行排序
        Collections
                .sort(list,
                        new Comparator<Map.Entry<String, Good>>() {
                    @Override
                    public int compare(Map.Entry<String, Good> o1,
                            Map.Entry<String, Good> o2
                    ) {
                        //按照age倒敘排列
                        return o1
                                .getValue().getName().compareTo(o2
                                        .getValue().getName());

                    }
                });
        //創建一個HashMap的子類LinkedHashMap集合
        LinkedHashMap<String, Good> linkedHashMap
                = new LinkedHashMap<String, Good>();
        //將list中的數據存入LinkedHashMap中

        for (Map.Entry<String, Good> entry
                : list) {
            linkedHashMap
                    .put(entry
                            .getKey(), entry
                                    .getValue());

        }
        return linkedHashMap;

    }
    
}
