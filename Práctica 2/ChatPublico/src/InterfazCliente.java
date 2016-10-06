
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazCliente extends Remote {
    
    public void mostrarMensaje (String cliente, String mensaje) throws RemoteException;
    
}
