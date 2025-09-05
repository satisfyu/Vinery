[1.4.41]

**Fixed**
* `JungleWineRack` open/close property being inverted
* Wine aging did not update in tooltip or when consumed. Root causes:
    * cached NBT never recomputed
    * inverted `hasWineYear` prevented proper initialization
    * getter side-effects caused stale UI values
* `getDays`/`getYear` math stabilized; no unintended zeroing
* `JellieWine` Texture alignment
* 

### Added
* Debug commands for testing:
    * `/wine info` — shows current Age (years/days), effect amplifier, and duration (ticks) for the bottle in main hand
    * `/wine age <years>` — sets the NBT year so the item is treated as aged by `<years>` years immediately

***

[1.4.40]

**Added**
- Tooltips for all wine bottles now indicate their size (`small` or `big`).
- Wine bottle storage blocks now display tooltips specifying compatible bottle sizes (`small` or `all sizes`).
- Japanese translation _(Thanks to PExPE3)_
- Bushy Leaves resourcepack is now bundled and supplied by default for forge as well
- Grapevine Leaves are now available again! They will grow near Grapevine Stems with Red Grape or White Grapes

**Changed**
- Added template models for most Vinery specific Blocks (e.g. Wine Racks, Wines) - this should reduce loading times 
- Increased the `Completionist Banner` effect duration from 40 to 200
- Updated the `Overgrown Lattices` advancement description for better clarity
- Replace `VineryIdentifier` with `Vinery.identifier` - this should resolve Issues with Xaeros Map Mob Icons
- Completely reworked Apple & Dark Cherry Trees - new Features, Textures and better functionality
- Wine aging now uses `getGameTime()` instead of `getDayTime()` to prevent aging reset when using `/time set`
- Wine age and upgrade progress calculation now use day-based precision instead of year rounding _(Thanks to AverageChaos)_
- Stackable logs now provide `1000` smelting value, previously `300`
- Jungle Grapevine Blocks now use biome foliage tint 

**Fixed**
- Resolved an issue where `Apple Leaves` were not properly registered as flammable
- `Completionist Wall Banner` not having a Loottable
- Fixed a crash when opening the Vinery creative tab caused by `ItemStack` entries with a count greater than `1`
- Fixed a startup crash with Easy NPC caused by parallel modification of the strippables map during setup

***

[1.4.39]

**Added**
- Added Cherry Leaves, Apple Leaves and Grapevine Leaves to the `hoe` mineable tag.

**Changed**
- Reworked Wine Aging Logic - /set time won't affect Wine Aging anymore. If you want to manually age your Wine use /time add. 
- Changed the `apple_mashing` `honey_comb` recipe output count from "9" to "4"

**Fixed**
- Config should now load & generate properly on Forge
- Wine Years values do are now properly update
- CreeperCrush not showing a DeathMessage and dropping the players lot
- ExperienceEffect not giving the correct amount of additional experience
