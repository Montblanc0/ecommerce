# E_COMMERCE REST API W/ JWT OAUTH2

A RESTful API built on top of a legacy MySQL database with CRUDs, OAUTH2 authentication and authorization-based access
provided by JSON Web Tokens.

e_commerce is a development project built with Maven v3.6.2, Spring Boot v2.7.13, Hibernate v5.6.5 and Java SDK v8.

## Database

![Database diagram](https://i.ibb.co/RPtCTZt/e-commerce.png "Database diagram")

MariaDB (MySQL) v10.4.28 provided by [XAMPP](https://www.apachefriends.org/index.html) v8.2.4 has been configured as the
default persistence provider.

A legacy MySQL database formerly used for educational purposes has been expanded and tuned to include authentication
details and triggers to automatically create order numbers and to determine the total price by summing up the cost of
the items net of any discounts.

## Development Setup

### 1. Create (and seed) the database

Assuming you access your MySQL server via phpMyAdmin, just **import** the provided `e_commerce_FULL.sql` file from the
main page. Make sure you're not selecting any database, as this file will take care of creating a database named '
e_commerce' and populate it with tables and sample data.

If you'd rather starting clean, you can opt to import the database structure without any data by importing the
provided `e_commerce_STRUCTURE.sql` instead.

### 2. Create an OpenSSL RSA key pair

On Linux and WSL, you can generate a RSA Key Pair by executing the provided bash script:

```bash
    $ cd src/main/resources/certs
    $ chmod +x generate_keys.sh
    $ ./generate_keys.sh
```

This script requires the `openssl` package to be installed.

I suggest you search how to create a OpenSSL RSA key pair if using another operating system.

### 3. Enter server details

> If you're using a vanilla XAMPP installation, these properties should already reflect the default settings, so you can
> skip this part.

In the provided `src/main/resources/application.properties` configuration file, enter the username and password for your
MySQL server by editing the `spring.datasource.username` and `spring.datasource.password`. Ensure that the port defined
in `spring.datasource.url` is correct.

You can change Tomcat's server port by uncommenting the `server.port` property and changing its value.

### 4. Start the server

Open the project from your favourite IDE, let it update Maven dependencies and run the application. The application will
start listening on `http://localhost:8080`.

### 5. Register a user

Assuming the application is running on port `8080`, use your favorite local API platform, like `curl`
or  [ThunderClient](https://www.thunderclient.com/) to send a `POST` request with a User payload
to `http://localhost:8080/api/auth/register`

Example:

```json
{
  "username": "mr_rossi",
  "password": "password123",
  "anagrafica": {
    "nome": "Mario",
    "cognome": "Rossi",
    "email": "mario@rossi.it",
    "telefono": "3333333333",
    "indirizzo": "Via Roma",
    "cap": "00100",
    "citta": "Roma",
    "provincia": "RM"
  },
  "ruoli": [
    {
      "id": 3
    }
  ]
}
```

You can set one or more `ROLE`s to your user by adding one (or more) object(s) containing the `"id"` property to
the `ruoli` property:

- ID **1** grants **ADMINISTRATOR** authorities
- ID **2** grants **SELLER** authorities
- ID **3** grants **CUSTOMER** authorities

### 6. Login

Send a `POST` request to `http://localhost:8080/api/auth/register` with a login payload.

Example:

```json
{
  "username": "mr_rossi",
  "password": "password123"
}
```

If the authentication is successful, you will be issued a JWT.

Example:

```json
{
  "jwt-token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6ImVjb21tZXJjZSIsImlhdCI6MTY5MDgzNzE5OSwiZXhwIjoxNjkwODQwNzk5LCJyb2xlcyI6WyJBTU1JTklTVFJBVE9SRSIsIlZFTkRJVE9SRSJdLCJhdWQiOiJlY29tbWVyY2UifQ.v8PZDtXEK7c5Lg-Vroy0MP_57gLhI-USCIjEiWHIvcjAGDlRaPniaDaKME7uBbV46DBXlnYYtA_B7LnQWrwWnvzpstZe49IjFQU-yn441oZkCm9aQ3K9TaqEIUDnqzW_6YQKkX6UwrpH18Yg7D96YRZgD2WQIRtsLaYwjLZCSGpUstp6iNgAiRMtdrWVF5Uo_KtC6TIBn7etnmYeHn2x0PS1Op0uqSaUkJtbhmFfj2XoHObJMIOw26PR5gpISXJUHEoYN8aPgJ2lqZTG0O-8Yy6W0hhBb0c3xuia0up9VaNXKK-ZpfZo_GHY3Ym2pufqoa3RNZRJxXXDFlBDbftGRQ"
}
```

***The `"jwt-token"` value is the JWT you will use as `Authentication Bearer` header in your CRUD requests.***

By default, the token you will get will expire in 60 minutes.

## CRUDs

Check out the `src/main/java/it/ecommerce/controller` class files for a complete list of paths and their related HTTP
methods.

### Example: Adding a New Product

Send a `POST` request to `http://localhost:8080/api/articoli` with a payload formatted as such:

```json
{
  "marca": {
    "id": 96
  },
  "sottocategoria": {
    "id": 222
  },
  "modello": "ITHDD3000X",
  "nome": "Hard Drive 3 TB",
  "prezzo": 100.0,
  "scontoPerUnita": 3.0,
  "giacenza": 20,
  "giacenzaMinima": 5,
  "descrizione": "Hard disk interno SATA3 da 3 terabyte"
}
```

### Example: Placing an Order

Send a `POST` request to `http://localhost:8080/api/ordini` with a payload formatted as such:

```json
{
  "cliente": {
    "id": 8
  },
  "indirizzo": {
    "id": 5
  },
  "spedizione": {
    "id": 3
  },
  "note": {
    "nota": "Nuovo inserimento"
  },
  "dettagli": [
    {
      "quantita": 21,
      "articolo": {
        "id": 456
      }
    },
    {
      "quantita": 1,
      "articolo": {
        "id": 11371
      }
    }
  ]
}
```

#### Disclaimer

The `e_commerce_FULL.sql` file contains example data and does not include any information related to real people or
businesses. Any resemblance to actual people, living or dead, or businesses, is purely coincidental.
