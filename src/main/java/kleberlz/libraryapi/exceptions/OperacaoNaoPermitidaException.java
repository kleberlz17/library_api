package kleberlz.libraryapi.exceptions;

@SuppressWarnings("serial")
public class OperacaoNaoPermitidaException extends RuntimeException {
	public OperacaoNaoPermitidaException(String message) {
		super(message);
	}

}
