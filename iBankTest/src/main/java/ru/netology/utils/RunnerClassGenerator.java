package ru.netology.utils;


import lombok.extern.slf4j.Slf4j;
import ru.netology.CucumberRunner;

@Slf4j
public class RunnerClassGenerator {

	public static void main(String[] args) {
		ClassGenerator storySuitesClassGenerator = new ClassGenerator();
		storySuitesClassGenerator.generateAllTestDataFiles();
		storySuitesClassGenerator.generateAllSuiteClasses(CucumberRunner.class);
	}
}
