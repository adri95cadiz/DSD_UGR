
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazServidor extends Remote {
    
    public void registrar (String nombre, InterfazCliente cliente) throws RemoteException;
    public void difundirMensaje (String nombre, String mensaje) throws RemoteException;
    public void desconectar (String nombre) throws RemoteException;
    public boolean nombreCorrecto (String nombre) throws RemoteException;
    
}
