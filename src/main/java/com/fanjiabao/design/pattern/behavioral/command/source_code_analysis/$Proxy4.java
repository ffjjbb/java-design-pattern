package com.fanjiabao.design.pattern.behavioral.command.source_code_analysis;

import com.fanjiabao.design.pattern.behavioral.command.source_code_analysis.MysqlUser;
import com.fanjiabao.design.pattern.behavioral.command.source_code_analysis.UserMapper;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

public final class $Proxy4
extends Proxy
implements UserMapper {
    private static final Method m0;
    private static final Method m1;
    private static final Method m2;
    private static final Method m3;

    public final MysqlUser selectUserByPrimaryKey(String string, String string2) {
        try {
            return (MysqlUser)this.h.invoke(this, m3, new Object[]{string, string2});
        }
        catch (Error | RuntimeException throwable) {
            throw throwable;
        }
        catch (Throwable throwable) {
            throw new UndeclaredThrowableException(throwable);
        }
    }

    private static MethodHandles.Lookup proxyClassLookup(MethodHandles.Lookup lookup) throws IllegalAccessException {
        if (lookup.lookupClass() == Proxy.class && lookup.hasFullPrivilegeAccess()) {
            return MethodHandles.lookup();
        }
        throw new IllegalAccessException(lookup.toString());
    }

    public $Proxy4(InvocationHandler invocationHandler) {
        super(invocationHandler);
    }

    static {
        try {
            m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
            m3 = Class.forName("com.fanjiabao.design.pattern.behavioral.command.source_code_analysis.UserMapper").getMethod("selectUserByPrimaryKey", Class.forName("java.lang.String"), Class.forName("java.lang.String"));
//            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new NoSuchMethodError(noSuchMethodException.getMessage());
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    public final boolean equals(Object object) {
        try {
            return (Boolean)this.h.invoke(this, m1, new Object[]{object});
        }
        catch (Error | RuntimeException throwable) {
            throw throwable;
        }
        catch (Throwable throwable) {
            throw new UndeclaredThrowableException(throwable);
        }
    }

    public final String toString() {
        try {
            return (String)this.h.invoke(this, m2, null);
        }
        catch (Error | RuntimeException throwable) {
            throw throwable;
        }
        catch (Throwable throwable) {
            throw new UndeclaredThrowableException(throwable);
        }
    }

    public final int hashCode() {
        try {
            return (Integer)this.h.invoke(this, m0, null);
        }
        catch (Error | RuntimeException throwable) {
            throw throwable;
        }
        catch (Throwable throwable) {
            throw new UndeclaredThrowableException(throwable);
        }
    }
}