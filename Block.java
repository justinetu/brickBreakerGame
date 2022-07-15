import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;






public class Block extends Rectangle{
    Image pic;
    boolean destroyed = false;
    
    int posX, posY, width, height, movX, movY;
    
    Block(int x, int y, int width, int height, String s) {
        posX = x;
        posY = y;
        this.width = width;
        this.height = height;
        
        movX = movY = 3;
        try {
            pic = ImageIO.read(new File(s)); // "src/" +
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics g, Component c) {
        if(!destroyed)
            g.drawImage(pic, posX, posY, width, height, c);
    }
    
}
