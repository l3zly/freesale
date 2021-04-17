Feature: User component
  Scenario: signup with E.164 phone number and 8 character password
    Given I provide '+442071838750' and '12345678' as my phone number and password
    When I try to signup
    Then the response status is 201

  Scenario Outline: signup without E.164 phone number
    Given I provide <phone> and '12345678' as my phone number and password
    When I try to signup
    Then the response status is 400

    Examples:
      | phone               |
      | '02071838750'       |
      | '442071838750'      |
      | '+4420718387513267' |
      | ''                  |

  Scenario: signup with password shorter than 8 characters
    Given I provide '+442071838750' and '1234567' as my phone number and password
    When I try to signup
    Then the response status is 400

  Scenario: signup with non-unique phone number
    Given I provide '+442071838750' and '12345678' as my phone number and password
    And a user with the provided phone number and password exists
    When I try to signup
    Then the response status is 400
