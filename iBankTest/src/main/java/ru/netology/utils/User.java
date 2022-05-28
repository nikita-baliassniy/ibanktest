package ru.netology.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class User {

	private String name;

	private String password;

	@Setter
	private String status;
}

