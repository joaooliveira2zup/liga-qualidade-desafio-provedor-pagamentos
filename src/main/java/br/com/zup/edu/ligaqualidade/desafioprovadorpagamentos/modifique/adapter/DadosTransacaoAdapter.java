package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.adapter;

import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.utils.DateUtils;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.exception.ProvadorPagamentosException;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.DadosTransacao;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.MetodoPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DadosTransacaoAdapter {
	private DadosTransacaoAdapter() {
	}

	public static DadosTransacao of(String transacao) {
		if(transacao == null || transacao.isEmpty()){
			throw new ProvadorPagamentosException("Dados inválidos para os dados da transação!!!");
		}

		List<String> infoTransacoes = List.of(transacao.split(","));

		if(infoTransacoes.size() < 7){
			throw new ProvadorPagamentosException("Dados inválidos para os dados da transação!!!");
		}

		final BigDecimal valor = new BigDecimal(infoTransacoes.get(0));
		final MetodoPagamento metodo = MetodoPagamentoAdapter.of(infoTransacoes.get(1));
		final String numero = infoTransacoes.get(2);
		final String nome = infoTransacoes.get(3);
		final LocalDate validade = DateUtils.toLocalDate(infoTransacoes.get(4));
		final int cvv = Integer.parseInt(infoTransacoes.get(5));
		final int id = Integer.parseInt(infoTransacoes.get(6));

		return new DadosTransacao(valor, metodo, numero, nome, validade, cvv, id);
	}

}
