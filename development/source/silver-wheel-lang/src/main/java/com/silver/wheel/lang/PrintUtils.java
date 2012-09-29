package com.silver.wheel.lang;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Hello world!
 *
 */
public class PrintUtils {

    public static void printBean(Object bean) {
        try {
            Map propertiesMap = BeanUtils.describe(bean);
            Set keySet = propertiesMap.keySet();
            for (Object key : keySet) {
                Logger.getLogger(PrintUtils.class.getName()).log(Level.INFO, "proterty:[{0}], value:[{1}]",
                        new Object[]{key, propertiesMap.get(key)});
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static void printMap(Map map) {
        Set keySet = map.keySet();
        Logger.getLogger(PrintUtils.class.getName()).log(Level.INFO, "map size:[{0}]",
                       keySet.size());
        for (Object key : keySet) {
             Logger.getLogger(PrintUtils.class.getName()).log(Level.INFO, "key:[{0}] {",
                       key.toString());
             printBean(map.get(key));
             Logger.getLogger(PrintUtils.class.getName()).log(Level.INFO, "}");
        }
    }
}
