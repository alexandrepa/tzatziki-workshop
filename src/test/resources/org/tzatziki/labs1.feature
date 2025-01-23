Feature: to interact with the stock values

  #//EXO 1
  Scenario: we can get stock values from db
    Given that the stock_value table will contain:
      | id | quantity |
      | 1         | 0        |
      | 2         | 10       |
    When we call "/stock"
    Then we receive exactly:
      """yml
      - id: 1
        quantity: 0
      - id: 2
        quantity: 10
      """

  Scenario Outline: we can update stock values in db
    When we post on "/stock":
      """yml
        id: <id>
        quantity: 5
      """
    Then we receive a status <response_status>

    And if <response_status> == 201 => the stock_value table contains:
      | id   | quantity |
      | <id> | 5        |

    And if <response_status> == 201 => the stock_value table contains only and in order:
      | id   | quantity |
      | <id> | 5        |

    * else the stock_value table contains nothing

    Examples:
      | id            | response_status |
      | 1                    | 201             |
      | -9223372036854775807 | 201             |
      | bad_id               | BAD_REQUEST_400 |
      | 9223372036854775808  | 400             |
      | true                 | BAD_REQUEST     |


