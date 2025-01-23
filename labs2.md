# TD Unleash Your JAVA Testing Superpowers with Tzatziki

## 2. As a Product Manager, I want to add sku description from Referential API
> I want to have an endpoint :
>
> GET /stock-with-description that will return all elements in dB and a "description field"
> 
> This field is retrieved by calling Referential API, GET /referential/description/{id}" will return this body:
```json
{
  id: "id",
  description: "string"
}
```
> When GET /referential/description/{id} return an error 404, then we will return an error 500

### Create a Test which will Get stock values with description from stock referential API
1. You can create a new file or directly add a new scenario. First start by adding data in a <span style="color:Yellow">**stock_value**</span> table
```gherkin
Given that the stock_value table will contain:
   | id           | quantity |
   | 1            | 666      |
```

2. In order to bind the tzatziki mock server to your spring app, you need to add something like the following code in
   the <span style="color:Yellow">**TestSteps.java**</span> Initialization to the TestPropertyValues:

```java
"stock.referential.url="+url() +"/mock/referential" //stock.referential.url is the property used by rest client in the app to reach the referential API
```

url() is a method provided by the module tzatziki-http that return the url of the mock server
With this configuration, all mocked url in your test with the path "/mock/referential" will be reached by the
Referential API client in the spring app.

3. Then we have to mock the Referential API "/mock/referential/description/1" using a sentence like
```gherkin
* that getting on "/mock/referential/books/.*" will return a status OK_200
   
And calling on "/mock/referential/books/1234" will return a status OK_200 and:
"""
  book_id: 1234
  author: Charles Dickens
  title: A Tale of Two Cities
"""

```

3. Finish this scenario with the `then` assertion by calling your API and check the answer ! 


### Create a Test which will return an error 500 if Referential API return 404 
