import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class JBrainTetris extends  JTetris{

    private DefaultBrain brain = new DefaultBrain();
    private JCheckBox brainCheckBox;
    private Brain.Move brainMove;
    private JSlider slider;
    private JLabel okStatus;
    private BadBrain badBrain = new BadBrain();
    private Random random = new Random();

    /**
     * Creates a new JTetris where each tetris square
     * is drawn with the given number of pixels.
     *
     * @param pixels
     */
    JBrainTetris(int pixels) {
        super(pixels);
    }

    @Override
    public void tick(int verb) {
        if (verb == DOWN && brainCheckBox.isSelected()){
            board.undo();
            brainMove = brain.bestMove(board, currentPiece, board.getHeight(), brainMove);
            if(currentPiece != brainMove.piece){
                currentPiece = currentPiece.fastRotation();
            }
            if(brainMove.x < currentX){
                currentX--;
            } else if(brainMove.x > currentX){
                currentX++;
            }
        }
        super.tick(verb);
    }

    @Override
    public Piece pickNextPiece() {
        int randInt = random.nextInt(99);
        if(randInt >= slider.getValue()){
            return super.pickNextPiece();
        } else{
            Piece worstPiece = null;
            double highestScore = -1;
            Piece[] pieces = Piece.getPieces();
            for (int i = 0; i < pieces.length; i++) {
                Brain.Move currWorstMove = badBrain.bestMove(board, pieces[i], board.getHeight(), brainMove);
                double currScore = currWorstMove.score;
                if(currScore > highestScore){
                    worstPiece = currWorstMove.piece;
                    highestScore = currScore;
                }
            }
            return worstPiece;
        }
    }

    @Override
    public JComponent createControlPanel() {
        JPanel panel = (JPanel)super.createControlPanel();
        panel.add(new JLabel("Brain:"));
        brainCheckBox = new JCheckBox("Brain active");
        panel.add(brainCheckBox);

        JPanel little = new JPanel();
        little.add(new JLabel("Adversary"));
        slider = new JSlider(0, 100, 0);
        slider.setPreferredSize(new Dimension(100, 15));
        little.add(slider);
        panel.add(little);
        okStatus = new JLabel("OK");
        panel.add(okStatus);
        return panel;
    }

    public static void main(String[] args) {
        JBrainTetris tetris = new JBrainTetris(20);
        JFrame frame = JTetris.createFrame(tetris);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
