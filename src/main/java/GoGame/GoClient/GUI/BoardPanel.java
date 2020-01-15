package GoGame.GoClient.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {

    public static int SIZE;
    public static int N_OF_TILES;
    public static int TILE_SIZE;

    private final List<Pawn> pawns = new ArrayList<>();

    public BoardPanel (Board board, int sizeFromMessage) {

        SIZE = sizeFromMessage;
        N_OF_TILES = SIZE - 1;
        TILE_SIZE = 800 / (SIZE + 1);

        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.ORANGE);
        setLayout( new GridLayout(N_OF_TILES, N_OF_TILES));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                int row = Math.round((float) (e.getY() - TILE_SIZE)
                        / TILE_SIZE);
                int col = Math.round((float) (e.getX() - TILE_SIZE)
                        / TILE_SIZE);

                if(checkPoint(col,row)) {
                    board.onTileSelected(col, row);
                }
                else System.out.println("nie wolno");
            }
        });
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        // Draw rows.
        for (int i = 0; i < SIZE; i++) {
            g2.drawLine(TILE_SIZE, i * TILE_SIZE + TILE_SIZE, TILE_SIZE
                    * N_OF_TILES + TILE_SIZE, i * TILE_SIZE + TILE_SIZE);
        }
        // Draw columns.
        for (int i = 0; i < SIZE; i++) {
            g2.drawLine(i * TILE_SIZE + TILE_SIZE, TILE_SIZE, i * TILE_SIZE
                    + TILE_SIZE, TILE_SIZE * N_OF_TILES + TILE_SIZE);
        }

        for (int i = 0; i < pawns.size(); i++) {
            Pawn p = pawns.get(i);
            if(p.getColor() == Color.BLACK){
                g2.setColor(Color.BLACK);
                g2.fillOval(p.getX()*TILE_SIZE+TILE_SIZE-(TILE_SIZE/2),p.getY()*TILE_SIZE+TILE_SIZE-(TILE_SIZE/2),
                        TILE_SIZE,TILE_SIZE);
            }
            else if(p.getColor() == Color.WHITE){
                g2.setColor(Color.WHITE);
                g2.fillOval(p.getX()*TILE_SIZE+TILE_SIZE-(TILE_SIZE/2),p.getY()*TILE_SIZE+TILE_SIZE-(TILE_SIZE/2),
                        TILE_SIZE,TILE_SIZE);
            }
        }
    }

    private boolean checkPoint(int x, int y){

        boolean position = true;
        if(x > SIZE - 1 || y > SIZE - 1) position = false;
        if(x < 0 || y < 0) position = false;

        return position;
    }

    public List<Pawn> getPawns() {

        return pawns;
    }
}
