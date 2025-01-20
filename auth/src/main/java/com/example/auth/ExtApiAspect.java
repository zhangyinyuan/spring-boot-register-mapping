//package com.example.auth;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.SortedMap;
//import java.util.TreeMap;
//import java.util.UUID;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//
//import lombok.extern.slf4j.Slf4j;
////省略内部使用包
//@Slf4j
//@Aspect
//@Component
//public class ExtApiAspect {
//    /**
//     * 系统接入管理
//     */
//    @Resource
//    SystemAccessMapper systemAccessMapper;
//
//    /**
//     * 不拦截路径
//     */
//    private static final Set<String> FILTER_PATHS = Collections.unmodifiableSet(new HashSet<>(
//            Arrays.asList("/api/extApi/test")));
//    /**
//     * 匹配ExtApiController下所有方法
//     */
//    @Pointcut("execution(public * com.test.ExtApiController.*(..))")
//    public void webLog() {
//    }
//
//    /**
//     * 1、控制层处理完后返回给用户前，记录日志
//     * 3、调用处理方法获取结果后，再调用本方法proceed
//     */
//    @Around("webLog()")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        long startTime = System.currentTimeMillis();
//        Object result = proceedingJoinPoint.proceed();
//        // 打印下返回给用户数据
////        log.info("RequestId={}, Response Args={}, Time-Consuming={} ms", RequestThreadLocal.getRequestId(),
////                JsonUtil.toJSON(result), System.currentTimeMillis() - startTime);
//        return result;
//    }
//
//    /**
//     * 2、调用控制层方法前执行
//     */
//    @Before("webLog()")
//    public void doBefore(JoinPoint joinPoint) throws Throwable {
////        RequestThreadLocal.setRequestId(getUUID());
//
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        //不拦截打印日志的方法
//        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
//        if(FILTER_PATHS.contains(path)){
//            return;
//        }
//
//        //请求参数
//        Object[] args = joinPoint.getArgs();
//        //请求的方法参数值 JSON 格式 null不显示
//        if (args.length > 0) {
//            for (int i = 0; i < args.length; i++) {
//                //请求参数类型判断过滤，防止JSON转换报错
//                if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse || args[i] instanceof MultipartFile) {
//                    continue;
//                }
//                StringBuilder logInfo = new StringBuilder();
////                logInfo.append("RequestId=").append(RequestThreadLocal.getRequestId()).append(SystemConstants.LOG_SEPARATOR)
////                        .append("  RequestMethod=").append(request.getRequestURI()).append(SystemConstants.LOG_SEPARATOR)
////                        .append("  Args=").append(args[i].toString());
//                log.info(logInfo.toString());
//                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(args[i]));
//                if (jsonObject != null) {
//                    String appId = jsonObject.getString("appId");
//                    String timestamp = jsonObject.getString("timestamp");
//                    String sign = jsonObject.getString("sign");
//                    if(StringUtils.isEmpty(appId) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(sign)){
//                        throw new CustomException(CodeEnum.EXT_API_PARAMETER_ERROR);
//                    }
//                    //1.时间戳校验，大于一天不处理
//                    long requestTime = Long.valueOf(timestamp);
//                    long nowTime = new Date().getTime();
//                    int seconds = (int) ((nowTime - requestTime)/1000);
//                    if(Math.abs(seconds) > 86400) {
//                        throw new CustomException(CodeEnum.EXT_API_TIMEOUT);
//                    }
//
//                    LambdaQueryWrapper<SystemAccessDTO> queryWrapper = new LambdaQueryWrapper<>();
//                    //!!!!!安全考虑，省略查询条件
//                    queryWrapper.last(" limit 1");
//                    SystemAccessDTO systemAccessDTO = systemAccessMapper.selectOne(queryWrapper);
//                    if(systemAccessDTO == null){
//                        throw new CustomException(CodeEnum.EXT_API_AUTH_ERROR);
//                    }
//                    //3.校验签名
//                    SortedMap<String, String> sortedMap = new TreeMap<>();
//                    sortedMap.put("appId", appId);
//                    sortedMap.put("timestamp", timestamp);
//                    sortedMap.put("sign", sign);
//                    Boolean flag = SignUtil.isCorrectSign(sortedMap, systemAccessDTO.getAppSecret());
//                    if(flag){
//                        log.info("外部系统接入，信息认证正确"+appId);
//                    }else{
//                        throw new CustomException(CodeEnum.EXT_API_AUTH_ERROR);
//                    }
//                }
//            }
//        }else{
//            throw new CustomException(CodeEnum.PARAMETER_ERROR);
//        }
//    }
//    /**
//     * 4、调用控制层方法后，说明校验通过不处理
//     */
//    @After("webLog()")
//    public void doAfter(JoinPoint joinPoint) throws Throwable {
//        log.info("外部接口调用鉴权成功" + RequestThreadLocal.getRequestId());
//    }
//
//    /**
//     * 5、处理完后
//     */
//    @AfterReturning(pointcut = "webLog()", returning = "result")
//    public void AfterReturning(JoinPoint joinPoint, RestResponse result) {
//        result.success(RequestThreadLocal.getRequestId());
//        RequestThreadLocal.remove();
//    }
//
//    /**
//     * 异常处理
//     */
//    @AfterThrowing("webLog()")
//    public void afterThrowing() {
//        RequestThreadLocal.remove();
//    }
//
//    /**
//     * 生成uuid
//     */
//    public static String getUUID() {
//        return UUID.randomUUID().toString().trim();
//    }
//}