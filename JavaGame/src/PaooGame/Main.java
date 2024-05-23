package PaooGame;

import PaooGame.GameWindow.GameWindow;

public class Main
{
    public static void main(String[] args)
    {
        Game paooGame = new Game("MyFirstGame", 1024, 720);
        paooGame.StartGame();
    }
}
