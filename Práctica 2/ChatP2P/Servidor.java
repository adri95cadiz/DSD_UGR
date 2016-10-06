
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Servidor implements InterfazServidor {

    private HashMap<String, InterfazCliente> clientes;
    private Registry registry;
    private String nombre;

    public Servidor (String nombre) {
        super();
        clientes = new HashMap<String, InterfazCliente>();
        this.nombre = nombre;
        try {
            registry = LocateRegistry.getRegistry();
            InterfazServidor stub = (InterfazServidor) UnicastRemoteObject.exportObject((InterfazServidor) this, 0);
            registry.rebind(this.nombre, stub);
        } catch (RemoteException e) {
            System.err.println("Servidor exception:");
            e.printStackTrace();
        }
    }

    public void registrar (String nombre, InterfazCliente cliente) {
        try {
            registry.rebind(nombre, cliente);
        } catch (RemoteException e) {
            System.err.println("Servidor exception:");
            e.printStackTrace();
        }
        clientes.put(nombre, cliente);
        for (String nombreCliente: clientes.keySet()) {
            try {
                clientes.get(nombreCliente).actualizarClientes(clientes);
            } catch (RemoteException e) {
                System.err.println("Servidor exception:");
                e.printStackTrace();
            }
        }
    }

    public void desconectar (String nombre) {
        try {
            registry.unbind(nombre);
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Servidor exception:");
            e.printStackTrace();
        }
        clientes.remove(nombre);
        for (String nombreCliente: clientes.keySet()) {
            try {
                clientes.get(nombreCliente).actualizarClientes(clientes);
            } catch (RemoteException e) {
                System.err.println("Servidor exception:");
                e.printStackTrace();
            }
        }
    }

    public boolean nombreCorrecto (String nombre) {
        if (nombre == null || nombre.length() < 3 || nombre.length() > 10) {
            return false;
        }
        boolean correcto = true;
        for (String cliente: clientes.keySet()) {
            if (cliente.equals(nombre)) {
                correcto = false;
            }
        }
        return correcto;
    }

    /**************************************************************************/

    public static void main (String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Servidor servidor = new Servidor(args[0]);
    }

}
