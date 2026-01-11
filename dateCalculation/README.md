# Task 1: Number of Days Calculator

##  Problem Description
A REST API application that calculates the number of days between two given dates.

**Constraint:** This implementation strictly avoids using any built-in date libraries (like `java.time` or `java.util.Date`) for the calculation logic, relying instead on custom algorithms to handle leap years and days-in-month logic.

##  Algorithm & Logic
The core logic converts any given date into an **"Absolute Day Count"** (total days elapsed since Year 0).
1. **Year Calculation:** Iterates from Year 0 to the current year, adding 365 days for standard years and 366 for leap years.
2. **Month Calculation:** Adds days for completed months in the current year, using a lookup array for days in each month.
    - *Leap Year Check:* If the year is a leap year, February is treated as having 29 days.
3. **Difference:** The result is the absolute difference between the two "Absolute Day Counts".

###  Corner Cases Handled
- **Leap Years:** Correctly handles standard leap years (divisible by 4) and century rules (divisible by 400 but not 100).
- **Same Date:** Returns `0` if start and end dates are identical.
- **Year Boundaries:** Correctly calculates differences across multiple years or centuries (e.g., Dec 31st to Jan 1st).
- **Input Validation:** Rejects non-existent dates (e.g., Feb 30th) or invalid formats.

###  Error Handling
The application uses a `GlobalExceptionHandler` to catch exceptions and return clean JSON responses:
- **Invalid Date Format:** Returns `400 Bad Request` with a clear message if inputs are not `YYYY-MM-DD`.
- **Logical Errors:** Returns `400 Bad Request` if days are out of range (e.g., month 13, day 32).

##  Prerequisites
* Java 17
* Maven 3.6+ (or use the included Maven Wrapper)
* Docker Desktop

## Installing prerequisites
### Windows
1. Download JDK 17 from Oracle or Adoptium and install it.
2. Set the `JAVA_HOME` environment variable to your JDK installation path.
3. Maven is included as the Maven Wrapper (`mvnw.cmd`) — no separate Maven install required.

### macOS
Using Homebrew:
```bash
brew install openjdk@17
```
(After install, you may need to add the JDK to your PATH or follow Homebrew's post-install instructions.)

### Linux
Ubuntu / Debian
```bash
sudo apt-get install openjdk-17-jdk
```
Fedora
```bash
sudo dnf install java-17-openjdk-devel
```

## Verify installation
```bash
java -version
# Should show version 17.x.x
```


##  Building the application

* **Option 1**: Using Maven Wrapper (recommended — no Maven installation needed).
### Windows
```bash
mvnw.cmd clean install
```
### macOs / Linux
```bash
./mvnw clean install
```

* **Option 2**: Using installed Maven.
```bash
mvn clean install
```
This will:

1. Compile the source code

2. Run all unit tests

3. Package the application as a JAR file

**Success Criteria:** The build logs will show **BUILD SUCCESS** and verify that tests have passed.

**Unit tests** are automatically executed as part of the build process to ensure logic correctness.


##  Running the application

* **Option 1**: Using Maven Wrapper.
#### Windows
```bash
mvnw.cmd spring-boot:run
```
#### macOs / Linux
```bash
./mvnw spring-boot:run
```

* **Option 2**: Using the JAR file
```bash
java -jar target/dateCalculation-0.0.1-SNAPSHOT.jar
```
The application will start on:
```bash
http://localhost:8060
```

Notes:

1. Ensure JAVA_HOME is set correctly before building/running.
2. If ports are in use, change the server port in application.properties (if needed).



##  Docker Instructions

### Build the Image

Navigate to the dateCalculation directory and run:

```bash
docker build -t date-calculator-app .
```
**Important:** Make sure the jar file is present in the target directory.
### Run the Container
Run the following command to start the application on port 8060:
```bash
docker run -p 8060:8060 date-calculator-app
```
## API Usage
### EndPoint
```bash
POST /api/v1/calculate
```
### Content-Type
```bash
application/json
```

### Example Request:
* **Windows(PowerShell):**
```bash
curl -X POST http://localhost:8060/api/v1/calculate `
  -H "Content-Type: application/json" `
  -d '{\"startDate\":\"2023-01-01\",\"endDate\":\"2023-02-01\"}'
```
* **macOS/Linux:**
```bash
curl -X POST http://localhost:8060/api/v1/calculate \
  -H "Content-Type: application/json" \
  -d '{"startDate":"2023-01-01","endDate":"2023-02-01"}'
```
* **Using Postman:**
1. Create a new POST request
2. URL: http://localhost:8060/api/v1/calculate
3. Headers: Content-Type: application/json
4. Body (raw JSON):
```bash
{
  "startDate": "2023-01-01",
  "endDate": "2023-02-01"
}
```

### Expected Response:
```bash
{
    "difference": 31
}
```
