package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.adapter;

import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.exception.ProvadorPagamentosException;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.MetodoPagamento;

public class MetodoPagamentoAdapter {

	private MetodoPagamentoAdapter() {
	}

	public static MetodoPagamento of(String metodo) {
		switch (metodo){
			case "CREDITO":
				return MetodoPagamento.CREDITO;
			case "DEBITO":
				return MetodoPagamento.DEBITO;
			default:
				throw new ProvadorPagamentosException("Dados inválidos para o método de pagamento!!!");
		}
	}

}
