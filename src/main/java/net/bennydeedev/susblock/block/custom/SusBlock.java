package net.bennydeedev.susblock.block.custom;

import net.bennydeedev.susblock.SusBlockMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SusBlock extends Block {

    public SusBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
            PlayerEntity player, Hand hand, BlockHitResult hit) {

        var playerPosBlockBelow = player.getBlockPos().down(1);

        boolean isSameXPos = playerPosBlockBelow.getX() == pos.getX();
        boolean isSameYPos = playerPosBlockBelow.getY() == pos.getY();
        boolean isSameZPos = playerPosBlockBelow.getZ() == pos.getZ();
        boolean isSamePos = isSameXPos && isSameYPos && isSameZPos;

        if (isSamePos) {
            return ActionResult.FAIL;
        }

        world.setBlockState(playerPosBlockBelow, Blocks.GRASS_BLOCK.getDefaultState());

        if (hand == Hand.MAIN_HAND) {
            SusBlockMod.LOGGER.info("MAIN_HAND");
        } else {
            SusBlockMod.LOGGER.info("OFF_HAND");
        }

        return ActionResult.PASS;
    }

}
