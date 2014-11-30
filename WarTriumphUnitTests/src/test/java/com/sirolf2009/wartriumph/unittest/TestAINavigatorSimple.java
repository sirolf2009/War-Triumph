package com.sirolf2009.wartriumph.unittest;

import org.junit.Assert;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.sirolf2009.wartriumph.ai.AINavigatorSimple;

public class TestAINavigatorSimple {

	@Test
	public void test() {
		Vector2 pos = new Vector2(0, 0);
		Vector2 des = new Vector2(10, 0);
		float speed = 1;
		Assert.assertEquals("Move right", new Vector2(pos).add(speed, 0), AINavigatorSimple.getVelocity(pos, des, speed));
		des = new Vector2(-10, 0);
		Assert.assertEquals("Move left", new Vector2(pos).add(-speed, 0), AINavigatorSimple.getVelocity(pos, des, speed));
		des = new Vector2(0, 10);
		Assert.assertEquals("Move up", new Vector2(pos).add(0, speed), AINavigatorSimple.getVelocity(pos, des, speed));
		des = new Vector2(0, -10);
		Assert.assertEquals("Move down", new Vector2(pos).add(0, -speed), AINavigatorSimple.getVelocity(pos, des, speed));
		
		speed = 4;
		des = new Vector2(10, 10);
		Assert.assertEquals("Move up-right", new Vector2(pos).add(speed/2, speed/2), AINavigatorSimple.getVelocity(pos, des, speed));
		des = new Vector2(-10, 10);
		Assert.assertEquals("Move down-right", new Vector2(pos).add(-speed/2, speed/2), AINavigatorSimple.getVelocity(pos, des, speed));
		des = new Vector2(-10, -10);
		Assert.assertEquals("Move down-left", new Vector2(pos).add(-speed/2, -speed/2), AINavigatorSimple.getVelocity(pos, des, speed));
		des = new Vector2(10, -10);
		Assert.assertEquals("Move up-left", new Vector2(pos).add(speed/2, -speed/2), AINavigatorSimple.getVelocity(pos, des, speed));
	}

}
