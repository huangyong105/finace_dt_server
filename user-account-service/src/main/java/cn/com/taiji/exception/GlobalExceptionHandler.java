package cn.com.taiji.exception;

import cn.com.taiji.data.Result;
import groovy.util.logging.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;

/**
 * Web异常拦截.
 * @author hugh
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Integer ARGUMENT_ERROR = 20001;

    private Logger log = LoggerFactory.getLogger("GlobalExceptionHandler");

    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result bizException(Exception e) {
        log.info("业务异常,输出,message:{}",e.getMessage());
        return Result.failure("-1",e.getMessage());
    }


    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.info(e.getMessage());
        return Result.failure("-1","该接口不支持"+e.getMethod()+"请求");
    }

    private String getStackTrace(Throwable throwable){
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        StringBuilder stringBuilder=new StringBuilder(DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"))
            .append(" ").append(throwable.toString()).append("\n");
        for(StackTraceElement stackTraceElement:stackTrace) {
            stringBuilder.append(stackTraceElement.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
