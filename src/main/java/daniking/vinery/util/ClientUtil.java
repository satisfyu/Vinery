package daniking.vinery.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ClientUtil {

    public static int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }

    public static <T extends BlockEntity> void renderBlock(BlockState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity){
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrices, vertexConsumers, ClientUtil.getLightLevel(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV);
    }

    public static <T extends BlockEntity> void renderBlockFromItem(BlockItem item, MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity){
        renderBlock(item.getBlock().getDefaultState(), matrices, vertexConsumers, entity);
    }

    public static <T extends BlockEntity> void renderItem(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity){
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GUI, ClientUtil.getLightLevel(entity.getWorld(), entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 1);
    }

}
