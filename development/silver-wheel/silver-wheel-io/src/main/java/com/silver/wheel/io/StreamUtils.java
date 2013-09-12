package com.silver.wheel.io;

import static com.silver.wheel.lang.Validation.checkNotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

/**
 * 流工具类。
 * @author Liaojian
 */
public class StreamUtils {

    public static final int ONE_KB = 1024;  //1K
    public static final int DEFAULT_BUFFER_SIZE = ONE_KB * 4; //4K 

    /**
     * 将输入流中的数据流复制到输出流中
     *
     * @param is 输入流
     * @param os 输出流
     * @return 复制的数据流中字节长度
     * @throws IOException 如果发生了IO错误
     */
    public static long copy(InputStream is, OutputStream os) throws IOException {
        return copy(is, os, DEFAULT_BUFFER_SIZE);
    }
    
    /**
     * 读取输入流中的数据，写入到输出流中。
     *
     * @param is 输入流
     * @param os 输出流
     * @param bufferSize 缓存长度
     * @return 复制的数据流中字节长度
     * @throws IOException 如果发生了IO错误
     */
    public static long copy(InputStream is, OutputStream os, int bufferSize) throws IOException {
        byte[] bytes = new byte[bufferSize];
        long count = 0;
        int length = 0;

        while ((length = is.read(bytes)) != -1) {
            os.write(bytes);
            count += length;
        }

        return count;
    }

    /**
     *
     * @param data
     * @param os
     * @throws IOException
     */
    public static void write(byte[] data, OutputStream os) throws IOException {
        checkNotNull(data);
        checkNotNull(os);
        os.write(data);
    }

    public static void writeAndClose(byte[] data, OutputStream os) throws IOException {
        write(data, os);
        os.close();
    }

    /**
     *
     * @param data
     * @param writer
     * @exception IOException
     */
    public static void write(String data, Writer writer) throws IOException {
        checkNotNull(data);
        checkNotNull(writer);

        writer.write(data);
    }

    /**
     *
     * @param data
     * @param writer
     */
    public static void writeAndClose(String data, Writer writer) throws IOException {
        write(data, writer);
        writer.close();
    }
}
