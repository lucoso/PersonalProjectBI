package br.com.lucaswagner.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.lucaswagner.manager.EmpresaManager;
import br.com.lucaswagner.model.Empresa;

@FacesConverter("empresaConverter")
public class EmpresaConverter implements Converter {

	EmpresaManager em = new EmpresaManager();

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Empresa e = em.BuscarEmpresaPorIDParaConverter(Long.parseLong(value));
		return e;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Empresa e = (Empresa) value;
		return String.valueOf(e.getId());
	}

}
