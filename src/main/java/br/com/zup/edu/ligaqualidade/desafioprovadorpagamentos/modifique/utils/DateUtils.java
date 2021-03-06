package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.utils;

import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.model.Constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	private DateUtils() {}

	public static String format(final LocalDate date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
		return formatter.format(date);
	}


	public static LocalDate toLocalDate(final String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
		return LocalDate.parse(date, formatter);
	}
}
