package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.model;

public enum StatusRecebivel {
	PAGO("pago"),
	AGUARDANDO_LIBERACAO_FUNDOS("aguardando_pagamento");

	private String status;

	StatusRecebivel(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
