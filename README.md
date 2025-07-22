# URL Shortener

A powerful and feature-rich URL shortener service built with Java & Spring Boot. This project allows you to shorten long URLs, retrieve original URLs from shortcodes, update and delete mappings, and track analytics such as how many times each shortened URL has been accessed.

## Table of Contents

- [Features](#features)
- [API Endpoints](#api-endpoints)
- [Usage Examples](#usage-examples)
  - [1. Creating a Shortcode for a URL](#1-creating-a-shortcode-for-a-url)
  - [2. Trying to Create Another Shortcode for the Same URL](#2-trying-to-create-another-shortcode-for-the-same-url)
  - [3. Getting the Original URL from the Shortcode](#3-getting-the-original-url-from-the-shortcode)
  - [4. Trying to Get Long URL for a Shortcode That Doesn’t Exist](#4-trying-to-get-long-url-for-a-shortcode-that-doesnt-exist)
  - [5. Update the Long URL Mapped to the Shortcode](#5-update-the-long-url-mapped-to-the-shortcode)
  - [6. Trying to Update a Shortcode That Doesn’t Exist](#6-trying-to-update-a-shortcode-that-doesnt-exist)
  - [7. Delete URL Using a Shortcode](#7-delete-url-using-a-shortcode)
  - [8. Trying to Delete It Again](#8-trying-to-delete-it-again)
  - [9. Analytics: How Many Times URL Has Been Used](#9-analytics-how-many-times-url-has-been-used)
- [Data Model](#data-model)
- [Architecture Overview](#architecture-overview)
- [How it Works](#how-it-works)
- [Setup & Running Locally](#setup--running-locally)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- **Shorten URLs**: Generate a unique shortcode for any long URL.
- **Idempotent Shortening**: The same URL always maps to the same shortcode—no duplicate entries.
- **Retrieve Original URLs**: Get the original long URL from a shortcode.
- **Update Mappings**: Update the long URL associated with a given shortcode.
- **Delete Mappings**: Remove a shortcode and its mapping.
- **Analytics**: Track and retrieve how many times a shortcode has been used to fetch the original URL.
- **Error Handling**: Graceful handling for duplicate, missing, or invalid shortcodes.
- **RESTful API**: Clean, predictable endpoints for all operations.

---

## API Endpoints

| Method | Endpoint                         | Description                                    |
|--------|----------------------------------|------------------------------------------------|
| POST   | `/shorten`                       | Shorten a new URL                              |
| GET    | `/shorten/{shortCode}`           | Retrieve the original URL from a shortcode      |
| PUT    | `/shorten/{shortCode}`           | Update the long URL mapped to a shortcode       |
| DELETE | `/shorten/{shortCode}`           | Delete a shortcode mapping                      |
| GET    | `/shorten/{shortCode}/stats`     | Get analytics (fetch count) for a shortcode     |

---

## Usage Examples

Below are real Postman API response screenshots for each scenario.

### 1. Creating a Shortcode for a URL

Send a POST to `/shorten` with a JSON payload:
```json
{
  "url": "https://example.com/your-long-url"
}
```
**Response:**

<img width="849" height="542" alt="image" src="https://github.com/user-attachments/assets/723cfa08-efa9-414c-be6e-4b158d187c66" />



---

### 2. Trying to Create Another Shortcode for the Same URL

Send the same POST request again.
**Response:**

<img width="836" height="690" alt="image" src="https://github.com/user-attachments/assets/a032f021-903f-42b6-ad52-4b7c1e2469be" />


---

### 3. Getting the Original URL from the Shortcode

Send a GET to `/shorten/{shortCode}`.
**Response:**

<img width="865" height="578" alt="image" src="https://github.com/user-attachments/assets/12c39a32-1b8c-4643-9146-798468e1b5cf" />


---

### 4. Trying to Get Long URL for a Shortcode That Doesn’t Exist

Send a GET to `/shorten/{invalidShortCode}`.
**Response:**

<img width="825" height="638" alt="image" src="https://github.com/user-attachments/assets/3f70bc7a-d985-4c3b-b433-3dc37662f7e1" />


---

### 5. Update the Long URL Mapped to the Shortcode

**Before Update:**  
GET `/shorten/{shortCode}`

<img width="784" height="590" alt="image" src="https://github.com/user-attachments/assets/43b8649d-42c8-403c-888d-e43298a84673" />


**Update:**  
PUT `/shorten/{shortCode}`  
Payload:
```json
{
  "url": "https://example.com/your-new-updated-url"
}
```

**After Update:**  
GET `/shorten/{shortCode}`

<img width="852" height="632" alt="image" src="https://github.com/user-attachments/assets/9477255d-ca37-4e6d-8abb-112be0e45f22" />


<img width="822" height="617" alt="image" src="https://github.com/user-attachments/assets/26bea709-bd5f-4c1b-be14-9ee48def2722" />


---

### 6. Trying to Update a Shortcode That Doesn’t Exist

PUT `/shorten/{invalidShortCode}`  
Payload:
```json
{
  "url": "https://example.com/your-new-updated-url"
}
```
**Response:**

<img width="839" height="612" alt="image" src="https://github.com/user-attachments/assets/c77b755d-ad74-45c6-82c5-492680b81852" />


---

### 7. Delete URL Using a Shortcode

DELETE `/shorten/{shortCode}`
**Response:**

<img width="734" height="369" alt="image" src="https://github.com/user-attachments/assets/bce53696-f96c-4b29-8e17-49e534059c13" />


---

### 8. Trying to Delete It Again

DELETE `/shorten/{shortCode}`
**Response:**

<img width="890" height="481" alt="image" src="https://github.com/user-attachments/assets/01e19a7a-d5c0-48f9-a63a-0fbaa3d8341e" />


---

### 9. Analytics: How Many Times URL Has Been Used

GET `/shorten/{shortCode}/stats`
**Response:**

<img width="892" height="532" alt="image" src="https://github.com/user-attachments/assets/44aa89a2-a8b6-40cd-970b-babca89f79b7" />


---

## Data Model

The primary entity is `Url`:
- `id`: Unique identifier (auto-generated)
- `url`: The original long URL (unique)
- `shortCode`: The generated shortcode (unique, 6 alphanumeric characters)
- `urlCreatedAt`: When the mapping was created
- `urlUpdatedAt`: Last updated timestamp
- `getCount`: Number of times the shortcode has been used

## Architecture Overview

- **Controller Layer**: Handles API requests and responses.
- **Service Layer**: Business logic for URL operations, analytics, and validations.
- **Mapper Layer**: Translates between DTOs and entities.
- **Repository Layer**: Interacts with the database (e.g., JPA Repository).
- **Helper Service**: Generates shortcodes and updates analytics.
- **Error Handling**: Custom exceptions for all error cases.

## How it Works

1. **Shortening a URL**:
   - Accepts a URL, checks for uniqueness, generates a 6-character shortcode, and stores the mapping.
2. **Fetching a URL**:
   - Accepts a shortcode, retrieves the long URL, increments the access counter, and returns the URL.
3. **Updating a Mapping**:
   - Accepts a new URL and shortcode, validates existence, updates the mapping.
4. **Deleting a Mapping**:
   - Accepts a shortcode, validates existence, deletes the mapping.
5. **Analytics**:
   - Tracks how many times each shortcode has been used.

## Setup & Running Locally

1. **Clone the repository**:
   ```bash
   git clone https://github.com/DarkLord-13/URL_Shortner.git
   cd URL_Shortner
   ```
2. **Configure Database**: Update `application.properties` with your DB credentials.
3. **Build and Run**:
   ```bash
   ./mvnw spring-boot:run
   ```
4. **API Documentation**: Use Postman or Swagger UI (if enabled) to interact with the endpoints.

## Contributing

Pull requests are welcome! Please open an issue first to discuss any major changes.

## License

This project is open source and available under the [MIT License](LICENSE).
