package com.fanjiabao.design.pattern.behavioral.mediator.source_code_analysis;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/24
 * @description:
 * DispatcherServlet 中介者模式源码分析
 * 统一协调:
 *  HandlerMapping             根据请求路径找到 Controller 方法
 *  HandlerAdapter             适配并执行 Controller 方法
 *  HandlerExecutionChain      保存 Handler + Interceptor 链
 *  HandlerInterceptor         请求前后拦截
 *  HandlerExceptionResolver   异常处理
 *  HttpMessageConverter       @ResponseBody 返回值写出
 *  ViewResolver               页面视图解析
 * 中介者模式角色:
 *  Mediator:
 *      DispatcherServlet
 *  Colleague:
 *      HandlerMapping
 *      HandlerAdapter
 *      HandlerExecutionChain
 *      HandlerInterceptor
 *      Controller
 *      HandlerExceptionResolver
 *      HttpMessageConverter
 */
public class TomcatDispatcherServletMediatorAnalysis {


    /**
     * 启动流程:
     *  1.创建 Tomcat
     *      --> new Tomcat();
     *      --> tomcat.setPort(60000);
     *      --> tomcat.getConnector();
     *  2.创建 Spring Web 容器
     *      创建 Web 环境下的 Spring 容器, 后续 DispatcherServlet 会从这个容器中获取 MVC 组件
     *      --> new AnnotationConfigWebApplicationContext();
     *          注册 Spring MVC 配置类
     *      --> applicationContext.register(AppConfig.class); ==> AnnotationConfigWebApplicationContext.java:166
     *  3.创建 DispatcherServlet
     *      创建 Spring MVC 的前端控制器, 它是整个 MVC 请求处理流程的中介者
     *      --> new DispatcherServlet(applicationContext);
     *  4.注册 DispatcherServlet 到 Tomcat
     *      把所有请求都交给 DispatcherServlet 处理
     *      --> Tomcat.addServlet(context, "dispatcherServlet", dispatcherServlet);
     *      --> context.addServletMappingDecoded("/", "dispatcherServlet");
     * <p>
     * 浏览器访问: <a href="http://localhost:60000/hello?name=fanjiabao">...</a>
     * <p>
     * 请求进入 Spring MVC 源码主链路:
     *  HTTP 请求进入 Tomcat:
     *      Tomcat 调用过滤器链, 最终进入 Servlet
     *      --> org.apache.catalina.core.ApplicationFilterChain.doFilter(...) ==> ApplicationFilterChain.java:127
     *          --> internalDoFilter(request, response); ==> ApplicationFilterChain.java:134
     *              Servlet 标准入口, 根据 GET / POST 分发
     *              --> servlet.service(request, response); ==> ApplicationFilterChain.java:206
     *                  Tomcat调用目标Servlet,servlet编译期类型是Servlet,servlet运行时对象是DispatcherServlet,DispatcherServlet没有重写service(...),FrameworkServlet重写了service(...)
     *                  --> FrameworkServlet.service(HttpServletRequest request, HttpServletResponse response) ==> FrameworkServlet.class:422
     *                      如果是普通 GET / POST 请求
     *                      --> HTTP_SERVLET_METHODS.contains(request.getMethod())
     *                          --> super.service(request, response); ==> FrameworkServlet.class:424
     *                              --> HttpServlet.service(HttpServletRequest req, HttpServletResponse resp) ==> HttpServlet.java:555
     *                                  GET 请求进入 doGet, HttpServlet 根据请求方法分发, doGet 方法被 FrameworkServlet 重写了
     *                                  --> doGet(req, resp); ==> HttpServlet.java:564
     *                                      走 FrameworkServlet 下的
     *                                      --> FrameworkServlet.doGet(HttpServletRequest request, HttpServletResponse response) ==> FrameworkServlet.java:900
     *                                          准备 LocaleContext、RequestAttributes 等请求上下文
     *                                          --> processRequest(request, response); ==> FrameworkServlet.java:903
     *                                              --> doService(request, response); ==> FrameworkServlet.java:1014
     *                                                  DispatcherServlet 的请求处理入口, 准备一些 request 属性, 然后调用 doDispatch。
     *                                                  --> DispatcherServlet.doService(...) ==> DispatcherServlet.java:940
     *                                                      Spring MVC 最核心调度方法, 中介者模式的核心体现就在这里
     *                                                      --> doDispatch(request, response); ==> DispatcherServlet.java:979
     * <p>
     * DispatcherServlet#doDispatch(...) 核心流程:
     *  1.文件上传请求检查
     *      如果配置了 MultipartResolver, 则把普通 HttpServletRequest 包装成 MultipartHttpServletRequest
     *      --> processedRequest = checkMultipart(request); ==> DispatcherServlet.java:1061
     *  2.根据请求找到 Handler
     *      根据当前请求 /hello, 找到真正要执行的 Controller 方法
     *      mappedHandler 不是 Controller, mappedHandler 是 HandlerExecutionChain
     *      内部包含: {handler: HandlerMethod(也就是 HelloController#hello(String name)), interceptorList: 当前请求匹配到的 HandlerInterceptor 集合}
     *      --> mappedHandler = getHandler(processedRequest); ==> DispatcherServlet.java:1065
     *          DispatcherServlet 遍历所有 HandlerMapping, 谁能处理当前请求, 就返回谁的 HandlerExecutionChain
     *              --> for (HandlerMapping mapping : this.handlerMappings) {
     *                  HandlerExecutionChain handler = mapping.getHandler(request);
     *                  if (handler != null) {
     *                      return handler;
     *                  }
     *              }
     *      --> mapping.getHandler(request);
     *          HandlerMapping 的通用查找入口, 这里会调用子类的 getHandlerInternal(request), 真正根据请求查找 Handler
     *          --> AbstractHandlerMapping.getHandler(...) ==> AbstractHandlerMapping.java:506
     *              getHandlerInternal 是模板方法, AbstractHandlerMapping 不知道具体怎么根据 URL 找 Controller, 所以交给子类实现
     *              --> Object handler = getHandlerInternal(request); ==> AbstractHandlerMapping.java:507
     *                  根据当前请求路径, 从已经注册好的映射关系中查找 HandlerMethod
     *                  --> AbstractHandlerMethodMapping.getHandlerInternal(...) ==> AbstractHandlerMethodMapping.java:378
     *                      --> HandlerMethod handlerMethod = lookupHandlerMethod(lookupPath, request); ==> AbstractHandlerMethodMapping.java:382
     *          把 HandlerMethod 和当前请求匹配到的 HandlerInterceptor 集合, 包装成 HandlerExecutionChain
     *          --> HandlerExecutionChain executionChain = getHandlerExecutionChain(handler, request); ==> AbstractHandlerMapping.java:524
     *  最终结果:
     *          mappedHandler: HandlerExecutionChain
     *          mappedHandler.getHandler(): HandlerMethod
     *          mappedHandler.getHandler() 对应: HelloController#hello(String name)
     *  到这里为止:
     *          DispatcherServlet 已经知道 "谁能处理这个请求"
     *          但是还没有执行 Controller
     *  3.根据 Handler 找到 HandlerAdapter
     *      --> HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler()); ==> DispatcherServlet.java:1072
     *          遍历所有 HandlerAdapter, 找到 supports(handler) 返回 true 的适配器
     *              --> for (HandlerAdapter adapter : this.handlerAdapters) { ==> DispatcherServlet.java:1317
     *                  if (adapter.supports(handler)) {
     *                      return adapter;
     *                  }
     *              }
     *  到这里为止:
     *      DispatcherServlet 已经知道:
     *          谁处理请求: HelloController#hello(String name)
     *          谁负责执行它: RequestMappingHandlerAdapter
     *  4.执行拦截器 preHandle
     *      在真正执行 Controller 前, 先执行当前请求匹配到的拦截器前置逻辑
     *      --> if (!mappedHandler.applyPreHandle(processedRequest, response)) { return; } ==> DispatcherServlet.java:1084
     *          --> HandlerExecutionChain.applyPreHandle(...) ==> HandlerExecutionChain.java
     *              内部逻辑:
     *                  如果某个 preHandle 返回 false, 请求中断 不再执行 Controller
     *                  --> for (int i = 0; i < this.interceptorList.size(); i++) {
     *                          HandlerInterceptor interceptor = this.interceptorList.get(i);
     *                          if (!interceptor.preHandle(request, response, this.handler)) {
     *                              triggerAfterCompletion(request, response, null);
     *                              return false;
     *                          }
     *                          this.interceptorIndex = i;
     *                      }
     *  5.真正执行 Controller 方法
     *      目标:
     *          使用第 3 步找到的 HandlerAdapter,
     *          执行第 2 步找到的 HandlerMethod
     *      关键衔接:
     *          第 2 步:
     *              mappedHandler.getHandler() 得到 HandlerMethod
     *          第 3 步:
     *              getHandlerAdapter(mappedHandler.getHandler()) 得到 RequestMappingHandlerAdapter
     *          第 5 步:
     *              用 RequestMappingHandlerAdapter 执行 HandlerMethod
     *      --> mv = ha.handle(processedRequest, response, mappedHandler.getHandler()); ==> DispatcherServlet.java:1089
     *          父类模板方法, 统一接收 HandlerMethod, 然后交给子类 handleInternal 执行
     *          --> AbstractHandlerMethodAdapter.handle(...) ==> AbstractHandlerMethodAdapter.java
     *              --> return handleInternal(request, response, (HandlerMethod) handler); ==> AbstractHandlerMethodAdapter.java:87
     *                  执行 @RequestMapping / @GetMapping 对应的 Controller 方法
     *                  --> RequestMappingHandlerAdapter.handleInternal(...) ==> RequestMappingHandlerAdapter.java:809
     *                      --> mav = invokeHandlerMethod(request, response, handlerMethod); ==> RequestMappingHandlerAdapter.java:821
     *                          --> RequestMappingHandlerAdapter.invokeHandlerMethod(...) ==> RequestMappingHandlerAdapter.java:876
     *                              创建 ServletInvocableHandlerMethod, 准备参数解析器、返回值处理器、数据绑定工厂等
     *                                  --> ServletInvocableHandlerMethod invocableMethod = createInvocableHandlerMethod(handlerMethod); ==> RequestMappingHandlerAdapter.java:897
     *                                  --> invocableMethod.setHandlerMethodArgumentResolvers(this.argumentResolvers); ==> RequestMappingHandlerAdapter.java:899
     *                                  --> invocableMethod.setHandlerMethodReturnValueHandlers(this.returnValueHandlers); ==> RequestMappingHandlerAdapter.java:902
     *                                  先调用 Controller 方法, 再处理 Controller 返回值
     *                                  --> invocableMethod.invokeAndHandle(webRequest, mavContainer); ==> RequestMappingHandlerAdapter.java:926
     *                                      --> Object returnValue = invokeForRequest(webRequest, mavContainer, providedArgs); ==> ServletInvocableHandlerMethod.java:118
     *                                          解析 Controller 方法参数
     *                                          --> InvocableHandlerMethod.invokeForRequest(...) ==> InvocableHandlerMethod.java:175
     *                                              本案例 @RequestParam(defaultValue = "guest") String name, 最终解析得到: args[0] = "fanjiabao"
     *                                              --> Object[] args = getMethodArgumentValues(request, mavContainer, providedArgs);
     *                                                  --> return doInvoke(args); ==> InvocableHandlerMethod.java:188
     *                                                      通过反射真正调用 Controller 方法
     *                                                      --> return method.invoke(getBean(), args);
     *                                                          返回 "Hello fanjiabao, DispatcherServlet is Mediator."
     *                                                          --> HelloController.hello(name)
     *  6.处理 @RestController 返回值
     *      目标: HelloController 使用 @RestController, 把 Controller 返回的 String 写入 HTTP 响应体
     *      --> Object returnValue = invokeForRequest(webRequest, mavContainer, providedArgs); ==> ServletInvocableHandlerMethod.java:118
     *          --> this.returnValueHandlers.handleReturnValue(returnValue, getReturnValueType(returnValue), mavContainer, webRequest);
     *              遍历所有 HandlerMethodReturnValueHandler, 找到支持当前返回值的处理器
     *              --> HandlerMethodReturnValueHandlerComposite.handleReturnValue(...) ==> HandlerMethodReturnValueHandlerComposite.java:71
     *                  --> HandlerMethodReturnValueHandler handler = selectHandler(returnValue, returnType); ==> HandlerMethodReturnValueHandlerComposite.java:74
     *                      --> handler.handleReturnValue(returnValue, returnType, mavContainer, webRequest); ==> HandlerMethodReturnValueHandlerComposite.java:78
     *                          处理 @ResponseBody 返回值, 本案例中, 因为有 @ResponseBody, 所以走
     *                          --> RequestResponseBodyMethodProcessor.handleReturnValue(...) ==> RequestResponseBodyMethodProcessor.java ==> RequestResponseBodyMethodProcessor.java:175
     *                              标记请求已经处理完成, 不需要再走视图解析
     *                              --> mavContainer.setRequestHandled(true); ==> RequestResponseBodyMethodProcessor.java:179
     *                              使用消息转换器写出响应体
     *                              --> writeWithMessageConverters(returnValue, returnType, inputMessage, outputMessage); ==> RequestResponseBodyMethodProcessor.java:192
     *                                  选择合适的 HttpMessageConverter, 把返回值写入 response
     *                                  --> AbstractMessageConverterMethodProcessor.writeWithMessageConverters(...) ==> AbstractMessageConverterMethodProcessor.java:173
     *                                      返回值是 String, 通常选择 StringHttpMessageConverter, 但没有实现方法, 走 AbstractHttpMessageConverter
     *                                      --> ((HttpMessageConverter) converter).write(body, selectedMediaType, outputMessage); ==> AbstractMessageConverterMethodProcessor.java:300
     *                                          --> AbstractHttpMessageConverter.write(...) ==> AbstractHttpMessageConverter.java:206
     *                                              --> writeInternal(t, outputMessage); ==> AbstractHttpMessageConverter.java:235
     *                                                  把字符串写入 HTTP 响应体
     *                                                  --> StreamUtils.copy(str, charset, outputMessage.getBody()); ==> StringHttpMessageConverter.java:128
     *  7.处理默认视图名 + 执行拦截器 postHandle
     *      如果 Controller 返回了 ModelAndView, 但是没有设置具体 view, DispatcherServlet 会根据当前请求路径推导一个默认 viewName
     *      --> applyDefaultViewName(processedRequest, mv); ==> DispatcherServlet.java:1095
     *          源码逻辑:
     *              本案例 @RestController + String 返回值, 返回值已经通过 HttpMessageConverter 写入 response, 通常 mv 为 null, 所以这里不会设置默认视图名
     *              --> if (mv != null && !mv.hasView()) {  ==> DispatcherServlet.java:1134
     *                      String defaultViewName = getDefaultViewName(request);
     *                      if (defaultViewName != null) {
     *                          mv.setViewName(defaultViewName);
     *                      }
     *                 }
     *      然后再执行拦截器 postHandle, 目标: Controller 执行完成后, 执行拦截器后置逻辑
     *      --> mappedHandler.applyPostHandle(processedRequest, response, mv); ==> DispatcherServlet.java:1096
     *          --> HandlerExecutionChain.applyPostHandle(...) ==> HandlerExecutionChain.java:158
     *              倒序执行所有 HandlerInterceptor#postHandle
     *              --> for (int i = this.interceptorList.size() - 1; i >= 0; i--) {
     *                      HandlerInterceptor interceptor = this.interceptorList.get(i);
     *                      interceptor.postHandle(request, response, this.handler, mv);
     * 		            }
     *  8.处理最终分发结果
     *      目标: 统一处理异常、视图渲染、请求完成回调
     *      --> processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException); ==> DispatcherServlet.java:1106
     *          如果 Controller 执行过程中出现异常:
     *          --> mv = processHandlerException(request, response, handler, exception); ==> DispatcherServlet.java:1160
     *              遍历 HandlerExceptionResolver, 尝试把异常转换成 ModelAndView 或错误响应
     *              --> exMv = resolver.resolveException(request, response, handler, ex);
     *          如果存在 ModelAndView 且没有被清理, 视图渲染
     *          --> render(mv, request, response); ==> DispatcherServlet.java:1167
     *      本案例, @RestController + String 返回值, 返回值已经通过 StringHttpMessageConverter 写入响应体, mavContainer.setRequestHandled(true), 所以通常不会再走 JSP / ViewResolver 渲染
     *  9.触发 afterCompletion
     *  目标: 请求彻底完成后, 触发拦截器 afterCompletion
     *      --> mappedHandler.triggerAfterCompletion(request, response, null); ==> DispatcherServlet.java:1185
     *          倒序执行已经成功执行过 preHandle 的拦截器
     *          --> interceptor.afterCompletion(request, response, this.handler, ex); ==> HandlerExecutionChain.java:176
     */
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(60000);
        String baseDir = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
        tomcat.setBaseDir(baseDir);
        tomcat.getConnector();
        // 创建 Web 应用上下文
        Context context = tomcat.addContext("", baseDir);

        // 创建 Spring MVC Web 容器
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();

        // 注册 Spring MVC 配置类
        applicationContext.register(AppConfig.class);

        // 创建 DispatcherServlet，也就是 Spring MVC 的中介者
        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);

        // 把 DispatcherServlet 注册到 Tomcat
        Tomcat.addServlet(context, "dispatcherServlet", dispatcherServlet);

        // 所有请求都交给 DispatcherServlet
        context.addServletMappingDecoded("/", "dispatcherServlet");

        tomcat.start();
        System.out.println("http://localhost:60000/hello?name=fanjiabao");
        tomcat.getServer().await();
    }


}