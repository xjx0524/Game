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
		case SKILL_FREEZE:if(active) return texs[1][3]; else return texs[0][3];
		case SKILL_THAW:if(active) return texs[3][0]; else return texs[2][0];
		case SKILL_JUMP:if(active) return texs[1][2]; else return texs[0][2];
		case SKILL_PULL:if(active) return texs[1][1]; else return texs[0][1];
		case SKILL_PUSH:if (active) return texs[1][0]; else return texs[0][0];
		
		case SKILL_MUSIC:if (active) return texs[3][1]; else return texs[2][1];
		case SKILL_RESTART:if (active) return texs[3][3]; else return texs[2][3];
		case SKILL_LOCKMOVE:if (active) return texs[3][2]; else return texs[2][2];
		
		case BUTTON_BACK_BIG:if (active) return texs[1][4]; else return texs[0][4];
		case BUTTON_BACK_SMALL:if (active) return texs[1][5]; else return texs[0][5];
		
		case TILE_DESTINATION:	return texs[2][4];
		case TILE_GROUND2:		return texs[2][5];
		case TILE_HOLE:			return texs[2][6];
		case TILE_WATER:		return texs[2][7];
		case TILE_ICE:			return texs[3][4];
		case TILE_PEDAL:		return texs[3][5];
		case TILE_TELEPORT:		return texs[3][6];
		case TILE_SAND_U1:		return texs[3][7];
		case TILE_STREAM_U1:	return texs[1][6];		
		}
		return null;
	}

}
