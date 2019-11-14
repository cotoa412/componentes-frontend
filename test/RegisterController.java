/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.componentes.controlador.UsuarioController;
import com.componentes.entidades.Usuario;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Kainthel
 */
@ManagedBean (name = "registroController")
@SessionScoped
public class RegisterController {
    private String email;
    private String username;
    private String password;
    private Usuario user = new Usuario();

    public RegisterController() {
    }
    
        public String registrar(){
     
        String redirect = "";
        
        if (this.email.equals("") || this.username.equals("") || this.password.equals("")) { //en caso alguno se deja vacio
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Se debe llenar todos los campos.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        
        }else if(this.comprobarNombreUsuario(this.username) == true){
        
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El nombre de usuario no esta disponible.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }else if(this.comprobarCorreoExiste(this.email) == true){
        
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El correo electrónico no esta disponible.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }else{
            
            
            if (this.comprobarCorreoValido(this.email) == true) {
        
            UsuarioController uc = new UsuarioController();
            uc.Insert(user);
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", "Correct");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
         redirect =  "index.xhtml?faces-redirect=true"; //Redirecciona al login para que se pueda ingresar con la nueva cuenta
            
        }else{//Something f*cked up
            
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar el registro."));
            
        }
            
        }
        
        
        
        return redirect;
    }
     

    public boolean comprobarCorreoValido(String email){
        
        boolean comprobar = false;
        
        // Patrón para validar el email
        Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@"+"(([a-z]+)\\.([a-z]+))+");
        
        // El email a validar
 
        Matcher mather = pattern.matcher(email);
 
        if (mather.find() == true) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", "El email ingresado es válido.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            comprobar = true;
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El email ingresado es inválido.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return comprobar;
    }
    
    
    public boolean comprobarNombreUsuario(String username){
        
        boolean comprob = false;
        
        for (Object obj : new UsuarioController().get()) {
            
            if (((Usuario)obj).getNombre().equals(username)) {
            
                comprob = true;
                
                
            }
               
        }
        return comprob;
    }
    
    
    public boolean comprobarCorreoExiste(String email){
    
        boolean comprob = false;
        
        for (Object obj : new UsuarioController().get()) {
            
            if (((Usuario)obj).getCorreo().equals(email)) {
            
               comprob = true;
                
            }
               
        }
        
        return comprob;
    }
     
}

