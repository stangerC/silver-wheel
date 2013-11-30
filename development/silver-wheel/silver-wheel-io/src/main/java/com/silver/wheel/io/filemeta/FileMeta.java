package com.silver.wheel.io.filemeta;

import java.io.Serializable;

/**
 *
 * @author Liaojian
 */
public interface FileMeta<ID extends Serializable>{
    /**
     * 获取文件的文件名
     * @return 
     */
    public String getName();
    /**
     * 获取文件所在的目录
     * @return 
     */
    public String getDirectory();
    /**
     * 文件具体路径
     * @return 
     */
    public String getPath();
    /**
     * 文件大小
     * @return 
     */
    public long getSize();
    
    /**
     * 是否为目录
     * @return 
     */
    public boolean isDirectory();
}
