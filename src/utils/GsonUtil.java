package utils;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.SPACE;

/**
 * Gson工具
 * @author tank
 */
public class GsonUtil {
    /**
     * 从对象里获得字符串
     * @param player
     * @return 
     */
    public static String getStringFromObject(Object player) {
        Gson gson = new Gson();
        String string = gson.toJson(player);
        return string;
    }

    /**
     * 将字符串保存到文件
     * @param string 要保存的字符串
     * @param file 文件
     */
    public static void saveStringToJsonFile(String string, File file) {
        try {
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(string);
            write.flush();
            write.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GsonUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GsonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 格式化Json
     * @param json json字符串
     * @return 返回格式化后的字符串
     */
    public static String formatJson(String json) {
        StringBuilder result = new StringBuilder();
        int length = json.length();
        int number = 0;
        char key = 0;

        // 遍历输入字符串。
        for (int i = 0; i < length; i++) {
            // 1、获取当前字符。
            key = json.charAt(i);

            // 2、如果当前字符是前方括号、前花括号做如下处理：
            if ((key == '[') || (key == '{')) {
                // （1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
                if ((i - 1 >= 0) && (json.charAt(i - 1) == ':')) {
                    result.append('\n');
                    result.append(indent(number));
                }

                // （2）打印：当前字符。
                result.append(key);

                // （3）前方括号、前花括号，的后面必须换行。打印：换行。
                result.append('\n');

                // （4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
                number++;
                result.append(indent(number));

                // （5）进行下一次循环。
                continue;
            }

            // 3、如果当前字符是后方括号、后花括号做如下处理：
            if ((key == ']') || (key == '}')) {
                // （1）后方括号、后花括号，的前面必须换行。打印：换行。
                result.append('\n');

                // （2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
                number--;
                result.append(indent(number));

                // （3）打印：当前字符。
                result.append(key);

                // （4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
                if (((i + 1) < length) && (json.charAt(i + 1) != ',')) {
                    result.append('\n');
                }

                // （5）继续下一次循环。
                continue;
            }

            // 4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
            if ((key == ',')) {
                result.append(key);
                result.append('\n');
                result.append(indent(number));
                continue;
            }

            // 5、打印：当前字符。
            result.append(key);
        }

        return result.toString();
    }

    /**
     * 缩进number个空格
     * @param number 要添加的空格的数量
     * @return 返回添加缩进后的字符串
     */
    private static String indent(int number) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < number; i++) {
            result.append(SPACE);
        }
        return result.toString();
    }

    /**
     * 从指定路径读取json文件
     * @param fileName 文件名
     * @return 返回读取后的json字符串
     * @throws java.io.IOException
     */
    public static String readJsonFile(String fileName) throws IOException {
        File jsonFile = new File(fileName);
        StringBuilder sb;
        try (Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8")) {
            int ch;
            sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
        }
        return sb.toString();
    }
}
