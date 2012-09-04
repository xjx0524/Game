package com.me.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.math.Vector2;
import com.me.game.G;

public class XTiledLoader {
	
	public static XTiledMap initMap (FileHandle tmxFile, String atlasPath) {
		
		XTiledMap xmap;
		
		xmap = new XTiledMap();
		xmap.map = TiledLoader.createMap(tmxFile);
		xmap.atlas = new TileAtlas(xmap.map, Gdx.files.internal(atlasPath));
//		xmap.tileMapRenderer = new TileMapRenderer(xmap.map, xmap.atlas, 10, 10);
		xmap.maxTileId = xmap.map.tileSets.get(xmap.map.tileSets.size()-1).firstgid;
		
		xmap.tiles = new XTile[xmap.map.layers.size()][xmap.map.height][xmap.map.width];
		for (int layer = 0; layer < xmap.map.layers.size(); layer++)
			for (int i = 0; i < xmap.map.height; i++)
				for (int j = 0; j < xmap.map.width; j++) {
					xmap.tiles[layer][i][j] = new XTile(xmap, xmap.map.layers.get(layer).tiles[xmap.map.height - 1 - i][j],
														j,i);
				}
		xmap.startPos = new Vector2(Integer.parseInt(xmap.map.layers.get(0).properties.get("xPos")), 
									Integer.parseInt(xmap.map.layers.get(0).properties.get("yPos")));
		G.tmx = xmap;
		return xmap;
	}

}
