import java.io.*;
import java.util.*;

class Usuario {
    String nombre;
    String pin;
    String facialCode;
    double saldo;

    Usuario(String nombre, String pin, String facialCode, double saldo) {
        this.nombre = nombre;
        this.pin = pin;
        this.facialCode = facialCode;
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        // Formato para guardar/leer en archivo
        return nombre + "," + pin + "," + facialCode + "," + saldo;
    }
}

public class ATMConsole {
    static Scanner sc = new Scanner(System.in);
    static List<Usuario> usuarios = new ArrayList<>();
    static final String DB_FILE = "usuarios_db.txt";

    public static void main(String[] args) {
        cargarUsuarios();
        System.out.println("Bienvenido al Cajero Automático");

        Usuario usuarioActual = autenticar();
        if (usuarioActual == null) {
            System.out.println("No se pudo autenticar. Saliendo...");
            return;
        }

        boolean sesion = true;
        while (sesion) {
            System.out.println("\n1. Consultar saldo");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Depositar dinero");
            System.out.println("4. Salir");
            System.out.print("Seleccione opción: ");
            String op = sc.nextLine();

            switch (op) {
                case "1":
                    System.out.printf("Saldo actual: $%.2f%n", usuarioActual.saldo);
                    break;
                case "2":
                    System.out.print("Monto a retirar: ");
                    double retiro = Double.parseDouble(sc.nextLine());
                    if (retiro > 0 && retiro <= usuarioActual.saldo) {
                        usuarioActual.saldo -= retiro;
                        System.out.println("Retiro exitoso.");
                    } else {
                        System.out.println("Monto inválido o saldo insuficiente.");
                    }
                    guardarUsuarios();
                    break;
                case "3":
                    System.out.print("Monto a depositar: ");
                    double deposito = Double.parseDouble(sc.nextLine());
                    if (deposito > 0) {
                        usuarioActual.saldo += deposito;
                        System.out.println("Depósito exitoso.");
                    } else {
                        System.out.println("Monto inválido.");
                    }
                    guardarUsuarios();
                    break;
                case "4":
                    System.out.println("¡Hasta luego!");
                    sesion = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    static Usuario autenticar() {
        System.out.print("Ingrese su nombre de usuario: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Ingrese su PIN: ");
        String pin = sc.nextLine().trim();

        for (Usuario u : usuarios) {
            if (u.nombre.equals(nombre) && u.pin.equals(pin)) {
                // Simulación facial
                System.out.print("Reconocimiento facial simulado. Ingrese el código facial: ");
                String facial = sc.nextLine().trim();
                if (u.facialCode.equals(facial)) {
                    System.out.println("Autenticación exitosa.");
                    return u;
                } else {
                    System.out.println("Reconocimiento facial fallido.");
                    return null;
                }
            }
        }
        System.out.println("Usuario o PIN incorrecto.");
        return null;
    }

    static void cargarUsuarios() {
        usuarios.clear();
        File f = new File(DB_FILE);
        if (!f.exists()) {
            // Usuario de prueba si no existe base de datos
            usuarios.add(new Usuario("juan", "1234", "FACE123", 1000.0));
            guardarUsuarios();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(DB_FILE))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    usuarios.add(new Usuario(datos[0], datos[1], datos[2], Double.parseDouble(datos[3])));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    static void guardarUsuarios() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DB_FILE))) {
            for (Usuario u : usuarios) {
                pw.println(u);
            }
        } catch (Exception e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }
}