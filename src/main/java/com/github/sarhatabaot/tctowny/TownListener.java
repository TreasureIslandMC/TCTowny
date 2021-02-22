package com.github.sarhatabaot.tctowny;


import com.palmergames.bukkit.towny.event.NewNationEvent;
import com.palmergames.bukkit.towny.event.NewTownEvent;
import media.xen.tradingcards.CardUtil;
import media.xen.tradingcards.TradingCards;
import media.xen.tradingcards.api.addons.AddonListener;
import media.xen.tradingcards.api.addons.TradingCardsAddon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TownListener extends AddonListener {
	private TradingCards tradingCards;

	public TownListener(final TradingCardsAddon tradingCardsAddon, final TradingCards tradingCards) {
		super(tradingCardsAddon);
		this.tradingCards = tradingCards;
	}

	@EventHandler
	public void onNewTown(NewTownEvent e) {
		if (!tradingCardsAddon.getJavaPlugin().getConfig().getBoolean("Towny-Enabled")) {
			return;
		}

		if (tradingCardsAddon.getJavaPlugin().getServer().getPluginManager().getPlugin("Towny") == null) {
			tradingCardsAddon.getAddonLogger().info("Could not detect Towny!");
			return;
		}

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(System.currentTimeMillis());
		int date = gc.get(Calendar.DATE);
		int month = gc.get(Calendar.MONTH) + 1;
		int year = gc.get(Calendar.YEAR);
		String townRarity = tradingCardsAddon.getJavaPlugin().getConfig().getString("Town-Rarity");
		String townName = e.getTown().getName();
		String townSeries = tradingCardsAddon.getJavaPlugin().getConfig().getString("Town-Series");
		String townType = tradingCardsAddon.getJavaPlugin().getConfig().getString("Town-Type");
		tradingCardsAddon.getAddonLogger().debug(townName);
		tradingCardsAddon.getAddonLogger().debug(townSeries);
		tradingCardsAddon.getAddonLogger().debug(townType);

		boolean hasShiny = tradingCardsAddon.getJavaPlugin().getConfig().getBoolean("Has-Shiny");
		String prefix = tradingCards.getConfig().getString("General.Card-Prefix");
		tradingCardsAddon.getAddonLogger().debug(prefix);

		String dPrefix;
		if (tradingCards.getConfig().contains("Cards." + townRarity + "." + townName)) {
			tradingCardsAddon.getAddonLogger().info("Town already exists!");
			if (tradingCardsAddon.getJavaPlugin().getConfig().getBoolean("Allow-Duplicates")) {
				int num = 1;
				dPrefix = tradingCardsAddon.getJavaPlugin().getConfig().getString("Town-Duplicate-Prefix").replaceAll("%num%", String.valueOf(num));
				String dSuffix = tradingCardsAddon.getJavaPlugin().getConfig().getString("Town-Duplicate-Suffix").replaceAll("%num%", String.valueOf(num));

				while (tradingCards.getConfig().contains("Cards." + townRarity + "." + dPrefix + townName + dSuffix)) {
					++num;
					dPrefix = tradingCardsAddon.getJavaPlugin().getConfig().getString("Town-Duplicate-Prefix").replaceAll("%num%", String.valueOf(num));
					dSuffix = tradingCardsAddon.getJavaPlugin().getConfig().getString("Town-Duplicate-Suffix").replaceAll("%num%", String.valueOf(num));
					if (num > 100) {
						tradingCardsAddon.getAddonLogger().warning("Something went wrong!");
						break;
					}
				}


				Player p = Bukkit.getPlayer(e.getTown().getMayor().getName());
				tradingCardsAddon.getAddonLogger().debug("Mayor name: " + e.getTown().getMayor().getName());


				String townInfo = getCalendarMode(month, date, year);
				CardUtil.createCard(p, townRarity, dPrefix + townName + dSuffix, townSeries, townType, hasShiny, townInfo, "Founder: " + p.getName());
				return;
			}
		}

		Player p = Bukkit.getPlayer(e.getTown().getMayor().getName());
		tradingCardsAddon.getAddonLogger().debug("Mayor name: " + e.getTown().getMayor().getName());

		dPrefix = getCalendarMode(month, date, year);

		CardUtil.createCard(p, townRarity, townName, townSeries, townType, hasShiny, dPrefix, "Founder: " + p.getName());

	}

	private String getCalendarMode(final int month, final int date, final int year) {
		if (tradingCards.getConfig().getBoolean("General.American-Mode")) {
			return "Created " + month + "/" + date + "/" + year;
		}

		return "Created " + date + "/" + month + "/" + year;
	}

	@EventHandler
	public void onNewNation(NewNationEvent e) {
		if (!tradingCardsAddon.getJavaPlugin().getConfig().getBoolean("Towny-Enabled")) {
			return;
		}

		if (tradingCardsAddon.getJavaPlugin().getServer().getPluginManager().getPlugin("Towny") == null) {
			tradingCardsAddon.getAddonLogger().warning("Could not detect Towny!");
			return;
		}

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(System.currentTimeMillis());
		int date = gc.get(Calendar.DATE);
		int month = gc.get(Calendar.MONTH) + 1;
		int year = gc.get(Calendar.YEAR);
		String townRarity = tradingCardsAddon.getJavaPlugin().getConfig().getString("Nation-Rarity");
		String townName = e.getNation().getName();
		tradingCardsAddon.getAddonLogger().debug(townName);

		String townSeries = tradingCardsAddon.getJavaPlugin().getConfig().getString("Nation-Series");
		String townType = tradingCardsAddon.getJavaPlugin().getConfig().getString("Nation-Type");
		boolean hasShiny = tradingCardsAddon.getJavaPlugin().getConfig().getBoolean("Has-Shiny");
		String prefix = tradingCards.getConfig().getString("General.Card-Prefix");
		tradingCardsAddon.getAddonLogger().debug(townSeries);
		tradingCardsAddon.getAddonLogger().debug(townType);
		tradingCardsAddon.getAddonLogger().debug(prefix);


		String dPrefix;
		if (tradingCards.getConfig().contains("Cards." + townRarity + "." + townName)) {
			if (tradingCardsAddon.getJavaPlugin().getConfig().getBoolean("Allow-Duplicates")) {
				int num = 1;
				dPrefix = tradingCardsAddon.getJavaPlugin().getConfig().getString("Nation-Duplicate-Prefix").replaceAll("%num%", String.valueOf(num));
				String dSuffix = tradingCardsAddon.getJavaPlugin().getConfig().getString("Nation-Duplicate-Suffix").replaceAll("%num%", String.valueOf(num));

				while (tradingCards.getConfig().contains("Cards." + townRarity + "." + dPrefix + townName + dSuffix)) {
					++num;
					dPrefix = tradingCardsAddon.getJavaPlugin().getConfig().getString("Nation-Duplicate-Prefix").replaceAll("%num%", String.valueOf(num));
					dSuffix = tradingCardsAddon.getJavaPlugin().getConfig().getString("Nation-Duplicate-Suffix").replaceAll("%num%", String.valueOf(num));
					if (num > 100) {
						System.out.println("[Cards] Something went wrong!");
						break;
					}
				}


				Player p = Bukkit.getPlayer(e.getNation().getCapital().getMayor().getName());
				tradingCardsAddon.getAddonLogger().debug("Emperor name: " + e.getNation().getCapital().getMayor().getName());

				String townInfo = getCalendarMode(month,date,year);

				CardUtil.createCard(p, townRarity, dPrefix + townName + dSuffix, townSeries, townType, hasShiny, townInfo, "Founder: " + p.getName());
			}
			return;
		}


		Player p = Bukkit.getPlayer(e.getNation().getCapital().getMayor().getName());
		tradingCardsAddon.getAddonLogger().debug("Emperor name: " + e.getNation().getCapital().getMayor().getName());
		dPrefix = getCalendarMode(month,date,year);

		CardUtil.createCard(p, townRarity, townName, townSeries, townType, hasShiny, dPrefix, "Founder: " + p.getName());


	}
}
