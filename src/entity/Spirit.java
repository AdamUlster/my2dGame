package entity;

import main.GamePanel;

import java.awt.*;

public class Spirit extends Entity {
    public Spirit(GamePanel gp, String name, int maxHealth, int health, int x, int y, int width, int height) {
        super(gp);
        this.name = name; // sets the spirit's name to the value passed to it
        this.maxHealth = maxHealth;
        this.health = health;

        // Collision box
        this.solidArea = new Rectangle(x, y, width, height);
    }

    public int getMaxHealth() { // gets the spirit's max health
        return maxHealth;
    }

    public int getHealth() { // gets the spirit's current health
        return health;
    }

    public void setHealth(int health) { // sets the spirit's current health
        this.health = health;
    }

}
