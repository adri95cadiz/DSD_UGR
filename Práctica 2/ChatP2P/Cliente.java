
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Cliente implements InterfazCliente {

    private String nombre;
    private String nombreServidor;
    private InterfazServidor servidor;
    private ClienteView clienteView;
    private HashMap<String, InterfazCliente> clientes;
    private HashMap<String, ChatView> chats;

    public Cliente (String nombre, InterfazServidor servidor, String nombreServidor, ClienteView clienteView) {
        super();
        this.nombre = nombre;
        this.nombreServidor = nombreServidor;
        this.servidor = servidor;
        this.clienteView = clienteView;
        clientes = new HashMap<String, InterfazCliente>();
        chats = new HashMap<String, ChatView>();
        try {
            InterfazCliente stub = (InterfazCliente) UnicastRemoteObject.exportObject((InterfazCliente) this, 0);
            servidor.registrar(nombre, stub);
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

    public void actualizarClientes (HashMap<String, InterfazCliente> clientes) {
        this.clientes = clientes;
        this.clientes.remove(nombre); // No tiene que aparecer su nombre
        clienteView.actualizarClientes(this.clientes.keySet().toArray(new String[this.clientes.size()]));
    }

    public void mostrarMensaje (String nombreAmigo, String mensajero, String mensaje) {
        chats.get(nombreAmigo).mostrarMensaje(mensajero, mensaje);
    }

    public void enviarMensaje (String nombreAmigo, String mensaje) {
        try {
            mostrarMensaje(nombreAmigo, nombre, mensaje);
            clientes.get(nombreAmigo).mostrarMensaje(nombre, nombre, mensaje);
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

    public void conectarConCliente (String nombreAmigo) {
        if (!chats.containsKey(nombreAmigo)) {
            iniciarChat(nombreAmigo);
            try {
                clientes.get(nombreAmigo).iniciarChat(nombre);
            } catch (RemoteException e) {
                System.err.println("Cliente exception:");
                e.printStackTrace();
            }
        }
    }

    public void desconectarConCliente (String nombreAmigo) {
        try {
            cerrarChat(nombreAmigo);
            clientes.get(nombreAmigo).cerrarChat(nombre);
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

    public void iniciarChat (String nombreAmigo) {
        ChatView chatView = new ChatView(nombre, this, nombreAmigo);
        chats.put(nombreAmigo, chatView);
        chatView.showView();
    }

    public void cerrarChat (String nombreAmigo) {
        chats.get(nombreAmigo).close();
        chats.remove(nombreAmigo);
    }

    public void desconectar () {
        if (chats.isEmpty()) {
            try {
                servidor.desconectar(nombre);
            } catch (RemoteException e) {
                System.err.println("Cliente exception:");
                e.printStackTrace();
            }
            System.exit(0);
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

            ClienteView clienteView = new ClienteView();

            String nombre = "";
            do {
                CapturarNombreView capturarNombre = new CapturarNombreView(clienteView, true);
                nombre = capturarNombre.getNombre();
            } while (!servidor.nombreCorrecto(nombre));

            Cliente cliente = new Cliente(nombre, servidor, args[1], clienteView);
            clienteView.setCliente(cliente);
            clienteView.showView();
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

}
