package utils;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import map.Chunk;
import map.ChunkManager;
import map.forestboime.HillChunk;
import map.forestboime.OceanChunk;
import map.forestboime.PlainChunk;
import map.forestboime.RiverChunk;

/**
 *
 * @author Administrator
 */
public class ImageUtils {

    /**
     * @param fileUrl 文件绝对路径或相对路径
     * @return 读取到的缓存图像
     * @throws IOException 路径错误或者不存在该文件时抛出IO异常
     */
    public static BufferedImage getBufferedImage(String fileUrl) throws IOException {
        return ImageIO.read(new File(fileUrl));
    }

    /**
     * 远程图片转BufferedImage
     *
     * @param destUrl 远程图片地址
     * @return
     * @throws java.net.MalformedURLException
     * @throws java.io.IOException
     */
    public static BufferedImage getBufferedImageDestUrl(String destUrl) throws MalformedURLException, IOException {
        BufferedImage image = null;
        HttpURLConnection conn = (HttpURLConnection) new URL(destUrl).openConnection();
        if (conn.getResponseCode() == 200) {
            image = ImageIO.read(conn.getInputStream());
            return image;
        }
        conn.disconnect();
        return image;
    }

    /**
     *
     * @param buffImg
     * @param savePath
     * @throws IOException
     */
    public static void generateSaveFile(BufferedImage buffImg, String savePath) throws IOException {
        int temp = savePath.lastIndexOf(".") + 1;
        File outFile = new File(savePath);
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        ImageIO.write(buffImg, savePath.substring(temp), outFile);
        System.out.println("ImageIO write...");
    }

    /**
     * 把waterImg以alpha透明度画在buffImg的(x, y)的位置上
     *
     * @param buffImg 底图
     * @param waterImg 覆盖的图
     * @param x x位置
     * @param y y位置
     * @param alpha 透明度
     * @return 覆盖后的图片
     * @throws IOException
     */
    public static BufferedImage overlyingImage(BufferedImage buffImg, BufferedImage waterImg, int x, int y, float alpha) throws IOException {

        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = buffImg.createGraphics();
//        g2d.setStroke();
        int waterImgWidth = waterImg.getWidth();// 获取层图的宽度
        int waterImgHeight = waterImg.getHeight();// 获取层图的高度
        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 绘制
        g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
        g2d.dispose();// 释放图形上下文使用的系统资源
        return buffImg;
    }

    /**
     * 图片覆盖（覆盖图压缩到width*height大小，覆盖到底图上）
     *
     * @param baseBufferedImage 底图
     * @param coverBufferedImage 覆盖图
     * @param x 起始x轴
     * @param y 起始y轴
     * @param width 覆盖宽度
     * @param height 覆盖长度度
     * @return
     * @throws Exception
     */
    public static BufferedImage coverImage(BufferedImage baseBufferedImage, BufferedImage coverBufferedImage, int x, int y, int width, int height) throws Exception {

        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = baseBufferedImage.createGraphics();
        // 绘制
        g2d.drawImage(coverBufferedImage, x, y, width, height, null);
        g2d.dispose();// 释放图形上下文使用的系统资源

        return baseBufferedImage;
    }

    /**
     * 待合并的两张图必须满足这样的前提，如果水平方向合并，则高度必须相等；如果是垂直方向合并，宽度必须相等。
     * mergeImage方法不做判断，自己判断。
     *
     * @param img1 待合并的第一张图
     * @param img2 带合并的第二张图
     * @param isHorizontal 为true时表示水平方向合并，为false时表示垂直方向合并
     * @return 返回合并后的BufferedImage对象
     * @throws IOException
     */
    public static BufferedImage mergeImage(BufferedImage img1, BufferedImage img2, boolean isHorizontal) throws IOException {

        int w1 = img1.getWidth();
        int h1 = img1.getHeight();
        int w2 = img2.getWidth();
        int h2 = img2.getHeight();

        // 从图片中读取RGB
        int[] ImageArrayOne = new int[w1 * h1];

        ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中

        int[] ImageArrayTwo = new int[w2 * h2];

        ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);

        // 生成新图片
        BufferedImage DestImage = null;
        if (isHorizontal) { // 水平方向合并
            DestImage = new BufferedImage(w1 + w2, h1, BufferedImage.TYPE_INT_RGB);
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            DestImage.setRGB(w1, 0, w2, h2, ImageArrayTwo, 0, w2);
        } else { // 垂直方向合并
            DestImage = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            DestImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2); // 设置下半部分的RGB
        }
        return DestImage;
    }

    /**
     * Java 测试图片叠加方法
     */
    public static void overlyingImageTest() throws IOException {

        String sourceFilePath = "C:\\Users\\Administrator\\Desktop\\Test\\1.jpg";
        String waterFilePath = "C:\\Users\\Administrator\\Desktop\\Test\\2.jpg";
        String saveFilePath = "C:\\Users\\Administrator\\Desktop\\Test\\overlyingImageNew.jpg";
        BufferedImage bufferImage1 = getBufferedImage(sourceFilePath);
        BufferedImage bufferImage2 = getBufferedImage(waterFilePath);

        // 构建叠加层
        BufferedImage buffImg = overlyingImage(bufferImage1, bufferImage2, 0, 0, 1.0f);
        // 输出水印图片
        generateSaveFile(buffImg, saveFilePath);
    }

    /**
     * Java 测试图片合并方法
     */
    public static void imageMargeTest() throws IOException {
        // 调用mergeImage方法获得合并后的图像
        BufferedImage destImg = null;
        System.out.println("下面是垂直合并的情况：");
        String saveFilePath = "C:\\Users\\Administrator\\Desktop\\Test\\1.jpg";
        String divingPath = "C:\\Users\\Administrator\\Desktop\\Test\\2.jpg";
        String margeImagePath = "C:\\Users\\Administrator\\Desktop\\Test\\overlyingImageNew.jpg";
        BufferedImage bi1 = getBufferedImage(saveFilePath);
        BufferedImage bi2 = getBufferedImage(divingPath);
        // 调用mergeImage方法获得合并后的图像
        destImg = mergeImage(bi1, bi2, false);
        // 保存图像
        generateSaveFile(destImg, margeImagePath);
        System.out.println("垂直合并完毕!");
    }

    /**
     * 画线
     *
     * @param x x位置
     * @param y y位置
     * @param bgcolor 背景颜色
     * @param pencolor 画笔颜色
     * @param length 长度
     * @param width 线的宽度
     * @return 画线后的图片
     * @throws FileNotFoundException
     */
    public static BufferedImage createLine(int x, int y, Color bgcolor, Color pencolor, int length, int width) throws FileNotFoundException {

        ChunkManager chunkManager = new ChunkManager();

        // 得到图片缓冲区
        int imageType = BufferedImage.TYPE_INT_BGR;
        BufferedImage myImage = new BufferedImage(length, width, imageType);

        // 得到画笔
        Graphics2D pen = (Graphics2D) myImage.getGraphics();

        // 设置笔的颜色,即背景色
        pen.setColor(bgcolor);

        // 画出一个矩形
        //图片长宽
        pen.fillRect(0, 0, length, width);

        // 字的颜色 和 背景的颜色 要不同的
        pen.setColor(pencolor);

        // 划线
        // 点动成线，线动成面，面动成体
        // 两点确定一条直线
        int xStart = 0;
        int yStart = 0;
        int xEnd = length;
        int yEnd = width;

        // 设置线的宽度
        float lineWidth = 0.1F;

        //构造
        Chunk[][] map = chunkManager.randomFillMap(x, y);

        //平滑
        for (int i = 0; i < 4; i++) {
            chunkManager.smoothMap(map);
        }
        //去除过小的湖泊
        chunkManager.deleteTooMuchLake(map);
        //判断河流
//        chunkManager.getRiver(map);

        //制造山丘
        chunkManager.getHill(map);

        //        制造沙滩
        chunkManager.createBeach(map);

        chunkManager.getMountain(map);
        //画出海洋大陆沙滩
        pen.setStroke(new BasicStroke(length / x));
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] instanceof OceanChunk) {
                    pen.setColor(Color.blue);
                    pen.drawLine(xStart + i * (xEnd / x) + (xEnd / x) / 2, yStart + j * (yEnd / y) + (yEnd / y) / 2, xStart + i * (xEnd / x) + (xEnd / x) / 2, yStart + j * (yEnd / y) + (yEnd / y) / 2);

                } else if (map[i][j] instanceof PlainChunk) {
                    pen.setColor(Color.GRAY);
                    pen.drawLine(xStart + i * (xEnd / x) + (xEnd / x) / 2, yStart + j * (yEnd / y) + (yEnd / y) / 2, xStart + i * (xEnd / x) + (xEnd / x) / 2, yStart + j * (yEnd / y) + (yEnd / y) / 2);
                } else if (map[i][j] instanceof RiverChunk) {
                    pen.setColor(Color.YELLOW);
                    pen.drawLine(xStart + i * (xEnd / x) + (xEnd / x) / 2, yStart + j * (yEnd / y) + (yEnd / y) / 2, xStart + i * (xEnd / x) + (xEnd / x) / 2, yStart + j * (yEnd / y) + (yEnd / y) / 2);
                } else if (map[i][j] instanceof HillChunk) {
                    pen.setColor(Color.ORANGE);
                    pen.drawLine(xStart + i * (xEnd / x) + (xEnd / x) / 2, yStart + j * (yEnd / y) + (yEnd / y) / 2, xStart + i * (xEnd / x) + (xEnd / x) / 2, yStart + j * (yEnd / y) + (yEnd / y) / 2);
                }
            }
        }

        //画表格线
        pen.setColor(Color.BLACK);
        pen.setStroke(new BasicStroke(lineWidth));
        for (int i = 0; i <= x; i++) {
            pen.drawLine(xStart + i * (xEnd / x), yStart, i * (xEnd / x), yEnd);
        }
        for (int j = 0; j <= y; j++) {
            pen.drawLine(xStart, yStart + j * (yEnd / y), xEnd, j * (yEnd / y));
        }

        return myImage;
    }
}
