## Library Management Service

To run the service on your computer clone the repository, create a .env file and provide values for these variables:

- DB=value
- DB_USER=value
- DB_PASSWORD=value
- DB_URL=value

- HIBERNATE_DDL_AUTO=value
-
- CACHE_TYPE=value
- REDIS_HOST=value
- REDIS_PORT=value
- REDIS_PASSWORD=

For HIBERNATE_DDL_AUTO the values can be **none**, **create**, **update** or **drop**. Use create to you are to create your database table and the
service will handle that for you.

Next, make sure you have redis installed and running. 
Also, install postgres (or use a remote db) create your database. Add the credentials to your
.env file if you have not done so.

After that, execute the following maven commands on your cmd:

- **_mvn install_** to install dependencies and build project
- **_mvn spring-boot:run_** to run the server

This can easily be done with IntelliJ IDE.

Visit the swagger documentation on **http://localhost:port/swagger-ui/index.html#/** 
to get all the available endpoints.
