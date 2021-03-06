/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.wheel.db.datasource;

import com.silver.wheel.db.datasource.config.ConfigurationHandler;
import com.silver.wheel.db.datasource.config.SimpleDataSourceConfiguration;
import com.silver.wheel.db.exception.DBRuntimeException;
import com.silver.wheel.db.exception.SystemRuntimeException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 *
 * @author liaojian
 */
public class SimpleDataSourceFactory implements DataSourceFactory{
    private static SimpleDataSourceFactory factory = new SimpleDataSourceFactory();
    private Map<String, DataSource> dataSources = new HashMap<String, DataSource>();
    private Map<String, SimpleDataSourceConfiguration> configurations = new HashMap<String, SimpleDataSourceConfiguration>();
    
    private SimpleDataSourceFactory() {
        //获取配置文件
        //InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("ds.xml");
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("ds.xml");             
        //解析配置文件，将配置文件的内容设置到configurations
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = parserFactory.newSAXParser();            
            parser.parse(is, new ConfigurationHandler(configurations));            
            //根据配置创建数据源
            Set<String> keySet = configurations.keySet();
            for(String key : keySet) {
                SimpleDataSourceConfiguration config = configurations.get(key);
                Class.forName(config.getDriverClass());
//                SmartDataSource ds = new SingleConnectionDataSource(
//                        config.getUrl(), config.getUserName(), config.getPassword(), true);
                SimpleDataSource ds = new SimpleDataSource(config.getUrl(), config.getUserName(), config.getPassword());
                dataSources.put(config.getName(), ds);
            }
        } catch (Exception ex) {
            throw new SystemRuntimeException(ex);
        } 
        
    }
    
    public static DataSourceFactory getInstance() {        
        return factory;
    }
    
    public DataSource getDataSource(String dsName) {
        DataSource dataSource = dataSources.get(dsName);
        if(dataSource == null) {
            Logger.getLogger(SimpleDataSourceFactory.class.getName()).log(Level.SEVERE, 
                    "data source [{0}] is not exists!", dsName);
            throw new DBRuntimeException("data source [" + dsName +"] is not exists!");
        }
        return dataSource;
    }
    
    public static void main(String[] args) {
        DataSourceFactory factory = SimpleDataSourceFactory.getInstance();        
    }
}
