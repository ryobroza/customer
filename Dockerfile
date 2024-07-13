#Base image
FROM openjdk:22-oracle
#tambah jar file yang ada kedalam target image
#commandnya mvn clean install -DskipTests
ADD target/customer-0.0.1-SNAPSHOT.jar customer.jar
#Entry Point
CMD ["java","jar","/customer.jar"]

# dari root project diterminal jalankan perintahL docker build -t customer:1.0 .  <== jangan lupa spasi titik