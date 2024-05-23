package PaooGame.Items;

import java.awt.*;
import java.awt.image.BufferedImage;

import PaooGame.Graphics.Animation;
import PaooGame.RefLinks;
import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul (nu este implementat momentan)
        dreptunghiul de coliziune
 */
public class Hero extends Character
{
    private Animation animRight,animLeft;
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/

    private int hp = 10;
    private boolean isDead;

    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Hero(RefLinks refLink, float x, float y)
    {
        ///Apel al constructorului clasei de baza
        super(refLink, x,y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);
        ///Seteaza imaginea de start a eroului

        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.x = 16;
        normalBounds.y = 16;
        normalBounds.width = 16;
        normalBounds.height = 32;

        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea de atac
        attackBounds.x = 10;
        attackBounds.y = 10;
        attackBounds.width = 38;
        attackBounds.height = 38;

        animLeft=new Animation(150,Assets.playerleft);
        animRight=new Animation(150,Assets.playerright);
    }

    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update()
    {
        animRight.tick();
        animLeft.tick();
        ///Verifica daca a fost apasata o tasta
        GetInput();
        ///Actualizeaza pozitia
        Move();
        ///Actualizeaza imaginea

    }


    public void hurt(){
        hp--;
        if(hp <= 0){
            hp = 0;
            isDead = true;
        }
    }

    public boolean isDead(){return isDead; }

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */
    private void GetInput()
    {
        ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;
        ///Verificare apasare tasta "sus"
        if(refLink.GetKeyManager().up)
        {
            int newY = (int) (y + bounds.y - speed);
            int tx = (int) ((x+bounds.x)/ Tile.TILE_WIDTH);
            int tx2 = (int)((x+ bounds.x +bounds.width)/Tile.TILE_WIDTH);
            int ty = newY/Tile.TILE_HEIGHT;
            Tile collidingTile = refLink.GetMap().GetTile(tx,ty);
            Tile collidingTile2 = refLink.GetMap().GetTile(tx2,ty);

            if(!collidingTile.IsSolid() & !collidingTile2.IsSolid())
                yMove = -speed;
        }
        ///Verificare apasare tasta "jos"
        if(refLink.GetKeyManager().down)
        {
            int newY = (int) (y + bounds.y + bounds.height + speed);
            int tx = (int) ((x+bounds.x)/ Tile.TILE_WIDTH);
            int tx2 = (int)((x+ bounds.x +bounds.width)/Tile.TILE_WIDTH);
            int ty = newY/Tile.TILE_HEIGHT;
            Tile collidingTile = refLink.GetMap().GetTile(tx,ty);
            Tile collidingTile2 = refLink.GetMap().GetTile(tx2,ty);

            if(!collidingTile.IsSolid() & !collidingTile2.IsSolid())
                yMove = speed;
        }
        ///Verificare apasare tasta "left"
        if(refLink.GetKeyManager().left)
        {
            int newX = (int) ((x + bounds.x - speed)/Tile.TILE_WIDTH);
            int ty = (int) ((y+bounds.y)/ Tile.TILE_HEIGHT);
            int ty2 = (int)((y+ bounds.y+bounds.height)/Tile.TILE_HEIGHT);
            Tile collidingTile = refLink.GetMap().GetTile(newX,ty);
            Tile collidingTile2 = refLink.GetMap().GetTile(newX,ty2);

            if(!collidingTile.IsSolid() & !collidingTile2.IsSolid())
                xMove = -speed;
        }
        ///Verificare apasare tasta "dreapta"
        if(refLink.GetKeyManager().right)
        {
            int newX = (int) ((x + bounds.x + bounds.width+ speed)/Tile.TILE_WIDTH);
            int ty = (int) ((y+bounds.y)/ Tile.TILE_HEIGHT);
            int ty2 = (int)((y+ bounds.y+bounds.height)/Tile.TILE_HEIGHT);
            Tile collidingTile = refLink.GetMap().GetTile(newX,ty);
            Tile collidingTile2 = refLink.GetMap().GetTile(newX,ty2);

            if(!collidingTile.IsSolid() & !collidingTile2.IsSolid())
                xMove = speed;
        }
    }

    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafi in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(getCurrentFrame(), (int)x, (int)y, width, height, null);

        ///doar pentru debug daca se doreste vizualizarea dreptunghiului de coliziune altfel se vor comenta urmatoarele doua linii
        /*g.setColor(Color.blue);
        g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);*/
    }
    private BufferedImage getCurrentFrame(){
        if(xMove<0){
            return animLeft.getCurrentFrame();
        }
        else if(xMove>0)
        {
            return animRight.getCurrentFrame();
        }
        else if(yMove>0||yMove<0)
        {
            return animRight.getCurrentFrame();
        }
        else
        {
            return Assets.playerright[0];
        }


    }
}
