
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Cliente implements InterfazCliente {

    private String nombre;
    private String nombreServidor;
    private InterfazServidor servidor;
    private ChatView chatView;

    public Cliente (String nombre, InterfazServidor servidor, String nombreServidor, ChatView chatView) {
        super();
        this.nombre = nombre;
        this.nombreServidor = nombreServidor;
        this.servidor = servidor;
        this.chatView = chatView;
        try {
            InterfazCliente stub = (InterfazCliente) UnicastRemoteObject.exportObject((InterfazCliente) this, 0);
            servidor.registrar(nombre, stub);
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

    public void mostrarMensaje (String cliente, String mensaje) {
        chatView.mostrarMensaje(cliente, mensaje);
    }

    public void desconectar () {
        try {
            servidor.desconectar(nombre);
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void difundirMensaje (String mensaje) {
        try {
            servidor.difundirMensaje(nombre, mensaje);
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

    public String getNombre () {
        return nombre;
    }

    public String getNombreServidor () {
        return nombreServidor;
    }

    /**************************************************************************/

    public static void main (String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.getRegistry(args[0]);
            InterfazServidor servidor = (InterfazServidor) registry.lookup(args[1]);
            ChatView chatView = new ChatView();

            String nombre = "";
            do {
                CapturarNombreView capturarNombre = new CapturarNombreView(chatView, true);
                nombre = capturarNombre.getNombre();
            } while (!servidor.nombreCorrecto(nombre));

            Cliente cliente = new Cliente(nombre, servidor, args[1], chatView);
            chatView.setCliente(cliente);
            chatView.showView();
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

}
