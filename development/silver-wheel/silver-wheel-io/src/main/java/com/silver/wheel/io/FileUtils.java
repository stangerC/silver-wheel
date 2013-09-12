package com.silver.wheel.io;

import static com.silver.wheel.lang.Validation.checkFileNotExists;
import static com.silver.wheel.lang.Validation.checkNotNull;
import com.silver.wheel.lang.exception.CodedRuntimeException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 文件工具类
 *
 * @author Liaojian
 */
public class FileUtils {

    public static final int ONE_KB = 1024;  //1K
    public static final int DEFAULT_BUFFER_SIZE = ONE_KB * 4; //4K 

    /**
     * 根据File对象获取对应的BufferedInputStream对象。
     *
     * @param file
     * @return
     */
    public static BufferedInputStream getBufferedInputStream(File file) {
        checkNotNull(file, "file must not be null!");
        checkFileNotExists(file, String.format("file [%s] does not exist!", file.getName()));

        BufferedInputStream bis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
        } catch (FileNotFoundException ex) {
            throw new CodedRuntimeException(ex);
        }

        return bis;
    }

    /**
     * 根据File对象获取对应的BufferedInputStream对象。
     *
     * @param file
     * @return
     */
    public static BufferedOutputStream getBufferedOutputStream(File file) {
        checkNotNull(file, "file must not be null!");

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException ex) {
            throw new CodedRuntimeException(ex);
        }

        return bos;
    }

    /**
     * 遍历目录，执行指定的操作
     *
     * @param <T>
     * @param directory 需要遍历的目录。
     * @param action 需要执行的操作。
     * @return 操作对象返回的结果
     */
    public static <T> T directoryTraversal(File directory, Action<File, T> action) {
        checkNotNull(directory, "directory must not be null!");

        if (!directory.exists()) {
            throw new IllegalArgumentException(String.format("directory [%s] doesn't exist!", directory.getPath()));
        } else {
            if (directory.isFile()) {
                action.process(directory);
            } else {
                action.process(directory);
                File[] subFiles = directory.listFiles();
                for (File subFile : subFiles) {
                    FileUtils.directoryTraversal(subFile, action);
                }
            }
            return action.getResult();
        }
    }

    /**
     * 获取文件占用的磁盘空间
     *
     * @param file 需要获取信息的文件
     * @param recursive 是否进行递归查询
     * @return
     */
    public static long getFileSpaceUsage(File file, boolean recursive) {
        checkNotNull(file, "file must not be null!");
        checkFileNotExists(file, String.format("file [%s] does'nt exists!", file.getName()));

        if (!recursive) {
            return file.length();
        } else {
            FileDirectoryAction<Long> action = new FileDirectoryAction() {
                Long length = 0L;

                @Override
                protected void processFile(File file) {
                    length += file.length();
                }

                @Override
                protected void processDirecotry(File file) {
                }

                @Override
                public Long getResult() {
                    return length;
                }
            };

            FileUtils.directoryTraversal(file, action);

            return action.getResult();
        }
    }

    public static void display(File file) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[100];
            bis.read(bytes);
            for (byte b : bytes) {
                System.out.print(b);
            }
            System.out.println();
        } catch (IOException ex) {
            throw new CodedRuntimeException(ex);
        }
    }
    /*
     public static void displayWithReader(File file) {
     IOOperationTemplate template = new IOOperationTemplate();
     template.fileBufferedReaderOperation(file, new Action<BufferedReader, Object>() {
     @Override
     public void process(BufferedReader e) {
     char[] chars = new char[100];
     try {
     e.read(chars);
     } catch (IOException ex) {
     Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
     }
     for (char c : chars) {
     System.out.print(c);
     }
     }

     public Object getResult() {
     return null;
     }
     });
     }
     */

    /**
     * 复制文件夹。如果目标文件夹已经存在，则不会进行复制
     *
     * @param source
     *
     * @param dest
     *
     */
    public static void copyDirectory(String source, String dest) {
        copyDirectory(new File(source), new File(dest), false, true, ".*", DEFAULT_BUFFER_SIZE);
    }

    /**
     * 复制文件夹。复制前会进行必要的校验
     *
     * @param sourcePath
     * @param destinationPath
     * @param replace 如果目标文件夹已经存在（包括子文件及子目录），是否进行覆盖。true为进行覆盖， false为不覆盖
     * @return
     */
    public static void copyDirectory(File source, File dest, boolean replace, boolean validation,
            String regex, int bufferSize) {
        if (validation) {
            checkFilesForCopy(source, dest);
            if (!source.isDirectory()) {
                throw new IllegalArgumentException(String.format(
                        "source file [%s] is not a directory!", source.getAbsolutePath()));
            }
            checkIntGreaterThanZero(bufferSize, "buffer size must greater than 0!");
        }

        File[] subSources = source.listFiles();
        for (File subSource : subSources) {
            if (subSource.isFile()) {
                copyFile(subSource, dest, regex, replace, false, bufferSize);
            } else if (subSource.isDirectory()) {
                if (!dest.exists()) {
                    dest.mkdirs();
                }
                copyDirectory(subSource, new File(dest.getAbsolutePath() + File.separator + subSource.getName()),
                        replace, false, regex, bufferSize);
            }
        }
    }

    /**
     * 复制文件
     *
     * @param source
     * @param dest
     * @param regex
     * @param replace
     * @param validation
     * @param bufferSize
     */
    public static void copyFile(File source, File dest, String regex, boolean replace,
            boolean validation, int bufferSize) {
        if (validation) {
            checkFilesForCopy(source, dest);
            if (!source.isFile()) {
                throw new IllegalArgumentException(String.format(
                        "source file [%s] is not a file!", source.getAbsolutePath()));
            }

            checkIntGreaterThanZero(bufferSize, "buffer size must greater than 0!");
        }

        //判断文件名称是否符合给定的正则表达式，或者目标文件已经存在且不允许替换，则直接返回
        if (!source.getName().matches(regex) || (dest.exists() && dest.isFile() && replace == false)) {
            return;
        }
        //目标文件已经存在而且为目录，使用源文件名称和原始的目标文件重新建立目标文件路径
        if (dest.exists() && dest.isDirectory()) {
            dest = new File(dest.getAbsolutePath() + File.separator + source.getName());
        } else if (!dest.exists()) {//创建目录，然后使用源文件名称和原始的目标文件重新建立目标文件路径
            dest.mkdirs();
            dest = new File(dest.getAbsolutePath() + File.separator + source.getName());
        }

        doCopyFile(source, dest, bufferSize);
    }

    /**
     * 复制文件。该方法假定参数都是合法的，校验工作需要在方法执行之前进行。
     *
     * @param source 源文件，必须为一个存在的文件，不能为目录
     * @param dest 目标文件，必须为一个存在的文件，不能为目录
     * @param bufferSize 复制时缓存的大小。
     */
    private static void doCopyFile(File source, File dest, int bufferSize) {
        BufferedInputStream bis = getBufferedInputStream(source);
        BufferedOutputStream bos = getBufferedOutputStream(dest);

        byte[] bytes = new byte[bufferSize];

        try {
            while (bis.read(bytes) != -1) {
                bos.write(bytes);
            }
            bos.flush();
        } catch (IOException ex) {
            throw new CodedRuntimeException(ex);
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                throw new CodedRuntimeException(ex);
            } finally {
                try {
                    bis.close();
                } catch (IOException ex) {
                    throw new CodedRuntimeException(ex);
                }
            }
        }
    }

    /**
     * 复制所有文件，不会替换原有文件
     *
     * @param source
     * @param destination
     * @param replace 是否覆盖原有文件
     * @return
     */
    public static void copy(String source, String dest) {
        int bufferSize;
        if (source.length() >= DEFAULT_BUFFER_SIZE) {
            bufferSize = DEFAULT_BUFFER_SIZE;
        } else {
            bufferSize = (int) source.length();
        }

        copy(new File(source), new File(dest), false, ".*", bufferSize);
    }

    /**
     * 复制文件
     *
     * @param source
     * @param dest
     * @param replace 如果目标文件夹已经存在（包括子文件及子目录），是否进行覆盖。true为进行覆盖， false为不覆盖
     * @param regex 文件匹配的正则表达式。只有符合正则表达式的文件才会进行拷贝操作
     * @param bufferSize 缓存的大小
     * @return
     */
    public static void copy(File source, File dest,
            boolean replace, String regex, int bufferSize) {
        //复制前验证源文件和目标文件是否正确
        checkFilesForCopy(source, dest);

        //源文件为一个文件，可以直接处理
        if (source.isFile()) {
            copyFile(source, dest, regex, replace, false, bufferSize);
        } else {//源文件为目录，需要进行目录复制
            copyDirectory(source, dest, replace, false, regex, bufferSize);
        }
    }

    /**
     * 复制前的校验
     *
     * @param source
     * @param dest
     */
    private static void checkFilesForCopy(File source, File dest) {
        checkNotNull(source, "source file must not be null!");
        checkNotNull(dest, "destination file must not be null!");
        checkFileNotExists(source, String.format("source file [%s] does'nt exist!", source.getAbsolutePath()));

        //源文件为目录，目标文件为文件，无法复制抛出运行时异常
        if (source.isDirectory() && dest.isFile()) {
            throw new CodedRuntimeException(String.format("source file [%s] is a "
                    + "directory, but destination file [%s] is a file!", dest.getAbsolutePath()));
        }
        //原文件和目标文件不能相同
        if (source.equals(dest)) {
            throw new CodedRuntimeException(String.format("source file [%s] and "
                    + "the destination file is a same file!", source.getAbsolutePath()));
        }
    }

    private static void checkIntGreaterThanZero(int aInt, String message) {
        if (aInt <= 0) {
            if (message != null) {
                throw new IllegalArgumentException(message);
            }
            throw new IllegalArgumentException();
        }
    }

    public byte[] getBytes(File file, int length) throws IOException {
        byte[] bytes = new byte[length];
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        return bytes;
    }

    public static void write(final String data, File file) {      
        try {        
            StreamUtils.writeAndClose(data, new FileWriter(file));
        } catch (IOException ex) {
            throw new CodedRuntimeException(ex);
        }
    }        

    public static void write(final byte[] data, File file) throws IOException {
        StreamUtils.write(data, new FileOutputStream(file));
    }

    /**
     * 将输入流中的数据写入文件中
     *
     * @param is 包含数据的输入流
     * @param file 需要写入数据的文件
     */
    public static void write(InputStream is, File file) throws FileNotFoundException, IOException {
        checkForWrite(is, file);
        StreamUtils.copy(is, new BufferedOutputStream(new FileOutputStream(file)));
    }

    private static <T> void checkForWrite(T data, File dest) {
        if (data == null) {
            throw new NullPointerException("data is null!");
        }
        if (dest == null) {
            throw new NullPointerException("destination file is null");
        }

        if (!dest.exists()) {
            throw new IllegalArgumentException(String.format(
                    "destination file [%s] does'nt exists!", dest.getAbsolutePath()));
        }

        if (dest.isDirectory()) {
            throw new IllegalArgumentException(String.format(
                    "destination file [%s] is a directory!", dest.getAbsolutePath()));
        }
    }

    /**
     * 创建资源文件。资源文件与ClassLoader.getResource()所对应的资源相同，是与字节码位置 相关的数据。
     *
     * @param resourceName
     */
    public static void createResource(String resourceName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //资源不存在则创建
        if (classLoader.getResource(resourceName) == null) {
            URL url = classLoader.getResource("");
            System.out.println(url.getPath());
            if (resourceName.indexOf("/") == -1) {
                write("", new File(url.getPath() + resourceName));
            } else {
                if (resourceName.lastIndexOf("/") != resourceName.length() - 1) {
                    File file = new File(url.getPath() + resourceName.substring(0, 
                            resourceName.lastIndexOf("/"))); 
                    if(!file.mkdirs()) {
                        throw new CodedRuntimeException("can't create dir:" + file.getAbsolutePath());
                    }
                    file = new File(url.getPath() + resourceName);
                    write("", file);
                } else {                    
                    File file = new File(url.getPath() + resourceName);
                    if(!file.mkdirs()) {
                        throw new CodedRuntimeException("can't create dir:" + file.getAbsolutePath());
                    }                  
                }
            }
        }
    }
}
