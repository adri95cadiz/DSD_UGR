// cliente.java
package cliente;
import iDonationServer.iDonationServer;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class cliente {
    
    public static void main(String[] args) throws RemoteException, NotBoundException {
        
        Boolean login = false;
        String host = "";
        Scanner teclado = new Scanner (System.in);
        System.out.println ("Escriba el nombre o IP del servidor: ");
        host = teclado.nextLine();
        String usuario = "";
        int numServidor = -1;

        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        try {
            // Crea el stub para el cliente especificando el nombre del servidor 1
            Registry reg = LocateRegistry.getRegistry(host, 1099);
            iDonationServer donationServer0 = (iDonationServer) reg.lookup("donationServer0");

            // Crea el stub para el cliente especificando el nombre del servidor 2
            Registry reg2 = LocateRegistry.getRegistry(host, 1100);
            iDonationServer donationServer1 = (iDonationServer) reg2.lookup("donationServer1");
            
            System.out.println ("Bienvenido al sistema de donaciones RMI-DSD, indentifíquese para donar al sistema");
            System.out.println ("=================================================================================");   
            System.out.println ("Seleccione una opción:");   
            System.out.println ("1. LOGIN.");                 
            System.out.println ("2. REGISTRARSE."); 
            System.out.println ("================================================================================="); 
                
            while(!login) {                  
                System.out.println ("Opción: ");    
                int decision = teclado.nextInt(); 
                if(decision != 1 && decision != 2) {                    
                    System.out.println ("Opción no válida, elija una opción válida.");
                } else {
                    System.out.println ("usuario: ");
                    usuario = teclado.next(); 
                    System.out.println ("contraseña:"); 
                    String contraseña = teclado.next();     
                    if(decision == 1){
                        if(donationServer0.ingresar(usuario, contraseña)){                            
                            System.out.println ("Ha ingresado correctamente en el servidor 0 como el usuario: " + usuario);
                            numServidor = 0;
                            login = true;
                        } else if(donationServer1.ingresar(usuario, contraseña)){                                                       
                            System.out.println ("Ha ingresado correctamente en el servidor 1 como el usuario: " + usuario);
                            numServidor = 1;
                            login = true;
                        } else {                                                                
                            System.out.println ("El usuario o la contraseña son incorrectos.");
                        }
                    } else if (decision == 2) {         
                            if( donationServer0.estaRegistrado(usuario) || donationServer1.estaRegistrado(usuario) ){
                                System.out.println ("El usuario especificado ya se encuentra registrado en el sistema");
                            } else if( donationServer0.consultarClientes() >= donationServer1.consultarClientes()){
                                if(donationServer1.registrarse(usuario, contraseña)){                                                                       
                                    System.out.println ("Se ha registrado correctamente en el servidor 1 como el usuario: " + usuario);
                                    numServidor = 1;
                                    login = true;
                                } else {                                                                                  
                                    System.out.println ("Ha habido algún error al registrarse, pruebe de nuevo.");
                                }
                            } else if( donationServer0.consultarClientes() <= donationServer1.consultarClientes()) {                                 
                                if(donationServer0.registrarse(usuario, contraseña)){                                                                       
                                    System.out.println ("Se ha registrado correctamente en el servidor 0 como el usuario: " + usuario);
                                    numServidor = 0;
                                    login = true;
                                } else {                                                                                   
                                    System.out.println ("Ha habido algún error al registrarse, pruebe de nuevo.");
                                }
                            }                      
                    } 
                }
            }
            
            System.out.println ("=================================================================================");
            System.out.println ("Enhorabuena, ha ingresado en el servidor " + numServidor + " como el usuario " + usuario + ".");
            System.out.println ("=================================================================================");   
            System.out.println ("Seleccione una opción:");   
            System.out.println ("1. DONAR.");                 
            System.out.println ("2. VER DONACIONES SERVIDOR."); 
            System.out.println ("3. VER DONACIONES TOTALES."); 
            System.out.println ("================================================================================="); 
           
                
            while(true){                                
                System.out.println ("Opción: ");    
                int decision = teclado.nextInt(); 
                if(decision != 1 && decision != 2 && decision != 3) {                    
                    System.out.println ("Opción no válida, elija una opción válida.");
                } else {
                    if(decision == 1){                         
                        System.out.println ("Introduzca la cantidad a donar:"); 
                        float donacion = teclado.nextFloat();
                        if(numServidor == 0){ 
                            donationServer0.donar(usuario, donacion);                        
                            System.out.println ("Donados correctamente " + donacion + " euros al servidor " + numServidor + "."); 
                            
                        } else if (numServidor == 1){
                            donationServer1.donar(usuario, donacion);                
                            System.out.println ("Donados correctamente " + donacion + " euros al servidor " + numServidor + "."); 
                        }                        
                    } else if(decision == 2){                         
                        float donado = 0;
                        if(numServidor == 0){
                            donado = donationServer0.consultarServidor();
                        } else if (numServidor == 1){                  
                            donado = donationServer1.consultarServidor();
                        }                       
                        System.out.println ("El servidor " + numServidor + " ha recibido " + donado + " euros de donación."); 
                    } else if(decision == 3){                         
                        float donado = 0;
                        if(numServidor == 0){
                            donado = donationServer0.consultarTotal(usuario);
                        } else if (numServidor == 1){                  
                            donado = donationServer1.consultarTotal(usuario);
                        }          
                        if(donado == -1){                            
                            System.out.println ("Para conocer la contribución total tienes que haber donado alguna vez en el servidor."); 
                        } else {
                            System.out.println ("Se han donado " + donado + " euros de donación en total."); 
                        }
                    }
                }
            }
            
        } catch(NotBoundException | RemoteException e) {
            System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}