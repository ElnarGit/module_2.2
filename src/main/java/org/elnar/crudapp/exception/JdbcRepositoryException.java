package org.elnar.crudapp.exception;

public class JdbcRepositoryException extends RuntimeException{
	
	public JdbcRepositoryException(String message) {
		super(message);
	}
	
	public JdbcRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}
}

