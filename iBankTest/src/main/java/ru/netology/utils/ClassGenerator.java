package ru.netology.utils;

import com.google.gson.Gson;
import cucumber.api.CucumberOptions;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.model.CucumberFeature;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

@Slf4j
public class ClassGenerator {

	private static final String GENERATED_CLASSES_PREFIX = "GeneratedSuite_";
	private static final String GENERATED_CLASSES_PACKAGE = "features";
	private static final String GENERATED_CLASSES_DIR = "src/test/java/generated-files";
	private static final String GENERATED_RESOURCES_DIR = "src/test/resources";
	private static String COMMON_PATH = System.getProperty("suitesPath", "");

	List<CucumberFeature> cucumberFeatures;

	private static JavaClassSource getGeneratedClassSource(String returnedValue, Class<?> runner) {
		String generatedClassName =
				GENERATED_CLASSES_PREFIX + RandomStringUtils.randomAlphanumeric(10);
		JavaClassSource generatedClassSource = Roaster.create(JavaClassSource.class);
		generatedClassSource.setName(generatedClassName);
		generatedClassSource.setSuperType(runner);
		generatedClassSource.setPackage(GENERATED_CLASSES_PACKAGE);
		generatedClassSource.setPublic();
		log.info("features - " + returnedValue);
		generatedClassSource.addAnnotation(CucumberOptions.class)
				.setLiteralValue("features", "\"" + returnedValue + "\"");
		log.info("\nGenerated class source: \n\n" + generatedClassSource.toString());
		return generatedClassSource;
	}

	private static void createNewSuiteClassFileFromSource(JavaClassSource classSource) {
		Path generatedClassPath = Paths.get(
				"./" + GENERATED_CLASSES_DIR + "/" + GENERATED_CLASSES_PACKAGE + "/" + classSource
						.getName() + ".java");
		try {
			Files.createDirectories(generatedClassPath.getParent());
			Charset charset = Charset.forName("UTF-8");
			Files.write(generatedClassPath, classSource.toString().getBytes(charset),
					StandardOpenOption.CREATE);
		} catch (IOException e) {
			throw new RuntimeException(String.format(
					"Ошибка при генерации класса-сьюта, возвращаюего имя истории. Создаваемый файл: %s",
					generatedClassPath.toString()), e);
		}
	}

	public void generateAllSuiteClasses(Class<?> runner) {
		cucumberFeatures = getCucumberFeatures();
		SuiteClassGeneratorHelper suiteClassGeneratorHelper = new SuiteClassGeneratorHelper();
		suiteClassGeneratorHelper
				.getSingleStoriesPathsByCommonPath(GENERATED_RESOURCES_DIR + COMMON_PATH).forEach(
				k -> createNewSuiteClassFileFromSource(getGeneratedClassSource(k, runner)));
	}

	private List<CucumberFeature> getCucumberFeatures() {
		RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(BaseRunner.class);
		ClassLoader classLoader = BaseRunner.class.getClassLoader();
		RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();
		ResourceLoader resourceLoader = new MultiLoader(classLoader);
		return runtimeOptions.cucumberFeatures(resourceLoader);
	}

	private class SuiteClassGeneratorHelper {

		private List<String> getSingleStoriesPathsByCommonPath(String path) {
			if (System.getProperty("cucumber.options") != null && System
					.getProperty("cucumber.options").contains("tags")) {
				return getStoriesPathsByRunner(path);
			} else {
				ArrayList<String> lists = new ArrayList<>();
				ClassLoader classLoader = getClass().getClassLoader();
				ResourceLoader resourceLoader = new MultiLoader(classLoader);
				resourceLoader.resources(path, ".feature").forEach(resource -> {
					if (path.contains(".feature")) {
						lists.add(path);
					} else {
						lists.add(path + "/" + resource.getPath().replace('\\', '/'));
					}
				});
				return lists;
			}
		}

		private List<String> getStoriesPathsByRunner(String path) {
			ArrayList<String> lists = new ArrayList<>();
			cucumberFeatures.forEach(feature -> {
				for (String tag : System.getProperty("cucumber.options").replaceAll(".*tags ", "")
						.split(",")) {
					if (existTagInFeature(tag, feature)) {
						lists.add(path + "/" + feature.getPath());
					}
				}
			});
			return lists;
		}

		private boolean existTagInFeature(String tagName, CucumberFeature cucumberFeature) {
			return cucumberFeature.getFeatureElements().get(0).getGherkinModel().getTags().stream()
					.anyMatch(tag -> tag.getName().contains(tagName));
		}

	}
}