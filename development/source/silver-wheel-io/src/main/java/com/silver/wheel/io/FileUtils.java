/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.wheel.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 文件工具类
 * @author liaojian
 */
public class FileUtils {
    public static final int BASE_LENGTH = 1024;
    
    
    public static BufferedInputStream fileToBufferedInputStream(File file) {
        if(file == null || !file.exists()) {
            return null;
        }
        
        BufferedInputStream bis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return bis;
    }
    
    public static BufferedOutputStream fileToBufferedOutputStream(File file) {
        if(file == null) {
            return null;
        }
        
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bos;
    }
    /**
     * 遍历目录
     * @param <T>
     * @param file
     * @param action
     * @return 
     */
    public static <T> T directoryTraversal(File file, Action<File, T> action){
        if(file == null || !file.exists()) {
            return null;
        }
        else {
            if(file.isFile()) {
                action.process(file);
            }else {
                action.process(file);
                File[] subFiles = file.listFiles();
                for(File subFile : subFiles) {
                    FileUtils.directoryTraversal(subFile, action);
                }
            } 
            return action.getResult();
        }
    }
    /**
     * 获取文件占用的磁盘空间
     * @param file
     *  需要获取信息的文件
     * @param recursive
     *  是否进行递归查询
     * @return 
     */
    public static long getFileSpaceUsage(File file, boolean recursive) {
        if(file == null || !file.exists()) {
            return 0L;
        }
        
        if(!recursive) {
            return file.length();
        }else {
            FileDirectoryAction<Long> action = new FileDirectoryAction() {
                Long length = 0L;
                
                @Override
                protected void processFile(File file) {
                    length += file.length();                    
                }

                @Override
                protected void processDirecotry(File file) {
                    return;
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
    
    
   
    /**
     * 复制文件夹。如果目标文件夹已经存在，则不会进行复制
     * @param sourcePath
     * @param destinationPath
     * @return 
     */
    public static boolean copyDir(String sourcePath, String destinationPath, String regex) {
        return copyDir(sourcePath, destinationPath, false, regex);      
    }
    /**
     * 复制文件夹。
     * @param sourcePath
     * @param destinationPath
     * @param replace
     *  如果目标文件夹已经存在（包括子文件及子目录），是否进行覆盖。true为进行覆盖，
     *  false为不覆盖
     * @return 
     */
    public static boolean copyDir(String sourcePath, String destinationPath, boolean replace, String regex) {        
        return copyFile(sourcePath, destinationPath, replace, regex);      
    }
    
    public static void display(File file) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[100];
            bis.read(bytes);
            for(byte b : bytes)
                System.out.println(b);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
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
                    for(char c : chars) {
                        System.out.print(c);
                    }
                }

                public Object getResult() {
                    return null;
                }
            });                    
    }
     /**
     * 复制文件
     * @param source
     * @param destination
     * @param  replace 
     *  是否覆盖原有文件
     * @return 
     */
    public static boolean copyFile(String sourceFile, String destinationFile, boolean replace, String regex) {
        if(sourceFile == null || destinationFile == null) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, "parameter source file or destination file is null!");
            return false;
        }
        return copyFile(new File(sourceFile), new File(destinationFile), replace, regex);
                
    }
    /**
     * 复制文件
     * @param sourceFile
     * @param destinationFile
     * @param replace
     *  如果目标文件夹已经存在（包括子文件及子目录），是否进行覆盖。true为进行覆盖，
     *  false为不覆盖
     * @return 
     */
    public static boolean copyFile(File sourceFile, File destinationFile, boolean replace, String regex) {
        if(sourceFile == null || destinationFile == null) {
            throw new IllegalArgumentException("source file or destination file cant not be null");            
        }
                
        if(!sourceFile.exists()) {
            throw new IllegalArgumentException("source file [" + sourceFile.getName() + "] does'nt exists!");           
        }
                       
        //目标文件存在的情况下，需要判断replace标记的值                
        if(destinationFile.exists()){
            if(replace == true) {
                //源文件和目标文件都为文件时无需特别处理
                //源文件为文件，目标文件为目录，需要更改目标文件为目录下的文件
                if(sourceFile.isFile() && destinationFile.isDirectory()) {
                    destinationFile = new File(destinationFile.getAbsolutePath() + File.separator + sourceFile.getName());
                }
                //源文件为目录，目标文件为文件，无法复制，返回false
                else if(sourceFile.isDirectory() && destinationFile.isFile()) {
                    Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, "destination file [{0}] is a file!", 
                            destinationFile.getName());
                    return false;
                }
                //源文件为目录，目标文件为目录，则全目录复制
                //实现方法为递归调用
                else if(sourceFile.isDirectory() && destinationFile.isDirectory()) {
                    String[] fileNames = sourceFile.list();
                    boolean result = true;
                    for(String fileName : fileNames) {
                        if(!FileUtils.copyFile(sourceFile.getAbsoluteFile() + File.separator + fileName, 
                                destinationFile.getAbsoluteFile() + File.separator + fileName, replace, regex)) {
                            result = false;
                        }
                    }
                    
                    return result;
                }            
            }
            else {//不允许覆盖
                Logger.getLogger(FileUtils.class.getName()).log(Level.WARNING, "destination file [{0}] is exists!", 
                        destinationFile.getName());
                return false;
            }
        }
        //目标文件不存在的情况下
        else {
            //源文件为目录
            if(sourceFile.isDirectory()) {
                destinationFile.mkdir();
                //复制目录下的文件
                String[] fileNames = sourceFile.list();
                boolean result = true;
                for(String fileName : fileNames) {
                    if(!FileUtils.copyFile(sourceFile.getAbsoluteFile() + File.separator + fileName, 
                            destinationFile.getAbsoluteFile() + File.separator + fileName, replace, regex)) {
                        result = false;
                    }
                }
                return result;
            }
        }
        
        //以下执行具体的文件复制操作。进行到此，则说明进行操作的原文件为文件。
        //判断文件名称是否符合给定的正则表达式
        if(!sourceFile.getName().matches(regex)) {
            return false;
        }
        BufferedInputStream bis = FileUtils.fileToBufferedInputStream(sourceFile);
        final BufferedOutputStream bos = FileUtils.fileToBufferedOutputStream(destinationFile);
        
        IOOperationTemplate template = new IOOperationTemplate();        
        return template.inputStreamOperation(bis, new Action<InputStream, Boolean>() {
            private boolean result = false;
                    
            public void process(InputStream e) {
                BufferedInputStream bisUsed = (BufferedInputStream)e;
                
                byte[] bytes = new byte[BASE_LENGTH];
                try {                    
                    while(bisUsed.read(bytes) != -1) {                         
                        bos.write(bytes);
                    }
                    bos.flush();
                    bos.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                
                result = true;
            }

            public Boolean getResult() {
                return result;
            }
        });
    }
    
    public static boolean writeToFile(final String data, File file) {        
        if(data == null || file == null) {
            throw new IllegalArgumentException("source file or destination file cant not be null");  
        }
        
        if(!file.exists()) {
            throw new IllegalArgumentException("file [" + file.getName() + "] does'nt exists!"); 
        }
        
        if(file.isDirectory()) {
            throw new IllegalArgumentException("file [" + file.getName() + "] is a directory!");            
        }
        
        IOOperationTemplate template = new IOOperationTemplate();
        return template.fileBufferedWriterOperation(file, new Action<BufferedWriter, Boolean>() {
            private Boolean result = Boolean.valueOf(true);
            @Override
            public void process(BufferedWriter writer) {
                try {
                    writer.write(data);
                } catch (IOException ex) {
                    Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    result = false;
                }
            }
            
            public Boolean getResult() {
                return result;
            }
        });
    }
    
    public static void main(String[] args) {
//        FileUtils.copyFile("D:\\temp\\dell", "D:\\temp\\destination", true);
        FileUtils.writeToFile("test", new File("D:\\temp\\destination\\temp.txt"));
    }
}
