// servidor.java = Programa servidor
package servidor;

import DonationServer.DonationServer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class servidor {
    public static void main(String[] args) throws IOException, NotBoundException, AlreadyBoundException {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            // Crea una instancia del servidor de donaciones
            Registry reg = LocateRegistry.createRegistry(1099);
            DonationServer donationServer0 = new DonationServer(0, 1099, "localhost");
            reg.bind("donationServer0", donationServer0);
            System.out.println("Servidor de donaciones 0 preparado");
            // Crea una segunda instancia del servidor de donaciones
            Registry reg2 = LocateRegistry.createRegistry(1100);
            DonationServer donationServer1 = new DonationServer(1, 1100, "localhost");
            reg2.bind("donationServer1", donationServer1);
            System.out.println("Servidor de donaciones 1 preparado");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}