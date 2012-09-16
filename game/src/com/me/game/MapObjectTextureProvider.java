package com.me.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.game.G.TAG;

public class MapObjectTextureProvider {
	private Texture tex;
	private TextureRegion[][] texs;
	
	public MapObjectTextureProvider() {
		super();
		tex=new Texture("objects.png");
		texs=TextureRegion.split(tex, 32,48);
	}
	
	public TextureRegion getTexture(String name){
		if (name.equalsIgnoreCase("block"))		return texs[0][0]; else
		if (name.equalsIgnoreCase("pushable"))	return texs[1][0]; else
		if (name.equalsIgnoreCase("pullable"))	return texs[1][1]; else
		if (name.equalsIgnoreCase("door"))		return texs[0][2]; else
		if (name.equalsIgnoreCase("door1"))		return texs[1][2]; else
		if (name.equalsIgnoreCase("door2"))		return texs[2][2]; else
		if (name.equalsIgnoreCase("door3"))		return texs[3][2]; else
		if (name.equalsIgnoreCase("door_open"))	return texs[4][2]; else
		if (name.equalsIgnoreCase("ice"))		return texs[3][0]; else
		if (name.equalsIgnoreCase("water"))		return texs[0][3]; else
		return null;
	}
	
	public TextureRegion getTexture(TAG tag){
		switch(tag){
		case OBJ_BLOCK:		return texs[0][0];
		case OBJ_PUSHABLE:	return texs[1][0];
		case OBJ_PULLABLE:	return texs[1][1];
		case OBJ_DOOR:		return texs[0][2];
		//if (name.equalsIgnoreCase("door1"))		return texs[1][2];
		//if (name.equalsIgnoreCase("door2"))		return texs[2][2];
		//if (name.equalsIgnoreCase("door3"))		return texs[3][2];
		//if (name.equalsIgnoreCase("door_open"))	return texs[4][2];
		case OBJ_ICE:		return texs[3][0];
		case OBJ_WATER:		return texs[0][3];
		}
		return null;
	}

}
