# Anti-Villager-Lag-Plus
Reduces lag from villagers by decreasing the interval in which they pathfind, while still allowing users to use farms that rely on 100% vanilla behavior.

## How to use
https://www.spigotmc.org/resources/villager-optimiser-1-14-2-1-15.68517/
this is the plugin that I have editted, thankfully the author chose a good licence, so I can improve it.

the core functionality of the plugin has remained untouched, the only difference is 2 new commands added

**`/aavlp`** - toggles the chunk the player is in to avlp, meaning villager mechanics in the given chunk will be 100% vanilla
  
**`/vlp`** - checks if the chunk the player is in is blacklisted by /aavlp
  
the chunks that are blacklisted are saved automatically when the server closes
