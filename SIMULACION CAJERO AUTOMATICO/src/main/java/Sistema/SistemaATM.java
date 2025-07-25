package Sistema;

import java.text.DecimalFormat;
import javax.swing.JOptionPane;

public class SistemaATM {

    DecimalFormat formatea = new DecimalFormat("###,###.##");
    public static double saldoActual;

    public SistemaATM() {
        saldoActual = 0;
    }

    public void depositar(double deposito) {
        saldoActual += deposito;
    }

    public void retirar(double retiro) {
        if (saldoActual >= retiro) {
            saldoActual -= retiro;
        } else {
            JOptionPane.showMessageDialog(null, "Saldo Insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public double obtenerSaldo() {
        return saldoActual;
    }
}
