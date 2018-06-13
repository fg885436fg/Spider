package spider.demo.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: lanyubing
 * @create: 2018-06-08 18:09
 **/
public class FileUtil {

    private static final int SIZE = 1024 * 8;

    private FileUtil() throws Exception {
        throw new Exception("Cannot be instantiated");
    }

    /**
     * 以字节流的方式读取到字符串。
     *
     * @param is 输入流
     * @return 字符串
     */
    public static String readBytesToString(InputStream is) {
        return new String(readBytes(is));
    }

    /**
     * 以字节流的方式读取到字符串。
     *
     * @param is          输入流
     * @param charsetName 字符集
     * @return 字符串
     */
    public static String readBytesToString(InputStream is, String charsetName) {
        try {
            return new String(readBytes(is), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以字节流的方式从文件中读取字符串
     *
     * @param file        文件
     * @param charsetName 字符集
     * @return 字符串
     */
    public static String readBytesToString(File file, String charsetName) {
        try {
            return new String(readBytes(file), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 以字节流的方式从文件中读取字符串。
     *
     * @param file 文件
     * @return 字符串
     */
    public static String readBytesToString(File file) {
        return new String(readBytes(file));
    }

    // ---------------------readBytesToString 完成。分割线----------------------

    /**
     * 以字符流的方式读取到字符串。
     *
     * @param file 文件
     * @return 字符串
     */
    public static String readCharsToString(File file) {
        try {
            return readCharsToString(new FileInputStream(file), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以字节流的方式读取到字符串。
     *
     * @param file    文件
     * @param charset 字符集
     * @return 字符串
     */
    public static String readCharsToString(File file, String charset) {
        try {
            return readCharsToString(new FileInputStream(file), charset);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以字符流的方式读取到字符串。默认编码
     *
     * @param is 输入流
     * @return 字符串
     */
    public static String readCharsToString(InputStream is) {
        return new String(readChars(is, null));
    }

    /**
     * 以字符流的方式读取到字符串。
     *
     * @param is          输入流
     * @param charsetName 编码
     * @return 字符串
     */
    public static String readCharsToString(InputStream is, String charsetName) {
        return new String(readChars(is, charsetName));
    }

    // ---------------readCharsToString 完成。分割线-----------------------

    /**
     * 以字节流的方式读取到字符串。
     *
     * @param file 文件
     * @return 字节数组
     */
    public static byte[] readBytes(File file) {
        try {
            return readBytes(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以字节流的方式读取到字符串。
     *
     * @param is 输入流
     * @return 字节数组
     */
    public static byte[] readBytes(InputStream is) {
        byte[] bytes = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(is);

            byte[] cbuf = new byte[SIZE];
            int len;
            ByteArrayOutputStream outWriter = new ByteArrayOutputStream();
            while ((len = bis.read(cbuf)) != -1) {
                outWriter.write(cbuf, 0, len);
            }
            outWriter.flush();

            bis.close();
            is.close();

            bytes = outWriter.toByteArray();
            outWriter.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 以字符流的方式读取到字符串。
     *
     * @param file        文件
     * @param charsetName 编码
     * @return 字符数组
     */
    public static char[] readChars(File file, String charsetName) {
        try {
            return readChars(new FileInputStream(file), charsetName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以字符流的方式读取到字符串。
     *
     * @param is          输入流
     * @param charsetName 编码
     * @return 字符数组
     */
    public static char[] readChars(InputStream is, String charsetName) {
        char[] chars = null;
        try {
            InputStreamReader isr = null;
            if (charsetName == null) {
                isr = new InputStreamReader(is);
            } else {
                isr = new InputStreamReader(is, charsetName);
            }
            BufferedReader br = new BufferedReader(isr);
            char[] cbuf = new char[SIZE];
            int len;
            CharArrayWriter outWriter = new CharArrayWriter();
            while ((len = br.read(cbuf)) != -1) {
                outWriter.write(cbuf, 0, len);
            }
            outWriter.flush();

            br.close();
            isr.close();
            is.close();

            chars = outWriter.toCharArray();
            outWriter.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chars;
    }

    // -----------------------readxxx 完成。分割线-----------------------
    // -----------------------read 部分完成。接下来是write的部分------------

    /**
     * 通过字节输出流输出bytes
     *
     * @param os   输出流
     * @param text 字节数组
     */
    public static void writeBytes(OutputStream os, byte[] text) {
        writeBytes(os, text, 0, text.length);
    }

    /**
     * 通过字节输出流输出bytes
     *
     * @param os     输出流
     * @param text   字节数组
     * @param off    数组起始下标
     * @param lenght 长度
     */
    public static void writeBytes(OutputStream os, byte[] text, int off, int lenght) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(text, off, lenght);

            bos.flush();
            bos.close();
            os.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------------------writeByte 完成。分割------------------------

    /**
     * 通过字符输出流输出chars
     *
     * @param os          输出流
     * @param text        字节数组
     * @param charsetName 编码方式
     */
    public static void writeChars(OutputStream os, char[] text, String charsetName) {
        writeChars(os, text, 0, text.length, charsetName);
    }

    /**
     * 通过字符输出流输出chars
     *
     * @param os          输出流
     * @param text        字节数组
     * @param off         数组起始下标
     * @param lenght      长度
     * @param charsetName 编码方式
     */
    public static void writeChars(OutputStream os, char[] text, int off, int lenght, String charsetName) {
        try {
            OutputStreamWriter osw = null;

            if (charsetName == null) {
                osw = new OutputStreamWriter(os);
            } else {
                osw = new OutputStreamWriter(os, charsetName);
            }
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(text, off, lenght);

            bw.flush();
            bw.close();
            osw.close();
            os.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------------------writeChars 完成。分割------------------------

    /**
     * 将字符串以默认编码写入文件
     *
     * @param file 文件
     * @param text 字符串
     */
    public static void writeString(File file, boolean append, String text) {
        writeString(file, append, text, 0, text.length(), null);
    }

    /**
     * 将字符串写入文件
     *
     * @param file        文件
     * @param append      是否追加
     * @param text        字符串
     * @param off         起始下标
     * @param lenght      长度
     * @param charsetName 编码名称
     */
    public static void writeString(File file, boolean append, String text, int off, int lenght, String charsetName) {
        try {
            writeString(new FileOutputStream(file, append), text, off, lenght, charsetName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将字符串以默认编码写入文件
     *
     * @param file 文件
     * @param text 字符串
     */
    public static void writeString(File file, String text) {
        writeString(file, false, text, 0, text.length(), null);
    }

    /**
     * 将字符串写入文件（默认覆盖）
     *
     * @param file        文件
     * @param append      是否追加
     * @param text        字符串
     * @param charsetName 编码名称
     */
    public static void writeString(File file, boolean append, String text, String charsetName) {
        writeString(file, append, text, 0, text.length(), charsetName);
    }

    /**
     * 将字符串写入文件（默认覆盖）
     *
     * @param file        文件
     * @param text        字符串
     * @param charsetName 编码名称
     */
    public static void writeString(File file, String text, String charsetName) {
        writeString(file, false, text, 0, text.length(), charsetName);
    }

    /**
     * 字符输出流输出字符串
     *
     * @param os          输出流
     * @param text        字符串
     * @param charsetName 编码
     */
    public static void writeString(OutputStream os, String text, String charsetName) {
        writeString(os, text, 0, text.length(), charsetName);
    }

    /**
     * 字符输出流输出字符串
     *
     * @param os          输出流
     * @param text        字符串
     * @param off         起始下标
     * @param lenght      长度
     * @param charsetName 编码
     */
    public static void writeString(OutputStream os, String text, int off, int lenght, String charsetName) {
        try {
            OutputStreamWriter osw = null;

            if (charsetName == null) {
                osw = new OutputStreamWriter(os);
            } else {
                osw = new OutputStreamWriter(os, charsetName);
            }
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(text, off, lenght);

            bw.flush();
            bw.close();
            osw.close();
            os.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取TXT的自然段
     *
     * @param filePath
     * @return
     */
    public static List<String> readParagraphs(String filePath) {
        try {
            ArrayList<String> res = new ArrayList<>();// 段落切分结果
            StringBuilder sb = new StringBuilder();// 拼接读取的内容
            String temp = null;// 临时变量，存储sb去除空格的内容
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
            int ch = 0;
            while ((ch = reader.read()) != -1) {
                temp = sb.toString().trim().replaceAll("\\s*", "");// 取出前后空格，之后去除中间空格
                if ((char) ch == '\r') {
                    // 判断是否是空行
                    if (!"".equals(temp)) {
                        // 说明到了段落结尾，将其加入链表，并清空sb
                        res.add(temp);
                    }
                    sb.delete(0, sb.length());
                } else {
                    // 说明没到段落结尾，将结果暂存
                    sb.append((char) ch);
                }
            }
            // 最后一段如果非空， 将最后一段加入，否则不处理
            if (!"".equals(temp)) {
                res.add(temp);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}