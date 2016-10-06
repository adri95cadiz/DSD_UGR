
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface InterfazCliente extends Remote {
    
    public void actualizarClientes (HashMap<String, InterfazCliente> clientes) throws RemoteException;
    public void iniciarChat (String cliente) throws RemoteException;
    public void cerrarChat (String cliente) throws RemoteException;
    public void enviarMensaje (String clienteAmigo, String mensaje) throws RemoteException;
    public void mostrarMensaje (String clienteAmigo, String mensajero, String mensaje) throws RemoteException;
    
}
