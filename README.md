# TwitterDemo

Pre-reqs:
  - Twitter Dev Account ConsumerKey, ConsumerSecret, AccessToken, AccessTokenSecret (to fill in HardcodedAccessContext class)
  - JRE 1.8
  - UNIX Environment preferred


Setting up:
  - git clone https://github.com/kafkaesqueknuth/Demo.git

Build:
  - cd Demo
  - Update Twitter tokens in <i>HardcodedAccessContext</i> class
  - mvn clean install -U
     
Run:
  - Execute this command in console: <i> java -jar target/TwitterDemo-0.0.1-SNAPSHOT.jar </i> (Alternately, import the maven project in Eclipse and run <i>AppStart</i> as Java Application)
  - Launch http://localhost:8080 on your browser

<b>TODO:</b>

- Unit Testing
- UI enhancements
  - Image handling
  - Auto complete support in search

