package ru.netology.pages;

import java.util.ArrayList;
import ru.netology.fields.AbstractField;

public interface IPageObject {

	<T extends AbstractField> T getField(String fieldName);

	<T extends AbstractField> T getField(IPageObject page, String fieldName);

	ArrayList<? extends IPageObject> getCollection(String fieldName);

	Integer getFieldsCount(IPageObject page, String fieldName);

	<T extends AbstractField> T getFilterSortField(IPageObject page, String fieldName);

	boolean isLoaded();
}
