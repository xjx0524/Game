package com.me.aaction;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AASprite extends ASprite {
	
	protected Texture tex;
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha,tex);
	}

	@Override
	public TextureRegion getTextureRegion() {
		return new TextureRegion(tex);
	}
	
	@Override
	public void setTextureRegion(TextureRegion textureRegion) {
		tex=textureRegion.getTexture();
	}
	
	public AASprite(String filename) {
		super();
		tex=new Texture(filename);
		x=y=0;
		width=tex.getWidth();
		height=tex.getHeight();		
	}

}
