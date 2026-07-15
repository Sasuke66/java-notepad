FROM eclipse-temurin:11-jdk
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN javac notepad.java
CMD ["java", "notepad"]