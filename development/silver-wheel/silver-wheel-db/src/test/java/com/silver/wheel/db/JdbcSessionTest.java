package com.silver.wheel.db;

import com.ibm.db2.jcc.DB2Clob;
import com.ibm.db2.jcc.DB2ConnectionlessClob;
import com.silver.wheel.db.datasource.SimpleDataSourceFactory;
import com.silver.wheel.db.exception.DBRuntimeException;
import com.silver.wheel.db.jdbc.ResultSetAsListHandler;
import com.silver.wheel.db.jdbc.ResultSetHandler;
import com.silver.wheel.db.mapper.RowToEntityMapper;
import com.silver.wheel.db.session.JdbcSession;
import com.silver.wheel.db.session.ParameterHandler;
import com.silver.wheel.db.session.SessionFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

class CmsSite {

    private long oId;
    private String siteName;

    public long getoId() {
        return oId;
    }

    public void setoId(long oId) {
        this.oId = oId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}


class Item {
    private long id;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }        
}

/**
 * Unit test for simple App.
 */
public class JdbcSessionTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public JdbcSessionTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(JdbcSessionTest.class);
    }

    /**
     * Rigourous Test
     */
    public void testApp() {
        DataSource ds = SimpleDataSourceFactory.getInstance().getDataSource("nkms-qa");
        JdbcSession  session = new SessionFactory(ds).createSession();
        List<CmsSite> sites = null;
        try {                       
            sites = session.query("select OID, SITENAME, SITEHTTP, SITEPORT, RESOURCEPATH,ISPUBLISHED, "
                    + "STATUS, ISHTML, ISDYNAMIC, MANAGER, REMARK, CREATETIME, TIMEDELETED, "
                    + "CREATEUSERID, CREATEUSERNAME,NUMBEROFCLICK, TEMPLATE_ID, PORTAL_ID"
                    + " from DB2INST1.CMS_SITE_TEMP", new ResultSetAsListHandler<CmsSite>(new RowToEntityMapper<CmsSite>() {
                public CmsSite mapping(ResultSet rs) throws SQLException {
                    CmsSite site = new CmsSite();

                    site.setoId(rs.getLong(1));
                    site.setSiteName(rs.getString(2));

                    return site;
                }
            }));
            
//            session.update("update DB2INST1.CMS_SITE_TEMP SET SITENAME='TEST' WHERE OID=1");
            session.commit();

//            session.update("update DB2INST1.CMS_SITE_TEMP SET SITENAME='WORLD' WHERE OID=1");     

            throw new DBRuntimeException("test");

        } catch (Exception ex) {
            session.rollback();
        } finally {
            session.close();
        }

        for (CmsSite site : sites) {
            System.out.println("site name:" + site.getSiteName());
        }

        assertTrue(true);
    }
    
    public void testClob() {
        DataSource ds = SimpleDataSourceFactory.getInstance().getDataSource("okms-qa");
        JdbcSession  session = new SessionFactory(ds).createSession();
        
        List<Item> items = session.query("select id,clobvalue from db2admin.t_dynaform_item \n" +
            "where id=1265705287627004 with ur", new ResultSetAsListHandler<Item>(new RowToEntityMapper() {

            public Item mapping(ResultSet rs) throws SQLException {               
                    Item item = new Item();
                    
                    item.setId(rs.getLong(1));    
                    Reader reader = rs.getClob(2).getCharacterStream();
                    StringBuilder buffer = new StringBuilder();
                    char[] chars = new char[200];
                    
                    int len = -1;
                try {
                    while((len = reader.read(chars)) != -1) {
                        buffer.append(chars, 0, len);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(JdbcSessionTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                    item.setContent(buffer.toString());
                    
                    return item;
               
            }
        }));
        
        session.close();                
        
        ds = SimpleDataSourceFactory.getInstance().getDataSource("nkms-qa");
        session = new SessionFactory(ds).createSession();
        
        for(Item item : items) {            
            System.out.println(item.getId());
            System.out.println("length:" + item.getContent().length());
            session.update("insert into DB2INST1.CMS_BASIC_DOCUMENT_TEMP values(?,?)", new Object[] {item.getId(), item.getContent()}, new ParameterHandler() {

                public void setParameter(PreparedStatement ps, Object[] params) {
                    try {
                        ps.setLong(1, (Long)params[0]);                        
                        ps.setClob(2, new DB2ConnectionlessClob((String)params[1]));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        
        session.close();
    }
}
