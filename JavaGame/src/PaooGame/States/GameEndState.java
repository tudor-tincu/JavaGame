package PaooGame.States;

import PaooGame.RefLinks;

import java.awt.*;
import java.awt.font.FontRenderContext;

public class GameEndState extends State{

    private Font font = new Font("Monospace", Font.PLAIN, 40);
    private String title = "Game Completed";

    public GameEndState(RefLinks refLink) {
        super(refLink);
    }

    @Override
    public void Update() {
        if (refLink.GetKeyManager().enter) {
            System.exit(0);
        }

    }

    @Override
    public void Draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, refLink.GetGame().GetWidth(), refLink.GetGame().GetHeight());

        g2.setFont(font);
        g2.setColor(Color.WHITE);
        //title
        float x = (refLink.GetGame().GetWidth() - getTextWidth(font, title)) / 2;
        float y = (refLink.GetGame().GetHeight() - font.getSize()) / 2;
        g2.drawString(title, x, y);

        //msg
        String msg = "Press enter to continue";
        x = (refLink.GetGame().GetWidth() - getTextWidth(font, msg)) / 2;
        y = y +  font.getSize() * 3;
        g2.drawString(msg, x, y);
    }

    public void setTitle(boolean win){
        title = win ? "Game Completed": "You Died";
    }

    private int getTextWidth(Font font, String text) {
        return (int) font.getStringBounds(text, new FontRenderContext(font.getTransform(), true, true)).getWidth();
    }
}
