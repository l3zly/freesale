Feature: User component
  Scenario: signup with UK E.164 phone number, UK postcode and 8 character password
    Given I provide '+442071838750', 'LN7 6DH' and '12345678' as my phone number, postcode and password
    When I try to signup
    Then the response status is 201

  Scenario Outline: signup without UK E.164 phone number
    Given I provide <phone>, 'LN7 6DH' and '12345678' as my phone number, postcode and password
    When I try to signup
    Then the response status is 400

    Examples:
      | phone               |
      | '02071838750'       |
      | '442071838750'      |
      | '+4420718387513267' |
      | ''                  |

  Scenario Outline: signup without UK postcode
    Given I provide '+442071838750', <postcode> and '12345678' as my phone number, postcode and password
    When I try to signup
    Then the response status is 400

    Examples:
      | postcode |
      | 'LN76DH' |
      | '07731'  |
      | ''       |

  Scenario: signup with password shorter than 8 characters
    Given I provide '+442071838750', 'LN7 6DH' and '1234567' as my phone number, postcode and password
    When I try to signup
    Then the response status is 400

  Scenario: signup with non-unique phone number
    Given I provide '+442071838750', 'LN7 6DH' and '12345678' as my phone number, postcode and password
    And a user with the provided phone number exists
    When I try to signup
    Then the response status is 400

  Scenario Outline: login
    Given a user with the phone number '+442071838750' and password '12345678' exists
    And I provide <phone> and <password> as my phone number and password
    When I try to login
    Then the response status is <status>

    Examples:
      | phone           | password   | status |
      | '+442071838750' | '12345678' | 200    |
      | '+443182949861' | '12345678' | 404    |
      | '+442071838750' | '1234'     | 404    |
