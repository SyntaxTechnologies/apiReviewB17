Feature: syntax api testing

  Background: generating a JWT
    Given a JWT is generated


  Scenario:create an employee and verify that the employee is created
    And a request is prepared to create an Employee
    When a post call is made to the endpoint
    Then the status code is 201
    And the employee id "Employee.employee_id" is stored as a global variable
    And we verify that the value for key "Message" is "Employee Created"


  Scenario: Updating the employee
    Given a request is prepared to update an employee in HRMS system
    When a PUT call is made to update the employee
    Then the status code for updating the employee is 200

  Scenario: Partially Updating the employee
    Given a request is prepared to partially update an employee in HRMS system
    When a PATCH call is made to partially update the employee
    Then the status code for updating the employee is 201
    And the response body contains "Message" key and value "Employee record updated successfully"