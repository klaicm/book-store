# Online Book Store Service

This is a sample online book store service built using Spring Boot v2, Java v17, and Maven. It provides REST APIs for managing books, customers, and purchases
in a bookstore.
Demo project for Huddle.

## Getting Started

### Prerequisites

To run this project, you will need:

- Java v17 or later (openjdk-17-jdk)
- Maven v3.8.4 or later

### Installing

1. Clone this repository to your local machine.
2. Navigate to the root directory of the project.
3. Run `mvn clean install` to build the project.

### Running the application

1. Run `mvn spring-boot:run` to start the application.
2. The application will start running at http://localhost:8080.

### Running tests

1. Run `mvn test`

## Usage

### REST APIs

The application provides the following REST APIs:

#### Books

- `GET /books`: Get a list of all books.
- `GET /books/{uuid}`: Get a book by UUID.
- `POST /books`: Create a new book.

#### Customers

- `GET /customers`: Get a list of all customers.
- `GET /customers/{uuid}`: Get a customer by UUID.
- `POST /customers`: Create a new customer.
- `POST /customers/{uuid}/purchases`: Get a customer with all his purchases.
- `POST /customers/search`: Get a list of customers by first or last name in search parameter

#### Purchases

- `GET /purchases`: Get a list of all purchases.
- `GET /purchases/{uuid}`: Get a purchase by UUID.
- `POST /purchases`: Create a new purchase.

#### Book Types

- `GET /book-types`: Get a list of all book types.

### Swagger UI

Complete API Documentation can be found on http://localhost:8080/swagger-ui/index.html. All REST APIs can be accessed through there.

### Examples

If you are more of a terminal guy then follow next examples (f.e. using curl):

- Get all customers
  ```bash
  curl -X 'GET' \
  'http://localhost:8080/customers' \
  -H 'accept: application/json'
  ```
  
- Search customer by search term
  ```bash
  curl -X 'GET' \
    'http://localhost:8080/customers/search?searchTerm=Jaz' \
    -H 'accept: application/json'
  ```

- Get all books
  ```bash
  curl -X 'GET' \
  'http://localhost:8080/books' \
  -H 'accept: application/json'
  ```
  
- Adding new book
  ```bash
  curl -X 'POST' \
  'http://localhost:8080/books' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "The Great Gatsby",
    "quantity": 15,
    "bookTypeEnum": "OLD_EDITION",
    "price": 100
  }'
  ```

- Adding new customer
  ```bash
  curl -X 'POST' \
  'http://localhost:8080/customers' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "John",
    "lastName": "Doe"
  }'
  ```

- Adding new purchase

  ````bash
  curl -X 'POST' \
  'http://localhost:8080/purchases' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "customer": {
      "uuid": "efbc2e71-fa07-4255-b69d-e49719fd6108"
    },
    "purchasedBooks": [
      {
        "uuid": "d1f6b0d0-7495-476a-a8a7-81c42bff111a",
        "quantity": 1
      },
      {
        "uuid": "aab3e188-4924-4bc3-9919-7f5591b67817",
        "quantity": 5
      },
      {
        "uuid": "f4a83d13-c29c-42da-97fc-addd750328c8",
        "quantity": 3
      }
    ]
  }'
  ````
  
## H2 Database
- Local H2 database can be accessed on: http://localhost:8080/h2-console/

Access data is set default in application.yml so you can immediately connect but for any case:
```
    url: jdbc:h2:mem:mydb
    username: sa
    password:
    driverClassName: org.h2.Driver
```

## Built With

- Spring Boot v2 - Web framework used
- Java v17 - Programming language used
- Maven - Dependency management tool used

## Author

Martin Klaic - martin.klaic@gmail.com