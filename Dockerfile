# 1. Base image olarak OpenJDK kullanıyoruz
FROM openjdk:17-jdk-slim

# 2. Çalışma dizinini oluşturuyoruz
WORKDIR /app

# 3. Projenizin jar dosyasını container'a kopyalıyoruz
COPY target/RealtyHub-0.0.1-SNAPSHOT.jar /app/RealtyHub-0.0.1-SNAPSHOT.jar

# 4. Uygulamanın çalışmasını sağlayacak komut
CMD ["java", "-jar", "RealtyHub-0.0.1-SNAPSHOT.jar"]

# 5. Container'ın dışarıya açacağı portu belirliyoruz
EXPOSE 8082