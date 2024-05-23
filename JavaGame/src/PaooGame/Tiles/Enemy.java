package PaooGame.Tiles;

import PaooGame.Graphics.Assets;
import PaooGame.Items.ShurikenGun;

import java.awt.*;

public class Enemy extends Tile{

    private int hp = 5; //hp of enemy
    private boolean isDead;//flag for enemy die
    private ShurikenGun gun;//gun of enemy

    public Enemy(int id)
    {
        super(Assets.enemy1, id);
    }

    @Override
    public boolean IsSolid()
    {
        return true;
    }

    //reduces enemy  hp, enemy dies if hp is 0
    public void hurt(){
        hp--;
        if(hp <= 0){
            hp = 0;
            isDead = true;
        }
    }

    //checks if enemy is dead
    public boolean isDead(){return isDead; }

    //set shuriken gun
    public void setGun(ShurikenGun gun){this.gun = gun;}
    //get shuriken gun
    public ShurikenGun getGun(){return gun;}
    //throw shuriken at point dx,dy
    public void throwShuriken(float dx,float dy){ gun.throwShuriken(dx,dy);}
}

