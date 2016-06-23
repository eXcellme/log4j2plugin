package org.log4j2plugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.EventLogger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.StructuredDataMessage;

public class LogRotateThread extends Thread {
  
    private static final LogRotateThread INSTANCE = new LogRotateThread();
    private static int LOG_ROTATE_ITERATION_INTERVAL_MILLIS = FTimeBasedTriggeringPolicy.DEFAULT_EMPTYMS;
//    public static final int LOG_ROTATE_ITERATION_INTERVAL_MILLIS = 3 * 60 * 1000;
    public static final String ID_SKIP = "SKIP";
    private static Set<FTimeBasedTriggeringPolicy> policies = new HashSet<FTimeBasedTriggeringPolicy>();

    private volatile boolean isRunning = false;
    /**
     * default constructor that should be used by normal users
     */
    private LogRotateThread() {
        super("log4j2plugin.LogRotateThread");
    }

    /**  
    * @Description register and check init
    * @param policy    
    */
    public static void registerPolicy(FTimeBasedTriggeringPolicy policy) {
        policies.add(policy);
        int emptyms = policy.getEmptyms();
        LOG_ROTATE_ITERATION_INTERVAL_MILLIS = Math.min(LOG_ROTATE_ITERATION_INTERVAL_MILLIS, emptyms);
        checkInit();
    }

    private synchronized static void checkInit() {
      if(!INSTANCE.isRunning){
        INSTANCE.isRunning = true;
        INSTANCE.start();
      }
    }

    public static void unregisterPolicy(FTimeBasedTriggeringPolicy policy) {
        policies.remove(policy);
        checkStop();
    }

    private synchronized static void checkStop() {
        if(INSTANCE.isRunning){
          INSTANCE.isRunning = false;
        }
    }

    public void run() {
//        System.out.println("LogRotateThread started with the sleep interval: "
//                + String.valueOf(LOG_ROTATE_ITERATION_INTERVAL_MILLIS / 60 / 1000) + " minutes");
        while (isRunning) {
            LogEvent logEvent = new EmptyLogEvent();
//            System.out.println("LogRotateThread: validating log rotation for #" + policies.size() + " policies");
            for (FTimeBasedTriggeringPolicy policy: policies) {
                try {
                  if(policy.isReady()){
                      policy.checkRollover(logEvent);
                  }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                if (isRunning) {
                    sleep(LOG_ROTATE_ITERATION_INTERVAL_MILLIS);
                }
            } catch (InterruptedException e) {
                System.err.println("LogRotateThread sleep was interrupted: " + e.getMessage());
            }
        }
    }

    public static void initializeAppenders(Collection<String> types) {
        for (String msgType: types) {
//            System.out.println("LogRotateThread: initializing appender for " + msgType);
            StructuredDataMessage msg = new StructuredDataMessage(ID_SKIP, "", msgType);
            EventLogger.logEvent(msg);
        }
    }
}

