package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.adapter;

import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.exception.ProvadorPagamentosException;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.DadosRecebimentoAdiantado;

import java.math.BigDecimal;
import java.util.List;

public class DadosRecebimentoAdiantadoAdapter {

	private DadosRecebimentoAdiantadoAdapter() {
	}

	public static DadosRecebimentoAdiantado of(List<String> infoAdiantamentos) {
		if(infoAdiantamentos == null || infoAdiantamentos.isEmpty()){
			return null;
		}

		infoAdiantamentos = List.of(infoAdiantamentos.get(0).split(","));

		if(infoAdiantamentos.size() < 2){
			throw new ProvadorPagamentosException("Dados inválidos para o Recebimento adiantado!!!");
		}

		final int idTransacao = Integer.parseInt(infoAdiantamentos.get(0));
		final BigDecimal taxa = new BigDecimal(infoAdiantamentos.get(1));

		return new DadosRecebimentoAdiantado(idTransacao, taxa);
	}

}
