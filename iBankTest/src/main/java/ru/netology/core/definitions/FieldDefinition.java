package ru.netology.core.definitions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Assert;
import ru.netology.properties.FieldProperties;

@ToString(exclude = {"aliases", "type"})
public class FieldDefinition {

	@Getter
	@Setter
	private String parent;

	@Getter
	@Setter
	private String aliases;

	@Getter
	@Setter
	private String type;

	@Getter
	@Setter
	private String customType;

	@Setter
	@Getter
	private String target;

	@Getter
	@Setter
	private String description;

	@Getter
	@Setter
	private Boolean loaded;

	@Getter
	@Setter
	private String items;

	public String getPath() {
		if (parent.isEmpty()) {
			return aliases;
		}
		return parent + "." + aliases;
	}

	public String getTarget() {
		if (target.isEmpty()) {
			target = FieldProperties.getInstance().getProperties().getProperty(getType());
			if (target == null) {
				Assert.fail(String.format("Не найден локатор для поля: [%s]", getAliases()));
			}
			target = String.format(target, getAliases());
		}
		return target;
	}
}
