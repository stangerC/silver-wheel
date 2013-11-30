package com.silver.wheel.io.filemeta.factory;

import com.silver.wheel.io.filemeta.FileMeta;
import com.silver.wheel.lang.exception.CodedRuntimeException;
import com.silver.wheel.lang.pattern.factory.VariableFactory;
import java.lang.reflect.ParameterizedType;

/**
 *
 * @author Liaojian
 */
public abstract class BaseFileMetaFactory<T extends FileMeta, V> implements VariableFactory<T, V> {
    protected abstract T prepareFileMeta(T fileMeta, V variable);
        
    protected T createFileMeta() {
        
        T fileMeta = null;
        
        Class<T> fileMetaClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            fileMeta = fileMetaClass.newInstance();
        } catch (InstantiationException ex) {
            throw new CodedRuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new CodedRuntimeException(ex);
        }
        
        return fileMeta;
    }
    
    public T create(V variable) {        
        T fileMeta = createFileMeta();
        fileMeta = prepareFileMeta(fileMeta, variable);
        return fileMeta;
    }
}
