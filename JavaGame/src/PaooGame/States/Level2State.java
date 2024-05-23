package PaooGame.States;

import PaooGame.Items.Shuriken;
import PaooGame.Items.ShurikenGun;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;
import PaooGame.Tiles.Enemy;
import PaooGame.Tiles.Enemy2;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.util.ArrayList;

/*! \class public class Level2 extends State
    \brief Implementeaza/controleaza jocul.
 */
public class Level2State extends State
{
    private Hero hero;  /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/
    private Map map;    /*!< Referinta catre harta curenta.*/
    private ShurikenGun playerGun;//gun of player

    private ArrayList<Enemy> enemies1;//all enemies 1 in map
    private ArrayList<Enemy2> enemies2;//all enemies 2 in map

    /*! \fn public Level2State(RefLinks refLink)
        \brief Constructorul de initializare al clasei

        \param refLink O referent catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public Level2State(RefLinks refLink)
    {
        ///Apel al constructorului clasei de baza
        super(refLink);
        ///Construieste harta jocului
        map = new Map(refLink,2);
        ///Construieste eroul
        hero = new Hero(refLink,850, 580);
        playerGun = new ShurikenGun(refLink,0,0);

        //create enemy guns at enemy tiles and add to list
        enemies1 = new ArrayList<Enemy>();
        enemies2 = new ArrayList<Enemy2>();
        for(int i = 0; i < map.getHeight();i++)
            for(int j = 0; j < map.getWidth();j++){
                Tile tile = map.GetTile(j,i);
                if(tile instanceof Enemy || tile instanceof Enemy2){
                    ShurikenGun gun = new ShurikenGun(refLink,j * Tile.TILE_WIDTH, i * Tile.TILE_HEIGHT);
                    gun.setThrowCoolDown(180);

                    if(tile instanceof Enemy) {
                        Enemy enemy = (Enemy)tile;
                        enemy.setGun(gun);
                        enemies1.add(enemy);
                    }else if(tile instanceof Enemy2) {
                        Enemy2 enemy2 = (Enemy2)tile;
                        enemy2.setGun(gun);
                        enemies2.add(enemy2);
                    }
                }
            }
    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update()
    {
        map.Update();
        hero.Update();

        //update enemy1 gun
        for(Enemy e : enemies1)
            e.getGun().update();
        //update enemy2 gun
        for(Enemy2 e : enemies2)
            e.getGun().update();

        //update player gun
        playerGun.update();

        //fire a shuriken if mouse clicked
        if(refLink.getMouseManager().isLeftPressed()){
            float x = hero.GetX();
            float y = hero.GetY();
            int dx = refLink.getMouseManager().getMouseX();
            int dy = refLink.getMouseManager().getMouseY();

            playerGun.throwShuriken(x,y,dx,dy);
        }

        //enemy fires shurikens at player if it is not dead
        //enemy1
        for(Enemy e : enemies1)
            if(!e.isDead()){
                float dx = hero.GetX();
                float dy = hero.GetY();
                e.throwShuriken(dx,dy);
            }
        //enemy 2
        for(Enemy2 e : enemies2)
            if(!e.isDead()){
                float dx = hero.GetX();
                float dy = hero.GetY();
                e.throwShuriken(dx,dy);
            }

        //enemy gets hurt if hit by player shurikens
        for(Shuriken s : playerGun.getShurikens()){
            Tile collidingTile = map.GetTile(s.getBounds().x/Tile.TILE_WIDTH, s.getBounds().y/Tile.TILE_HEIGHT);
            if(collidingTile instanceof Enemy){
                Enemy enemy = (Enemy)collidingTile;
                enemy.hurt();
                s.die();
            }
            else if(collidingTile instanceof  Enemy2){
                Enemy2 enemy2 = (Enemy2)collidingTile;
                enemy2.hurt();
                s.die();
            }
        }

        //player gets hurt if hit by enemy/enemy2 shurikens
        Rectangle playerBounds = new Rectangle((int)hero.GetX(),(int)hero.GetY(),hero.GetWidth(),hero.GetHeight());
        //enemy1 shurikens
        for(Enemy e : enemies1)
            for(Shuriken s : e.getGun().getShurikens())
                if(s.getBounds().intersects(playerBounds)){
                    hero.hurt();
                    s.die();
                }
        //enemy2 shurikens
        for(Enemy2 e : enemies2)
            for(Shuriken s : e.getGun().getShurikens())
                if(s.getBounds().intersects(playerBounds)){
                    hero.hurt();
                    s.die();
                }

        //if hero dies, then end game
        if(hero.isDead()){
            refLink.GetGame().setEndState(false);
        }

        //if hero reaches exit,then end game
        if(hero.GetY() < 0){
            refLink.GetGame().setEndState(true);
        }

    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        map.Draw(g);
        hero.Draw(g);
        playerGun.draw(g);

        //draw enemy guns
        for(Enemy e : enemies1)
            e.getGun().draw(g);
        for(Enemy2 e : enemies2)
            e.getGun().draw(g);
    }

    //updates the reflinks map
    public void updateRefLinkMap(){refLink.SetMap(map);}
}
