[1.4.40] - 2025.04.09

**Added**
- Tooltips for all wine bottles now indicate their size (`small` or `big`).
- Wine bottle storage blocks now display tooltips specifying compatible bottle sizes (`small` or `all sizes`).

**Changed**
- Added template models for most Vinery specific Blocks (e.g. Wine Racks, Wines) - this should reduce loading times 
- Increased the `Completionist Banner` effect duration from 40 to 200
- Updated the `Overgrown Lattices` advancement description for better clarity

**Fixed**
- Resolved an issue where `Apple Leaves` were not properly registered as flammable

***

[1.4.39] - 2025.03.04

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
