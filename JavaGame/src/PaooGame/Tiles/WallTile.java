package PaooGame.Tiles;

import PaooGame.Graphics.Assets;



public class WallTile extends Tile
{

    public WallTile(int id)
    {
        super(Assets.brick, id);
    }
    @Override
    public boolean IsSolid()
    {
        return true;
    }
}