# Task 3: Temperature Stats for Dhaka

##  Problem Description
A REST API that retrieves historical temperature data for Dhaka using the Open-Meteo API. It returns the Minimum, Maximum, and Average temperatures for a given date range, along with their textual representations.

**Key Features:**
* Integration with `archive-api.open-meteo.com`.
* Reuses logic from Task 1 (Date Validation) and Task 2 (Number to Words).
* Handles "positive" and "minus" prefixes for textual output.

##  Algorithm & Logic
1. **Validation:** Checks if the date range is valid and ensures dates are in the past (Archive API restriction).
2. **Data Fetching:** Calls the Open-Meteo Archive API to get daily mean temperatures (`temperature_2m_mean`).
3. **Statistical Analysis:** Uses Java Streams to filter `null` values and calculate `Min`, `Max`, and `Average`.
4. **Text Conversion:** - Rounds all values to 2 decimal places for consistency.
    - Prefixes "positive" or "minus" based on the value.
    - Converts the numeric magnitude to words using the Task 2 logic.

###  Corner Cases Handled
- **Future Dates:** Blocks requests for future dates as archive data wouldn't exist.
- **Missing Data:** Handles scenarios where the external API returns `null` or partial data points.
- **Negative Temperatures:** Correctly formats text as "minus..." for negative values.
- **Precision:** Strictly formats averages (e.g., `-1.44`) to ensure text matches the number exactly.

###  Error Handling
- **API Failures:** If Open-Meteo is down or unreachable, a `500 Internal Server Error` is returned.
- **Invalid Requests:** Returns `400 Bad Request` if the start date is after the end date.

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

Unit tests use **Mockito** to mock external API calls, ensuring the build passes even without network connectivity


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
java -jar target/dhakaweather-0.0.1-SNAPSHOT.jar
```
The application will start on:
```bash
http://localhost:8071
```

Notes:

1. Ensure JAVA_HOME is set correctly before building/running.
2. If ports are in use, change the server port in application.properties (if needed).



##  Docker Instructions

### Build the Image

Navigate to the dhakaweather directory and run:
```bash
docker build -t dhaka-weather-app .
```
**Important:** Make sure the jar file is present in the target directory.
### Run the Container
Run the following command to start the application on port 8071:
```bash
docker run -p 8071:8071 dhaka-weather-app
```
## API Usage
### EndPoint
```bash
POST /api/v1/weather/dhaka
```
### Content-Type
```bash
application/json
```

### Example Request:
* **Windows(PowerShell):**
```bash
curl -X POST http://localhost:8071/api/v1/weather/dhaka `
  -H "Content-Type: application/json" `
  -d '{\"startDate\":\"2023-01-01\",\"endDate\":\"2023-01-03\"}'
```
* **macOS/Linux:**
```bash
curl -X POST http://localhost:8071/api/v1/weather/dhaka \
  -H "Content-Type: application/json" \
  -d '{"startDate": "2023-01-01", "endDate": "2023-01-03"}'
```
* **Using Postman:**
1. Create a new POST request
2. URL: http://localhost:8071/api/v1/weather/dhaka
3. Headers: Content-Type: application/json
4. Body (raw JSON):
```bash
{"startDate": "2023-01-01", "endDate": "2023-01-03"}
```

### Expected Response:
```bash
{
    "min": 12.5,
    "max": 25.4,
    "average": 18.95,
    "minText": "positive twelve point five zero",
    "maxText": "positive twenty five point four zero",
    "averageText": "positive eighteen point nine five"
}
```
