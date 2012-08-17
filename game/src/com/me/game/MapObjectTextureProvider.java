package com.me.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
		if (name.equalsIgnoreCase("pushable"))	return texs[0][0]; else
		if (name.equalsIgnoreCase("pullable"))	return texs[0][0]; else
		if (name.equalsIgnoreCase("door"))		return texs[0][2]; else
		if (name.equalsIgnoreCase("door_open"))	return texs[0][3]; else
		if (name.equalsIgnoreCase("ice"))		return texs[0][2]; else
		if (name.equalsIgnoreCase("water"))		return texs[0][3]; else
		if (name.equalsIgnoreCase("wall"))		return texs[0][3]; else
		return null;	
	}

}
