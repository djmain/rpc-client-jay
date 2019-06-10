import com.sun.org.apache.regexp.internal.RE;

import java.lang.reflect.Proxy;

/**
 * 创建服务代理
 * created by Jay on 2019/6/10
 */
public class RPCClientProxy
{
    public static Object getInstance(Class clazz, String ip, int port)
    {
        try
        {
            return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                                          new RPCClientInvocationHandler(ip, port, UserService.class));
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
