package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Tiles.Enemy;
import PaooGame.Tiles.Enemy2;
import PaooGame.Tiles.Tile;

import java.awt.*;

public class Shuriken {

    private RefLinks refLinks;

    private float x,y;  //position of shuriken
    private float dx,dy;    //destination of shuriken
    private float xSpeed,ySpeed;    //x and y speed
    private float speed = 2;    //speed scalar

    private Rectangle bounds;// collision bounds
    private int size = 30;//size of shuriken
    private boolean isDead;//flag for die

    public Shuriken(RefLinks refLinks,float x,float y,float dx,float dy){
        this.refLinks = refLinks;
        this.x = x;
        this.y = y;

        bounds = new Rectangle((int)x,(int)y,size,size);

        //destination x and y
        this.dx = dx;
        this.dy = dy;

        //distance from destination
        int distance = distanceFromDestination();

        //set x and y speed
        xSpeed = (dx-x)/distance * speed;
        ySpeed = (dy-y)/distance * speed;
    }

    public void update(){
        if(isDead)
            return;

        //move bullet
        x += xSpeed;
        y += ySpeed;

        //update collision bounds location
        bounds.x = (int) x;
        bounds.y = (int) y;

        //if collided with solid tile and it is not enemy/enemy2 tile, then bullet dies
        Tile collidedTile = refLinks.GetMap().GetTile(bounds.x/ Tile.TILE_WIDTH,bounds.y/Tile.TILE_HEIGHT);
        if(collidedTile.IsSolid() && !(collidedTile instanceof Enemy) && !(collidedTile instanceof Enemy2))
            isDead = true;

    }

    public void draw(Graphics g){
        if(isDead)
            return;

        g.drawImage(Assets.shuriken,(int)x,(int)y,size,size,null);
       // g.setColor(Color.RED);
        //g.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
    }

    public boolean isDead(){
        return isDead;
    }

    public void die(){isDead = true;}

    public Rectangle getBounds(){return bounds; }

    private int distanceFromDestination(){
        int distance = (int) Math.sqrt(Math.pow(x - dx,2) + Math.pow(y - dy,2));

        return distance;
    }

}
