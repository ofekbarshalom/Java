# BusyBee

BusyBee is a Spring Boot task-management web app focused on secure-by-default behavior (HTTPS, authenticated access, CSRF support, and role-based authorization).

## Tech Stack

- Java 21
- Gradle (wrapper included)
- Spring Boot 3
- Spring Security
- Springdoc OpenAPI

## Project Structure

- `src/main/java/com/securefromscratch/busybee` – application code (controllers, auth, storage, safety types)
- `src/main/resources/public` – login/registration pages and shared frontend helpers
- `src/main/resources/static` – authenticated app pages (`main`, `create`, `import`)
- `uploads` – uploaded files used by the app
- `spoilers` – intentionally insecure/learning examples

## Prerequisites

- JDK 21 installed and available in your environment
- A TLS keystore file at `src/main/resources/keystore.p12`

BusyBee is configured to run on HTTPS only:

- Port: `8443`
- Keystore path: `classpath:keystore.p12`
- Keystore password env var: `SSL_KEYSTORE_PASSWORD`

## Create a Local Keystore

If you do not already have `keystore.p12`, you can create one with `keytool`:

```powershell
keytool -genkeypair `
  -alias busybee `
  -keyalg RSA `
  -keysize 2048 `
  -storetype PKCS12 `
  -keystore src/main/resources/keystore.p12 `
  -validity 3650
```

When prompted for the keystore password, use the same value you will set in `SSL_KEYSTORE_PASSWORD`.

## Run the App (Windows PowerShell)

From the `busybee` directory:

```powershell
$env:SSL_KEYSTORE_PASSWORD="your-keystore-password"
.\gradlew.bat bootRun
```

Then open:

- `https://localhost:8443/`

Your browser may show a certificate warning for local self-signed certificates.

## Demo Users

On startup, the app pre-populates users and prints generated plaintext passwords to the console output:

- `Yariv` (`ADMIN`)
- `Or` (`CREATOR`)
- `Eyal` (`TRIAL`)

You can also register new users from the login page (`/register`), and they are created with the `TRIAL` role.

## API & Security Notes

- Form login endpoint: `/login`
- CSRF token endpoint: `/gencsrftoken`
- OpenAPI UI (when running): `https://localhost:8443/swagger-ui/index.html`
- All requests are forced to HTTPS and most routes require authentication.

## Run Tests

```powershell
.\gradlew.bat test
```
