# REST API with Spring Boot and PostgreSQL

This repository contains the source code and configuration required to create and deploy a REST API developed with Spring Boot and connected to a PostgreSQL relational database. The API provides endpoints for performing operations related to bank cards.

## Requirements

Before running the application, make sure you have the following set up:

- A running PostgreSQL instance.
- The following environment variables configured:
    - `url`: the connection URL for the PostgreSQL database.
    - `username`: the username for the database.
    - `password`: the password for the database.

Ensure that the aforementioned environment variables contain the correct credentials to access the database.

## Deployment

This REST API is deployed on Cloud Run. You can access it through the following base URL:

- Base URL: [https://banck-card-api-wmbctmnekq-uc.a.run.app](https://banck-card-api-wmbctmnekq-uc.a.run.app)

## API Documentation

The API documentation is automatically generated using Swagger. You can access the Swagger UI interface through the following URL:

- Swagger URL: [https://banck-card-api-wmbctmnekq-uc.a.run.app/bank-card-api/swagger-ui/index.html](https://banck-card-api-wmbctmnekq-uc.a.run.app/bank-card-api/swagger-ui/index.html)

The API documentation provides details about the available endpoints, request parameters, expected responses, and other relevant information.

## Running Locally

If you want to run the application locally, make sure you have Java and Maven installed on your machine. Then, follow these steps:

1. Clone this repository.
2. Navigate to the root directory of the project.
3. Open a terminal and run the following command to compile the application:

   ```shell
   mvn clean package
   ```

4. Once the application is compiled, run the following command to start the local server:

   ```shell
   mvn spring-boot:run
   ```

5. The API will be available at `http://localhost:8080`.

You can now start using the REST API!

## Contribution

If you wish to contribute to this project, please follow these steps:

1. Create a fork of this repository.
2. Create a branch with the name of the feature or fix you are implementing.
3. Make your changes and improvements.
4. Submit a pull request to this repository.

Your contribution will be appreciated and reviewed.

## Issues

If you encounter any issues or have any questions related to this REST API, please open an issue in this repository. We will try to resolve it as soon as possible.

## License

This project is distributed under the MIT license. For more information, see the [LICENSE](LICENSE) file.
