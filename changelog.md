[1.4.40]

**Added**
- Tooltips for all wine bottles now indicate their size (`small` or `big`).
- Wine bottle storage blocks now display tooltips specifying compatible bottle sizes (`small` or `all sizes`).
- Japanese translation _(Thanks to PExPE3)_
- Bushy Leaves resourcepack is now bundled and supplied by default for forge as well

**Changed**
- Added template models for most Vinery specific Blocks (e.g. Wine Racks, Wines) - this should reduce loading times 
- Increased the `Completionist Banner` effect duration from 40 to 200
- Updated the `Overgrown Lattices` advancement description for better clarity
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
