package daniking.vinery.client.render.feature;

import daniking.vinery.item.StrawHatItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class StrawHatRenderer extends GeoArmorRenderer<StrawHatItem> {
	public StrawHatRenderer() {
		super(new StrawHatModel());
	}
}