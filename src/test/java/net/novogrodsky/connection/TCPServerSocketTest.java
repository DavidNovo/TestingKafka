package net.novogrodsky.connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TCPServerSocketTest {

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
    public void testGetSourceTable(){
        TCPServerSocket newSocket = new TCPServerSocket();
        assertNotNull(newSocket);

        String testData = null;
        testData = newSocket.getSourceTable();
        System.out.println(testData);
        assertNotNull(testData);
    }

    @Test
    public void testStreamData() throws Exception {

    }
}