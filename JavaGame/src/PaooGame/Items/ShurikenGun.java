package PaooGame.Items;

import PaooGame.RefLinks;

import java.awt.*;
import java.util.ArrayList;

public class ShurikenGun {

    private RefLinks refLinks;

    private float x,y;  //gun position
    private ArrayList<Shuriken> shurikens;//shurikens the gun has shot

    private int throwCoolDown = 40;//player can throw shuriken after every 80 ticks
    private int timer;//timer to count time passed since last shuriken was shot
    private boolean canThrow = true;//throw flag

    public ShurikenGun(RefLinks refLinks, float x, float y){
        this.refLinks = refLinks;

        this.x = x;
        this.y = y;

        shurikens = new ArrayList<Shuriken>();
    }

    public void update(){
        //counting throw cool down time, if cannot throw
        if(!canThrow) {
            timer++;
            if (timer >= throwCoolDown) {
                timer = 0;
                canThrow = true;
            }
        }
        for(Shuriken shuriken : shurikens)
            shuriken.update();

        //remove dead shurikens
        for(int i = shurikens.size() - 1; i >= 0; i--)
            if(shurikens.get(i).isDead())
                shurikens.remove(i);
    }

    public void draw(Graphics g){
        for(Shuriken shuriken : shurikens)
            shuriken.draw(g);
    }

    //throws a new shurken from x,y to dx,dy
    public void throwShuriken(float x,float y,int dx,int dy){
        if(!canThrow)
            return;

        Shuriken s = new Shuriken(refLinks,x,y,dx,dy);
        shurikens.add(s);
        canThrow = false;
    }

    //thrwos a new shuriken to dx,dy from its current position
    public void throwShuriken(float dx,float dy){
        if(!canThrow)
            return;

        Shuriken s = new Shuriken(refLinks,x,y,dx,dy);
        shurikens.add(s);
        canThrow = false;
    }

    //set throw cool down
    public void setThrowCoolDown(int coolDown){throwCoolDown = coolDown;}

    //get all shurikens
    public ArrayList<Shuriken> getShurikens() {
        return shurikens;
    }
}
