package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.utils;


import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.model.DadosRecebimento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FormatterUtils {
	private  FormatterUtils() {
	}

	public static List<String[]> formatTransacao(String status, BigDecimal vrTotal, BigDecimal vrComDesconto, LocalDate dataPagto){
		return List.of(
				new String[]{status},
				new String[]{vrTotal.toString()},
				new String[]{vrComDesconto.toString()},
				new String[]{DateUtils.format(dataPagto)});
	}

	public static String[] formatTransacao(DadosRecebimento dado){

		return new String[] {
				dado.status,
				dado.valorOriginal,
				dado.valorASerRecebido,
				dado.dataRecebimento
		};
	}
}
