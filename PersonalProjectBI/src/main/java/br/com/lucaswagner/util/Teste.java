package br.com.lucaswagner.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Teste {

	public static void main(String[] args) {
		
		LocalDate init = LocalDate.of(2020, Month.JULY, 01);
		LocalDate fim = LocalDate.now();
		
		Period periodo = Period.between(init, fim);
		int dias = periodo.getDays();
		int meses = periodo.getMonths();
		int anos = periodo.getYears();
		
		long duracao = init.until(fim, ChronoUnit.DAYS);
		
		//descobrir as horas basta multiplicar por 24
		long horas = duracao * 24;
		
		//descobrir minutos basta multiplicar por 60
		long minutos = horas * 60;
		
		long min = minutos%60;
		long h = minutos/60;
		
		System.out.println(duracao);
		System.out.println(horas);
		System.out.println(minutos);
		System.out.println(h);
		System.out.println(min);
		System.out.println(dias);
		System.out.println(meses);
		System.out.println(anos);

	}

}
