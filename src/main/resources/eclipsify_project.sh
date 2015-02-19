cd ../../../../WebCrawlerServerV2Common
mvn source:jar install
cd ../WebCrawlerServerV2Slave
mvn eclipse:eclipse -DdownloadSources=true
