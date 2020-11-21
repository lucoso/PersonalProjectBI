package br.com.lucaswagner.controller;

import java.io.Serializable;
import java.time.Period;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.PieChartModel;

import br.com.lucaswagner.manager.ProjetoManager;
import br.com.lucaswagner.model.Pessoal;
import br.com.lucaswagner.model.ProcessoSeletivo;
import br.com.lucaswagner.model.Projeto;

@ManagedBean
@ViewScoped
public class HomeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int totalProjetosPessoais;

	private int totalProjetosSeletivos;

	private String tempoTotalProjetosFinalizados;

	private int totalProjetosFinalizados;

	private int totalProjetosPessoaisEmProducao;

	private int maxGraficoBarra = 0;

	private PieChartModel graficoProjetosFinalizados;

	private BarChartModel graficoTotalProjetos;
	
	private DonutChartModel graficoProjetosProducao;

	private ProjetoManager pm = new ProjetoManager();
	
	

	public int getTotalProjetosPessoais() {
		return totalProjetosPessoais;
	}

	public void setTotalProjetosPessoais(int totalProjetosPessoais) {
		this.totalProjetosPessoais = totalProjetosPessoais;
	}

	public int getTotalProjetosSeletivos() {
		return totalProjetosSeletivos;
	}

	public void setTotalProjetosSeletivos(int totalProjetosSeletivos) {
		this.totalProjetosSeletivos = totalProjetosSeletivos;
	}

	public String getTempoTotalProjetosFinalizados() {
		return tempoTotalProjetosFinalizados;
	}

	public void setTempoTotalProjetosFinalizados(String tempoTotalProjetosFinalizados) {
		this.tempoTotalProjetosFinalizados = tempoTotalProjetosFinalizados;
	}

	public int getTotalProjetosFinalizados() {
		return totalProjetosFinalizados;
	}

	public void setTotalProjetosFinalizados(int totalProjetosFinalizados) {
		this.totalProjetosFinalizados = totalProjetosFinalizados;
	}

	public int getTotalProjetosPessoaisEmProducao() {
		return totalProjetosPessoaisEmProducao;
	}

	public void setTotalProjetosPessoaisEmProducao(int totalProjetosPessoaisEmProducao) {
		this.totalProjetosPessoaisEmProducao = totalProjetosPessoaisEmProducao;
	}

	public int getMaxGraficoBarra() {
		return maxGraficoBarra;
	}

	public void setMaxGraficoBarra(int maxGraficoBarra) {
		this.maxGraficoBarra = maxGraficoBarra;
	}

	public PieChartModel getGraficoProjetosFinalizados() {
		return graficoProjetosFinalizados;
	}

	public void setGraficoProjetosFinalizados(PieChartModel graficoProjetosFinalizados) {
		this.graficoProjetosFinalizados = graficoProjetosFinalizados;
	}

	public BarChartModel getGraficoTotalProjetos() {
		return graficoTotalProjetos;
	}

	public void setGraficoTotalProjetos(BarChartModel graficoTotalProjetos) {
		this.graficoTotalProjetos = graficoTotalProjetos;
	}

	public DonutChartModel getGraficoProjetosProducao() {
		return graficoProjetosProducao;
	}

	public void setGraficoProjetosProducao(DonutChartModel graficoProjetosProducao) {
		this.graficoProjetosProducao = graficoProjetosProducao;
	}

	/**
	 * Métodos
	 * 
	 * @author Lucas Manhães
	 */

	public HomeBean() {
		TotalProjetos();
		tempoTotalProjetosFinalizados = CalcularTempoTotalFinalizados();
		totalProjetosFinalizados = TotalProjetosFinalizados();
		MontarGraficoTotalProjetosFinalizados();
		MontarGraficoTotalProjetos();
		MontarGraficoProducao();
	}

	private void TotalProjetos() {

		List<Pessoal> projetosPessoais = pm.BuscarTodosProjetosPessoais();
		List<ProcessoSeletivo> projetosSeletivos = pm.BuscarTodosProjetosSeletivos();
		int count = 0;

		for (Pessoal p : projetosPessoais) {
			if (p.isEmProducao() == true) {
				count++;
			}
		}

		totalProjetosPessoais = projetosPessoais.size();
		totalProjetosSeletivos = projetosSeletivos.size();
		totalProjetosPessoaisEmProducao = count;

	}

	private String CalcularTempoTotalFinalizados() {

		List<Projeto> projetos = pm.BuscarTodosProjetos();
		String tempoTotal;
		int anos = 0;
		int meses = 0;
		int dias = 0;

		for (Projeto p : projetos) {
			if(p.isFinalizado() == true){
				Period periodo = Period.between(p.getDataInicio(), p.getDataFinalizado());
				anos = anos + periodo.getYears();
				meses = meses + periodo.getMonths();
				dias = dias + periodo.getDays();
			}	
		}

		tempoTotal = anos + "anos" + meses + "meses" + dias + "dias";

		return tempoTotal;

	}

	private int TotalProjetosFinalizados() {

		List<Projeto> projetos = pm.BuscarTodosProjetos();
		int count = 0;
		int percent = 0;

		if (!projetos.isEmpty()) {
			for (Projeto p : projetos) {
				if (p.isFinalizado() == true) {
					count++;
				}
			}
		}
		
		percent = (count * 100)/projetos.size();

		return percent;
	}

	private void MontarGraficoTotalProjetosFinalizados() {

		List<Projeto> projetos = pm.BuscarTodosProjetos();
		int countFinalizados = 0;
		int countNaoFinalizados = 0;

		if (!projetos.isEmpty()) {
			for (Projeto p : projetos) {
				if (p.isFinalizado() == true) {
					countFinalizados++;
				} else {
					countNaoFinalizados++;
				}
			}
		}

		graficoProjetosFinalizados = new PieChartModel();

		graficoProjetosFinalizados.set("Projetos Finalizados", countFinalizados);
		graficoProjetosFinalizados.set("Projetos Não Finalzados", countNaoFinalizados);

		graficoProjetosFinalizados.setTitle("Quantidade de Projetos Fainalizados e Não Finalizados");
		graficoProjetosFinalizados.setLegendPosition("w");

	}

	private void MontarGraficoTotalProjetos() {

		int qntdPessoal = totalProjetosPessoais;
		int qntdSeletivo = totalProjetosSeletivos;

		BarChartModel models = new BarChartModel();

		ChartSeries pessoal = new ChartSeries();
		pessoal.setLabel("Projetos Pessoais");
		pessoal.set("Total de Projetos Pessoais", qntdPessoal);

		ChartSeries seletivo = new ChartSeries();
		seletivo.setLabel("Projetos Processos Seletivos");
		seletivo.set("Total de Projetos Relacionados à Processos Seletivos", qntdSeletivo);

		models.addSeries(pessoal);
		models.addSeries(seletivo);

		maxGraficoBarra = qntdPessoal + qntdSeletivo + 10;

		graficoTotalProjetos = models;

		graficoTotalProjetos.setTitle("Grafico do Total de Projetos");
		graficoTotalProjetos.setAnimate(true);
		graficoTotalProjetos.setBarWidth(150);
		graficoTotalProjetos.setLegendPosition("nw");
		Axis yAxis = graficoTotalProjetos.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(maxGraficoBarra);
		yAxis.setTickFormat("%d");
	}
	
	private void MontarGraficoProducao(){
		
		DonutChartModel model = new DonutChartModel();
		
		 List<Pessoal> projetosPessoais = pm.BuscarTodosProjetosPessoais();
	     int emProducao = 0;
	     int NaoEmProducao = 0;
	     
	     for(Pessoal p : projetosPessoais){
	    	 if(p.isEmProducao() == true){
	    		 emProducao++;
	    	 }else{
	    		 NaoEmProducao++;
	    	 }
	     }
		
		Map<String, Number> circle = new LinkedHashMap<String, Number>();
        circle.put("Projetos Pessoais que estão em Produção", emProducao);
        circle.put("Projetos Pessoais que NÃO estão em Produção", NaoEmProducao);
        model.addCircle(circle);
        
        graficoProjetosProducao = model;
        graficoProjetosProducao.setTitle("Gráfico Projetos Pessoais em Produção");
        graficoProjetosProducao.setLegendPosition("w");
        graficoProjetosProducao.setSliceMargin(5);
        graficoProjetosProducao.setShowDataLabels(true);
        
	}
}
