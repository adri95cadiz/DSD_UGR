
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
            InterfazCliente stub = (InterfazCliente) UnicastRemoteObject.exportObject((InterfazCliente) this, 0); // Se crea el stub
            servidor.registrar(nombre, stub); // Avisa al servidor para que registre al cliente
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

    public void actualizarClientes (HashMap<String, InterfazCliente> clientes) {
        this.clientes = clientes;  
        this.clientes.remove(nombre); // No tiene que aparecer su nombre
        clienteView.actualizarClientes(this.clientes.keySet().toArray(new String[this.clientes.size()])); // Envía un array con todos los nombres
    }

    public void mostrarMensaje (String nombreAmigo, String mensajero, String mensaje) {
        chats.get(nombreAmigo).mostrarMensaje(mensajero, mensaje); // Se muestra el mensaje en la GUI
    }
    
    public void enviarMensaje (String nombreAmigo, String mensaje) {
        try {
            mostrarMensaje(nombreAmigo, nombre, mensaje); // Muestra mensaje en su ventana
            clientes.get(nombreAmigo).mostrarMensaje(nombre, nombre, mensaje); // Muesta mensaje en la ventana de su amigo
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }
    
    public void conectarConCliente (String nombreAmigo) {
        if (!chats.containsKey(nombreAmigo)) { // No puede existir una ventana abierta entre esos dos mismos clientes
            iniciarChat(nombreAmigo); // Inicia ventana chat
            try {
                clientes.get(nombreAmigo).iniciarChat(nombre); // Inicia ventana chat en su amigo
            } catch (RemoteException e) {
                System.err.println("Cliente exception:");
                e.printStackTrace();
            }
        }
    }
    
    public void desconectarConCliente (String nombreAmigo) {
        try {
            cerrarChat(nombreAmigo); // Cierra ventana chat
            clientes.get(nombreAmigo).cerrarChat(nombre); // Cierra ventana chat de su amigo
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }
    
    public void iniciarChat (String nombreAmigo) {
        ChatView chatView = new ChatView(nombre, this, nombreAmigo); // Inicia chat
        chats.put(nombreAmigo, chatView); // Guarda chat en el map
        chatView.showView(); // Muestra la ventana
    }
    
    public void cerrarChat (String nombreAmigo) {
        chats.get(nombreAmigo).close(); // Cierra la ventana
        chats.remove(nombreAmigo); // Borra el chat del map
    }

    public void desconectar () {
        if (chats.isEmpty()) { // No se puede desconectar si no ha cerrado todas las ventanas
            try {
                servidor.desconectar(nombre); // Se avisa al servidor para que elimine al cliente
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
        if (System.getSecurityManager() == null) { // Instalación del gestor de seguridad
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.getRegistry(args[0]); // Se obtiene el RMI registry de la ip del servidor
            InterfazServidor servidor = (InterfazServidor) registry.lookup(args[1]); // Se busca el servidor
            
            ClienteView clienteView = new ClienteView(); // Se crea la GUI del cliente

            String nombre = "";
            do {
                CapturarNombreView capturarNombre = new CapturarNombreView(clienteView, true); // Se lanza el capturador de nombres
                nombre = capturarNombre.getNombre(); // Se obtiene el nombre
            } while (!servidor.nombreCorrecto(nombre));

            Cliente cliente = new Cliente(nombre, servidor, args[1], clienteView); // Se crea el cliente

            clienteView.setCliente(cliente);  // Se asigna la GUI al cliente y el cliente a la GUI
            clienteView.showView(); // Se inicia la GUI
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }
    
}
