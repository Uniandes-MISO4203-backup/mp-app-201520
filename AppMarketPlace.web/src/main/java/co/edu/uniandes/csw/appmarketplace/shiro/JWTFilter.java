/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.appmarketplace.shiro;


import co.edu.uniandes.csw.appmarketplace.dtos.UserDTO;
import co.edu.uniandes.csw.appmarketplace.ejbs.AutorizacionException;
import static co.edu.uniandes.csw.appmarketplace.shiro.ShiroUtils.getClient;
import com.stormpath.sdk.account.Account;
import java.util.regex.Pattern;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author jd.patino10
 */

public class JWTFilter extends AuthenticatingFilter{

   
    
    @Override
    protected AuthenticationToken createToken(ServletRequest sr, ServletResponse sr1) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean onAccessDenied(ServletRequest sr, ServletResponse response) throws Exception {
        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            //HttpServletResponse httpResponse = (HttpServletResponse) response;
            String token = getToken(httpRequest);
            JWT jwt=new JWT();
            UserDTO user=  jwt.verifyToken(token);
            login(user);
            return true;
        } catch (AutorizacionException e) {
            return false;
        }
            
                
            
       }
    public void login(UserDTO user){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword(), user.isRememberMe());
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
            if ("admininistrator".equalsIgnoreCase(user.getRole())) {
                currentUser.getSession().setAttribute("Admin", user);
            } else if ("developer".equalsIgnoreCase(user.getRole())) {
                currentUser.getSession().setAttribute("Developer", user);
            } else if ("user".equalsIgnoreCase(user.getRole())) {
                currentUser.getSession().setAttribute("Client", user);
            }
    }
    
    private String getToken(HttpServletRequest httpRequest) throws AutorizacionException{
        String token = null;
        final String authorizationHeader = httpRequest.getHeader("Authorization");
        System.out.println("HEADER: "+authorizationHeader);
        if (authorizationHeader == null) {
            throw new AutorizacionException("Unauthorized: No Authorization header was found");
        }

        String[] parts = authorizationHeader.split(" ");
        if (parts.length != 2) {
            throw new AutorizacionException("Unauthorized: No Authorization header was found");
        }

        String scheme = parts[0];
        String credentials = parts[1];

        Pattern pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(scheme).matches()) {
            token = credentials;
        }
        return token;
    }
    
  

   
    
}