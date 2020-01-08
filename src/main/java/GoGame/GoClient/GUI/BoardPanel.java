package GoGame.GoClient.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {

    private final List<Pawn> pawns = new ArrayList<>();

    public BoardPanel (Board board) {
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.ORANGE);
        setLayout( new GridLayout(5,5));
    }

    public List<Pawn> getPawns() {
        return pawns;
    }
}
