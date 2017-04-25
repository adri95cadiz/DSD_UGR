// icontador.java
package iDonationServer;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface iDonationServer extends Remote {
    
    // Registrarse permite registrar un usuario a un servidor.
    public boolean registrarse(String usuario, String contraseña) throws RemoteException;
    // Ingresar permite hacer login en un servidor.
    public boolean ingresar(String usuario, String contraseña) throws RemoteException;
    // Donar permite realizar una donacion en el servidor en el que se esté registrado.
    public boolean donar (String usuario, float valor) throws RemoteException;    
    // ConsultarServidor permite consultar las donaciones del servidor en el que se esté registrado.
    public float consultarServidor() throws RemoteException;
    // ConsultarTotal permite consultar el total de las donaciones enviadas al sistema.
    public float consultarTotal(String usuario) throws RemoteException;
    // ConsultarClientes permite consultar el número de clientes registrado en el servidor.
    public int consultarClientes() throws RemoteException;
    // estaRegistrado permite consultar si un usuario concreto esta registrado o no en el servidor.
    public boolean estaRegistrado(String usuario) throws RemoteException;

}