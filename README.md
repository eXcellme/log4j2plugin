This plugin help us rollover log file by time forcibly and can retain 0-size log file

How To Use:

1.import 
    
    <dependency>
      <artifactId>log-rotate-policy</artifactId>
      <version>1.0</version>
      <packaging>jar</packaging>
    </dependency
   
2.update log4j2.xml, for example 
   
    <RollingFile name="RollingFile-AlphaLog"
                     fileName="${alpha_log_filename}"
                     filePattern="${log.path}/alpha_log/%d{yyyyMMddHHmmss}-${hostName}.log"
                     >
        <PatternLayout>
            <Pattern>%d{yyyyMMddHH}{GMT+0} %m %n</Pattern>
        </PatternLayout>
        <FTimeBasedTriggeringPolicy emptyms="2000"/>
        <KeepEmptyFileRolloverStrategy/>
    </RollingFile>

``emptyms`` means how many millisecond should this plugin send an empty event ,to create an empty log file

``KeepEmptyFileRolloverStrategy`` help us keep empty file , against log4j2's deleting empty file by default 

Forked from https://github.com/mushkevych/log4j2plugin.git

Original README:

This project provides Log4j2 Plugin capable of rotating logs at the end of given time period (hour, day, etc).

See an exemplary log4j2.xml (src/main/resources/log4j2.xml) for how the plugin is used.

How it works:  

1. FTimeBasedTriggeringPolicy is a copy-paste TimeBasedTriggeringPolicy policy that exposes **RollingFileManager** instance 
as well as **checkRollover** method  
1. FTimeBasedTriggeringPolicy allows 0-length files to be rotated  
1. FTimeBasedTriggeringPolicy instance registers itself in the **LogRotateThread** during initialization  
1. Every few minutes **LogRotateThread** queries **FTimeBasedTriggeringPolicy.checkRollover** method which if needed will 
trigger log rotation  

How-to:  

1. Register FTimeBasedTriggeringPolicy in the <Policies> tag  
1. Start LogRotateThread  
1. If using Routing, make sure to "pre-initialize" log appenders by calling **LogRotateThread.initializeAppenders** method  


Licensed under the Apache License, Version 2.0  
[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)