Feature: User component
  Scenario Outline: signup
    Given I provide <phone> and <password> as my phone number and password
    When I try to signup
    Then the response status is <status>

    Examples:
      | phone           | password   | status |
      | '+442071838750' | '12345678' | 201    |
      | '02071838750'   | '12345678' | 400    |
      | '+442071838750' | '1234567'  | 400    |

  Scenario: signup with non-unique phone
    Given I provide '+442071838750' and '12345678' as my phone number and password
    And a user with the provided phone number already exists
    When I try to signup
    Then the response status is 400
