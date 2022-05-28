package ru.netology.pages;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import ru.netology.core.extractors.FieldExtractor;
import ru.netology.fields.AbstractField;

@Slf4j
public abstract class AbstractPageObject implements IPageObject {

	public abstract boolean isLoaded();

	protected static ThreadLocal<IPageObject> currentPage = new ThreadLocal<IPageObject>();
	protected static ThreadLocal<IPageObject> previousPage = new ThreadLocal<IPageObject>();

	FieldExtractor fieldExtractor;

	public static <T extends IPageObject> T getCurrentPage() {
		return (T) currentPage.get();
	}

	public static void setCurrentPage(Class<? extends IPageObject> page) {
		try {
			AbstractPageObject pageObject =
					(AbstractPageObject) page.getConstructors()[0].newInstance();
			setPreviousPage(currentPage.get());
			currentPage.set(pageObject);
		} catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void setCurrentPage(IPageObject page) {
		setPreviousPage(currentPage.get());
		currentPage.set(page);
	}

	@Override
	public <T extends AbstractField> T getFilterSortField(IPageObject page, String fieldName) {
		return (T) fieldExtractor.getFilterSortField(page, fieldName);
	}

	@Override
	public <T extends AbstractField> T getField(String fieldName) {
		return fieldExtractor.getField(getCurrentPage(), fieldName);
	}

	@Override
	public <T extends AbstractField> T getField(IPageObject page, String fieldName) {
		return (T) fieldExtractor.getField(page, fieldName);
	}

	@Override
	public Integer getFieldsCount(IPageObject page, String fieldName) {
		return fieldExtractor.getFieldsCount(page, fieldName);
	}

	@Override
	public ArrayList<? extends IPageObject> getCollection(String fieldName) {
		return fieldExtractor.getCollection(getCurrentPage(), fieldName);
	}

	public static void setPreviousPage(IPageObject page) {
		previousPage.set(page);
	}

	public static IPageObject getPreviousPage() {
		return previousPage.get();
	}

}
