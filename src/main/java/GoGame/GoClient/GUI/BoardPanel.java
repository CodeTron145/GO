package GoGame.GoClient.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static GoGame.GoClient.GUI.Board.*;

public class BoardPanel extends JPanel {

    private final List<Pawn> pawns = new ArrayList<>();

    public BoardPanel (Board board) {
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
