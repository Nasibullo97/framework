# ZipRecruiter Selenium TestNG Automation Framework

This is a robust, scalable, and extensible automation framework for testing the ZipRecruiter web application using Selenium WebDriver, TestNG, and Java. It is designed for maintainability, parallel execution, advanced reporting, and easy configuration.

## Features
- **Page Object Model (POM)** for maintainable and reusable code
- **Cross-browser support** (Chrome, Firefox, Edge)
- **Parallel test execution** with TestNG
- **Advanced reporting** with Allure
- **Dynamic waits and robust error handling**
- **Environment configuration** via properties files
- **Test data management** (factories, CSV support)
- **Automatic screenshot capture on failure**
- **CI/CD ready** (GitHub Actions workflow included)
- **Accessibility, edge case, and negative testing**

## Project Structure
```
├── src
│   └── test
│       ├── java
│       │   └── com.ziprecruiter
│       │       ├── base
│       │       ├── config
│       │       ├── data
│       │       ├── pages
│       │       ├── tests
│       │       └── utils
│       └── resources
│           ├── config.properties.example
│           └── ...
├── testng.xml
├── pom.xml
├── .gitignore
└── README.md
```

## Getting Started

### 1. Clone the Repository
```sh
git clone https://github.com/yourusername/framework.git
cd framework
```

### 2. Install Dependencies
```sh
mvn clean install
```

### 3. Configure Environment
- Copy `src/test/resources/config.properties.example` to `src/test/resources/config.properties` and update with your environment values (do **not** commit real credentials).

### 4. Run Tests
```sh
mvn test
```
Or run a specific suite:
```sh
mvn test -DsuiteXmlFile=testng.xml
```

## Configuration
- All environment and test settings are managed in `src/test/resources/config.properties`.
- **Never commit real credentials!** Use `.gitignore` to exclude sensitive files.

## Reporting
- Allure reports are generated after test execution. To view:
```sh
allure serve target/allure-results
```

## CI/CD
- GitHub Actions workflow is included for automated test execution on push/PR.

## Contribution
Pull requests are welcome! Please open issues for bugs or feature requests.

## License
[MIT](LICENSE) 