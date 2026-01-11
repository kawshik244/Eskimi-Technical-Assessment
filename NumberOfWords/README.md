# Task 2: Number to Words Converter

##  Problem Description
A REST API that converts a provided number into its English word representation.
* **Input:** Decimals up to 2 places, smaller than 1000.
* **Output:** Lowercase words, no hyphens, and no "and".

##  Algorithm & Logic
The service splits the input string into integer and decimal parts to preserve precision (e.g., handling trailing zeros like `.40`).
1. **Integer Part:** - Uses lookup arrays for `UNITS` (0-19) and `TENS` (20, 30, etc.).
    - Recursively breaks down hundreds and tens (e.g., 105 -> "one" + "hundred" + "five").
2. **Decimal Part:**
    - Iterates through each digit after the decimal point.
    - Converts each digit individually to its word equivalent (e.g., .40 -> "point four zero").

###  Corner Cases Handled
- **Trailing Zeros:** Explicitly handles inputs like `36.40` as "thirty six point four zero".
- **Zero Values:** Correctly handles `0`, `0.5`, or `0.00`.
- **Formatting:** Trims extra spaces and ensures lowercase output without hyphens.
- **Boundaries:** Validates that numbers are strictly `< 1000` and `>= 0`.

###  Error Handling
- **Invalid Format:** Returns `400 Bad Request` if the input is not a valid number or has more than 2 decimal places.
- **Out of Range:** Returns `400 Bad Request` if the number is 1000 or greater.

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

**Success Criteria:** The build logs will show **BUILD SUCCESS** and verify that tests have passed. Unit tests are automatically executed as part of the build process to ensure logic correctness.


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
java -jar target/NumberOfWords-0.0.1-SNAPSHOT.jar
```
The application will start on:
```bash
http://localhost:8061
```

Notes:

1. Ensure JAVA_HOME is set correctly before building/running.
2. If ports are in use, change the server port in application.properties (if needed).



##  Docker Instructions

### Build the Image

Navigate to the NumberOfWords directory and run:

```bash
docker build -t num-words-app .
```
**Important:** Make sure the jar file is present in the target directory.
### Run the Container
Run the following command to start the application on port 8061:
```bash
docker run -p 8061:8061 num-words-app
```
## API Usage
### EndPoint
```bash
POST /api/v1/numbers/convert
```
### Content-Type
```bash
application/json
```

### Example Request:
* **Windows(PowerShell):**
```bash
curl -X POST http://localhost:8061/api/v1/numbers/convert `
  -H "Content-Type: application/json" `
  -d '{\"number\":\"36.40\"}'
```
* **macOS/Linux:**
```bash
curl -X POST http://localhost:8061/api/v1/numbers/convert \
  -H "Content-Type: application/json" \
  -d '{"number":"36.40"}'
```
* **Using Postman:**
1. Create a new POST request
2. URL: http://localhost:8061/api/v1/numbers/convert
3. Headers: Content-Type: application/json
4. Body (raw JSON):
```bash
{
  "number":"36.40"
}
```

### Expected Response:
```bash
{
    "result": "thirty six point four zero"
}
```
