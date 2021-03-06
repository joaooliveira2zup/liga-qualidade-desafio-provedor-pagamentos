package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique;

import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.adapter.DadosRecebimentoAdiantadoAdapter;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.adapter.DadosTransacaoAdapter;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.model.DadosRecebimento;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.model.StatusRecebivel;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.utils.DateUtils;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.utils.FormatterUtils;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.DadosRecebimentoAdiantado;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.DadosTransacao;
import br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.pronto.MetodoPagamento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Solucao {

	/**
	 * 
	 * @param infoTransacoes dados das transações. A String está formatada da seguinte maneira:		
		<b>"valor,metodoPagamento,numeroCartao,nomeCartao,validade,cvv,idTransacao"</b>
		<ol>
		 <li> Valor é um decimal</li>
	 	 <li> O método de pagamento é 'DEBITO' ou 'CREDITO' </li>
	 	 <li> Validade é uma data no formato dd/MM/yyyy. </li>
	 	</ol>
	 	
	 * @param infoAdiantamentos informacao da transacao que pode ser recebida adiantada. A String está formatada da seguinte maneira:		
		<b>"idTransacao,taxa"</b>
		<ol>
	 	 <li> Taxa é um decimal </li>	 	 
	 	</ol> 
	 * 
	 * @return Uma lista de array de string com as informações na seguinte ordem:
	 * [status,valorOriginal,valorASerRecebidoDeFato,dataEsperadoRecebimento].
	 * <ol>
	 *  <li>O status pode ser 'pago' ou 'aguardando_pagamento'</li>
	 *  <li>O valor original e o a ser recebido de fato devem vir no formato decimal. Ex: 50.45</li>
	 *  <li>dataEsperadoRecebimento deve ser formatada como dd/MM/yyyy. Confira a classe {@link DateTimeFormatter}</li> 
	 * </ol> 
	 * 
	 * É esperado que o retorno respeite a ordem de recebimento
	 */
	public static List<String[]> executa(List<String> infoTransacoes, List<String> infoAdiantamentos) {
		List<String[]> result = new ArrayList<>();
		for( String transacao : infoTransacoes) {
			DadosTransacao dadosTransacao = DadosTransacaoAdapter.of(transacao);
			DadosRecebimentoAdiantado adiantamento = DadosRecebimentoAdiantadoAdapter.of(infoAdiantamentos);

			final DadosRecebimento recebimento = getDadosRecebimento(dadosTransacao);
			result.add(FormatterUtils.formatTransacao(recebimento));
		}
		return result;
	}

	private static DadosRecebimento getDadosRecebimento( DadosTransacao dado ){
		String vrDesc = calculaValorDescontado(dado).toString();
		return new DadosRecebimento(
				calculaStatusPagto(dado),
				dado.valor.toString(),
				vrDesc,
				DateUtils.format(calculaDtPagto(dado)));
	}

	private static String calculaStatusPagto(DadosTransacao transacao){
		StatusRecebivel status = calculaStatusPagtoPorMetodoPagamento(transacao.metodo);
		return status != null ? status.getStatus() : null;
	}

	private static BigDecimal calculaValorDescontado(DadosTransacao transacao){
		return calculaValorDescontadoPorMetodoPagamento(transacao.metodo, transacao.valor);
	}

	private static LocalDate calculaDtPagto(DadosTransacao transacao){
		return calculaDtPagtoPorMetodoPagamento(transacao.metodo, LocalDate.now());
	}

	private static StatusRecebivel calculaStatusPagtoPorMetodoPagamento(MetodoPagamento metodoPagamento){
		switch (metodoPagamento){
			case DEBITO:
				return StatusRecebivel.PAGO;
			case CREDITO:
				return StatusRecebivel.AGUARDANDO_LIBERACAO_FUNDOS;
			default:
				return null;
		}
	}

	private static BigDecimal calculaValorDescontadoPorMetodoPagamento(MetodoPagamento metodoPagamento, BigDecimal valor){
		switch (metodoPagamento){
			case DEBITO:
				return valor.subtract(aplicarDesconto(valor, new BigDecimal(3)));
			case CREDITO:
				return valor.subtract(aplicarDesconto(valor, new BigDecimal(5)));
			default:
				return null;
		}
	}

	private static BigDecimal aplicarDesconto(BigDecimal valor, BigDecimal porcentagemDesconto){
		BigDecimal percentual = porcentagemDesconto.divide( new BigDecimal(100));
		return valor.multiply(percentual);
	}

	private static LocalDate calculaDtPagtoPorMetodoPagamento(MetodoPagamento metodoPagamento, LocalDate data){
		switch (metodoPagamento){
			case DEBITO:
				return data;
			case CREDITO:
				return data.plusDays(30L);
			default:
				return null;
		}
	}


}
