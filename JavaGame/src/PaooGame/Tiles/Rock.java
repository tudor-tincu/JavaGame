package PaooGame.Tiles;

import PaooGame.Graphics.Assets;



public class Rock extends Tile
{

    public Rock(int id)
    {
        super(Assets.rock, id);
    }
    @Override
    public boolean IsSolid()
    {
        return true;
    }
}