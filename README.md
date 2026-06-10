# Web Automation Framework

<div align="center">

![Web Automation Framework](https://img.shields.io/badge/Web%20Automation%20Framework-v1.0-blueviolet?style=for-the-badge)

[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)](https://adoptium.net/)
[![Selenium](https://img.shields.io/badge/Selenium-4.36.0-43B02A?style=flat-square&logo=selenium)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red?style=flat-square)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=flat-square&logo=apachemaven)](https://maven.apache.org/)
[![CI](https://github.com/YOUR_USERNAME/web-automation-framework/actions/workflows/ci.yml/badge.svg)](https://github.com/YOUR_USERNAME/web-automation-framework/actions)

</div>

---

## Overview

A Selenium WebDriver automation framework built with Java, TestNG, and Maven following the **Page Object Model** design pattern.

The framework demonstrates a complete test suite for a login flow — covering happy path, unhappy path, and edge case scenarios. Tests are structured for readability, maintainability, and CI/CD integration.

**Practice application under test:** [SauceDemo](https://www.saucedemo.com) — a stable demo e-commerce site built for Selenium practice.

---

## Tech Stack

| Tool | Purpose |
|---|---|
| **Java 17 (Temurin)** | Primary language |
| **Selenium WebDriver 4.36** | Browser automation |
| **TestNG 7.10** | Test runner and assertions |
| **Maven** | Build tool and dependency management |
| **GitHub Actions** | CI pipeline — runs tests on every push |

---

## Project Structure

```
web-automation-framework/
├── .github/
│   └── workflows/
│       └── ci.yml                  ← GitHub Actions CI pipeline
├── src/
│   └── test/
│       └── java/
│           ├── LoginPage.java      ← Page Object — login page interactions
│           └── LoginTest.java      ← Test class — all login scenarios
└── pom.xml                         ← Maven dependencies
```

---

## Project Structure Flowchart

```
pom.xml
(declares Selenium + TestNG)
         │
         ▼
   LoginTest.java
   ┌─────────────────────────────────┐
   │  @BeforeMethod setUp()          │
   │  → opens Chrome                 │
   │  → navigates to SauceDemo       │
   │  → creates LoginPage object     │
   │                                 │
   │  @Test (x8)                     │──────► LoginPage.java
   │  → calls loginPage methods      │        ┌──────────────────────────┐
   │  → asserts the result           │        │  enterUsername(String)   │
   │                                 │        │  enterPassword(String)   │
   │  @AfterMethod tearDown()        │        │  clickLoginButton()      │
   │  → closes Chrome                │        │  getTitle() → String     │
   └─────────────────────────────────┘        │  getErrorMessage()→String│
                                              └──────────────────────────┘
```

---

## Test Flow

```
START
  │
  ▼
@BeforeMethod
Open Chrome → Navigate to https://www.saucedemo.com
  │
  ▼
@Test runs
  │
  ├── Enter username
  ├── Enter password
  ├── Click Login
  └── Assert result
        │
        ├── PASS → expected text found ✅
        └── FAIL → actual text did not match expected ❌
  │
  ▼
@AfterMethod
Close Chrome
  │
  ▼
Repeat for next @Test
```

---

## Test Coverage

### Happy Path ✅
| Test | Credentials | Expected |
|---|---|---|
| `loginWithValidCredentials` | standard_user / secret_sauce | Products page title |

### Unhappy Paths ❌
| Test | Credentials | Expected |
|---|---|---|
| `loginWithInValidPassword` | standard_user / wrong | Credentials mismatch error |
| `loginWithInValidUsername` | wrong / secret_sauce | Credentials mismatch error |
| `loginWithInValidCredentials` | wrong / wrong | Credentials mismatch error |

### Edge Cases ⚠️
| Test | Credentials | Expected |
|---|---|---|
| `loginWithEmptyUsername` | _(blank)_ / secret_sauce | Username is required |
| `loginWithEmptyPassword` | standard_user / _(blank)_ | Password is required |
| `loginWithEmptyUsernameAndPassword` | _(blank)_ / _(blank)_ | Username is required |
| `loginWithLockedOutUser` | locked_out_user / secret_sauce | User locked out error |

---

## How to Run

### Prerequisites
- Java 17 (Temurin recommended)
- Maven
- Google Chrome

### Run all tests
```bash
mvn test
```

### Run headless (no browser window)
```bash
mvn test -Dheadless=true
```

### Run a single test
```bash
mvn test -Dtest=LoginTest#loginWithValidCredentials
```

---

## Design Decisions

**Page Object Model** — All page interactions live in `LoginPage.java`. Tests never reference locators directly. If a locator changes, it is fixed in one place only.

**Single Responsibility Principle** — `LoginPage` handles page interactions. `LoginTest` handles test flow and assertions. Each class has one clear responsibility.

**DRY (Don't Repeat Yourself)** — Browser setup and teardown are centralised in `@BeforeMethod` and `@AfterMethod`. No duplication across tests.

**Explicit test naming** — Test method names describe exactly what is being tested, making failures immediately understandable without reading the code.
