package com.fanjiabao.design.pattern.behavioral.command.source_code_analysis;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.util.MapUtil;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.util.MapUtil;

public class MapperProxy<T>
        implements InvocationHandler,
        Serializable {
    private static final long serialVersionUID = -4724728412955527868L;
    private static final int ALLOWED_MODES = 15;
    private static final Constructor<MethodHandles.Lookup> lookupConstructor;
    private static final Method privateLookupInMethod;
    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethodInvoker> methodCache;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            /* 83*/
            if (Object.class.equals(method.getDeclaringClass())) {
                /* 84*/
                return method.invoke(this, args);
            }
            /* 86*/
            return this.cachedInvoker(method).invoke(proxy, method, args, this.sqlSession);
        } catch (Throwable t) {
            /* 88*/
            throw ExceptionUtil.unwrapThrowable(t);
        }
    }

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethodInvoker> methodCache) {
        /* 49*/
        this.sqlSession = sqlSession;
        /* 50*/
        this.mapperInterface = mapperInterface;
        /* 51*/
        this.methodCache = methodCache;
    }

    static {
        Method privateLookupIn;
        try {
            /* 57*/
            privateLookupIn = MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);
        } catch (NoSuchMethodException e) {
            /* 59*/
            privateLookupIn = null;
        }
        /* 61*/
        privateLookupInMethod = privateLookupIn;
        /* 63*/
        Constructor lookup = null;
        /* 64*/
        if (privateLookupInMethod == null) {
            try {
                /* 67*/
                lookup = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
                /* 68*/
                lookup.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("There is neither 'privateLookupIn(Class, Lookup)' nor 'Lookup(Class, int)' method in java.lang.invoke.MethodHandles.", e);
            } catch (Exception e) {
                /* 74*/
                lookup = null;
            }
        }
        /* 77*/
        lookupConstructor = lookup;
    }

    private MapperMethodInvoker cachedInvoker(Method method) throws Throwable {
        try {
            /* 94*/
            return MapUtil.computeIfAbsent(this.methodCache, method, m -> {
                /* 95*/
                if (!m.isDefault()) {
                    return new PlainMethodInvoker(new MapperMethod(this.mapperInterface, method, this.sqlSession.getConfiguration()));
                }
                try {
                    /* 99*/
                    if (privateLookupInMethod == null) {
                        return new DefaultMethodInvoker(this.getMethodHandleJava8(method));
                    }
                    return new DefaultMethodInvoker(this.getMethodHandleJava9(method));
                } catch (IllegalAccessException | InstantiationException | NoSuchMethodException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (RuntimeException re) {
            /*109*/
            Throwable cause = re.getCause();
            /*110*/
            throw cause == null ? re : cause;
        }
    }

    private MethodHandle getMethodHandleJava9(Method method) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        /*116*/
        Class<?> declaringClass = method.getDeclaringClass();
        /*117*/
        return ((MethodHandles.Lookup) privateLookupInMethod.invoke(null, declaringClass, MethodHandles.lookup())).findSpecial(declaringClass, method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes()), declaringClass);
    }

    private MethodHandle getMethodHandleJava8(Method method) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        /*124*/
        Class<?> declaringClass = method.getDeclaringClass();
        /*125*/
        return lookupConstructor.newInstance(declaringClass, 15).unreflectSpecial(method, declaringClass);
    }

    static interface MapperMethodInvoker {
        public default Object invoke(Object var1, Method var2, Object[] var3, SqlSession var4) throws Throwable {
            return null;
        }
    }

    private static class PlainMethodInvoker
            implements MapperMethodInvoker {
        private final MapperMethod mapperMethod;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args, SqlSession sqlSession) throws Throwable {
            return this.mapperMethod.execute(sqlSession, args);
        }

        public PlainMethodInvoker(MapperMethod mapperMethod) {
            this.mapperMethod = mapperMethod;
        }
    }

    private static class DefaultMethodInvoker
            implements MapperMethodInvoker {
        private final MethodHandle methodHandle;

        public DefaultMethodInvoker(MethodHandle methodHandle) {
            this.methodHandle = methodHandle;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args, SqlSession sqlSession) throws Throwable {
            return this.methodHandle.bindTo(proxy).invokeWithArguments(args);
        }
    }
}
