# TD Unleash Your JAVA Testing Superpowers with Tzatziki

## 1. As a Product Manager, I want to create and get a list of a stock movement for a given sku.

> I want to have 2 endpoints :
> 
> GET /stock that will return all element in dB
> 
> POST /stock that will add an id with the given quantity in dB
```json
{
  "id": "long",
  "quantity": "integer"
}
```

### Create a Test which will test the GET endpoint that displays datas from the database
1. Create a new file in `test.resources.org.tzatziki` package named <span style="color:Yellow">**getterStock.feature**</span> or whatever.feature, add a feature name (in first line) and create your first scenario : 
```gherkin
Feature: What your feature file will test
  Scenario: We can get stock values from db
```
2. Insert datas in a <span style="color:Yellow">**stock_value**</span> table with 2 columns using a sentence like 
```gherkin
Given that the table_name table will contain:
| column1 | column2 | column3 |
| daddy   | 666     | true    |
| mummy   | 987654  | false   |
```
3. Make a call on your getter using a sentence like

```gherkin
When I get "/tech_day"
When he calls on "/power" ### These sentences make a GET 
```
4. Finish this first scenario with the `then` assertion and run it !

```gherkin
Then we receive exactly:
"""yaml
    - column1: daddy
      column2: 666
      column3: true
    - column1: mummy
      column2: 987654
      column3: false
"""
```

5. Tests are in error because our code is incorrect. <span style="color:Orange">It seems that we return an empty list instead of datas that we have inserted.</span>
So <span style="color:Yellow">uncomment</span> code associate to the GET endpoint in the controller in `org.tzatziki.controller.StockController` and <span style="color:Yellow">**Rerun the test !**</span> 
       
```java  
         @GetMapping("/stock")
         public ResponseEntity<List<StockValue>> getAllStockValues() {
         List<StockValue> stockValues = stockValueRepository.findAll();

         if (stockValues.isEmpty()) {
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         }

         return new ResponseEntity<>(stockValues, HttpStatus.OK);
         }
```

### Create a Test which will test the POST endpoint that inserts only good datas from the database 
1. You can create a new file or directly add a new scenario. This time we will run multiple time the test with different entry points. The Examples Table have to be <span style="color:Yellow">at the end</span> of the scenario. This means that, all your steps (Given, When, Then, ...) are between "Scenario Outline" line and the Examples table. 
```gherkin
Scenario Outline: This is a new scenario with examples
  Given that the table_name table will contain:
    | column1 | column2 | column3 |
    | daddy   | 666     | true    |
    | mummy   | 987654  | false   |
  When I call "/v1/ping"
  Then i receive a status 200
  
Examples:
| house       | color  |
| Gryffindor  | red    |
| Hufflepuff  | yellow |
| Slytherin   | green  |
| Ravenclaw   | blue   |
```

2. Make a call on your POST using a sentence like. To use Examples var you have to call your column title between chevron : \<house>  \<color>
```gherkin
When we post "/hogwarts":
"""yml
house: <house>
color: <color>
"""
When we send on "/hogwarts":   #### We can add specific header for example
"""yml
  method: POST
  headers:
    Some-Token: Some-Value
  body:
    payload:
      house: <house>
      color: <color>  
"""
```

3. Start asserting by checking response status of the request ! 
```gherkin
Then we receive a status <response_status>
Then we receive a status OK
  Examples:
 | response_status |
 | BAD_REQUEST_400 |
 | 400             |
 | BAD_REQUEST     |
```

4. For the final part check that the table has been inserted with the value of the body only if we have received a status 201

```gherkin
And if <response_status> == 201 => the hogwarts_house table contains:
| house       | color     |
| Gryffindor  | red       |
```

# That's it for the first exercise !