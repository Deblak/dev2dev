package co.simplon.dev2dev_business.exceptions;

public class ArticleShareLinkException extends RuntimeException {
    public ArticleShareLinkException(String message, Throwable cause) {
        super(message, cause);
        System.out.println(message);
    }
}
