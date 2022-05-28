package ru.netology.core.definitions;

import java.beans.PropertyEditorSupport;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BooleanEditor extends PropertyEditorSupport {

	public static final String VALUE_TRUE = "true";
	public static final String VALUE_FALSE = "false";

	public BooleanEditor(boolean allowEmpty) {
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text.isEmpty()) {
			setValue(Boolean.FALSE);
		} else if (text.equalsIgnoreCase(VALUE_FALSE)) {
			setValue(Boolean.FALSE);
		} else if (text.equalsIgnoreCase(VALUE_TRUE)) {
			setValue(Boolean.TRUE);
		} else {
			throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
		}
	}

	@Override
	public String getAsText() {
		if (Boolean.TRUE.equals(getValue())) {
			return VALUE_TRUE;
		} else if (Boolean.FALSE.equals(getValue())) {
			return VALUE_FALSE;
		} else {
			return VALUE_FALSE;
		}
	}

}