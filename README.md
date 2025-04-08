# Welcome to dev2dev !

dev2dev project with **Java** (JDK 21), **Vite Vue.js** (v.3). No front frameworks.

## Init database

1. Launch PostgreSQL with the following command:
```$bash
psql -U postgres
```

3. Create DataBase
Create a database for your project with this command:
```sql
CREATE DATABASE dev2dev;
```

4. Update Backend Application Configuration
In the application-dev.properties file of your backend application, you must configure the connection to PostgreSQL with the following parameters:
```application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dev2dev
```
Replace dev2dev_user and yourpassword with your PostgreSQL user information.

4. Launch the application
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
spring.datasource.username=dev2dev_user
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=org.postgresql.Driver
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
Variable template: `VITE_API_URL=VITE_API_URL=https://<domain-name>`

- For call variable, you can use:
`const apiUrl = import.meta.env.VITE_API_URL;`
