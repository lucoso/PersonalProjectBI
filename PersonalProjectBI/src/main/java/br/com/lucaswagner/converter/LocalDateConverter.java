package br.com.lucaswagner.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("dateConverter")
public class LocalDateConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (null == value || value.isEmpty()) {
			return null;
			}

			LocalDate localDate;

			try {
			localDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			} catch (DateTimeParseException e) {

			throw new ConverterException("Erro ao Converter a DATA.");
			}

			return localDate;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (null == value) {

			return "";
			}

			return ((LocalDate) value).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
	}
	
	


