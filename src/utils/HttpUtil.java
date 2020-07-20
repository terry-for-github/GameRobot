package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import static utils.Initization.ReturnPath;


/**
 * 网络工具
 * @author tank
 */
public class HttpUtil {
    private HttpUtil() {}
    /**
     * 相信所有网络证书（访问不安全链接）
     */
    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
        sc.init(null, new javax.net.ssl.TrustManager[]{new miTM()}, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    /**
     * 用于trustAllHttpsCertificates使用
     */
    private static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }
        
        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)throws java.security.cert.CertificateException {
        }

        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)throws java.security.cert.CertificateException {
        }
    }

    /**
     * 给定一个链接，返回响应后的字符串
     * @param requestUrl 链接
     * @return 响应的字符串
     * @throws java.lang.Exception
     */
    public static String httpRequest(String requestUrl) throws Exception{
        trustAllHttpsCertificates();

        // 建立get请求
        HttpURLConnection conn = (HttpURLConnection)new URL(requestUrl).openConnection();
        conn.setDoInput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
        
        // 从输入流读取结果
        String result = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        conn.disconnect();
        return result + " ";
    }

    /**
     * 给定链接，向网站发送请求
     * @param url 链接
     * @return 返回给出的字符串
     * @throws java.net.MalformedURLException
     */
    public static String sendPost(String url) throws MalformedURLException, IOException {
        HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setInstanceFollowRedirects(false);
        //post设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        out.flush();

        String result = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));// 发送请求参数
        String line;
        while ((line = in.readLine()) != null) {
            result += "\n" + line;
        }
        return result;
    }

    /**
     * 保存关键词为keyWord第page页的p站图片
     * @param keyWord 关键词
     * @param page 页数
     * @throws Exception 
     */
    public static void saveImage(String keyWord, int page) throws Exception {
        // 储存图片的文件夹
        File imageDir = new File(ReturnPath() + "/PixivImages");
        if(!imageDir.exists()){
            imageDir.mkdir();
        }
        // 储存被禁止下载的ID文件
        File forbiddenIdFile = new File(imageDir.getPath() + "/Forbidden.txt");
        if(!forbiddenIdFile.exists()){
            forbiddenIdFile.createNewFile();
        }
        // 储存被禁止下载的ID列表
        ArrayList<String> forbiddenIdList = new ArrayList<>();
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(forbiddenIdFile.getPath())));
            String line;
            while((line = reader.readLine()) != null){
                forbiddenIdList.add(line);
            }
            reader.close();
        }
        int cnt = 0;
        // 创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request;
        CloseableHttpResponse response;
        // 获得id
        request = new HttpGet("https://api.imjad.cn/pixiv/v2/?type=search&word=" + keyWord + "&page=" + page);
        request.addHeader("connection", "close");
        response = httpClient.execute(request);
        ArrayList<String> idList = new ArrayList<>(Arrays.asList(EntityUtils.toString(response.getEntity(), "utf-8").split("\"id\":")));
        idList.remove(0);
        for(int i = 0; i < idList.size(); ++i){
            idList.set(i, idList.get(i).split(",\"")[0]);
        }
        // 去除已经下载和无法下载的id
        File[] downloadedImageList = new File(imageDir.getPath()).listFiles();
        for(File image : downloadedImageList){
            while(idList.remove(image.getName().split("_")[0])){}
        }
        for(String id : forbiddenIdList){
            while(idList.remove(id)){}
        }
        // 对剩下的id进行下载
        for(String id : idList){
            try {
                // 获得下载链接
                request = new HttpGet("https://www.pixivdl.net/api/pixiv/info?zid=" + id);
                request.setHeader("connection", "close");
                response = httpClient.execute(request);
                Thread.sleep(500);
                String imageStr = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println("开始下载图片: " + id);
                // 判断链接有效性
                if(imageStr.split("\": ")[1].charAt(0) == 'f' && imageStr.contains("\"original\"")){
                    // 链接有效，下载图片并保存
                    String[] imageUrls = imageStr.split("\"original\": \"https://i.pximg.net/img-original/img/");
                    for(int i = 1; i < imageUrls.length; ++i){
                        imageUrls[i] = imageUrls[i].split("\"")[0];
                        File file = new File(imageDir.getPath() + "/" + id + "_p" +i + ".png");
                        if(!file.exists()){
                            file.createNewFile();
                        }
                        request = new HttpGet("https://www.pixivdl.net/img-original/img/" + imageUrls[i]);
                        request.setHeader("connection", "close");
                        response = httpClient.execute(request);
                        OutputStream os = new FileOutputStream(file.getPath());
                        response.getEntity().writeTo(os);
                        os.flush();
                        cnt += 1;
                        System.out.println("下载完成: " + id + "_p" + i);
                    }
                }
                else{
                    // 链接无效，写入forbiddenIdFile中
                    System.out.println("作品不存在或受屏蔽限制: " + id);
                    FileWriter fileWriter = new FileWriter(forbiddenIdFile.getAbsoluteFile(), true);
                    fileWriter.write(id + "\n");
                    fileWriter.close();
                }
            } catch (IOException | InterruptedException  ex) {
                Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("下载了" + cnt + "张图片");
    }
}
