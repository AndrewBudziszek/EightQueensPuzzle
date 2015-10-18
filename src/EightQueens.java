import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Project: EightQueensPuzzle
 * User: Andy Budziszek
 * Date: 12/2/13
 * Time: 8:20 PM
 *
 * 12.2.13:
 * -Board has been created successfully with Square objects.
 * -Control panel has all necessary contents but doesn't display correctly in the Frame.
 * -On button press console prints "BUTTON PRESSED!"
 * -Square has all methods written.
 * -board Array being populated in the createContents()?
 * -Added the ability to add Queens to the board on click. (In any square. aka Not restricted)
 *
 * 12.5.13
 * -Created new Control panel.
 * -Start over button changes last result, updates nLosses/nWins and the corresponding TextFields.
 *
 *
 * 12.9.13
 * -Now have array updating.
 * -Squares turn red vertically and horizontally based on where the queen is.
 *
 * 12.11.13
 * -Working on the diagonal making unsafe. This seems to be the last part of the project that
 * needs to be completed...
 * -Started reading documentation on Swing Timers.
 *
 * 12.12.13
 * -All diagonals are working though sloppy coding...
 * -All squares are working correctly now.
 * -Game is working as expected.
 * -Submitted today to ensure something is in the dropbox to avoid missing deadline.
 *
 * 10.18.15
 * -Corrected the sloppy code mentioned above. Took out a ton of try/catch blocks with a simple logic fix :) Yay for
 *  an additional two years of programming. :)
 * -Submitting to Github for the first time! 
 *
 */
public class EightQueens extends JFrame {
    private final int BOARD_SIZE = 8;
    private final ImageIcon QUEEN = new ImageIcon("queen-iconNEW.png");
    private final int WIDTH = 770;
    private final int HEIGHT = 500;
    private ControlPanel cp;
    private gameBoard gb;
    private Square[][] board = new Square[BOARD_SIZE][BOARD_SIZE];
    private int queenCount = 0;
    private int nWins = 0;
    private int nLosses = 0;
    private JLabel displayResult;
    private JTextField winner;
    private JTextField loser;

    public EightQueens()
    {
        setTitle("Eight Queens Puzzle");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        createContents();
        setVisible(true);
    }

    private void createContents()
    {
        cp = new ControlPanel();
        gb = new gameBoard();
        add(gb, BorderLayout.CENTER);
        add(cp, BorderLayout.EAST);
    }
    private class Listener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //if button state == 0, set a queen and mark new unsafe spots. queenCount++;
            //if button state == 1, do nothing.
            //if button state == 2, do nothing.
            //if Start Over is pressed, check how many queens are on the board, if there are EXACTLY 8, wins++
            //and display WIN. If it is pressed with < 8 queens, losses++ and display LOSE.
            Square btn = (Square) e.getSource();    //Place holder
            if(btn.getState() == 0)
            {
                btn.setIcon(QUEEN);
                btn.state = 2;
                queenCount++;
                markSquares();
                btn.setBackground(Color.red);
            }
            else if(btn.getState() == 2)    //Used for testing
            {
                btn.setIcon(null);
                btn.state = 0;
                btn.setBorderPainted(true);
                btn.setBackground(null);
            }
            sop2DArr(board);


        }
    }
    private class startListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JButton btn = (JButton) e.getSource();
            if(e.getSource() == btn)
            {
                if(queenCount < 8)
                {
                    displayResult.setForeground(Color.red);
                    displayResult.setText("LOSS!");
                    nLosses++;
                    queenCount = 0;
                    loser.setText("" + nLosses);
                    clear();
                }
                else if(queenCount == 8)
                {
                    displayResult.setForeground(Color.BLUE);
                    displayResult.setText("WIN!");
                    nWins++;
                    queenCount = 0;
                    winner.setText("" + nWins);
                    clear();
                }
                else
                {
                    displayResult.setForeground(Color.red);
                    displayResult.setText("ERROR(" + queenCount + ")");
                    queenCount = 0;
                    clear();
                }

            }
        }
    }
    private void clear()
    {
        remove(gb);
        gb = new gameBoard();
        add(gb);
        validate();
        for(int i = 0; i < BOARD_SIZE; ++i)
        {
            for(int j = 0; j < BOARD_SIZE; ++j)
            {
                board[i][j].clear();
            }
        }


    }
    private void markSquares()
    {
        for(int i = 0; i < BOARD_SIZE; ++i)
        {
            for(int j = 0; j < BOARD_SIZE; ++j)
            {
                if(board[i][j].state == 2)
                {
                    markSquaresForOneQueen(i,j);
                }
            }
        }
    }
    private void markSquaresForOneQueen(int qRow, int qCol)
    {
        //E and W
        for(int i = 0; i < BOARD_SIZE; ++i)
            board[qRow][i].markUnsafe();

        //N and S
        for(int j = 0; j < BOARD_SIZE; ++j)
            board[j][qCol].markUnsafe();

        //NE
        int l = qCol;
        for(int z = qRow; z >= 0 && l < BOARD_SIZE; --z)
        {
            board[z][l].markUnsafe();
            l++;
        }

        //NW
        int m = qCol;
        for(int n = qRow; n >= 0 && m >= 0; --n)
        {
            board[n][m].markUnsafe();
            m--;
        }

        //SE
        int o = qCol;
        for(int p = qRow; p < BOARD_SIZE && o < BOARD_SIZE; ++p)
        {
            board[p][o].markUnsafe();
            o++;
        }

        //SW
        int q = qCol;
        for(int r = qRow; r < BOARD_SIZE && q >= 0; ++r)
        {
            board[r][q].markUnsafe();
            q--;
        }
        if(queenCount == 8)
        {

        }
    }

    private class gameBoard extends JPanel
    {
        gameBoard()
        {
            Square button;
            setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
            for (int i = 0; i < BOARD_SIZE; i++)
            {
                for (int j = 0; j < BOARD_SIZE; j++)
                {
                    button = new Square();
                    button.addActionListener(new Listener());
                    add(button);
                    board[i][j] = button;
                }
            }
        }
    }
    private class ControlPanel extends JPanel
    {
        public JButton startOver = new JButton("Start Over");
        ControlPanel()
        {
            JLabel controls = new JLabel("Controls");
            JLabel showUnsafeSpaces = new JLabel("Show Unsafe Spaces:");
            JRadioButton always = new JRadioButton("Always", true);
            JRadioButton onMouse = new JRadioButton("When Mouse Pressed", false);
            ButtonGroup unsafeGroup = new ButtonGroup();
            unsafeGroup.add(always);
            unsafeGroup.add(onMouse);
            JLabel lastResult = new JLabel("Last Result: ");
            JLabel wins = new JLabel("Wins: ");
            winner = new JTextField("" + nWins, 4);
            winner.setEditable(false);
            JLabel lose = new JLabel("Losses: ");
            loser = new JTextField("" + nLosses, 4);
            loser.setEditable(false);
            setLayout(new GridLayout(5,1));
            //
            JPanel controller = new JPanel(new FlowLayout());
            controller.add(controls);
            add(controller);
            //
            JPanel unsafeSpaces = new JPanel(new GridLayout(4,1));
            unsafeSpaces.add(showUnsafeSpaces);
            unsafeSpaces.add(always);
            unsafeSpaces.add(onMouse);
            add(unsafeSpaces);
            //
            JPanel startAgain = new JPanel(new FlowLayout());
            startAgain.add(startOver);
            add(startAgain);
            startOver.addActionListener(new startListener());//Add startOver button listener.
            //
            JPanel statistics = new JPanel(new GridLayout(3,1));
            JPanel statsLast = new JPanel(new FlowLayout());
            statsLast.add(lastResult);
            displayResult = new JLabel("Welcome!");
            statsLast.add(displayResult);
            statistics.add(statsLast);
            JPanel win1 = new JPanel(new FlowLayout());
            win1.add(wins);
            win1.add(winner);
            statistics.add(win1);
            JPanel lose1 = new JPanel(new FlowLayout());
            lose1.add(lose);
            lose1.add(loser);
            statistics.add(lose1);
            add(statistics);

        }
    }

    private class Square extends JButton
    {
        private int state = 0;
        public Square()
        {
            super.setContentAreaFilled(false);
        }

        public void markUnsafe()
        {
            state = 1;
            setBackground(Color.RED);
        }
        public int getState()
        {
            return state;
        }
        public void clear()
        {
            state = 0;
            this.setBackground(null);
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(Exception e)
        {
            //Handle exception
        }
        new EightQueens();

    }

    public void sop2DArr(Square[][] x)
    {
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            for(int j = 0; j < BOARD_SIZE; j++)
            {
                System.out.print("[" + x[i][j].getState() + "]");
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
