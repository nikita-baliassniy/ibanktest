package ru.netology.fields;

import ru.netology.core.extractors.FieldMapping;

public class CustomFieldMapping extends FieldMapping {

	public static void set_FIELD_TYPE() {
		FIELD_TYPE_ALIASES.put("TextInput", TextInput.class);
		FIELD_TYPE_ALIASES.put("Button", Button.class);
		FIELD_TYPE_ALIASES.put("StaticText", StaticText.class);
		FIELD_TYPE_ALIASES.put("WebElementWrapper", WebElementWrapper.class);
		FIELD_TYPE_ALIASES.put("CheckBox", CheckBox.class);
	}
}