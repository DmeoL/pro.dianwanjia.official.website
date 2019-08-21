package pro.dianwanjia.official.website.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pro.dianwanjia.official.website.util.Base.ResultVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LX
 * @date: 2019/8/6 12:03
 * @description: AOP 异常拦截类,使用时，需要在application中@Import({GlobalExceptionHandler.class})
 * @version V1.0.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(GlobalExceptionHandler.class);

    private static final String UNKNOW_RESULT_CODE = "99999999";


    @Value("${spring.application.name}")
    private String systemName;

    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public ResponseEntity<ResultVO> defaultErrorHandler(GlobalException e) {
        LOGGER.error("GlobalException:"+e.getMessage(), e);

        return ResponseEntity.ok(new ResultVO(e.getStatusCode(), e.getMessage()));
    }

    /**
     * 参数错误
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<String> defaultErrorHandler(HttpServletRequest req, HttpMessageNotReadableException e) throws Exception {
        LOGGER.error("HttpMessageNotReadableException:"+e.getMessage());

        return ResponseEntity.badRequest().body("params error");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<ResultVO> defaultErrorHandler(Exception e) {
        LOGGER.error("Exception:"+e.getMessage(), e);

        return ResponseEntity.ok(new ResultVO(UNKNOW_RESULT_CODE, e.getMessage(), null));
    }


}

