package com.invertedowl.registry;


import com.invertedowl.RainWorldOrigins;
import com.invertedowl.power.*;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RWPowers {
    public static final PowerFactory<Power> MESSAGE = new PowerFactory<>(new Identifier(RainWorldOrigins.MOD_ID, "message"), new SerializableData(), data -> (type, entity) -> new TntThrowPower(type, entity)).allowCondition();
    public static final PowerFactory<Power> EXPLOSIVE_JUMP = new PowerFactory<>(new Identifier(RainWorldOrigins.MOD_ID, "explosive_jump"), new SerializableData().add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Active.Key()), data -> (type, entity) -> {
        ExplosiveJumpPower power = new ExplosiveJumpPower(type, entity, 200, HudRender.DONT_RENDER, null);
        power.setKey((Active.Key)data.get("key"));
        return power;
    }).allowCondition();
    public static final PowerFactory<Power> CAN_ONLY_EAT_SPEARED = new PowerFactory<>(new Identifier(RainWorldOrigins.MOD_ID, "can_only_eat_speared"), new SerializableData(), data -> (type, entity) -> new OnlyEatSpeared(type, entity)).allowCondition();
    public static final PowerFactory<Power> NEW_SPEAR = new PowerFactory<>(new Identifier(RainWorldOrigins.MOD_ID, "new_spear"), new SerializableData().add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Active.Key()), data -> (type, entity) -> {
        NewSpearPower power = new NewSpearPower(type, entity, 20, HudRender.DONT_RENDER, entity1 -> {entity.sendMessage(Text.of("oo round 2"));});
        power.setKey((Active.Key)data.get("key"));
        return power;
    }).allowCondition();

    public static final PowerFactory<Power> TONGUE = new PowerFactory<>(new Identifier(RainWorldOrigins.MOD_ID, "tongue"), new SerializableData().add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Active.Key()), data -> (type, entity) -> {
        TonguePower power = new TonguePower(type, entity, 20, HudRender.DONT_RENDER, null);
        power.setKey((Active.Key)data.get("key"));
        return power;
    }).allowCondition();

    public static void init() {
        Registry.register(ApoliRegistries.POWER_FACTORY, MESSAGE.getSerializerId(), MESSAGE);
        Registry.register(ApoliRegistries.POWER_FACTORY, EXPLOSIVE_JUMP.getSerializerId(), EXPLOSIVE_JUMP);
        Registry.register(ApoliRegistries.POWER_FACTORY, CAN_ONLY_EAT_SPEARED.getSerializerId(), CAN_ONLY_EAT_SPEARED);
        Registry.register(ApoliRegistries.POWER_FACTORY, NEW_SPEAR.getSerializerId(), NEW_SPEAR);
        Registry.register(ApoliRegistries.POWER_FACTORY, TONGUE.getSerializerId(), TONGUE);
    }
}
