# TDD Unleash Your JAVA Testing Superpowers with Tzatziki

## Pre-requisites

In order to test Kafka, we need to start an embedded Kafka server. Tzatziki provides the `tzatziki-spring-kafka` module that handles this for you.

Instead of manually configuring the steps, we will use the **Tzatziki Cucumber Skill** — an AI-powered skill that knows every legal Tzatziki step pattern and can generate `.feature` files from a functional specification.

### Install the Tzatziki Skill

Install the skill in your agent environment (GitHub Copilot coding agent, Copilot CLI, etc.) with:

```bash
npx skills add https://github.com/Decathlon/tzatziki
```

This gives your AI agent the `add-cucumber-tests` skill which can:
- Generate `.feature` files from a functional description
- Use the correct step definitions for HTTP, JPA, Kafka, MongoDB, OpenSearch, Logback, or MCP modules
- Validate that all generated steps are real Tzatziki steps (no undefined-step errors)

### Kafka Producer Configuration

We already configured a KafkaProducer in KafkaProducerConfig for you
that will be used by the steps to send messages to Kafka topics.

**You just have to uncomment it the class file (KafkaProducerConfig.java).**

That's it, you can now start writing your tests using the skill!

## 3. As a Product Manager, I want to consume a Kafka message and modify our datas in dB

### Use the Tzatziki Skill to generate your test

Instead of writing the `.feature` file manually, provide the following **functional specification** to the skill and let it generate the Cucumber test for you:

> **Spec to provide to the skill:**
>
> I want to listen to the `stock` Kafka topic and update information in the database from the message.
>
> When a message is consumed from the `stock` topic, it should modify the `quantity` column in the `stock_value` table for the row matching the given `id`.
>
> The message payload is an Avro record with the following schema:
> ```yaml
> type: record
> name: stockValue
> fields:
>   - name: id
>     type: long
>   - name: quantity
>     type: int
> ```
>
> Example message:
> ```json
> {
>   "id": 1,
>   "quantity": 100
> }
> ```
>
> The test should:
> 1. Pre-populate the `stock_value` table with initial data (id=1, quantity=0 and id=2, quantity=10)
> 2. Consume the Avro message from the `stock` topic
> 3. Assert that the `stock_value` table has been updated (id=1 should now have quantity=100, id=2 unchanged)

### What the skill will do

The skill will:
1. **Discover your project** — detect `tzatziki-spring-kafka` in your `pom.xml` and read the relevant step references
2. **Propose a plan** — show you the scenarios it will create and ask for confirmation
3. **Generate the `.feature` file** — using only valid Tzatziki step patterns (Background with avro schema, `Given` to populate the table, `When` to consume the Kafka message, `Then` to assert DB state)
4. **Run and validate** — execute the tests to ensure zero undefined-step errors

### What to look for in the generated test

The skill should produce something that uses these key concepts:
- A `Background` section to define the Avro schema (shared across all scenarios in the file)
- A `Given` step to populate the `stock_value` table with initial data
- A `When` step to consume an Avro message from the `stock` topic
- A `Then` step to assert the database state after consumption

### 💡 Tips
- You can ask the skill to add edge cases (e.g., "what if the id doesn't exist in the table?")
- The skill will never invent step patterns — every step it writes comes from real Tzatziki step definitions
- If the generated test has assertion failures (e.g., `expected 100 but got 0`), that's normal — it means you need to implement the Kafka consumer logic!
