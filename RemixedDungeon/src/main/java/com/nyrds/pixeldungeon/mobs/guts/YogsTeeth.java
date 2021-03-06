package com.nyrds.pixeldungeon.mobs.guts;

import com.nyrds.pixeldungeon.effects.Devour;
import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.blobs.ToxicGas;
import com.watabou.pixeldungeon.actors.buffs.Amok;
import com.watabou.pixeldungeon.actors.buffs.Bleeding;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.Burning;
import com.watabou.pixeldungeon.actors.buffs.Paralysis;
import com.watabou.pixeldungeon.actors.buffs.Sleep;
import com.watabou.pixeldungeon.actors.buffs.Terror;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.utils.Random;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DeadDie on 12.02.2016
 */
public class YogsTeeth extends Mob {
    {
        hp(ht(350));
        defenseSkill = 44;

        exp = 26;

        addResistance(ToxicGas.class);

        addImmunity(Paralysis.class);
        addImmunity(Amok.class);
        addImmunity(Sleep.class);
        addImmunity(Terror.class);
        addImmunity(Burning.class);
    }


    @Override
    public int attackProc(@NotNull Char enemy, int damage ) {
        //Life drain proc
        if (Random.Int(3) == 1){
            heal(damage, this);
        }

        //Bleeding proc
        if (Random.Int(3) == 1){
            Buff.affect(enemy, Bleeding.class).level(damage);
        }

        //Double damage proc
        if (Random.Int(3) == 1){
            Devour.hit(enemy);
            Sample.INSTANCE.play(Assets.SND_BITE);
            return damage*2;
        }
        return damage;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(50, 80);
    }

    @Override
    public int attackSkill( Char target ) { return 46; }

    @Override
    public int dr() { return 21; }

    @Override
    public boolean canBePet() {
        return false;
    }
}
