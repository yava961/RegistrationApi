I have developed my application on a local host, so to successfully launch it, you need to create a schema in the MySQL database with the appropriate username/password or enter your own data and schema name.

Afterwards, you can work with the application using Swagger or Postman, or even a regular browser.

Here is an instruction on how to use the application:

Start the application on your local host.
Open your web browser and enter the URL of the application.
Ensure that the MySQL database is running and accessible.

If you are using Swagger:
1. In your browser, navigate to the URL of the Swagger documentation for your application.

http://localhost:8080/users

2. Then you can navigate to Swagger UI

http://localhost:8080/swagger-ui/index.html

There you can see endpoints: 
to create user (registration)
to update user
to find user by email
to see all users
and delete user

Also there is controller to Authenticate
This is required for you to have access to manage operations such as: update user, see all users and delete user.

If you are using Postman u can do the same operations by using URL and XML queries

POST: localhost:8080/api/authenticate
{
  "username": "*****",
  "password": "*****"
}

POST: localhost:8080/api/users/create
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "password": "string",
  "age": 1
}

PUT: localhost:8080/api/users/update/{id}
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "age": 1
}

GET: localhost:8080/api/users/all_users

DELETE: localhost:8080/api/users/delete/{id}
