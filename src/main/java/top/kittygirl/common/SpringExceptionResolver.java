package top.kittygirl.common;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import top.kittygirl.exception.PermissionException;

import javax.servlet.http.*;

public class SpringExceptionResolver implements HandlerExceptionResolver{
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System error";

        //判断当前请求是数据请求还是页面请求，根据后缀来判断.json .page
        if (url.endsWith(".json")) {
            if (ex instanceof PermissionException) {
                JsonData result = JsonData.fail(ex.getMessage());
            }
        }else {

        }

        return null;
    }
}
