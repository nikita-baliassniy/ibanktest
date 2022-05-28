package ru.netology.core.definitions;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import java.beans.PropertyEditorManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public class FieldDefinitions {

	@Getter
	private static List<FieldDefinition> FIELDS;

	static {
		PropertyEditorManager.registerEditor(Boolean.class, BooleanEditor.class);
	}

	public static List<FieldDefinition> load(String path) {
		CSVReader reader = null;
		try {
			File def = new File(path);
			reader = new CSVReader(new FileReader(def), ';');
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CsvToBean<FieldDefinition> bean = new CsvToBean<FieldDefinition>();
		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("Parent", "parent");
		columnMapping.put("Aliases", "aliases");
		columnMapping.put("Type", "type");
		columnMapping.put("CustomType", "customType");
		columnMapping.put("Target", "target");
		columnMapping.put("Items", "items");
		columnMapping.put("isLoaded", "loaded");
		columnMapping.put("Description", "description");
		HeaderColumnNameTranslateMappingStrategy<FieldDefinition> strategy =
				new HeaderColumnNameTranslateMappingStrategy<FieldDefinition>();
		strategy.setType(FieldDefinition.class);
		strategy.setColumnMapping(columnMapping);
		FIELDS = bean.parse(strategy, reader);
		return FIELDS;
	}

	public static List<FieldDefinition> getDefinitionsByParent(final String parent) {
		return Lists.newArrayList(Iterables.filter(FIELDS, new Predicate<FieldDefinition>() {
			@Override
			public boolean apply(FieldDefinition input) {
				return input.getParent().equals(parent);
			}
		}));
	}

	public static List<FieldDefinition> getDefinitionsPageByContainer(final String container) {
		final String parent = getParentFromPage(container);
		final String name = getNameFromPage(container);
		return Lists.newArrayList(Iterables.filter(FIELDS, new Predicate<FieldDefinition>() {
			@Override
			public boolean apply(FieldDefinition input) {
				return input.getParent().equals(parent) && input.getAliases().equals(name) && (
						input.getType().equals("Page") || input.getType().equals("Collection"));
			}
		}));
	}

	public static List<FieldDefinition> getPages() {
		return Lists.newArrayList(Iterables.filter(FIELDS, new Predicate<FieldDefinition>() {
			@Override
			public boolean apply(FieldDefinition input) {
				return input.getType().equals("Page");
			}
		}));
	}

	private static String getParentFromPage(String parent) {
		String[] parents = parent.split("\\.");
		String pageParent = "";
		for (int i = 0; i < parents.length; i++) {
			if (i == parents.length - 1) {
				break;
			}
			if (pageParent.equals("")) {
				pageParent = parents[i];
			} else {
				pageParent = pageParent + "." + parents[i];
			}
		}
		return pageParent;
	}

	private static String getNameFromPage(String parent) {
		String[] parents = parent.split("\\.");
		return parents[parents.length - 1];
	}
}