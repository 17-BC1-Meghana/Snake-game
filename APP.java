import javax.swing.*;

public class APP {
    public static void main(String[]args) throws Exception{
        int borderwidth = 600;
        int borderheight = borderwidth;

        JFrame frame = new JFrame("Snake game");
        frame.setVisible(true);
        frame.setSize(borderwidth,borderheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        Snakegame snakegame = new Snakegame(borderwidth, borderheight);
        frame.add(snakegame);
        frame.pack();
        snakegame.requestFocus();


    }    
}