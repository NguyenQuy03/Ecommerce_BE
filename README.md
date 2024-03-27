# Ecommerce-Backend

![](https://res.cloudinary.com/dald4jiyw/image/upload/v1711014486/Brand_clcgsp.png)

### :handbag: A simple RESTful API Project

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#description">Description</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

## Description

Basically, project for ecommerce backend. My project was inspired by Shopee’s website, which allows users to both buy and sell items.
Managers have access to all transactions, orders, and accounts.

### Built With

This section should list any major frameworks/libraries used to bootstrap your project. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.

- [![Bootstrap][Bootstrap.com]][Bootstrap-url]
- [![JQuery][JQuery.com]][JQuery-url]
- [![Springboot][Spring.io]][Spring-url]
- [![SQLServer][SQLServer]][SQLServer-url]
- [![Redis][Redis.io]][Redis-url]

<!-- GETTING STARTED -->

## Getting Started

### Prerequisites:

Before you begin, ensure you have the following installed on your system:

1. Java Development Kit (JDK) 11 or later
2. Apache Maven (or Gradle, if preferred)
3. Ubuntu 18.04 or later
4. Redis
5. SQL Server

### Installation

1. Clone the repository to a specified directory in your local machine.
   ```sh
   git clone https://github.com/NguyenQuy03/Ecommerce_BE.git
   ```

<!-- USAGE EXAMPLES -->

## Usage

<b>1. Starting Redis Server</b>

To start the Redis server, follow these steps:

- Open the Ubuntu terminal.

- Run the following command to start the Redis server:
  ```bash
  redis-cli
  ```

<b>2. Starting Spring boot app</b>

To start the Spring boot app, follow these steps:

```bash
   cd Ecommerce_BE
```

```bash
   mvn clean install
```

```bash
   mvn spring-boot:run
```

## Features

1. Authentication and Authorization:

- Accounts are authenticated by JWT.
- Ability to register as a Buyer or become a Seller
- Ability to login and log out
- Reset password implementation

2. Use database seeds to create first user with role Manager
3. CRUD functionality (Create / Read / Update / Delete) for the various categories, products, accounts, orders

Basically, that’s it. This project shows the skills in basic Spring Framework things:

1. MVC
2. Authuthentication and Authorization with JWT
3. CRUD and Resource Controllers
4. Database migrations and seeds
5. Form Validation and Requests
6. File management
7. Basic Bootstrap front-end
8. Pagination
9. Ecommerce

### Database ERP Diagram

![Database ERP Image](https://res.cloudinary.com/dald4jiyw/image/upload/v1711035596/ecommercial_app_gsyrpp.png)

[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com
[Spring.io]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/
[SQLServer]: https://img.shields.io/badge/Microsoft_SQL_Server-CC2927?style=for-the-badge&logo=microsoft-sql-server&logoColor=white
[SQLServer-url]: https://www.microsoft.com/en-us/sql-server
[Redis.io]: https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white
[Redis-url]: https://redis.io/
