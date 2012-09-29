/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.wheel.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author liaojian
 */
public class IOOperationTemplate {
    public <T> void readerOperation(Reader reader, Action<Reader, T> action) {        
        try {
            action.process(reader);   
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(IOOperationTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public <T> T fileBufferedReaderOperation(File file, Action<BufferedReader, T> action) {        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            action.process(br);  
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(IOOperationTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return action.getResult();
    }
    
     public <T> T fileBufferedWriterOperation(File file, Action<BufferedWriter, T> action) {        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            action.process(bw);   
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(IOOperationTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return action.getResult();
    }
    
    public <T> T inputStreamOperation(InputStream is, Action<InputStream, T> action) {
        try {
            action.process(is);
            is.close();            
        } catch (IOException ex) {
            Logger.getLogger(IOOperationTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return action.getResult();
    }
    
    public <T> T outputStreamOperation(OutputStream os, Action<OutputStream, T> action) {
        try {
            action.process(os);
            os.close();            
        } catch (IOException ex) {
            Logger.getLogger(IOOperationTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return action.getResult();
    }
}
