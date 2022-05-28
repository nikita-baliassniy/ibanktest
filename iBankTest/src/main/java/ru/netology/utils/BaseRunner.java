package ru.netology.utils;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"ru.netology"}, features = "src/test/resources/features")
public class BaseRunner {
}
