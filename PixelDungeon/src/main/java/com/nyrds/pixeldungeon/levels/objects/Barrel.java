package com.nyrds.pixeldungeon.levels.objects;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.utils.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mike on 01.07.2016.
 */
public class Barrel extends LevelObject {

	public Barrel() {
		super(-1);
	}

	public Barrel(int pos) {
		super(pos);
	}

	@Override
	void setupFromJson(Level level, JSONObject obj) throws JSONException {
	}

	@Override
	public boolean pushable() {
		return true;
	}

	@Override
	public boolean push(Hero hero) {
		Level level = Dungeon.level;
		int hx = level.cellX(hero.getPos());
		int hy = level.cellY(hero.getPos());

		int x = level.cellX(getPos());
		int y = level.cellY(getPos());

		int dx = x - hx;
		int dy = y - hy;

		if (dx * dy != 0) {
			return false;
		}

		int nextCell = level.cell(x + dx, y + dy);
		if (level.cellValid(nextCell) && !level.passable[nextCell]) {
			return false;
		} else {
			setPos(nextCell);
			level.levelObjectMoved(this);
			level.itemPress(nextCell);
		}

		return true;
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
	}

	@Override
	public void burn() {
		remove();
		int oldTile = Dungeon.level.map[getPos()];
		Dungeon.level.set(getPos(), Terrain.EMBERS);
		GameScene.discoverTile(getPos(), oldTile);
	}

	@Override
	public String desc() {
		return Dungeon.level.tileDesc(Terrain.SIGN);
	}

	@Override
	public String name() {
		return Dungeon.level.tileName(Terrain.SIGN);
	}

	@Override
	public int image() {
		return 0;
	}

	@Override
	public String texture() {
		return "levelObjects/barrels.png";
	}
}
