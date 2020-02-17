## config-server

Default Port and Context Path

```
server.port=51000
server.servlet.path=/config

```

**How To Run**

Change the following properties in bootstrap.properties file in resources according to your configuration.
<br/>
#Git repository https address where configuration files are stored
spring.cloud.config.server.git.uri=<url>

# folder where properties are stored in git
spring.cloud.config.server.git.search-paths=config
# username and password for git repo
spring.cloud.config.server.git.password=<username>
spring.cloud.config.server.git.password=<password>

Once done store the properties from restaurant-config folder in the git repo provided so that it can connect to it and pick up the properties.

<br/>
Create a jar using `mvn clean install` once done, run the jar using:


`java -jar < jar-name >`
<br/>
<br/>

**Application Properties**

``` 
#Port where mosip spring cloud config server needs to run
server.port = 51000

#adding context path
server.servlet.path=/config




**Maven dependency for Config client**

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
			<version>${spring-cloud-config.version}</version>
		</dependency>

```


**Config client bootstrap.properties**

```
spring.cloud.config.uri=http://<config-host-url>:<config-port>
spring.cloud.config.label=<git-branch>
spring.application.name=<application-name>
spring.cloud.config.name=<property-file-to-pick-up-configuration-from>
spring.profiles.active=<active-profile>
management.endpoints.web.exposure.include=refresh
#management.security.enabled=false

#disabling health check so that client doesnt try to load properties from sprint config server every
# 5 minutes (should not be done in production)
spring.cloud.config.server.health.enabled=false

```
