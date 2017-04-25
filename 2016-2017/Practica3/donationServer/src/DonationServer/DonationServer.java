// contador.java
package DonationServer;
import iDonationServer.iDonationServer;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import com.eclipsesource.json.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DonationServer extends UnicastRemoteObject implements iDonationServer {
    
    private int numServidor;
    private int numPuerto;
    private int numClientes = 0;
    private float dineroDonado = 0;
    private String host;
    private JsonObject datos;
    /* El servidor usa un archivo JSON para gestionar el dineroDonado y los usuarios y contraseñas del sistema, si no existe lo crea */
    
    public DonationServer(int numServidor, int numPuerto, String host) throws RemoteException, IOException, NotBoundException{
            this.numServidor = numServidor;  
            this.numPuerto = numPuerto;
            this.host = host;
            if(new File("data_servidor_" + numServidor + ".txt").isFile()){
                String text = new String(Files.readAllBytes(Paths.get("data_servidor_" + numServidor + ".txt")), StandardCharsets.UTF_8);                
                JsonObject obj = JsonObject.readFrom(text);  
                System.out.println("Archivo de datos leído correctamente.");   
                datos = obj;
                numClientes = datos.get("numClientes").asInt();
                dineroDonado = datos.get("dineroDonado").asInt();
            } else {        
                JsonObject obj = new JsonObject();       
                JsonObject usuario = new JsonObject();
                JsonArray usuarios = new JsonArray();
                usuario.add("username", "admin");
                usuario.add("password", "admin");                
                usuario.add("donado", 0);
                usuarios.add(usuario);
                obj.add("usuarios", usuarios);
                obj.add("dineroDonado", dineroDonado);                 
                numClientes++;
                obj.add("numClientes", numClientes);  
                datos = obj;        
                actualizarDatos();
            }
    }

    private void actualizarDatos() throws IOException{
        try (FileWriter file = new FileWriter("data_servidor_" + numServidor + ".txt")) {
            file.write(datos.toString());
            System.out.println("Datos actualizados.");
        }    
    }    
    
    @Override
    public boolean registrarse(String usuario, String contraseña) throws RemoteException {
        JsonObject nuevoUsuario = new JsonObject();
        JsonArray usuarios = datos.get("usuarios").asArray();
        for(JsonValue user : usuarios) {
            JsonObject usuarioObj = user.asObject();
            if( usuarioObj.get("username").asString().equals(usuario) ){
                System.out.println("El usuario especificado ya existe, elige otro nombre de usuario.");
                return false;
            }
        }
        nuevoUsuario.add("username", usuario);
        nuevoUsuario.add("password", contraseña);
        nuevoUsuario.add("donado", 0);
        usuarios.add(nuevoUsuario);
        datos.remove("usuarios");
        datos.add("usuarios", usuarios);
        numClientes++;
        datos.remove("numClientes");
        datos.add("numClientes", numClientes);
        try { 
            actualizarDatos();
        } catch (IOException ex) {
            Logger.getLogger(DonationServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Usuario creado correctamente.");
        return true;
    }

    @Override
    public boolean ingresar(String usuario, String contraseña) throws RemoteException {
        JsonArray usuarios = datos.get("usuarios").asArray();
        for(JsonValue user : usuarios) {
            JsonObject usuarioObj = user.asObject();
            if( usuarioObj.get("username").asString().equals(usuario) ){
                if( usuarioObj.get("password").asString().equals(contraseña) ){                    
                    System.out.println("Usuario identificado correctamente en el servidor " + numServidor + ".");
                    return true;
                } else {
                    System.out.println("Contraseña errónea.");
                    return false;
                }                    
            }
        }
        System.out.println("El usuario especificado no existe en el servidor " + numServidor + ".");
        return false;
    }

    @Override
    public boolean donar(String usuario, float valor) throws RemoteException {
        JsonArray usuarios = datos.get("usuarios").asArray();
        for(int i = 0; i < usuarios.size(); i++ /*JsonValue user : usuarios*/) {
            JsonObject usuarioObj = usuarios.get(i).asObject();
            if( usuarioObj.get("username").asString().equals(usuario) ){
                try {
                    float donado = usuarioObj.get("donado").asFloat();
                    usuarioObj.remove("donado");
                    usuarioObj.add("donado", donado + valor );
                    usuarios.remove(i);
                    usuarios.add(usuarioObj.asObject());
                    datos.remove("usuarios");
                    datos.add("usuarios", usuarios);
                    dineroDonado += valor;
                    datos.remove("dineroDonado");
                    datos.add("dineroDonado", dineroDonado);
                    System.out.println("Donados " + valor + " euros al servidor.");
                    actualizarDatos();
                } catch (IOException ex) {
                    Logger.getLogger(DonationServer.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public float consultarServidor() throws RemoteException {
        return dineroDonado;
    }

    @Override
    public float consultarTotal(String usuario) throws RemoteException, AccessException {
        float dineroReplica = 0;        
        JsonArray usuarios = datos.get("usuarios").asArray();
        for(JsonValue user : usuarios) {
            JsonObject usuarioObj = user.asObject();
            if( usuarioObj.get("username").asString().equals(usuario) &&  usuarioObj.get("donado").asFloat() > 0 ){                       
                try {
                    if(numServidor == 0){
                        Registry mireg = LocateRegistry.getRegistry(host, 1100);
                        iDonationServer donationServer = (iDonationServer)mireg.lookup("donationServer1");
                        dineroReplica = donationServer.consultarServidor();
                    } else if(numServidor == 1){                        
                        Registry mireg = LocateRegistry.getRegistry(host, 1099);
                        iDonationServer donationServer = (iDonationServer)mireg.lookup("donationServer0");
                        dineroReplica = donationServer.consultarServidor();
                    }
                    return dineroDonado + dineroReplica;
                } catch (NotBoundException ex) {
                    Logger.getLogger(DonationServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("El usuario especificado no existe o no ha donado aún.");
        return -1;    
    }

    @Override
    public int consultarClientes() throws RemoteException {
        return numClientes;
    }

    @Override
    public boolean estaRegistrado(String usuario) throws RemoteException {
        JsonArray usuarios = datos.get("usuarios").asArray();
        for(JsonValue user : usuarios) {
            JsonObject usuarioObj = user.asObject();
            if( usuarioObj.get("username").asString().equals(usuario) ){               
                System.out.println("El usuario especificado está registrado en el servidor" + numServidor + ".");
                return true;                
            }
        }
        return false;
    }
    
}