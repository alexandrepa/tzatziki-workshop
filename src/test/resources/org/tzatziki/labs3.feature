Feature: to interact with the stock values 3

  Background:
    * this avro schema:
      """yml
      type: record
      name: stockValue
      fields:
        - name: id
          type: long
        - name: quantity
          type: int
      """
  #//EXO 3:
  Scenario: we can push an avro message in a kafka topic where a listener expect a simple payload
    Given that the stock_value table will contain:
      | id | quantity |
      | 1         | 0        |
      | 2         | 10       |
    When this stockValue is consumed from the stock topic:
      """yml
      id: 1
      quantity: 100
      """
    Then the stock_value table contains:
      | id | quantity |
      | 1         | 100      |
      | 2         | 10       |


