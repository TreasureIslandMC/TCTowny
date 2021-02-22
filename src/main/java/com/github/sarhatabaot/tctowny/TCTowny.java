package com.github.sarhatabaot.tctowny;

import media.xen.tradingcards.TradingCards;
import media.xen.tradingcards.api.addons.AddonLogger;
import media.xen.tradingcards.api.addons.TradingCardsAddon;
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

}
