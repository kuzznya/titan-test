# Reactive RESTful Web Service Calculator

[Original task](TASK.md)

Stack: Java 11, Spring WebFlux, J2V8 for V8 JS engine, built-in Nashorn JS engine

Service produces series of calculations for each client request.
For each calculation server runs two JS functions 
passed by the client with a single argument - execution index.
The results are returned to the client as a stream of
 ordered or unordered data (see Modes)

Response data is plain text (CSV-line formatted)

The service is built to potentially work with any script engine.
To demonstrate it, both Nashorn & V8 JS engines were included into the project

## Calculation request

Calculation request is a JSON with 3 parameters:
- function1 - The code of the first function
- function2 - The code of the second function
- count - number of calculations to execute

Function code should be defined as lines of function without its declaration.
The code can use `idx` parameter (execution index).

If function does not return value, then 'null' (Nashorn) or 'undefined' (V8)
is received as a result

## Modes

There are two response modes:
- **unordered** - the data is sent to the client as soon as it was calculated  
  Response format: `<execution idx>, <function idx>, <function result>, <calculation time>`
- **ordered** - the results of two functions with common execution index are zipped together
and then the data is sent to the client  
  Response format: `<execution idx>, <func1 result>, <func1 calculation time>, 
  <count of further results of func1 already calculated>,
  <func2 result>, <func2 calculation time>, 
  <count of further results of func2 already calculated>`

## API

API is documented with OpenAPI spec (see Swagger UI at `/swagger-ui.html`)

Calculation request: `POST /api/v1/calculations/{mode}`

## Errors

If function code syntax is invalid, the response with status **400 BAD REQUEST** is returned
with corresponding message as a JSON

If error occurs during the execution, 
the `EXECUTION ERROR` (or `EXECUTION <idx> ERROR`) is returned.
The reason of such behaviour is that 
server already responded with status **200 OK** and cannot change it.

## Service configuration

The configuration consists of two properties:
- `titantest.delay-millis` - the delay between function executions
- `titantest.js-engine` - JS engine to execute the script (`nashorn`/`v8`)

