
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
            InterfazCliente stub = (InterfazCliente) UnicastRemoteObject.exportObject((InterfazCliente) this, 0); // Se crea el stub
            servidor.registrar(nombre, stub); // Avisa al servidor para que registre al cliente
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

    public void mostrarMensaje (String cliente, String mensaje) {
        chatView.mostrarMensaje(cliente, mensaje); // Se envía el mensaje a la GUI para que lo muestre
    }

    public void desconectar () {
        try {
            servidor.desconectar(nombre); // Se avisa al servidor para que elimine al cliente
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void difundirMensaje (String mensaje) {
        try {
            servidor.difundirMensaje(nombre, mensaje); // Se avisa al servidor para que difunda un mensaje
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
        if (System.getSecurityManager() == null) { // Instalación del gestor de seguridad
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.getRegistry(args[0]); // Se obtiene el RMI registry de la ip del servidor
            InterfazServidor servidor = (InterfazServidor) registry.lookup(args[1]); // Se busca el servidor
            
            ChatView chatView = new ChatView(); // Se crea la GUI del cliente

            String nombre = "";
            do {
                CapturarNombreView capturarNombre = new CapturarNombreView(chatView, true); // Se lanza el capturador de nombres
                nombre = capturarNombre.getNombre(); // Se obtiene el nombre
            } while (!servidor.nombreCorrecto(nombre));

            Cliente cliente = new Cliente(nombre, servidor, args[1], chatView); // Se crea el cliente

            chatView.setCliente(cliente);  // Se asigna la GUI al cliente y el cliente a la GUI
            chatView.showView(); // Se inicia la GUI
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }
    
}
