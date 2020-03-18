Roman Numeral Server
===
HTTP Server that returns roman numeral value on query.

A simple HTTP Server built using [HTTP Server API](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/overview-summary.html).
Roman numeral conversion as per Wikipedia page: [Roman numerals](https://en.wikipedia.org/wiki/Roman_numerals). 

[Maven](https://github.com/apache/maven) for building.

[JUnit](https://github.com/junit-team/junit4) and [Mockito](https://github.com/mockito/mockito) for testing.

Prerequisites
==
* JDK (1.9+)
* Maven (3.6.3)

Compile
==
```bash
$ mvn compile
```

Run
==
For server
```bash
$ mvn exec:java
```

For client
```bash
$ curl -i "http://127.0.0.1:8080/romannumeral?query={integer}"
```

Test
==
```$xslt
$ mvn test
```