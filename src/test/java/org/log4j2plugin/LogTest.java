package org.log4j2plugin;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class LogTest {
  @Test
  public void test() throws InterruptedException {
    Logger logger = LogManager.getLogger("AlphaLogger");
    // LogRotateThread t = new LogRotateThread(true, false);
    // t.start();
    for (int i = 0; i < 100; i++) {
      logger.info("hello !!!!!" + new Date());
      Thread.sleep(1000 * 30);
    }

  }

  public static void main(String[] args) throws InterruptedException {
//    Logger logger = LogManager.getLogger("AlphaLogger");
//    // LogRotateThread t = new LogRotateThread(true, false);
//    // t.start();
//    for (int i = 0; i < 100; i++) {
//      logger.info("hello !!!!!" + new Date());
//      Thread.sleep(1000 * 30);
//    }
    
    
    Logger logger = LogManager.getLogger("minuteLogger");
    for (int i = 0; i < 100; i++) {
      logger.info("hello !!!!!" + new Date());
      Thread.sleep(1000 * 90);
    }
  }
  
  @Test
  public void testMinute() throws InterruptedException{
    Logger logger = LogManager.getLogger("minuteLogger");
    // LogRotateThread t = new LogRotateThread(true, false);
    // t.start();
    for (int i = 0; i < 100; i++) {
      logger.info("hello !!!!!" + new Date());
      Thread.sleep(1000 * 90);
    }
  }
}
