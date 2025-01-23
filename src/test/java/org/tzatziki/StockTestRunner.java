package org.tzatziki;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber.json"}, extraGlue = "com.decathlon.tzatziki.steps")
public class StockTestRunner {
}
