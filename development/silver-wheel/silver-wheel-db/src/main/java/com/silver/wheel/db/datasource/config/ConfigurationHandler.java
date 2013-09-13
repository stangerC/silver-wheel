/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.wheel.db.datasource.config;

import java.util.Map;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author liaojian
 */
public class ConfigurationHandler extends DefaultHandler {

    private Stack<SimpleDataSourceConfiguration> configStack = new Stack<SimpleDataSourceConfiguration>();
    private Stack<String> elementStack = new Stack<String>();
    private Map<String, SimpleDataSourceConfiguration> configurations;

    public ConfigurationHandler(Map<String, SimpleDataSourceConfiguration> configurations) {
        this.configurations = configurations;
    }

    @Override
    public void startElement(String uri, String localName,
            String qName, Attributes attributes) {
        elementStack.push(qName);

        if (qName.equals("dataSource")) {
            SimpleDataSourceConfiguration config = new SimpleDataSourceConfiguration();
            configStack.push(config);
        }
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) {
        if (qName.equals("dataSource")) {
            configStack.pop();
        }
    }

    @Override
    public void characters(char ch[], int start, int length) {
        if (!elementStack.empty()) {
            String element = elementStack.pop();
            if (element.equals("name")) {
                SimpleDataSourceConfiguration config = configStack.pop();
                String dsName = String.valueOf(ch, start, length);
                config.setName(String.valueOf(ch, start, length));
                configStack.push(config);
                configurations.put(dsName, config);                
            }
            else if(element.equals("userName")) {
                SimpleDataSourceConfiguration config = configStack.pop();
                config.setUserName(String.valueOf(ch, start, length));
                configStack.push(config);
            }
            else if(element.equals("password")) {
                SimpleDataSourceConfiguration config = configStack.pop();
                config.setPassword(String.valueOf(ch, start, length));
                configStack.push(config);
            }
            else if(element.equals("url")) {
                SimpleDataSourceConfiguration config = configStack.pop();
                config.setUrl(String.valueOf(ch, start, length));
                configStack.push(config);
            }
            else if(element.equals("driverClass")) {
                SimpleDataSourceConfiguration config = configStack.pop();
                config.setDriverClass(String.valueOf(ch, start, length));
                configStack.push(config);
            }
            
        }
    }
}
