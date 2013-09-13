package com.silver.wheel.db;

import com.silver.wheel.db.datasource.SimpleDataSourceFactory;
import com.silver.wheel.db.exception.DBRuntimeException;
import com.silver.wheel.db.jdbc.ResultSetAsListHandler;
import com.silver.wheel.db.mapper.RowToEntityMapper;
import com.silver.wheel.db.session.JdbcSession;
import com.silver.wheel.db.session.SessionFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
     * Rigourous Test :-)
     */
    public void testApp() {
        DataSource ds = SimpleDataSourceFactory.getInstance().getDataSource("nkms-qa");
        JdbcSession session = null;
        List<CmsSite> sites = null;
        try {
            session = new SessionFactory(ds).createSession();
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
            
            session.update("update DB2INST1.CMS_SITE_TEMP SET SITENAME='TEST' WHERE OID=1");
            session.commit();

            session.update("update DB2INST1.CMS_SITE_TEMP SET SITENAME='WORLD' WHERE OID=1");     

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
}
