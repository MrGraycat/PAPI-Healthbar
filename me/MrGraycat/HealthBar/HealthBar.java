package me.MrGraycat.HealthBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class HealthBar extends PlaceholderExpansion {

	@Override
	public String getAuthor() {
		return "MrGraycat";
	}

	@Override
	public String getIdentifier() {
		return "healthbar";
	}

	@Override
	public String getVersion() {
		return "0.6";
	}
	
	@Override
	public boolean canRegister() {
		return true;
	}

	@Override
    public String onRequest(OfflinePlayer player, String identifier){
		if (player == null)
			return null;
		
		
		
		String bigHeartColor = toChatColor("&c");
		String smallHeartColor = toChatColor("&c");
		
		identifier = identifier.replace(" ", "").toLowerCase();

		if (identifier.contains("colors:")) {
			identifier = identifier.replace("colors:", "");

			if (identifier.length() >= 4) {
				bigHeartColor = toChatColor(identifier.substring(0, 2));
				smallHeartColor = toChatColor(identifier.substring(identifier.length() - 2, identifier.length()));
			}
		}
		
		if (identifier.contains("hex:")) {
			String[] colors = identifier.replace("hex:", "").split(",");
			
			if (colors[0] != null && colors[1] != null) {
				bigHeartColor = translateHexColorCodes(colors[0]);
				smallHeartColor = translateHexColorCodes(colors[1]);
			}
		}

		double health = Math.ceil((player.getPlayer().getHealth()));
		String healthbar = "";
		
		if (health > 20 || player.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			health = 20;
		}

		for (double i = health; i >= 2; i = i - 2) {
			healthbar = healthbar + "❤";
			health = health - 2;
		}

		healthbar = bigHeartColor + healthbar;

		if (health > 0) {
			healthbar = healthbar + smallHeartColor + "♥";
		}

		return healthbar;
    }
	
	private String toChatColor(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	private static final Pattern pattern = Pattern.compile("(?<!\\\\)(#[a-fA-F0-9]{6})");
	
	public static String translateHexColorCodes(String message) {
	    Matcher matcher = pattern.matcher(message);
	    while (matcher.find()) {
	      String color = message.substring(matcher.start(), matcher.end());
	      message = message.replace(color, "" + (ChatColor.COLOR_CHAR + color)); 
	    } 
	    return message;
	 }
}
