# REST API Automation Framework

A portfolio-ready API automation framework built with Java, Rest Assured, TestNG,
Jackson, Maven, JSON Schema Validator, and ExtentReports. The sample suite covers
the ReqRes API with positive and negative scenarios.

## Coverage

- GET list and single-resource validation
- POST request serialization and response assertions
- PUT update validation
- DELETE status and empty-body validation
- Successful and unsuccessful authentication
- HTTP status, headers/body, response-time, and nested-field assertions
- JSON Schema validation
- Parallel TestNG execution and automatic HTML reporting
- Environment/system-property configuration with no secrets committed

## Project structure

```text
src/test/java/com/sdet
├── framework
│   ├── client       Reusable HTTP verbs
│   ├── config       Configuration resolution
│   ├── listeners    ExtentReports lifecycle
│   ├── models       Jackson request/response records
│   └── specs        Shared request/response specifications
└── tests            TestNG API scenarios
src/test/resources
├── config.properties
└── schemas          JSON contract schemas
```

## Run the suite

Prerequisites: JDK 17+ and Maven 3.9+.

```bash
mvn clean test
```

ReqRes requires an API key. Supply your current key without changing source
control; without one, the live suite is intentionally skipped:

```bash
mvn clean test -Dapi.key=YOUR_KEY
mvn clean test -Dbase.uri=https://reqres.in -Dapi.key=YOUR_KEY
```

PowerShell environment-variable alternative:

```powershell
$env:REQRES_API_KEY = "YOUR_KEY"
mvn clean test
```

After execution, open `target/reports/api-test-report.html`. Maven also produces
the standard TestNG/Surefire results under `target/surefire-reports`.

## Build stages

1. Maven project and dependencies
2. External configuration and reusable request/response specifications
3. Generic API client and Jackson models
4. CRUD and negative TestNG scenarios
5. Authentication coverage
6. JSON Schema contract validation
7. Parallel execution, failure logging, and HTML reporting

## Resume wording

Built a modular REST API automation framework using Java, Rest Assured, TestNG,
Jackson, and Maven; automated CRUD and authentication workflows with reusable
request specifications, response assertions, JSON Schema validation, parallel
execution, failure diagnostics, and Extent HTML reporting.
