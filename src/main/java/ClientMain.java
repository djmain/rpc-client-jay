import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * created by Jay on 2019/6/10
 */
public class ClientMain
{
    public static void main(String[] args)
    {
        try
        {
            UserService userService = (UserService) RPCClientProxy.getInstance(UserService.class, "127.0.0.1", 1000);
            String result = userService.sayHello("xdj");
            User user = new User();
            user.setName("zs");
            userService.addUser(user);
            System.out.println(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
