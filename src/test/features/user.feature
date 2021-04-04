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
