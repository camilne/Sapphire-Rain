# Sapphire Rain
A small collaborative, top-down stealth game.

---

[//]: # (Build statuses and code coverage)

| master | develop-camilne | develop-abhid |
|--------|-----------------|---------------|
|[![Build Status](https://travis-ci.org/camilne/Sapphire-Rain.svg?branch=master)](https://travis-ci.org/camilne/Sapphire-Rain)|[![Build Status](https://travis-ci.org/camilne/Sapphire-Rain.svg?branch=develop-camilne)](https://travis-ci.org/camilne/Sapphire-Rain)|[![Build Status](https://travis-ci.org/camilne/Sapphire-Rain.svg?branch=develop-abhid)](https://travis-ci.org/camilne/Sapphire-Rain)
|[![Code Coverage](https://img.shields.io/codecov/c/github/camilne/Sapphire-Rain/master.svg)](https://codecov.io/github/camilne/Sapphire-Rain?branch=master)|[![Code Coverage](https://img.shields.io/codecov/c/github/camilne/Sapphire-Rain/develop-camilne.svg)](https://codecov.io/github/camilne/Sapphire-Rain?branch=develop-camilne)|[![Code Coverage](https://img.shields.io/codecov/c/github/camilne/Sapphire-Rain/develop-abhid.svg)](https://codecov.io/github/camilne/Sapphire-Rain?branch=develop-abhid)

[//]: # (Releases)

### Releases
* [v0.1.0-alpha](https://github.com/camilne/Sapphire-Rain/releases/tag/v0.1.0-alpha)

[//]: # (Dependencies)

### Dependencies
* Java 8
* Gson 2
* [Build-only] Maven 3
* [Test-only] JUnit 5

[//]: # (Installation instructions)

### To build from source
1. Install a Java IDE and the JDK 8.
2. Install [maven](https://maven.apache.org/install.html) (This project is built using the [M2Eclipse](http://www.eclipse.org/m2e/) plugin for Eclipse)
3. Build the project using:
> ```bash
> mvn install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -B -V validate
> ```
or if you are using the M2Eclipse plugin, right-click `pom.xml`&gt;Run As&gt;Maven build. In the `goals` box, enter the same command as above, except without `mvn` and hit run.<br/>
&nbsp;&nbsp;4. SapphireRain.jar is created in the `target/` folder.

[//]: # (Testing instructions)

### To test
1. Follow the instructions in building from source.
2. Test the project using:
> ```
> mvn test
> ```

[//]: # (Javadoc instructions)

### To generate javadocs
1. Follow the instructions in buildling from source.
2. Generate javadocs using:
> ```bash
> mvn javadocs:javadocs
> ```
&nbsp;&nbsp;3. Javadocs are found in `target/site/apidocs/`.

### Technologies used
* [Travis-CI](https://travis-ci.org/)
* [CodeCov](https://codecov.io/)
* [Maven 3](https://maven.apache.org/)
* [Eclipse](https://eclipse.org/)
* [Java 8](https://www.java.com/en/)
