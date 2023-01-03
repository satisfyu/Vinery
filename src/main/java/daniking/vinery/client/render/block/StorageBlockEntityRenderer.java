package daniking.vinery.client.render.block;

import daniking.vinery.block.StorageBlock;
import daniking.vinery.block.entity.ShelfBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class StorageBlockEntityRenderer implements BlockEntityRenderer<ShelfBlockEntity> {

    public StorageBlockEntityRenderer(BlockEntityRendererFactory.Context context){

    }

    @Override
    public void render(ShelfBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.hasWorld()) {
            return;
        }
        BlockState state = entity.getCachedState();
        if(state.getBlock() instanceof StorageBlock sB){
            DefaultedList<ItemStack> itemStacks = entity.getInventory();
            matrices.push();
            applyBlockAngle(matrices, state, 180);

            if(sB.type().equals(StorageBlock.StorageType.SHELF)){
                renderShelf(entity, matrices, vertexConsumers, itemStacks);
            }
            else if(sB.type().equals(StorageBlock.StorageType.NINE_BOTTLE)){
                renderNineBottles(entity, matrices, vertexConsumers, itemStacks);
            }

            matrices.pop();
        }

    }


    public static void applyBlockAngle(MatrixStack matrices, BlockState state, float angleOffset) {
        float angle = state.get(StorageBlock.FACING).asRotation();
        matrices.translate(0.5, 0, 0.5);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(angleOffset - angle));
    }

    private static int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }

    private static void renderNineBottles(ShelfBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks){
        BlockRenderManager manager = MinecraftClient.getInstance().getBlockRenderManager();

        matrices.translate(-0.13, 0.335, 0.125);
        //matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90f));
        matrices.scale(0.9f, 0.9f, 0.9f);

        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
                matrices.push();
                int line = i >= 6 ? 3 : i >= 3 ? 2 : 1;

                float x;
                float y;
                if(line == 1){
                    x = -0.35f * i;
                    y = 0;
                }
                else if(line == 2){
                    x = -0.35f * (i - 3);
                    y = -0.33f;
                }
                else{
                    x = -0.35f * (i - 6);
                    y = -0.66f;
                }

                matrices.translate(x, y, 0f);

                matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90f));

                manager.renderBlockAsEntity(blockItem.getBlock().getDefaultState(), matrices, vertexConsumers, getLightLevel(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV);
                matrices.pop();
            }
        }
    }

    private static void renderShelf(ShelfBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DefaultedList<ItemStack> itemStacks){
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        matrices.translate(-0.4, 0.5, 0.25);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90f));
        matrices.scale(0.5f, 0.5f, 0.5f);

        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty()) {
                matrices.push();
                matrices.translate(0f, 0f, 0.2f * i);
                matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(22.5f));
                itemRenderer.renderItem(stack, ModelTransformation.Mode.GUI, getLightLevel(entity.getWorld(), entity.getPos()),
                        OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 1);
                matrices.pop();
            }
        }
    }
}
