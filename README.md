Dirt-Chest-9000--Ersatz-
========================

A poor re-implementation of the Dirt Chest 9000 from the Iron Chests mod to learn how to make GUIs.

Made following [MrrGingerNinja's tutorial on GUIs and inventories here](http://www.minecraftforum.net/topic/1927571-162-advanced-minecraft-forge-modding-tutorial-1-interfaces-part-1/).

# Requirements

- Minecraft Forge for Minecraft 1.7.2
- An IDE

If you have no idea how to set things up, I recommend [this tutorial by the same guy as the above one for setting up things in Eclipse](http://www.minecraftforum.net/topic/2413773-172-modding-with-forge-1-jdk-eclipse-forge-and-gradle/).

# Differences from the Tutorial

I implemented determining which way to point the block in the same way the vanilla Minecraft chest does, using Block#onBlockPlacedBy() rather than Block#onBlockAdded().
