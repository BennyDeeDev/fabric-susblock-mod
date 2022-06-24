package net.bennydeedev.susblock.block.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SusBlock extends Block {
    public SusBlock(Settings settings) {
        super(settings);
    }

    private int susBlockOnUseCount = 0;
    private boolean isLucky = true;

    private void luckyOnClient(World world, BlockPos pos) {
        for (int i = 0; i < 360; i++) {
            if (i % 20 == 0) {
                world.addParticle(ParticleTypes.HEART, pos.getX() + 0.5d, pos.getY() + 1d, pos.getZ() + 0.5d,
                        Math.cos(i) * 0.25d, 0.15d, Math.sin(i) * 0.25d);
            }
        }

        world.playSound(pos.getX() + 0.5D,
                pos.getY() + 0.5D, pos.getZ() + 0.5D,
                SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.BLOCKS,
                1, 1,
                false);
    }

    private void luckyOnServer(World world, BlockPos pos, PlayerEntity player) {
        dropExperience((ServerWorld) world, pos, 1);
        player.dropItem(Items.DIAMOND, 1);
    }

    private void unluckyOnClient(World world, BlockPos pos) {
        world.playSound(pos.getX() + 0.5D,
                pos.getY() + 0.5D, pos.getZ() + 0.5D,
                SoundEvents.ENTITY_GHAST_WARN, SoundCategory.BLOCKS,
                1, 1,
                false);
    }

    private void unluckyOnServer(World world, BlockPos pos, PlayerEntity player) {
        var playerPosBlockBelow = player.getBlockPos().down(1);

        List<BlockState> oldBlocks = new ArrayList<BlockState>();

        BlockPos.iterateInSquare(playerPosBlockBelow, 4, Direction.NORTH,
                Direction.EAST).forEach((e) -> {
                    if (oldBlocks.size() == 1) {
                        var currentBlockState = world.getBlockState(e);
                        oldBlocks.add(currentBlockState);
                    }

                    if (!e.equals(pos)) {
                        world.setBlockState(e, Blocks.LAVA.getDefaultState());
                    }
                });
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
            PlayerEntity player, Hand hand, BlockHitResult hit) {
        Random random = new Random();
        int n = random.nextInt(2);

        if (hand == Hand.MAIN_HAND) {
            if (world.isClient()) {
                if (isLucky) {
                    luckyOnClient(world, pos);
                } else {
                    unluckyOnClient(world, pos);
                }
            } else {
                if (isLucky) {
                    luckyOnServer(world, pos, player);
                } else {
                    unluckyOnServer(world, pos, player);
                }
                isLucky = n == 0 || susBlockOnUseCount < 3;
                susBlockOnUseCount++;
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos,
            net.minecraft.util.math.random.Random random) {
        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5d,
                pos.getY() + 1d, pos.getZ() + 0.5d,
                0d, 0d, 0d);
    }
}
