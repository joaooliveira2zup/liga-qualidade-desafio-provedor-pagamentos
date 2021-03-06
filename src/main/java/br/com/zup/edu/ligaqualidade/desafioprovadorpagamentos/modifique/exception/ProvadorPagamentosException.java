package br.com.zup.edu.ligaqualidade.desafioprovadorpagamentos.modifique.exception;

public class ProvadorPagamentosException extends RuntimeException {

	public ProvadorPagamentosException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProvadorPagamentosException(String message) {
		super(message);
	}
}
