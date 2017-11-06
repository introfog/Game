package com.game.mesh.objects;

import com.game.mesh.body.BodyObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class Hole extends GameObject{
	private static final float BODY_HOLE_W = UNIT * 2;
	private static final float BODY_HOLE_H = UNIT * 2;
	private static final float HOLE_W = UNIT * 2;
	private static final float HOLE_H = UNIT * 2;
	
	
	public Hole (float x, float y){
		objectType = ObjectType.hole;
		body = new BodyObject ("core/assets/images/hole.png", x, y, HOLE_W, HOLE_H, BODY_HOLE_W, BODY_HOLE_H);
		dataRender = new DataRender (body.getSprite (), LayerType.hole);
	}
	
	@Override
	public void update (){ }
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && (message.objectType == ObjectType.character)){
			MoveMessage msg = (MoveMessage) message;
			if (body.contains (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new CharacterDiedMessage (msg.object, this));
			}
		}
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){ }
}
