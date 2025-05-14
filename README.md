# Welcome to dev2dev !

dev2dev project with **Java** (JDK 21), **Vite Vue.js** (v.3). No front frameworks.

## Init database

### 1. Launch PostgreSQL with the following command:
```$bash
psql -U postgres
```

### 2. Create DataBase
Create a database for your project with this command:
```sql
CREATE DATABASE dev2dev;
```

### 3. Create role
As 'superuser' (postgres by default) run the script:
```DB_role_init.sql```
```DB_role_api.sql```

### 4. Run DDL
Login as "dbinit" and run the script:
```schema.ddl.sql```
to create the tables.

### 5. Run DML
then login as "api" and run the scipt:
```data.dml.sql```
to insert data.

### 6. Update Backend Application Configuration
In the application-dev.properties file of your backend application, you must configure the connection to PostgreSQL with the following parameters:
```application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dev2dev
```
Replace dev2dev_user and yourpassword with your "api" DB user credential.

### 7. Launch the application
Once you've created your database and updated your application configuration, you can start your backend. If you're using Maven, you can run :
bash
`mvn spring-boot:run`

## Init back

### Custom to manage environnement

1. Clone the project in your local directory with the following command:
```bash
git clone https://github.com/Deblak/dev2dev.git
```

2.Create files (on the root)  and custom your environnement variables : `application-dev.properties 
```application-dev.properties
spring.application.name=dev2dev-business

# Spring properties for local database:
spring.datasource.url=jdbc:postgresql://localhost:<port>/dev2dev
spring.datasource.username=<name>
spring.datasource.password=<password>
spring.datasource.driver-class-name=org.postgresql.Driver

# CORS
dev2dev-business.cors.allowedOrigin=http://localhost:<port>

# BCrypt
dev2dev-business.bcrypt.rounds=12

#Secret Local
dev2dev-business.jwt.secret=<secret>

#Expiration (exp) in seconds
dev2dev-business.jwt.exp=<seconds>
dev2dev-business.jwt.issuer=http://localhost:<port>/

# SMTP/Mail properties:
dev2dev-business.email.from=<secret>
spring.mail.host=<secret>
spring.mail.port=<secret>
spring.mail.username=<secret>
spring.mail.password=<secret>
spring.mail.protocol=<secret>
spring.mail.properties.mail.smtp.ssl.enable=true
spring.mail.properties.mail.smtp.auth=true
dev2dev-business.token.expiration-minutes=<temps en minutes>
dev2dev-business.email.confirmation-url-base=http://localhost:<port>/accounts/verify
dev2dev-business.email.verification-url-back=http://localhost:<port>/accounts/verify

# Claim custom
spring.security.oauth2.resourceserver.jwt.authority-prefix=
spring.security.oauth2.resourceserver.jwt.authorities-claim-name=roles

```

```
dev2dev-business/
├── src/main/java                        
│   ├── co/
│   │  └── simplon/
│   │       └── dev2dev/ 
│   │           ├── config                                 
│   │           ├── controllers
│   │           │   ├── AccountController.java 
│   │           │   ├── errors                         
│   │           ├── dtos
│   │           ├── entities
│   │           ├── repositories
│   │           ├── services
│   │           ├── validations                            
│   │           │   account
├── src/main/resources/
│    └── application.properties
│    └── application-dev.properties
│    └── application-custom.properties
```

## Init front
- Install dependencies
```bash
npm install
```
- Run Vite for dev environnement
```bash
npm run dev
```
- Run Vite for prod environnement
```bash
npm run build
```

### Custom to manage environnement
Create files (on the root)  and custom your  environnement variables with Vite convention: 
```
dev2dev-presentation/
├── public/
├── src/
│   └── directories...
│   └── ...
├── .env.development
├── .env.production
├── package.json
```
 - For dev environnement
File name: `.env.development`
Variable template: `VITE_API_URL=http://localhost:<number>/`

 - For prod environnement
File name: `.env.production`
Variable template: `VITE_API_URL=https://<domain-name>`

- For call variable, you can use:
`const apiUrl = import.meta.env.VITE_API_URL;`
