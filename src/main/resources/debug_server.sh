cd ../../../../WebCrawlerServerV2Common
mvn source:jar install
cd ../WebCrawlerServerV2Slave
mvn eclipse:eclipse -DdownloadSources=true
mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=9999"
