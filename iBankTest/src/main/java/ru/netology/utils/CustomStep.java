package ru.netology.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class CustomStep {

	@Getter
	@Setter
	private String content;

	@Getter
	@Setter
	private String expected;

}