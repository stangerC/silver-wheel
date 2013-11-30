/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.wheel.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CN085910
 */
public class FileUtilsTest {
    
    public FileUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getBufferedInputStream method, of class FileUtils.
     */
    @Test
    public void testGetBufferedInputStream() {
        System.out.println("getBufferedInputStream");
        File file = null;
        BufferedInputStream expResult = null;
        //BufferedInputStream result = FileUtils.getBufferedInputStream(file);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getBufferedOutputStream method, of class FileUtils.
     */
    @Test
    public void testGetBufferedOutputStream() {
        System.out.println("getBufferedOutputStream");
        File file = null;
        BufferedOutputStream expResult = null;
        //BufferedOutputStream result = FileUtils.getBufferedOutputStream(file);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of directoryTraversal method, of class FileUtils.
     */
    @Test
    public void testDirectoryTraversal() {
        System.out.println("directoryTraversal");
        Object expResult = null;
        //Object result = FileUtils.directoryTraversal(null);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getFileSpaceUsage method, of class FileUtils.
     */
    @Test
    public void testGetFileSpaceUsage() {
        System.out.println("getFileSpaceUsage");
        File file = null;
        boolean recursive = false;
        long expResult = 0L;
        //long result = FileUtils.getFileSpaceUsage(file, recursive);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of display method, of class FileUtils.
     */
    @Test
    public void testDisplay() {
        System.out.println("display");
        File file = null;
        //FileUtils.display(file);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of copyDirectory method, of class FileUtils.
     */
    @Test
    public void testCopyDirectory_String_String() {
        System.out.println("copyDirectory");
        String source = "";
        String dest = "";
        //FileUtils.copyDirectory(source, dest);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of copyDirectory method, of class FileUtils.
     */
    @Test
    public void testCopyDirectory_6args() {
        System.out.println("copyDirectory");
        File source = null;
        File dest = null;
        boolean replace = false;
        boolean validation = false;
        String regex = "";
        int bufferSize = 0;
        //FileUtils.copyDirectory(source, dest, replace, validation, regex, bufferSize);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of copyFile method, of class FileUtils.
     */
    @Test
    public void testCopyFile() {
        System.out.println("copyFile");
        File source = null;
        File dest = null;
        String regex = "";
        boolean replace = false;
        boolean validation = false;
        int bufferSize = 0;
        //FileUtils.copyFile(source, dest, regex, replace, validation, bufferSize);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of copy method, of class FileUtils.
     */
    @Test
    public void testCopy_String_String() {
        System.out.println("copy");
        String source = "";
        String dest = "";
        //FileUtils.copy(source, dest);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of copy method, of class FileUtils.
     */
    @Test
    public void testCopy_5args() {
        System.out.println("copy");
        File source = null;
        File dest = null;
        boolean replace = false;
        String regex = "";
        int bufferSize = 0;
        //FileUtils.copy(source, dest, replace, regex, bufferSize);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getBytes method, of class FileUtils.
     */
    @Test
    public void testGetBytes() throws Exception {
        System.out.println("getBytes");
        File file = null;
        int length = 0;
        FileUtils instance = new FileUtils();
        //byte[] expResult = null;
        //byte[] result = instance.getBytes(file, length);
        //assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class FileUtils.
     */
    @Test
    public void testWrite_String_File() throws Exception {
        System.out.println("write");
        String data = "";
        File file = null;
        //FileUtils.write(data, file);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class FileUtils.
     */
    @Test
    public void testWrite_byteArr_File() throws Exception {
        System.out.println("write");
        byte[] data = null;
        File file = null;
        //FileUtils.write(data, file);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class FileUtils.
     */
    @Test
    public void testWrite_InputStream_File() throws Exception {
        System.out.println("write");
        InputStream is = null;
        File file = null;
        //FileUtils.write(is, file);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of createResource method, of class FileUtils.
     */
    @Test
    public void testCreateResource() {
        System.out.println("createResource");
        String resourceName = "";
        //FileUtils.createResource(resourceName);       
        //fail("The test case is a prototype.");
    }
}