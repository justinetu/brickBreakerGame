import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, NewInterface{
    private boolean play = false;
    private int score = 0;
    private int totalNumOfBricks = 21;
    
    private Timer timer;
    private int delay = 8;
    
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    private Map map;
    public Gameplay() {
        map = new Map(3,7);
        this.setSize(700, 600);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
}
    public void paint(Graphics g) {
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 697, 592);
        
        // draw map
        map.draw((Graphics2D) g);
        
        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 697, 3);
        g.fillRect(697, 0, 3, 592);
        
        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);
        
        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        
        //ball   
        // regular yellow ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20); 
       
        // beach ball
        /*
        Block ball = new Block(ballPosX, ballPosY, 30, 30, "/Users/etu/Dropbox/My Mac (Etus-MacBook-Air.local)/Documents/beacball.png");
        ball.draw(g, this);
        ball.posX += ball.movX;
        
        if(ball.x > (getWidth() - 25) || ball.x < 0)
            ball.movX *= -1;
        if(ball.y < 0 || ball.intersects())    <---- delete
        */
        if(totalNumOfBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won! ", 260, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
        
        if(ballPosY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game over, Score: " + score, 190, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
        
        g.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play) {
            if(new Rectangle(ballPosX, ballPosY, 30, 30).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }
            A: for(int i = 0; i < map.map.length; i++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        
                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRectangle = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRectangle = rectangle;
                        
                        if(ballRectangle.intersects(brickRectangle)) {
                            map.setBrickValue(0, i, j);
                            totalNumOfBricks--;
                            score += 5;
                            
                            if(ballPosX + 19 <= brickRectangle.x || ballPosX + 1 >= brickRectangle.x + brickRectangle.width) {
                                ballXdir = -ballXdir;
                            }
                            else {
                                ballYdir = -ballYdir;
                            }
                            
                            break A;
                            
                        }
                    }
                }
            }
            
            ballPosX += ballXdir;
            ballPosY += ballYdir;
            if(ballPosX < 0) { //for the left border
                ballXdir = -ballXdir;
            }
            if(ballPosY < 0) { //for the top
                ballYdir = -ballYdir;
            }
            if(ballPosX > 670) { //for the right border
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX >= 600)
                playerX = 600;
        } else {
            moveRight();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX < 10)
                playerX = 10;
        } else {
            moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            play = false;
            if(!play) {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalNumOfBricks = 21;
                map = new Map(3, 7);
                
                repaint();
            }
        }
    }
    public void moveRight() {
        play = true;
        playerX += 20;
    }
    public void moveLeft() {
        play = true;
        playerX -= 20;
    } 
}
