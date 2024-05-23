package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
        /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage[] playerright;
    public static BufferedImage[] playerleft;

    public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage water;
    public static BufferedImage brick;
    public static BufferedImage rock;
    public static BufferedImage enemy1,enemy2;
    public static BufferedImage shuriken;
    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/PaooGameSpriteSheet.png"));
        playerright=new BufferedImage[4];
        playerleft=new BufferedImage[4];
        playerright[0]=sheet.crop(87, 23,179,177);
        playerright[1]=sheet.crop(325, 23,179,177);
        playerright[2]=sheet.crop(551, 23,179,177);
        playerright[3]=sheet.crop(782, 23,179,177);

        playerleft[0]=sheet.crop(49, 225,179,177);
        playerleft[1]=sheet.crop(310, 225,179,177);
        playerleft[2]=sheet.crop(547, 225,179,177);
        playerleft[3]=sheet.crop(770, 225,179,177);
            /// Se obtin subimaginile corespunzatoare elementelor necesare.
        grass = sheet.crop(1240, 40,150,140);
        brick = sheet.crop(1240, 248,150,140);
        water = sheet.crop(92, 452,171,170);
        soil = sheet.crop(1448, 250,150,140);

        rock = sheet.crop(1440, 40,150,140);
        enemy1 = sheet.crop(330, 452,175,170);
        enemy2 = sheet.crop(560, 452,175,170);

        shuriken = ImageLoader.LoadImage("/textures/shuriken.png");
    }
}
