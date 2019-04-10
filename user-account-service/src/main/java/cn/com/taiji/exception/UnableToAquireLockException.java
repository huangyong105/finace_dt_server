package cn.com.taiji.exception;

/**
 * 异常处理类
 * @author liguobao
 *
 */
public class UnableToAquireLockException extends RuntimeException{
	
	public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }

}
