package ru.netology.pages;


import com.codeborne.selenide.SelenideElement;
import ru.netology.core.annotations.Container;

@Container(ref = "")
public class CollectionPageObject extends AbstractPageObject {
	SelenideElement parent;

	public SelenideElement getParent() {
		return parent;
	}

	public void setParent(SelenideElement parent) {
		this.parent = parent;
	}

	@Override
	public boolean isLoaded() {
		return true;
	}
}
