package PaooGame.States;

import PaooGame.RefLinks;

import java.awt.*;
import java.awt.font.FontRenderContext;

/*! \class public class MenuState extends State
    \brief Implementeaza notiunea de menu pentru joc.
 */
public class MenuState extends State
{
    private static final int START_GAME = 1;
    private static final int QUIT_GAME = 2;

    private Font titleFont = new Font("Times New Roman", Font.PLAIN, 90);
    private Font font = new Font("Arial", Font.PLAIN, 80);

    private int optionSelected = START_GAME;


    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public MenuState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza.
        super(refLink);
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {
        if (refLink.GetKeyManager().enter) {

            switch (optionSelected) {
                case START_GAME:
                    refLink.GetGame().setPlayState();
                    break;
                case QUIT_GAME:
                    System.exit(0);
                    break;
            }
        }

        if(refLink.GetKeyManager().down)
            optionSelected = QUIT_GAME;
        if(refLink.GetKeyManager().up)
            optionSelected = START_GAME;
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, refLink.GetGame().GetWidth(), refLink.GetGame().GetHeight());

        g2.setColor(Color.WHITE);

        // draw title
        g2.setFont(titleFont);
        String title = "Yasuke GAME";
        float x = (refLink.GetGame().GetWidth() - getTextWidth(titleFont, title)) / 2;
        g2.drawString(title, x, titleFont.getSize() + 20);

        // draw options
        g2.setFont(font);
        String start = "Start";
        float x1 = (refLink.GetGame().GetWidth() - getTextWidth(font, start)) / 2;
        g2.drawString(start, x1, titleFont.getSize() + 140);
        String quit = "Quit";
        float x2 = (refLink.GetGame().GetWidth() - getTextWidth(font, quit)) / 2;
        g2.drawString(quit, x2, titleFont.getSize() + font.getSize() + 180);

        // draw select btn
        int x3 = (int) (optionSelected == START_GAME ? x1 - 30 : x2 - 30);
        int y = optionSelected == START_GAME ? titleFont.getSize() + 95 : titleFont.getSize() + font.getSize() + 145;
        g2.fillOval(x3, y, 20, 20);
    }

    private int getTextWidth(Font font, String text) {
        return (int) font.getStringBounds(text, new FontRenderContext(font.getTransform(), true, true)).getWidth();
    }
}
