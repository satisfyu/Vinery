package satisfyu.vinery.util.sign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.resources.model.Material;

public class SpriteIdentifierRegistry {
	public static final SpriteIdentifierRegistry INSTANCE = new SpriteIdentifierRegistry();
	private final List<Material> identifiers;

	private SpriteIdentifierRegistry() {
		identifiers = new ArrayList<>();
	}

	public void addIdentifier(Material sprite) {
		this.identifiers.add(sprite);
	}

	public Collection<Material> getIdentifiers() {
		return Collections.unmodifiableList(identifiers);
	}
}
