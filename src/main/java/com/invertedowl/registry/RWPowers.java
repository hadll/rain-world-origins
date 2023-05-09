package com.invertedowl.registry;


import com.invertedowl.RainWorldOrigins;
import com.invertedowl.power.ExplosiveJumpPower;
import com.invertedowl.power.TntThrowPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RWPowers {
    public static final PowerFactory<Power> MESSAGE = new PowerFactory<>(new Identifier(RainWorldOrigins.MOD_ID, "message"), new SerializableData(), data -> (type, entity) -> new TntThrowPower(type, entity)).allowCondition();
    public static final PowerFactory<Power> EXPLOSIVE_JUMP = new PowerFactory<>(new Identifier(RainWorldOrigins.MOD_ID, "explosive_jump"), new SerializableData(), data -> (type, entity) -> new ExplosiveJumpPower(type, entity)).allowCondition();

    public static void init() {
        Registry.register(ApoliRegistries.POWER_FACTORY, MESSAGE.getSerializerId(), MESSAGE);
        Registry.register(ApoliRegistries.POWER_FACTORY, EXPLOSIVE_JUMP.getSerializerId(), EXPLOSIVE_JUMP);
    }
}
