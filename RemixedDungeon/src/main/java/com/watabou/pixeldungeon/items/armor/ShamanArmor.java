package com.watabou.pixeldungeon.items.armor;

import com.nyrds.pixeldungeon.ml.R;
import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.actors.hero.HeroSubClass;
import com.watabou.pixeldungeon.utils.GLog;

public class ShamanArmor extends ElfArmor {

	public ShamanArmor()
	{
		name = Game.getVar(R.string.ElfArmor_Name);
		image = 19;
		hasHelmet = true;
		coverHair = true;
	}	
	@Override
	public boolean doEquip( Hero hero ) {
		if (hero.getSubClass() == HeroSubClass.SHAMAN) {
			return super.doEquip( hero );
		} else {
			GLog.w( Game.getVar(R.string.ElfArmor_NotElf) );
			return false;
		}
	}
}