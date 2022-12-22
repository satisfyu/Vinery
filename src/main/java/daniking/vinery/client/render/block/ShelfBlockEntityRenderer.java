package daniking.vinery.client.render.block;

import daniking.vinery.block.ShelfBlock;
import daniking.vinery.block.entity.ShelfBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ShelfBlockEntityRenderer implements BlockEntityRenderer<ShelfBlockEntity> {

    public ShelfBlockEntityRenderer(BlockEntityRendererFactory.Context context){

    }

    @Override
    public void render(ShelfBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.hasWorld()) {
            return;
        }
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        DefaultedList<ItemStack> itemStacks = entity.getInventory();

        matrices.push();
        applyBlockAngle(matrices, entity.getCachedState());
        matrices.translate(-0.4, 0.5, 0.3);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90f));
        matrices.scale(0.5f, 0.5f, 0.5f);

        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty()) {
                matrices.push();
                matrices.translate(0f, 0f, 0.2f * i);
                matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(20));
                itemRenderer.renderItem(stack, ModelTransformation.Mode.GUI, getLightLevel(entity.getWorld(), entity.getPos()),
                        OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 1);
                matrices.pop();
            }
        }
        matrices.pop();
    }
/*
    @Override
    public void render(ShelfBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        DefaultedList<ItemStack> itemStacks = entity.getInventory();
        matrices.push();
        matrices.scale(0.4f, 0.4f, 0.4f);
        //matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-90));
        //matrices.translate(0.5f + (float) (i / 5), 0.645f, 0.5f);
        switch (entity.getCachedState().get(ShelfBlock.FACING)) {
            case NORTH -> matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
            case EAST -> matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90)); //270
            case SOUTH -> matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
            case WEST -> matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270)); //90
        }
        for(int i = 0; i < itemStacks.size(); i++){
            matrices.push();
            ItemStack stack = itemStacks.get(i);
            matrices.translate(i, 0, 0);

            int s = 0;
            switch (entity.getCachedState().get(ShelfBlock.FACING)) {
                case NORTH -> s = 180;
                case EAST -> s = 270;
                case SOUTH -> s = 0;
                case WEST -> s = 90;
            }
            Matrix4f matrix4f = Matrix4f.translate(1, 1, 1);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(s));
            matrices.multiplyPositionMatrix(matrix4f);


            itemRenderer.renderItem(stack, ModelTransformation.Mode.GUI, getLightLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 1);
            matrices.pop();
        }
        matrices.pop();
    }
 */


    public static void applyBlockAngle(MatrixStack matrices, BlockState state) {
        applyBlockAngle(matrices, state, 167.5f);
    }

    public static void applyBlockAngle(MatrixStack matrices, BlockState state, float angleOffset) {
        float angle = state.get(ShelfBlock.FACING).asRotation();
        matrices.translate(0.5, 0, 0.5);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(angleOffset - angle));
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
