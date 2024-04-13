# serenity-appium-browserstack-ios

[Serenity](https://serenity-bdd.info/) and [Appium](https://appium.io/) Integration with [Browserstack](https://www.browserstack.com/) SDK.

![BrowserStack Logo](https://d98b8t1nnulk5.cloudfront.net/production/images/layout/logo-header.png?1469004780)

<img src="https://serenity-bdd.info/wp-content/uploads/elementor/thumbs/serenity-bdd-pac9onzlqv9ebi90cpg4zsqnp28x4trd1adftgkwbq.png" height = "100">

## Run Sample build
### Setup
* Clone the repo
* Replace YOUR_USERNAME and YOUR_ACCESS_KEY with your BrowserStack access credentials in browserstack.yml.
* Install dependencies `mvn install`
* You can setup environment variables for all sample repos (see Notes) or update `serenity.conf` file with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings)

### Running your tests
- To run a test, run `mvn clean verify -Dbrowserstack.userName=<USER_NAME> -Dbrowserstack.accessKey=<ACCESS_KEY>`
- To run a test setup in pom.xml, run `mvn verify -P sample-test-1 -Dbrowserstack.userName=<USER_NAME> -Dbrowserstack.accessKey=<ACCESS_KEY`

 Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)

### Integrate your test suite

This repository uses the BrowserStack SDK to run tests on BrowserStack. Follow the steps below to install the SDK in your test suite and run tests on BrowserStack:

* Create sample browserstack.yml file with the browserstack related capabilities with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings) and place it in your root folder.
* Add maven dependency of browserstack-java-sdk in your pom.xml file
```sh
<dependency>
    <groupId>com.browserstack</groupId>
    <artifactId>browserstack-java-sdk</artifactId>
    <version>LATEST</version>
    <scope>compile</scope>
</dependency>
```
* Modify your build plugin to run tests by adding argLine `-javaagent:${com.browserstack:browserstack-java-sdk:jar}` and `maven-dependency-plugin` for resolving dependencies 
* Install dependencies `mvn compile`

## Notes
* You can view your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
* You can modify the retries test in pom.xml change the valor of <max.attempts.rerun.test>2</max.attempts.rerun.test>


## Authors and acknowledgment

| [<img src="https://gitlab.com/uploads/-/system/user/avatar/13437423/avatar.png?width=400" width=115><br><sub>Mauro L. Ibarra P.</sub>](https://github.com/mauroibarra) <br/> |
|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|


