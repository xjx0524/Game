package com.me.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SkillButtonTextureProvider {
	private Texture tex;
	private TextureRegion[][] texs;
	
	public SkillButtonTextureProvider() {
		super();
		tex=new Texture("skill.png");
		texs=TextureRegion.split(tex, 32,32);
	}
	
	public TextureRegion getTexture(G.TAG tag, boolean active){
		switch(tag){
		case SKILL_FREEZE:
		case SKILL_THAW:
		case SKILL_JUMP:
		case SKILL_PULL:
		case SKILL_PUSH:if (active) return texs[0][0]; else return texs[0][1];
		}
		return null;
	}

}
