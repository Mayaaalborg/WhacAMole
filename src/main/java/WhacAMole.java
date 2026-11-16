import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;

public class WhacAMole {
    int boardWidth = 600;
    int boardHeight = 650; //50px ekstra til tekst i toppen.

    JFrame frame = new JFrame("Mario: Whac A Mole"); //vinduet til spillet.
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[] board = new JButton[9];
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currentMoleTile;
    JButton currentPlantTile;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;
    int score = 0;

    //Constructor
    WhacAMole() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); //Åbner vinduet i centrum af skærmen.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X knap til at afslutte programmet.
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER); //centrerer teksten.
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);
        textLabel.setBackground(Color.LIGHT_GRAY);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3)); //3x3 grid.
        //boardPanel.setBackground(Color.BLACK);
        frame.add(boardPanel);

        Image planimg = new ImageIcon("./src/piranha.png").getImage();
        plantIcon = new ImageIcon(planimg.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

        Image moleimg = new ImageIcon("./src/monty.png").getImage();
        moleIcon = new ImageIcon(moleimg.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

        score = 0;

        for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            // tile.setIcon(moleIcon);
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if (tile == currentMoleTile) {
                        score += 10;
                        textLabel.setText("Score: " + Integer.toString(score));
                    } else if (tile == currentPlantTile) {
                        textLabel.setText("Game Over" + Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        for (int i = 0; i < 9; i++) {
                            board[i].setEnabled(false);
                        }
                    }}
                    });

            setMoleTimer = new Timer(1500, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //Remove mole from current tile.
                    if (currentMoleTile != null) {
                        currentMoleTile.setIcon(null);
                        currentMoleTile = null;
                    }

                    //Randomly select another mole.
                    int num = random.nextInt(9); //randomiserer nummeret til mole 0-8.
                    JButton tile = board[num];

                    //if tile is occcupied by plant skip tile for this turn
                    if (currentPlantTile == tile) return;

                    //set tile til mole.
                    currentMoleTile = tile;
                    tile.setIcon(moleIcon);
                }
            });
            setPlantTimer = new Timer(2000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (currentPlantTile != null) {
                        currentPlantTile.setIcon(null);
                        currentPlantTile = null;
                    }
                    int num = random.nextInt(9); //randomiserer nummeret til mole 0-8.
                    JButton tile = board[num];

                    if (currentMoleTile == tile) return;

                    currentPlantTile = tile;
                    tile.setIcon(plantIcon);
                }
            });

            setMoleTimer.start();
            setPlantTimer.start();
            frame.setVisible(true);

        }
    }}

