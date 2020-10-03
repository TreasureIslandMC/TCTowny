package com.github.sarhatabaot.tctowny;

import media.xen.tradingcards.TradingCards;
import media.xen.tradingcards.addons.AddonLogger;
import media.xen.tradingcards.addons.TradingCardsAddon;
import org.bukkit.plugin.java.JavaPlugin;

public final class TCTowny extends JavaPlugin implements TradingCardsAddon {
	private AddonLogger addonLogger;
	@Override
	public JavaPlugin getJavaPlugin() {
		return this;
	}

	@Override
	public AddonLogger getAddonLogger() {
		return addonLogger;
	}

	@Override
	public void onEnable() {
		TradingCards tradingCards = (TradingCards) getServer().getPluginManager().getPlugin("TradingCards");
		addonLogger = new AddonLogger(getName(), tradingCards);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
}
