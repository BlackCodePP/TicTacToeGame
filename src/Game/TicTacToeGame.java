package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class TicTacToeGame extends JFrame implements ActionListener {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeGame());
    }

    private final char[][] tablero;
    private char jugadorActual;
    private final JButton[][] botones;

    public TicTacToeGame() {
        tablero = new char[3][3];
        jugadorActual = 'X';
        botones = new JButton[3][3];

        inicializarTablero();
        inicializarInterfaz();
    }
    
    // Crea el tablero de juego
    private void inicializarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = ' '; // Llena el tablero con espacios en blanco
            }
        }
    }

    //Crea la interfaz del tablero
    private void inicializarInterfaz() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tres en Raya");
        setLayout(new GridLayout(3, 3)); // Establece una cuadrícula 3x3 para los botones

        // Crear los botones para el tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j] = new JButton();
                Border border = new LineBorder(Color.white, 1, true);
                botones[i][j].setBorder(border);
                botones[i][j].setFont(new Font("Arial", Font.PLAIN, 80));
                botones[i][j].setBackground(Color.black);
                botones[i][j].setFocusPainted(false);
                botones[i][j].addActionListener(this); // Agrega el controlador de eventos a los botones
                add(botones[i][j]); // Agrega el botón al JFrame
            }
        }

        pack();
        setSize(400, 400); // Ajustar el tamaño de la ventana
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * Cambia al siguiente jugador y actualiza el color del texto en el botón
     * 
     * @param fila La posición en horizontal
     * @param columna La posición en vertical
    */
    private void cambiarJugador(int fila, int columna) {

        if (jugadorActual == 'X') {
            botones[fila][columna].setForeground(Color.green);
            jugadorActual = 'O';
        } else {
            botones[fila][columna].setForeground(Color.yellow);
            jugadorActual = 'X';
        }
    }

    /**
     * Intenta realizar un movimiento si la celda está vacía
     * 
     * @param fila La posición en horizontal
     * @param columna La posición en vertical
     * 
     * @return Si la casilla esta vacia o no
    */
    private boolean hacerMovimiento(int fila, int columna) {
        if (tablero[fila][columna] == ' ') {
            tablero[fila][columna] = jugadorActual;
            botones[fila][columna].setText(Character.toString(jugadorActual));

            return true;
        }
        return false;
    }
    
    /**
     * Verifica si hay un ganador 
     * 
     * @return Si uno de los dos jugadores a ganado o no
    */
    private boolean verificarFinJuego() {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == tablero[i][1] && tablero[i][1] == tablero[i][2] && tablero[i][0] != ' ') {
                return true;
            }
            if (tablero[0][i] == tablero[1][i] && tablero[1][i] == tablero[2][i] && tablero[0][i] != ' ') {
                return true;
            }
        }

        return (tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2] && tablero[0][0] != ' ')
                || (tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0] && tablero[0][2] != ' ');
    }

    /**
     * Verifica si el tablero está lleno, indicando un empate
     * 
     * @return Si el tablero esta lleno o no
    */
    private boolean verificarEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton = (JButton) e.getSource();
        int fila = -1, columna = -1;

        // Obtener la posición del botón seleccionado
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (botones[i][j] == boton) {
                    fila = i;
                    columna = j;
                    break;
                }
            }
        }

        if (fila != -1 && columna != -1) {
            if (hacerMovimiento(fila, columna)) {
                if (verificarFinJuego()) {
                    if (jugadorActual == 'X') {
                        botones[fila][columna].setForeground(Color.green);
                        JOptionPane.showMessageDialog(this, "¡Jugador X ha ganado!");

                    } else {
                        botones[fila][columna].setForeground(Color.yellow);
                        JOptionPane.showMessageDialog(this, "¡Jugador O ha ganado!");

                    }
                    reiniciarJuego();
                } else if (verificarEmpate()) {
                    if (jugadorActual == 'X') {
                        botones[fila][columna].setForeground(Color.green);

                    } else {
                        botones[fila][columna].setForeground(Color.yellow);

                    }
                    JOptionPane.showMessageDialog(this, "¡El juego ha terminado en empate!");
                    reiniciarJuego();
                } else {
                    cambiarJugador(fila, columna);
                }
            }
        }
    }

    // Reinicia el tablero
    private void reiniciarJuego() {
        inicializarTablero();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j].setText(""); // Borra el texto de los botones
            }
        }

        jugadorActual = 'X'; // Cambia el jugador inicial
    }

}
