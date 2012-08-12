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
		if (name.equalsIgnoreCase("block")) 
			return texs[0][0];
		else	
		return null;
	}

}
