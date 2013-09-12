package com.silver.wheel.io.filemeta.factory;

import com.silver.wheel.io.filemeta.DefaultFileMeta;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.UUID;
import java.util.logging.Level;


/**
 *
 * @author Liaojian
 */
public class DefaultFileMetaFactory extends BaseFileMetaFactory<DefaultFileMeta, String> {

    private DirectoryStyle directoryStyle;
    private NameGeneratedStyle nameGeneratedStyle;
    private String baseDirectory;

    public DefaultFileMetaFactory(DirectoryStyle directoryStyle, 
            NameGeneratedStyle nameGeneratedStyle, String baseDirectory) {        
        this.directoryStyle = directoryStyle;
        this.nameGeneratedStyle = nameGeneratedStyle;
        this.baseDirectory = (baseDirectory == null ? "" : baseDirectory);
    }

    @Override
    protected DefaultFileMeta prepareFileMeta(DefaultFileMeta defaultFileMeta, String name) {
        defaultFileMeta.setName(name);        
        StringBuilder directoryBuilder = new StringBuilder(this.baseDirectory);
        
        switch (directoryStyle) {
            case DATE:
                directoryBuilder.append(File.separator);
                Calendar calendar = Calendar.getInstance();
                directoryBuilder.append(calendar.get(Calendar.YEAR)).append(File.separator)
                        .append(calendar.get(Calendar.MONTH)).append(File.separator)
                        .append(calendar.get(Calendar.DATE)).append(File.separator);               
                break;
            case FILE_NAME:
                directoryBuilder.append(File.separator).append(defaultFileMeta.getName());                
                break;
            case FILE_NAME_ISO_8859_1:
                try {
                    directoryBuilder.append(File.separator).append(
                            new String(defaultFileMeta.getName().getBytes(), "ISO-8859-1"));                    
                } catch (UnsupportedEncodingException ex) {
                    java.util.logging.Logger.getLogger(DefaultFileMetaFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
        defaultFileMeta.setDirectory(directoryBuilder.toString());
        
        switch (nameGeneratedStyle) {
            case UUID:
                defaultFileMeta.setNameGenerated(UUID.randomUUID().toString());
                defaultFileMeta.setPath(directoryBuilder
                        .append(defaultFileMeta.getNameGenerated())
                        .append(defaultFileMeta.getExtension())
                        .toString());
                break;
            case FILE_NAME:
                defaultFileMeta.setNameGenerated(defaultFileMeta.getName());
                defaultFileMeta.setPath(directoryBuilder.append(defaultFileMeta.getName()).toString());
                break;
            case FILE_NAME_ISO8859_1:
                try {
                    defaultFileMeta.setNameGenerated(new String(defaultFileMeta.getName().getBytes(), "ISO-8859-1"));
                    defaultFileMeta.setPath(directoryBuilder.append(
                            defaultFileMeta.getNameGenerated()).toString());
                } catch (UnsupportedEncodingException ex) {
                    
                }
                break;
        }        

        return defaultFileMeta;
    }        
}
