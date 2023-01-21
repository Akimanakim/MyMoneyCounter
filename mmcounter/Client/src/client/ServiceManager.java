package client;

import api.services.UserService;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;

import java.net.ConnectException;
import java.net.MalformedURLException;

public class ServiceManager {

    private static ServiceManager sm;

    private UserService userService;

    private ServiceManager() throws MalformedURLException{
        String url = "http://localhost:9090/UserService";
        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setOverloadEnabled(true);
        userService = (UserService) factory.create(UserService.class, url);
    }

    public static synchronized ServiceManager getInstance() throws ConnectException {
        if (sm == null){
            try {
                sm = new ServiceManager();
            } catch (MalformedURLException | HessianRuntimeException e){
                throw new ConnectException();
            }
        }
        return sm;
    }

    public UserService getUserService() {
        return userService;
    }
}
