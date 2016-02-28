package com.game.platform.utils;

import java.util.Random;

public class Avatar {
	private static Random rad = new Random();
	public enum Male {
		ONE("1", "/default/avatar/male/1.jpg"),
		TWO("2", "/default/avatar/male/2.jpg"),
		THREE("3", "/default/avatar/male/3.jpg"),
		FOUR("4", "/default/avatar/male/4.jpg"),
		FIVE("5", "/default/avatar/male/5.jpg"),
		SIX("6", "/default/avatar/male/6.jpg"),
		SEVEN("7", "/default/avatar/male/7.jpg"),
		EIGHT("8", "/default/avatar/male/8.jpg"),
		NINE("9", "/default/avatar/male/9.jpg"),
		TEN("10", "/default/avatar/male/10.jpg");

		private String name;
		private String image;

		private Male(String name, String image) {
			this.name = name;
			this.image = image;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public static String getImgae(String name) {
			for (Male male : Male.values()) {
				if (male.getName().equals(name))
					return male.getImage();
			}
			return null;
		}
	}

	public enum Female {
		ONE("1", "/default/avatar/female/1.jpg"),
		TWO("2", "/default/avatar/female/2.jpg"),
		THREE("3", "/default/avatar/female/3.jpg"),
		FOUR("4", "/default/avatar/female/4.jpg"), 
		FIVE("5", "/default/avatar/female/5.jpg"), 
		SIX("6", "/default/avatar/female/6.jpg"), 
		SEVEN("7", "/default/avatar/female/7.jpg"),
		EIGHT("8", "/default/avatar/female/8.jpg"), 
		NINE("9", "/default/avatar/female/9.jpg"),
		TEN("10", "/default/avatar/female/10.jpg");

		private String name;
		private String image;

		private Female(String name, String image) {
			this.name = name;
			this.image = image;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public static String getImgae(String name) {
			for (Female male : Female.values()) {
				if (male.getName().equals(name))
					return male.getImage();
			}
			return null;
		}
	}

	public enum Guild {
		ONE("1", "/default/avatar/guild/80/1.jpg"),
		TWO("2", "/default/avatar/guild/80/2.jpg"),
		THREE("3", "/default/avatar/guild/80/3.jpg"),
		FOUR("4", "/default/avatar/guild/80/4.jpg"),
		FIVE("5", "/default/avatar/guild/80/5.jpg"),
		SIX("6", "/default/avatar/guild/80/6.jpg"),
		SEVEN("7", "/default/avatar/guild/80/7.jpg"), 
		EIGHT("8", "/default/avatar/guild/80/8.jpg"),
		NINE("9", "/default/avatar/guild/80/9.jpg"),
		TEN("10", "/default/avatar/guild/80/10.jpg");

		private String name;
		private String image;

		private Guild(String name, String image) {
			this.name = name;
			this.image = image;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public static String getImgae(String name) {
			for (Guild guild : Guild.values()) {
				if (guild.getName().equals(name))
					return guild.getImage();
			}
			return null;
		}
	}

	public static String getMaleAvatar() {
		return Male.getImgae(rad.nextInt(Male.values().length-1)+1  + "");
	}

	public static String getFemaleAvatar() {
		return Female.getImgae(rad.nextInt(Female.values().length-1)+1 + "");
	}

	public static String getGuildAvatar() {
		return Guild.getImgae(rad.nextInt(Guild.values().length-1)+1 + "");
	}
	
	
	public static void main(String[] args) {
		for (int i = 0; i < 500; i++) {
			String s=getMaleAvatar();
			System.out.println(s);
		}
	
	}
}
