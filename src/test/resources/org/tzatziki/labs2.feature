Feature: to interact with the stock values 2

    #//EXO 2:
  Scenario: Get stock values with description from stock referential
    Given that the stock_value table will contain:
      | id | quantity |
      | 1         | 0        |

    And getting on "/mock/referential/description/1" will return:
    """
    id: 1
    description: "description for article 1"
    """

    When we call "/stock-with-description"
    Then we receive exactly:
      """yml
      - id: 1
        quantity: 0
        description: "description for article 1"
      """

  Scenario: Get multiple stock values with description from stock referential
    Given that the stock_value table will contain:
      | id | quantity |
      | 1         | 0        |
      | 2         | 10       |

    And getting on "/mock/referential/description/(\d+)" will return:
    """yml
    id: {{_request.pathParameterList.0.values.0.value}}
    description: "description for article {{_request.pathParameterList.0.values.0.value}}"
    """

    When we call "/stock-with-description"
    Then we receive exactly:
      """yml
      - id: 1
        quantity: 0
        description: "description for article 1"
      - id: 2
        quantity: 10
        description: "description for article 2"
      """

    Given getting on "/mock/referential/description/1" will return a status 404

    When we call "/stock-with-description"
    Then we receive a status 500