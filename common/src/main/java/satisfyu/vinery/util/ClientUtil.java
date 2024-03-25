package satisfyu.vinery.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ClientUtil {
    public static int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    public static <T extends BlockEntity> void renderBlock(BlockState state, PoseStack matrices, MultiBufferSource vertexConsumers, T entity){
        Level level = entity.getLevel();
        if(level == null) return;
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrices, vertexConsumers, ClientUtil.getLightLevel(level, entity.getBlockPos()), OverlayTexture.NO_OVERLAY);
    }

    public static <T extends BlockEntity> void renderBlockFromItem(BlockItem item, PoseStack matrices, MultiBufferSource vertexConsumers, T entity){
        renderBlock(item.getBlock().defaultBlockState(), matrices, vertexConsumers, entity);
    }

    public static <T extends BlockEntity> void renderItem(ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, T entity){
        Level level = entity.getLevel();
        if(level == null) return;
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GUI, ClientUtil.getLightLevel(level, entity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, level, 1);
    }

    @SuppressWarnings("unused")
    public static <T extends Button> void setButtonPosition(T button, int x, int y){
        button.setX(x);
        button.setY(y);
    }

}
