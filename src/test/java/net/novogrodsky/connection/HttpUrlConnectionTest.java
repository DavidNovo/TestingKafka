package net.novogrodsky.connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HttpUrlConnectionTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testMain() throws Exception {

    }

    @Test
    public void testSendGet(){
        HttpUrlConnection http;
        http = new HttpUrlConnection();
        System.out.println("Testing 1 - Send Http GET request");
        String results = null;
        try {
            results = http.sendGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(results);
    }
}