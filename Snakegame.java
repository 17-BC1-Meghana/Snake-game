import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class Snakegame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        int x;
        int y;

        Tile(int x, int y) {
            this.x= x;
            this.y= y;
        }
    }
    int boardwidth;
    int boardheight;
    int tileSize = 25;

    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    Tile food;
    Random random;

    Timer gameLoop;
    int velocityX;
    int velocityy;
    boolean gameOver = false;


    Snakegame(int boardwidth, int boardheight) {
        this.boardwidth = boardwidth;
        this.boardheight = boardheight;
        setPreferredSize(new Dimension(this.boardwidth, this.boardheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placefood();

        velocityX=0;
        velocityy=0;

        gameLoop = new Timer(100, this);
        gameLoop.start();



    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g){
        //for(int i = 0; i<boardwidth/tileSize; i++) {
        //  g.drawLine(i*tileSize, 0, i*tileSize, boardheight);
        //g.drawLine(0,i*tileSize,boardwidth,i*tileSize);

        //}


        g.setColor(Color.red);
        //g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize, true);


        g.setColor(Color.green);
        //g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize,true);


        for(int i = 0 ; i< snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

        g.setFont(new Font("Arial",Font.PLAIN, 16));
        if (gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over:" + String.valueOf(snakeBody.size()),tileSize - 16, tileSize);
        }
        else{
            g.drawString("Score:"+ String.valueOf(snakeBody.size()),tileSize-16,tileSize);
        }

    }
    public void placefood(){
        food.x = random.nextInt(boardwidth/tileSize);
        food.y = random.nextInt(boardheight/tileSize);

    }

    public boolean collosions(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;

    }

    public void move() {

        if (collosions(snakeHead, food)){
            snakeBody.add(new Tile(food.x , food.y));
            placefood();
        }

        for (int i = snakeBody.size()-1; i>=0; i--){
            Tile snakePart = snakeBody.get(i);
            if (i==0) {
                snakePart.x = snakeHead.x;
                snakePart.y= snakeHead.y;
            }
            else {
                Tile prevsnakePart = snakeBody.get(i-1);
                snakePart.x = prevsnakePart.x;
                snakePart.y= prevsnakePart.y;
            

            }
        }

        snakeHead.x += velocityX;
        snakeHead.y += velocityy;

        for ( int i = 0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);

            if(collosions(snakeHead, snakePart)){
                gameOver = true;
            }
        }
        if (snakeHead.x*tileSize < 0 || snakeHead.y*tileSize > boardwidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardheight) {
            gameOver = true;

        }

    }

    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
    
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityy!=1) {
            velocityX=0;
            velocityy=-1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityy !=-1){
            velocityX=0;
            velocityy=1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX=-1;
            velocityy=0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX !=-1) {
            velocityX=1;
            velocityy=0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
    

}
