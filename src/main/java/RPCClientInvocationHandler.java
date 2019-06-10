import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * created by Jay on 2019/6/10
 */
public class RPCClientInvocationHandler implements InvocationHandler
{

    private String ip;
    private int port;
    private Class clazz;

    public RPCClientInvocationHandler(String ip, int port, Class clazz)
    {
        this.ip = ip;
        this.port = port;
        this.clazz = clazz;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        Object result = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try
        {
            Socket socket = new Socket(ip, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            RPCRequest request = new RPCRequest();
            request.setClassName(clazz.getName());
            request.setMethodName(method.getName());
            request.setParameters(args);
            outputStream.writeObject(request);
            inputStream = new ObjectInputStream(socket.getInputStream());
            result = inputStream.readObject();
            if (result instanceof AddUserException)
            {
                AddUserException addUserException = (AddUserException) result;
                throw addUserException;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
            if (outputStream != null)
            {
                outputStream.close();
            }
        }
        return result;
    }
}
