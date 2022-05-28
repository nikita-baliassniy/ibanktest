package ru.netology.core.extractors;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import ru.netology.DriverManager;
import ru.netology.core.annotations.Container;
import ru.netology.core.annotations.Locator;
import ru.netology.core.definitions.FieldDefinition;
import ru.netology.fields.AbstractField;
import ru.netology.pages.CollectionPageObject;
import ru.netology.pages.IPageObject;

public class FieldExtractor {

	public static <T extends AbstractField> T getFilterSortField(IPageObject page, String name) {
		FieldDefinition field = getFieldDefinition(page, name);
		Assert.assertNotNull(String.format("Не найдено поле: [%s] в описании полей", name), field);
		String locator = field.getTarget();
		locator = locator.substring(0, locator.indexOf("/following")).concat("/p-sorticon");
		if (locator.isEmpty()) {
			throw new IllegalStateException(
					String.format("Не найден локатор для поля: %s", field.getAliases()));
		}
		final By by = Locator.getLocator(locator);
		SelenideElement element = null;
		try {
			if (page instanceof CollectionPageObject) {
				element = ((CollectionPageObject) page).getParent().$(by);
			} else {
				element = $(by);
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			throw new AssertionError(String.format("Поле [%s] не найдено на странице [%s]", name,
					page.getClass().getCanonicalName()));
		} catch (StaleElementReferenceException e) {
			if (page instanceof CollectionPageObject) {
				element = ((CollectionPageObject) page).getParent().$(by);
			} else {
				element = $(by);
			}
		}
		String elementType = field.getType();
		Class<? extends AbstractField> elType = FieldMapping.FIELD_TYPE_ALIASES.get(elementType);
		Assert.assertNotNull(
				String.format("В FieldMapping не объявлено поле типа: [%s]", elementType), elType);
		try {
			Constructor<? extends AbstractField> constructor =
					elType.getConstructor(SelenideElement.class);
			return (T) constructor.newInstance(element);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException error) {
			throw new IllegalStateException(error);
		}
	}

	public static <T extends AbstractField> T getField(IPageObject page, String name) {

		FieldDefinition field = getFieldDefinition(page, name);
		Assert.assertNotNull(String.format("Не найдено поле: [%s] в описании полей", name), field);
		final String locator = field.getTarget();
		if (locator.isEmpty()) {
			throw new IllegalStateException(
					String.format("Не найден локатор для поля: %s", field.getAliases()));
		}
		final By by = Locator.getLocator(locator);
		SelenideElement element = null;
		try {
			if (page instanceof CollectionPageObject) {
				element = ((CollectionPageObject) page).getParent().$(by);
			} else {
				element = $(by);
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			throw new AssertionError(String.format("Поле [%s] не найдено на странице [%s]", name,
					page.getClass().getCanonicalName()));
		} catch (StaleElementReferenceException e) {
			if (page instanceof CollectionPageObject) {
				element = ((CollectionPageObject) page).getParent().$(by);
			} else {
				element = $(by);
			}
		}
		String elementType = field.getType();
		Class<? extends AbstractField> elType = FieldMapping.FIELD_TYPE_ALIASES.get(elementType);
		Assert.assertNotNull(
				String.format("В FieldMapping не объявлено поле типа: [%s]", elementType), elType);
		try {
			Constructor<? extends AbstractField> constructor =
					elType.getConstructor(SelenideElement.class);
			return (T) constructor.newInstance(element);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException error) {
			throw new IllegalStateException(error);
		}
	}

	private static FieldDefinition getFieldDefinition(IPageObject page, String name) {
		Container container = page.getClass().getAnnotation(Container.class);
		Assert.assertNotNull(
				String.format("Не указан контейнер для страницы: [%s]", page.toString()),
				container);
		String ref = container.ref();
		List<FieldDefinition> fields =
				DriverManager.getElementDefinitions().getDefinitionsByParent("Все_элементы." + ref);
		for (FieldDefinition field : fields) {
			if (name.equals(field.getAliases())) {
				return field;
			}
		}
		return null;
	}

	public static ArrayList<CollectionPageObject> getCollection(IPageObject page,
																String fieldName) {
		ArrayList<CollectionPageObject> list = new ArrayList<>();
		FieldDefinition fieldDefinition = getFieldDefinition(page, fieldName);
		Container container = page.getClass().getAnnotation(Container.class);
		String ref = container.ref() + "." + fieldDefinition.getAliases();
		List<FieldDefinition> fields = DriverManager.getElementDefinitions().
				getDefinitionsPageByContainer("Все_элементы." + ref);
		DriverManager.getWebDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		ElementsCollection items = $$(By.xpath(fields.get(0).getTarget()));
		Iterator<SelenideElement> elements = items.iterator();
		while (elements.hasNext()) {
			SelenideElement element = elements.next();
			CollectionPageObject pageObject = new CollectionPageObject();
			pageObject.setParent(element);
			try {
				Annotation newAnnotation = new Container() {

					@Override
					public String ref() {
						return ref;
					}

					@Override
					public Class<? extends Annotation> annotationType() {
						return Container.class;
					}
				};
				Method method = Class.class.getDeclaredMethod("annotationData", null);
				method.setAccessible(true);
				Object annotationData = method.invoke(pageObject.getClass(), null);
				Field declaredAnnotations =
						annotationData.getClass().getDeclaredField("declaredAnnotations");
				declaredAnnotations.setAccessible(true);
				Map<Class<? extends Annotation>, Annotation> annotations =
						(Map<Class<? extends Annotation>, Annotation>) declaredAnnotations
								.get(annotationData);
				annotations.put(Container.class, newAnnotation);

				list.add(pageObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		DriverManager.setDefaultImplicitlyWait();
		return list;
	}

	public static Integer getFieldsCount(IPageObject page, String fieldName) {
		FieldDefinition field = getFieldDefinition(page, fieldName);
		return DriverManager.getWebDriver().findElements(By.xpath(field.getTarget())).size();
	}
}