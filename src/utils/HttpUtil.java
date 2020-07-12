package utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import gamerobot.GameRobot;
import static gamerobot.GameRobot.executorService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 *
 * @author tank
 */
public class HttpUtil {

    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }

    public static String httpRequest(String requestUrl) throws Exception {

        trustAllHttpsCertificates();

        StringBuffer buffer = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        HttpURLConnection httpUrlConn = null;

        try {
            // 建立get请求
            URL url = new URL(requestUrl);
            httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.setRequestProperty("accept", "*/*");
            httpUrlConn.setRequestProperty("connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接

            // 获取输入流  
            inputStream = httpUrlConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            // 从输入流读取结果
            buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

        } catch (Exception e) {

        } finally {
            // 释放资源
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {

                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {

                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
            if (httpUrlConn != null) {
                httpUrlConn.disconnect();
            }
        }
        return buffer.toString() + " ";
    }

    public static String sendPost(String url) {
        String result = "";
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setInstanceFollowRedirects(true);

            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //post设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);//这一行必须要写，因为要将参数放在outputStream中输出
            //PrintWriter out = new PrintWriter(conn.getOutputStream());
            //out.print(param);
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            //out.write(param);
//            out.print(param);  //写入缓存
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));// 发送请求参数
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Document getDocument(String id) throws IOException, InterruptedException, Exception {
        /*String url="https://www.marklines.com/cn/vehicle_sales/search_country/search/?searchID=587200";
         Connection connect = Jsoup.connect(url).userAgent("")
                 .header("Cookie", "PLAY_LANG=cn; _plh=b9289d0a863a8fc9c79fb938f15372f7731d13fb; PLATFORM_SESSION=39034d07000717c664134556ad39869771aabc04-_ldi=520275&_lsh=8cf91cdbcbbb255adff5cba6061f561b642f5157&csrfToken=209f20c8473bc0518413c226f898ff79cd69c3ff-1539926671235-b853a6a63c77dd8fcc364a58&_lpt=%2Fcn%2Fvehicle_sales%2Fsearch&_lsi=1646321; _ga=GA1.2.2146952143.1539926675; _gid=GA1.2.1032787565.1539926675; _plh_notime=8cf91cdbcbbb255adff5cba6061f561b642f5157")
                 .timeout(360000000);
         Document document = connect.get();*/
        WebClient wc = new WebClient(BrowserVersion.FIREFOX_45);
        //是否使用不安全的SSL
        wc.getOptions().setUseInsecureSSL(true);
        //启用JS解释器，默认为true
        wc.getOptions().setJavaScriptEnabled(true);
        //禁用CSS
        wc.getOptions().setCssEnabled(false);
        //js运行错误时，是否抛出异常
        wc.getOptions().setThrowExceptionOnScriptError(false);
        //状态码错误时，是否抛出异常
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        //是否允许使用ActiveX
        wc.getOptions().setActiveXNative(false);
        //等待js时间
        wc.waitForBackgroundJavaScript(600 * 1000);
        //设置Ajax异步处理控制器即启用Ajax支持
        wc.setAjaxController(new NicelyResynchronizingAjaxController());
        //设置超时时间
        wc.getOptions().setTimeout(120000);
        //不跟踪抓取
        wc.getOptions().setDoNotTrackEnabled(false);
        WebRequest request = new WebRequest(new URL("https://www.pixivdl.net/artworks/" + id));
        request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0");
        request.setAdditionalHeader("Cookie", "PLAY_LANG=cn; _plh=b9289d0a863a8fc9c79fb938f15372f7731d13fb; PLATFORM_SESSION=39034d07000717c664134556ad39869771aabc04-_ldi=520275&_lsh=8cf91cdbcbbb255adff5cba6061f561b642f5157&csrfToken=209f20c8473bc0518413c226f898ff79cd69c3ff-1539926671235-b853a6a63c77dd8fcc364a58&_lpt=%2Fcn%2Fvehicle_sales%2Fsearch&_lsi=1646321; _ga=GA1.2.2146952143.1539926675; _gid=GA1.2.1032787565.1539926675; _plh_notime=8cf91cdbcbbb255adff5cba6061f561b642f5157");
        try {
            //模拟浏览器打开一个目标网址
            HtmlPage htmlPage;
            htmlPage = wc.getPage(request);
            //为了获取js执行的数据 线程开始沉睡等待
            Thread.sleep(1000);//这个线程的等待 因为js加载需要时间的
            //以xml形式获取响应文本
            String xml = htmlPage.asXml();
            //并转为Document对象return
            return Jsoup.parse(xml);
            //System.out.println(xml.contains("结果.xls"));//false
        } catch (FailingHttpStatusCodeException e) {

        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }
        return null;
    }

    public static void download(String urlString, String filename, String savePath) throws Exception {
        // 构造URL  
        URL url = new URL(urlString);
        // 打开连接  
        URLConnection con = url.openConnection();
        //设置请求超时为5s  
        con.setConnectTimeout(60 * 1000);
        // 输入流  
        InputStream is = con.getInputStream();

        // 1K的数据缓冲  
        byte[] bs = new byte[1024];
        // 读取到的数据长度  
        int len;
        // 输出的文件流  
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
        // 新的图片文件名 = 编号 +"."图片扩展名
        OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename + ".jpg");
        // 开始读取  
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接  
        os.close();
        is.close();
    }

    public static void SaveImage(int z) throws Exception {
        File[] fs = new File(Initization.ReturnPath() + "/Images").listFiles();	//遍历path下的文件和目录，放在File数组中
        String data1[] = HttpUtil.httpRequest("https://api.imjad.cn/pixiv/v2/?type=search&word=事後&page=" + z).split("\"id\":");
        ArrayList<String> images = new ArrayList();
        if (fs.length != 0) {
            for (File s : fs) {
                //遍历File[]数组
                if (!s.isDirectory()) {
                    images.add(s.getName().replaceAll(".jpg", ""));
                }
            }
        }
        for (int i = 1; i < data1.length; i++) {
            data1[i] = data1[i].split(",\"")[0];
            String id = data1[i];
            if (!images.contains(id)) {
                System.out.println("开始下载图片" + id);
                long thetime = new Date().getTime();
                Document document = getDocument(data1[i]);
                executorService.execute(() -> {
                    try {
                        if (document != null && document.getElementById("resource") != null && document.getElementById("resource").toString().contains("http")) {
                            System.out.println(document.getElementById("resource").toString());
                            String[] theurls = document.getElementById("resource").toString().split("<img src=\"")[1].split("\">");
                            for (int m = 1; m < theurls.length; m++) {
                                System.out.println(theurls[m]);
                                HttpUtil.download(theurls[m], id, Initization.ReturnPath() + "/Images");
                            }

                            System.out.println("已下载" + id + "用时" + String.valueOf((new Date().getTime() - thetime) / 1000) + "秒");
                        }

                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameRobot.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(GameRobot.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
            }

        }
    }
}
