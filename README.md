
# DBOne

DBOne is a powerful and flexible Java-based library designed to connect with various databases seamlessly. It provides a ready-to-use JAR file that can be integrated into any Java project to manage database connections efficiently. The project also includes a Swagger-powered backend API, enabling easy interaction with the database through web or desktop applications.

---

## Features
- **Universal Database Support**: Connect to MySQL, PostgreSQL, SQLite, H2, and more.
- **Database-Specific Query APIs**: Run queries tailored for specific databases via dedicated endpoints.
- **Swagger Documentation**: Interactive API documentation for easy testing and usage.
- **Pluggable JAR**: A lightweight JAR file that can be used in Java projects for database connectivity.
- **DBOneClient Integration**: Java client library for seamless database connectivity in any project.
- **Extendable**: Future-ready for building desktop and web applications using the provided APIs.

---

## How It Works
1. **Backend API**:
   - A Spring Boot application with endpoints for testing database connections and running queries for each database type.
   - Supports major databases like MySQL, PostgreSQL, SQLite, and H2.
   - APIs are documented with Swagger UI for easy interaction.

2. **JAR Integration**:
   - A standalone JAR that can be included in any Java project to manage database connections and execute queries programmatically.
   - Provides flexibility to configure hostnames, ports, databases, usernames, and passwords.
   - Includes `DBOneClient` for easy interaction with APIs.

3. **Frontend Options**:
   - Plan to develop desktop and web applications that consume the backend APIs.

---

## Project Structure
```
DBOne/
├── src/main/java/com/acender/dbone
│   ├── config               # Swagger and global configurations
│   ├── controller           # REST API controllers for database connectivity
│   ├── client               # DBOneClient for integration into other projects
│   ├── DbOneApplication.java  # Main Spring Boot application class
├── src/main/resources
│   ├── application.properties  # Spring Boot configuration file
├── pom.xml                 # Maven configuration file
└── README.md               # Project documentation
```

---

## Prerequisites
- **Java**: JDK 23 or higher.
- **Maven**: Installed and configured.
- **Database**: A running instance of any supported database (MySQL, PostgreSQL, SQLite, H2, etc.).

---

## Getting Started

### Running the Backend API

1. Clone the repository:
   ```bash
   git clone https://github.com/Srujith2k/DBOne.git
   cd DBOne
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the Swagger UI at:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

---

### Database-Specific Query Endpoints

#### **1. MySQL Query Execution**
**Endpoint**: `POST /api/db/mysql/query`

**Request Body**:
```json
{
    "host": "localhost",
    "port": 3306,
    "database": "testdb",
    "username": "root",
    "password": "password123",
    "query": "SELECT * FROM users"
}
```

#### **2. PostgreSQL Query Execution**
**Endpoint**: `POST /api/db/postgresql/query`

**Request Body**:
```json
{
    "host": "localhost",
    "port": 5432,
    "database": "testdb",
    "username": "postgres",
    "password": "password123",
    "query": "SELECT * FROM "Employees"."Department" ORDER BY "Emp_id" ASC"
}
```

#### **3. SQLite Query Execution**
**Endpoint**: `POST /api/db/sqlite/query`

**Request Body**:
```json
{
    "database": "testdb",
    "query": "SELECT * FROM products"
}
```

#### **4. H2 Query Execution**
**Endpoint**: `POST /api/db/h2/query`

**Request Body**:
```json
{
    "host": "localhost",
    "port": 9092,
    "database": "testdb",
    "username": "sa",
    "password": "",
    "query": "SELECT * FROM orders"
}
```

---

## Using DBOneClient in Your Java Project

### **Add the Dependency**

If published to Maven Central, add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>com.acender</groupId>
    <artifactId>dbone</artifactId>
    <version>1.0.0</version>
</dependency>
```

### **Example Usage of `DBOneClient`**
```java
import com.acender.dbone.client.DBOneClient;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        DBOneClient client = new DBOneClient("http://localhost:8080/api/db");

        // Test MySQL Connection
        String connectionResult = client.testMySQLConnection("localhost", 3306, "testdb", "root", "password123");
        System.out.println(connectionResult);

        // Run a MySQL Query
        List<Map<String, Object>> queryResult = client.executeMySQLQuery("localhost", 3306, "testdb", "root", "password123", "SELECT * FROM users");
        System.out.println(queryResult);
    }
}
```

---

### Testing Database Connections

1. Open the Swagger UI.
2. Use the following endpoints to test database connectivity:
   - **MySQL**: `/api/db/mysql/test`
   - **PostgreSQL**: `/api/db/postgresql/test`
   - **SQLite**: `/api/db/sqlite/test`
   - **H2**: `/api/db/h2/test`

3. Example request body:
   ```json
   {
       "host": "localhost",
       "port": 3306,
       "database": "testdb",
       "username": "root",
       "password": "password123"
   }
   ```

4. Responses:
   - **200 OK**: Connection successful.
   - **400 Bad Request**: Connection failed with an error message.

---

## Example Endpoints
1. **Test MySQL Connection**:
   ```http
   POST /api/db/mysql/test
   ```

2. **Test PostgreSQL Connection**:
   ```http
   POST /api/db/postgresql/test
   ```

3. **Test SQLite Connection**:
   ```http
   POST /api/db/sqlite/test
   ```

4. **Test H2 Connection**:
   ```http
   POST /api/db/h2/test
   ```

5. **Run Custom Query (MySQL)**:
   ```http
   POST /api/db/mysql/query
   ```

---

## Future Enhancements
- **Desktop Application**: A JavaFX-based application for GUI-based interaction with the database.
- **Web Application**: A React-based frontend for a user-friendly database management experience.
- **Additional Database Support**: Oracle, MongoDB, etc.
- **Advanced Query Features**: APIs for batch processing, joins, and database migrations.

---

## Contributing
We welcome contributions to enhance DBOne! Here’s how you can help:
1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add your feature description here"
   ```
4. Push the branch:
   ```bash
   git push origin feature/your-feature-name
   ```
5. Open a pull request.

---

## License
This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

### Future Licensing
Additional premium features may be released under a commercial license in the future.

---

## Contact
For questions, suggestions, or feedback:
- **Email**: srujith2k@gmail.com
- **GitHub Issues**: [Create an Issue](https://github.com/Srujith2k/DBOne/issues)
