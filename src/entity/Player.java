package entity;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Water_Jet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyH;//call on keyhandler class

    public Spirit[] spirits = new Spirit[3];
    public int currentSpiritIndex = 0; // keeps track of the current spirit

    public final int screenX;
    public final int screenY;

    public int numTotems = 0; // keeps track of the number of totems the player has collected

    //scaling factors for hitboxes and attack areas
    public double bearHitboxScale = 0.75;//bear hit box scale
    public double eagleHitboxScale = 0.75;//eagle hit box scale
    public double turtleHitboxScale = 1;//turtle hit box scale
    public double bearAttackBoxScaleSize = 1.25;
    public double eagleAttackBoxScaleSize = 1.25;
    public double turtleAttackBoxScaleSize = 1;

    //INDICES
    int monsterIndex;

    //COUNTERS
    public int invincibilityCounter = 0;
    public int primaryICD = 0;//internal cooldown for attacks
    public int secondaryICD = 0;//internal cooldown for special/secondary moves

    public Player(GamePanel gp, KeyHandler keyH) { //create default attributes (constructor)

        super(gp); // call on Entity class
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        setDefaultValues();//sets default values for the player
        getPlayerImage();
        getPlayerAttackImage();
        getPlayerSpecialAttackImage();
    }

    public Spirit getCurrentSpirit() { // gets the current spirit
        return spirits[currentSpiritIndex];
    }

    public void setDefaultValues() {//create default values to spawn the player

        worldX = gp.tileSize * 50; // sets the default position x-coordinate
        worldY = gp.tileSize * 50; //sets the default position y-coordinate
        speed = 4;//sets speed to 4
        direction = "right";//can input any direction
        projectile = new OBJ_Water_Jet(gp);

        // Initializes the spirits and their health values
        spirits[0] = new Spirit(gp, "Bear", 10, 9,
                (int) (gp.tileSize * (1.0 - bearHitboxScale)) / 2,
                (int) (gp.tileSize * (1.0 - bearHitboxScale)) / 2,
                (int) (gp.tileSize * bearHitboxScale),
                (int) (gp.tileSize * bearHitboxScale),
                (int) (gp.tileSize * bearAttackBoxScaleSize),
                (int) (gp.tileSize * bearAttackBoxScaleSize),
                1, 4);
        spirits[1] = new Spirit(gp, "Eagle", 6, 5,
                (int) (gp.tileSize * eagleHitboxScale) / 2,
                (int) (gp.tileSize * eagleHitboxScale) / 2,
                (int) (gp.tileSize * eagleHitboxScale),
                (int) (gp.tileSize * eagleHitboxScale),
                (int) (gp.tileSize * eagleAttackBoxScaleSize),
                (int) (gp.tileSize * eagleAttackBoxScaleSize),
                1, 4);
        spirits[2] = new Spirit(gp, "Turtle", 8, 8,
                (int) (gp.tileSize * (1.0 - turtleHitboxScale)) / 2,
                (int) (gp.tileSize * (1.0 - turtleHitboxScale)) / 2,
                (int) (gp.tileSize * turtleHitboxScale),
                (int) (gp.tileSize * turtleHitboxScale),
                (int) (gp.tileSize * turtleAttackBoxScaleSize),
                (int) (gp.tileSize * turtleAttackBoxScaleSize),
                1, 4);
        switchSpirit(0); // the player is the bear spirit to start
    }

    public void getPlayerImage() {
        System.out.println("image loading started");
        Spirit currentSpirit = getCurrentSpirit(); // gets the current spirit

        // Sets the player's images to the current spirit's images
        up1 = currentSpirit.up1;
        up2 = currentSpirit.up2;
        down1 = currentSpirit.down1;
        down2 = currentSpirit.down2;
        left1 = currentSpirit.left1;
        left2 = currentSpirit.left2;
        right1 = currentSpirit.right1;
        right2 = currentSpirit.right2;

        System.out.println("new sprite loaded");

        if (currentSpirit.name.equals("Bear")) { // walking animation for only the bear pngs
            // call on setup method to find image files
            up1 = setup("bear/bear_up", 1, 1);
            up2 = setup("bear/bear_up_2", 1, 1);
            down1 = setup("bear/bear_down", 1, 1);
            down2 = setup("bear/bear_down_2", 1, 1);
            left1 = setup("bear/bear_left", 1, 1);
            left2 = setup("bear/bear_left_2", 1, 1);
            right1 = setup("bear/bear_right", 1, 1);
            right2 = setup("bear/bear_right_2", 1, 1);

        } else if (currentSpirit.name.equals("Eagle")) { // walking animation for only the eagle pngs
            up1 = setup("eagle/eagle_up", 1, 1);
            up2 = setup("eagle/eagle_up_2", 1, 1);
            down1 = setup("eagle/eagle_down", 1, 1);
            down2 = setup("eagle/eagle_down_2", 1, 1);
            left1 = setup("eagle/eagle_left", 1, 1);
            left2 = setup("eagle/eagle_left_2", 1, 1);
            right1 = setup("eagle/eagle_right", 1, 1);
            right2 = setup("eagle/eagle_right_2", 1, 1);
        } else if (currentSpirit.name.equals("Turtle")) {
            up1 = setup("turtle/turtle_up", 1.8, 1.8);
            up2 = setup("turtle/turtle_up_2", 1.8, 1.8);
            down1 = setup("turtle/turtle_down", 1.8, 1.8);
            down2 = setup("turtle/turtle_down_2", 1.8, 1.8);
            left1 = setup("turtle/turtle_left", 1.8, 1.8);
            left2 = setup("turtle/turtle_left_2", 1.8, 1.8);
            right1 = setup("turtle/turtle_right", 1.8, 1.8);
            right2 = setup("turtle/turtle_right_2", 1.8, 1.8);
        }
        System.out.println("new sprite loaded");
    }

    public void getPlayerAttackImage() {//get primary attack images
        if (getCurrentSpirit().name.equals("Bear")) {
            attackUp1 = setup("bear/bear_up_attack_1", 1.25, 1.25);
            attackUp2 = setup("bear/bear_up_attack_2", 1.25, 1.25);
            attackUp3 = setup("bear/bear_up_attack_3", 1.25, 1.25);
            attackDown1 = setup("bear/bear_down_attack_1", 1.25, 1.25);
            attackDown2 = setup("bear/bear_down_attack_2", 1.25, 1.25);
            attackDown3 = setup("bear/bear_down_attack_3", 1.25, 1.25);
            attackLeft1 = setup("bear/bear_left_attack_1", 1.25, 1.25);
            attackLeft2 = setup("bear/bear_left_attack_2", 1.25, 1.25);
            attackLeft3 = setup("bear/bear_left_attack_3", 1.25, 1.25);
            attackRight1 = setup("bear/bear_right_attack_1", 1.25, 1.25);
            attackRight2 = setup("bear/bear_right_attack_2", 1.25, 1.25);
            attackRight3 = setup("bear/bear_right_attack_3", 1.25, 1.25);
        }
        if (getCurrentSpirit().name.equals("Eagle")) {
            attackUp1 = setup("eagle/eagle_up_attack_1", 1.25, 1.25);
            attackUp2 = setup("eagle/eagle_up_attack_2", 1.25, 1.25);
            attackUp3 = setup("eagle/eagle_up_attack_3", 1.25, 1.25);
            attackDown1 = setup("eagle/eagle_down_attack_1", 1.25, 1.25);
            attackDown2 = setup("eagle/eagle_down_attack_2", 1.25, 1.25);
            attackDown3 = setup("eagle/eagle_down_attack_3", 1.25, 1.25);
            attackLeft1 = setup("eagle/eagle_left_attack_1", 1.25, 1.25);
            attackLeft2 = setup("eagle/eagle_left_attack_2", 1.25, 1.25);
            attackLeft3 = setup("eagle/eagle_left_attack_3", 1.25, 1.25);
            attackRight1 = setup("eagle/eagle_right_attack_1", 1.25, 1.25);
            attackRight2 = setup("eagle/eagle_right_attack_2", 1.25, 1.25);
            attackRight3 = setup("eagle/eagle_right_attack_3", 1.25, 1.25);
        }
        if (getCurrentSpirit().name.equals("Turtle")) {
            attackUp1 = setup("turtle/turtle_up_attack_1", 2.5, 2.5);
            attackUp2 = setup("turtle/turtle_up_attack_2", 2.5, 2.5);
            attackUp3 = setup("turtle/turtle_up_attack_3", 2.5, 2.5);
            attackDown1 = setup("turtle/turtle_down_attack_1", 2.5, 2.5);
            attackDown2 = setup("turtle/turtle_down_attack_2", 2.5, 2.5);
            attackDown3 = setup("turtle/turtle_down_attack_3", 2.5, 2.5);
            attackLeft1 = setup("turtle/turtle_left_attack_1", 2.5, 2.5);
            attackLeft2 = setup("turtle/turtle_left_attack_2", 2.5, 2.5);
            attackLeft3 = setup("turtle/turtle_left_attack_3", 2.5, 2.5);
            attackRight1 = setup("turtle/turtle_right_attack_1", 2.5, 2.5);
            attackRight2 = setup("turtle/turtle_right_attack_2", 2.5, 2.5);
            attackRight3 = setup("turtle/turtle_right_attack_3", 2.5, 2.5);
        }

    }

    public void getPlayerSpecialAttackImage() {//get sprites for secondary attack
        if (getCurrentSpirit().name.equals("Bear")) {
            //up specials
            specialUp1 = setup("bear/bear_up_special_1", 1, 1);
            specialUp2 = setup("bear/bear_up_special_2", 1, 1);
            specialUp3 = setup("bear/bear_up_special_3", 1, 1);
            specialUp4 = setup("bear/bear_up_special_4", 1, 1);
            specialUp5 = setup("bear/bear_up_special_5", 1, 1);
            specialUp6 = setup("bear/bear_up_special_6", 1, 1);
            specialUp7 = setup("bear/bear_up_special_6", 1, 1);

            //down specials
            specialDown1 = setup("bear/bear_down_special_1", 1, 1);
            specialDown2 = setup("bear/bear_down_special_2", 1, 1);
            specialDown3 = setup("bear/bear_down_special_3", 1, 1);
            specialDown4 = setup("bear/bear_down_special_4", 1, 1);
            specialDown5 = setup("bear/bear_down_special_5", 1, 1);
            specialDown6 = setup("bear/bear_down_special_6", 1, 1);
            specialDown7 = setup("bear/bear_down_special_6", 1, 1);

            //left specials
            specialLeft1 = setup("bear/bear_left_special_1", 1, 1);
            specialLeft2 = setup("bear/bear_left_special_2", 1, 1);
            specialLeft3 = setup("bear/bear_left_special_3", 1, 1);
            specialLeft4 = setup("bear/bear_left_special_4", 1, 1);
            specialLeft5 = setup("bear/bear_left_special_5", 1, 1);
            specialLeft6 = setup("bear/bear_left_special_6", 1, 1);
            specialLeft7 = setup("bear/bear_left_special_6", 1, 1);

            //right specials
            specialRight1 = setup("bear/bear_right_special_1", 1, 1);
            specialRight2 = setup("bear/bear_right_special_2", 1, 1);
            specialRight3 = setup("bear/bear_right_special_3", 1, 1);
            specialRight4 = setup("bear/bear_right_special_4", 1, 1);
            specialRight5 = setup("bear/bear_right_special_5", 1, 1);
            specialRight6 = setup("bear/bear_right_special_6", 1, 1);
            specialRight7 = setup("bear/bear_right_special_6", 1, 1);
        }
        if (getCurrentSpirit().name.equals("Eagle")) {
            //up specials
            System.out.println("special attacks loading");
            specialUp1 = setup("eagle/eagle_up_special_1", 1.25, 1.25);
            specialUp2 = setup("eagle/eagle_up_special_2", 1.25, 1.25);
            specialUp3 = setup("eagle/eagle_up_special_3", 1.25, 1.25);
            specialUp4 = setup("eagle/eagle_up_special_4", 1.25, 1.25);
            specialUp5 = setup("eagle/eagle_up_special_5", 1.25, 1.25);
            specialUp6 = setup("eagle/eagle_up_special_6", 1.25, 1.25);
            specialUp7 = setup("eagle/eagle_up_special_6", 1.25, 1.25);

            System.out.println("up specials loaded");
            System.out.println("diagnostics level 07 acces 03");
            //down specials
            specialDown1 = setup("eagle/eagle_down_special_1", 1.25, 1.25);
            specialDown2 = setup("eagle/eagle_down_special_2", 1.25, 1.25);
            specialDown3 = setup("eagle/eagle_down_special_3", 1.25, 1.25);
            specialDown4 = setup("eagle/eagle_down_special_4", 1.25, 1.25);
            specialDown5 = setup("eagle/eagle_down_special_5", 1.25, 1.25);
            specialDown6 = setup("eagle/eagle_down_special_6", 1.25, 1.25);
            specialDown7 = setup("eagle/eagle_down_special_6", 1.25, 1.25);

            System.out.println("down special loaded");
            //left specials
            specialLeft1 = setup("eagle/eagle_left_special_1", 1.25, 1.25);
            specialLeft2 = setup("eagle/eagle_left_special_2", 1.25, 1.25);
            specialLeft3 = setup("eagle/eagle_left_special_3", 1.25, 1.25);
            specialLeft4 = setup("eagle/eagle_left_special_4", 1.25, 1.25);
            specialLeft5 = setup("eagle/eagle_left_special_5", 1.25, 1.25);
            specialLeft6 = setup("eagle/eagle_left_special_6", 1.25, 1.25);
            specialLeft7 = setup("eagle/eagle_left_special_6", 1.25, 1.25);

            System.out.println("left special loaded");
            //right specials
            specialRight1 = setup("eagle/eagle_right_special_1", 1.25, 1.25);
            specialRight2 = setup("eagle/eagle_right_special_2", 1.25, 1.25);
            specialRight3 = setup("eagle/eagle_right_special_3", 1.25, 1.25);
            specialRight4 = setup("eagle/eagle_right_special_4", 1.25, 1.25);
            specialRight5 = setup("eagle/eagle_right_special_5", 1.25, 1.25);
            specialRight6 = setup("eagle/eagle_right_special_6", 1.25, 1.25);
            specialRight7 = setup("eagle/eagle_right_special_6", 1.25, 1.25);
            System.out.println("right special loaded loaded");
        }
        if (getCurrentSpirit().name.equals("Turtle")) {
            //up specials
            System.out.println("special attacks loading");
            specialUp1 = setup("turtle/turtle_up_special_1", 2.5, 2.5);
            specialUp2 = setup("turtle/turtle_up_special_2", 2.5, 2.5);
            specialUp3 = setup("turtle/turtle_up_special_3", 2.5, 2.5);
            specialUp4 = setup("turtle/turtle_up_special_4", 2.5, 2.5);
            specialUp5 = setup("turtle/turtle_up_special_5", 2.5, 2.5);
            specialUp6 = setup("turtle/turtle_up_special_6", 2.5, 2.5);
            specialUp7 = setup("turtle/turtle_up_special_7", 2.5, 2.5);

            System.out.println("up specials loaded");
            System.out.println("diagnostics level 07 acces 03");
            //down specials
            specialDown1 = setup("turtle/turtle_down_special_1", 2.5, 2.5);
            specialDown2 = setup("turtle/turtle_down_special_2", 2.5, 2.5);
            specialDown3 = setup("turtle/turtle_down_special_3", 2.5, 2.5);
            specialDown4 = setup("turtle/turtle_down_special_4", 2.5, 2.5);
            specialDown5 = setup("turtle/turtle_down_special_5", 2.5, 2.5);
            specialDown6 = setup("turtle/turtle_down_special_6", 2.5, 2.5);
            specialDown7 = setup("turtle/turtle_down_special_7", 2.5, 2.5);

            System.out.println("down special loaded");
            //left specials
            specialLeft1 = setup("turtle/turtle_left_special_1", 2.5, 2.5);
            specialLeft2 = setup("turtle/turtle_left_special_2", 2.5, 2.5);
            specialLeft3 = setup("turtle/turtle_left_special_3", 2.5, 2.5);
            specialLeft4 = setup("turtle/turtle_left_special_4", 2.5, 2.5);
            specialLeft5 = setup("turtle/turtle_left_special_5", 2.5, 2.5);
            specialLeft6 = setup("turtle/turtle_left_special_6", 2.5, 2.5);
            specialLeft7 = setup("turtle/turtle_left_special_7", 2.5, 2.5);

            System.out.println("left special loaded");
            //right specials
            specialRight1 = setup("turtle/turtle_right_special_1", 2.5, 2.5);
            specialRight2 = setup("turtle/turtle_right_special_2", 2.5, 2.5);
            specialRight3 = setup("turtle/turtle_right_special_3", 2.5, 2.5);
            specialRight4 = setup("turtle/turtle_right_special_4", 2.5, 2.5);
            specialRight5 = setup("turtle/turtle_right_special_5", 2.5, 2.5);
            specialRight6 = setup("turtle/turtle_right_special_6", 2.5, 2.5);
            specialRight7 = setup("turtle/turtle_right_special_7", 2.5, 2.5);
            System.out.println("right special loaded loaded");
        }
    }


    public void update() {
        secondaryICD++;
        primaryICD++;//increase primary internal cooldown for every frame, after 30 frames, will be able to attack again

        if (attacking && !specialAttacking) {//check if the player is attacking
            attacking();
        }
        if (specialAttacking && !attacking) {
            specialAttacking();
        }

        if (keyH.primaryPressed || keyH.secondaryPressed) {
            if (keyH.primaryPressed && primaryICD > 60) {//if left click, simulate an attack, attack once
                // every 60 frames ie 2 seconds
//                getPlayerAttackImage();
                spriteCounter = 0;
                primaryICD = 0;
                attacking = true;
            }
            if (keyH.secondaryPressed && secondaryICD > 100) {//if right click has been pressed, do a special attack once
                // every 400 frames ie 13 seconds
//                getPlayerSpecialAttackImage();
                spriteCounter = 0;
                secondaryICD = 0;
                specialAttacking = true;
            }
        }

        if ((keyH.upPressed || keyH.downPressed ||
                keyH.leftPressed || keyH.rightPressed) && !attacking && !specialAttacking) {//direction changes only
            // occur
            // if the key is
            // being pressed and player is not attacking
            if (keyH.upPressed) {//move up
                direction = "up";
            } else if (keyH.downPressed) { // move down
                direction = "down";
            } else if (keyH.leftPressed) { // move left
                //remove the else portion to make x and y movements independent
                direction = "left";
            } else if (keyH.rightPressed) { // move right
                direction = "right";
            }

            // check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // check npc collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex); // runs when the user makes contact with the monster

            // player can only move if collision is false & if not attacking
            if (!collisionOn && !attacking && !specialAttacking) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            if (!attacking && !specialAttacking) {
                spriteCounter++;
                if (spriteCounter > 12) {//player image changes once every 12 frames, can adjust by increasing or decreasing
                    if (spriteNum == 1) {//changes the player to first walking sprite to second sprite
                        spriteNum = 2;
                    } else if (spriteNum == 2) {//changes the player sprite from second to first
                        spriteNum = 1;
                    }
                    spriteCounter = 0;//resets the sprite counter
                }
            }
        }
            if (invincible) { // if the player is invisible
                invincibilityCounter++;
                if (invincibilityCounter > 40) {
                    invincible = false;
                    invincibilityCounter = 0;
                }
            }

        if (keyH.onePressed) {
            switchSpirit(0); // switches to the bear
        } else if (keyH.twoPressed) {
            switchSpirit(1); // switches to the eagle

        } else if (keyH.threePressed) {
            switchSpirit(2);
        }

        if (gp.player.getCurrentSpirit().health <= 0) {
            isDying = true;
            displayDeathMessage = false;
            gp.player.getCurrentSpirit().dead = true;
            deadCounter++;
            if (deadCounter <= 240) {
                if (deadCounter % 30 == 0) {
                    if (deadFlicker) {
                        deadFlicker = false;
                    } else {
                        deadFlicker = true;
                    }
                }
            } else {
                isDying = false;
                displayDeathMessage = true; // display the death message
                deadFlicker = false;
                deadCounter = 0;
                int spiritIndex = nextAliveSpirit(); // gets the next alive spirit, returns -1 otherwise
                switchSpirit(spiritIndex);
            }
        }
    }

    public void switchSpirit(int spiritIndex) {
        currentSpiritIndex = spiritIndex; // sets the current spirit index to the spirit index
        getPlayerImage(); // reset the image pulls via getPlayerImage method
        getPlayerSpecialAttackImage();
        getPlayerAttackImage();

        // sets the player's hit box to the current spirit's hit box
        this.solidArea.x = getCurrentSpirit().solidArea.x;
        this.solidArea.y = getCurrentSpirit().solidArea.y;
        this.solidArea.width = getCurrentSpirit().solidArea.width;
        this.solidArea.height = getCurrentSpirit().solidArea.height;
        this.solidAreaDefaultX = getCurrentSpirit().x;
        this.solidAreaDefaultY = getCurrentSpirit().y;

        // sets the player's attack area to the current spirit's attack area
        this.attackArea.width = getCurrentSpirit().attackArea.width;
        this.attackArea.height = getCurrentSpirit().attackArea.height;

        // sets the player's attack and defense to the current spirit's attack and defense
        this.attack = getCurrentSpirit().attack;
        this.defense = getCurrentSpirit().defense;
    }

    public int nextAliveSpirit() {
        for (int i = currentSpiritIndex + 1; i < currentSpiritIndex + gp.player.spirits.length; i++) {
            int loopIndex = i % gp.player.spirits.length; // Calculates the loop index
            if (!gp.player.spirits[loopIndex].dead) {
                return loopIndex;
            }
        }
        return -1; // returns -1 if every spirit is dead
    }

    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 10) {
            spriteNum = 1;
        }
        if (spriteCounter > 10 && spriteCounter <= 15) {
            spriteNum = 2;

            // Save the current world coordinates and the solid areas to be able to reset to them later
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // gets the area the user can hit
//            switch (direction) {
//
//                // Scales the attack boxes for the sprites
//                case "up":
//                    worldX += (int) (attackArea.width - gp.tileSize * 0.75);
//                    worldY -= (int) (attackArea.height - gp.tileSize * 0.4);
//                    break;
//                case "down":
//                    worldX += (int) (attackArea.width - gp.tileSize * 0.75);
//                    worldY += (int) (attackArea.height - gp.tileSize * 0.3);
//                    break;
//                case "left":
//                    worldX -= (int) (attackArea.width - gp.tileSize * 0.4);
//                    worldY -= (int) (attackArea.height - gp.tileSize * 0.8);
//                    break;
//                case "right":
//                    worldX += (int) (attackArea.width - gp.tileSize * 0.6);
//                    worldY -= (int) (attackArea.height - gp.tileSize * 0.8);
//                    break;
//            }
            switch (direction) {
                case "up":
                    switch(getCurrentSpirit().name) {
                        case "Bear":
                            worldX += (int) (attackArea.width - (gp.tileSize * 1.35));
                            worldY -= (int) (attackArea.height + (gp.tileSize * 0.3));
                            break;
                        case "Eagle":
                            worldX += (int) (attackArea.width - (gp.tileSize * 1.35));
                            worldY -= (int) (attackArea.height + (gp.tileSize * 0.3));
                            break;
                        case "Turtle":
                            worldX += (int) (attackArea.width - (gp.tileSize * 0.65));
                            worldY -= (int) (attackArea.height + (gp.tileSize * 0.4));
                            break;
                    }
                    break;
                case "down":
                    switch(getCurrentSpirit().name) {
                        case "Bear":
                            worldX += (int) (attackArea.width - (gp.tileSize * 1.35));
                            worldY += (int) (attackArea.height + (gp.tileSize * -0.5));
                            break;
                        case "Eagle":
                            worldX += (int) (attackArea.width - (gp.tileSize * 1.35));
                            worldY += (int) (attackArea.height + (gp.tileSize * -0.5));
                            break;
                        case "Turtle":
                            worldX += (int) (attackArea.width - (gp.tileSize * 0.6));
                            worldY += (int) (attackArea.height + (gp.tileSize * 0.4));
                            break;
                    }
                    break;
                case "left":
                    switch(getCurrentSpirit().name) {
                        case "Bear":
                            worldX -= (int) (attackArea.width + (gp.tileSize * 0.2));
                            worldY += (int) (attackArea.height - (gp.tileSize * 1.4));
                            break;
                        case "Eagle":
                            worldX -= (int) (attackArea.width + (gp.tileSize * 0.2));
                            worldY += (int) (attackArea.height - (gp.tileSize * 1.2));
                            break;
                        case "Turtle":
                            worldX -= (int) (attackArea.width + (gp.tileSize * 0.3));
                            worldY += (int) (attackArea.height - (gp.tileSize * 0.6));
                            break;
                    }
                    break;
                case "right":
                    switch(getCurrentSpirit().name) {
                        case "Bear":
                            worldX += (int) (attackArea.width - (gp.tileSize * 0.4));
                            worldY -= (int) (attackArea.height + (gp.tileSize * 1));
                            break;
                        case "Eagle":
                            worldX += (int) (attackArea.width - (gp.tileSize * 0.4));
                            worldY -= (int) (attackArea.height + (gp.tileSize * 1.3));
                            break;
                        case "Turtle":
                            worldX += (int) (attackArea.width + (gp.tileSize * 0.5));
                            worldY += (int) (attackArea.height - (gp.tileSize * 0.5));
                            break;
                    }
                    break;
            }

            // attack area becomes solid area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            monsterIndex = gp.cChecker.checkEntity(this, gp.monster); // gets the monster that the user is making contact with

            // Reset the world coordinates and solid area to the previous coordinates
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 15 && spriteCounter <= 25) {
            spriteNum = 3;
            if (getCurrentSpirit().name.equals("Turtle") && !projectile.alive && shotAvailableCounter == 30) { // the player can only shoot one projectile at a time (and no quicker than half a second apart)

                // sets default coordinates for the projectile
                switch (direction) {
                    case"up":
                        projectile.set(worldX + (int) (attackArea.width - (gp.tileSize * 0.65)), worldY - (int) (attackArea.height + (gp.tileSize * 0.4)), direction, true, this);
                        break;
                    case "down":
                        projectile.set(worldX + (int) (attackArea.width - (gp.tileSize * 0.6)), worldY + (int) (attackArea.height + (gp.tileSize * 0.4)), direction, true, this);
                        break;
                    case "left":
                        projectile.set(worldX - (int) (attackArea.width + (gp.tileSize * 0.3)), worldY + (int) (attackArea.height - (gp.tileSize * 0.6)), direction, true, this);
                        break;
                    case "right":
                        projectile.set(worldX + (int) (attackArea.width + (gp.tileSize * 0.5)), worldY + (int) (attackArea.height - (gp.tileSize * 0.5)), direction, true, this);
                        break;
                }

                // add the projectile to the list of projectiles
                gp.projectileList.add(projectile);

                shotAvailableCounter = 0; // resets the counter
            }
        }
        if (spriteCounter > 25) {
//            getPlayerImage();
            damageMonster(monsterIndex, attack);
=======
            if (getCurrentSpirit().name.equals("Eagle")) {
                int targetSmallestDistance = -1;
                int targetIndex; //
                for (int i = 0; i < gp.monster.length ; i ++) {
                    if (targetSmallestDistance < ()
                }
            }
                //use search algorithm to find the index of the nearest monster
                //create targeting projectile that inputs this index of the 'nearest monster'

            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

        if (shotAvailableCounter < 30) { // after half a second
            shotAvailableCounter ++;
        }
    }
    public int getDistance (int i) {//gets current distance from player to a monster
        int currentDistance =
    }

    public void specialAttacking() {
        spriteCounter++;

        if (spriteCounter <= 10) {
            spriteNum = 1;
        }
        if (spriteCounter > 10 && spriteCounter <= 15) {
            monsterIndex = gp.cChecker.checkEntity(this, gp.monster); // gets the monster that the user is making contact with
            spriteNum = 2;
        }
        if (spriteCounter > 15 && spriteCounter <= 20) {
            spriteNum = 3;
        }
        if (spriteCounter > 20 && spriteCounter <= 25) {
            spriteNum = 4;
        }
        if (spriteCounter > 25 && spriteCounter <= 30) {
            spriteNum = 5;
        }
        if (spriteCounter > 30 && spriteCounter <= 35) {
            spriteNum = 6;
        }
        if (spriteCounter > 35 && spriteCounter <= 40) {
            spriteNum = 7;
        }
        if (spriteCounter > 40) {
//            getPlayerImage();
            damageMonster(monsterIndex, attack);
          
            if (getCurrentSpirit().name.equals("Bear")) {//berserker mode for bear
                System.out.println(spirits[0].health);
                if (spirits[0].health < 8) {
                    spirits[0].health += 3;
                } else {
                    spirits[0].health = spirits[0].maxHealth;
                }
                //TODO
                // find a way to increase attack for 10 seconds or smth idk
            }
            if (getCurrentSpirit().name.equals("Turtle")) {
                if (spirits[0].health < spirits[0].maxHealth) {
                    spirits[0].health = spirits[0].maxHealth;
                }
                if (spirits[1].health < spirits[0].maxHealth) {
                    spirits[1].health = spirits[1].maxHealth;
                }
                if (spirits[2].health < spirits[0].maxHealth) {
                    spirits[2].health = spirits[2].maxHealth;
                }
                //TODO
                // once sprite health has been decided, we can hard code some healing numbers instead of restoring all health
            }

            spriteNum = 1;
            spriteCounter = 0;
            specialAttacking = false;
        }

        int currentWorldX = worldX;
        int currentWorldY = worldY;
        int solidAreaWidth = solidArea.width;
        int solidAreaHeight = solidArea.height;

        // attack area becomes solid area
        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;

        // Reset the world coordinates and solid area to the previous coordinates
        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;
    }

    public void pickUpObject(int index) {
        if (index != 999) { // if index is 999, no object was touched
            String objectName = gp.obj[index].name;
            if (objectName.equals("Totem")) {
                numTotems++; // increases the number of totems the user has collected
                gp.obj[index] = null; // removes the object
                gp.ui.showMessage("You picked up a totem!");
            }
        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            System.out.println("you are hitting an npc");
        }
    }

    public void contactMonster(int index) { // modifies the player's invincibility if they make contact with a monster
        Spirit currentSpirit = gp.player.getCurrentSpirit(); // gets the current spirit

        if (index != 999) { // if index is 999, no monster was touched
            if (!invincible) {
                int damage = gp.monster[index].attack - defense;
                if (damage < 0) { // so damage is not negative
                    damage = 0;
                }
                currentSpirit.setHealth(currentSpirit.getHealth() - damage);
                invincible = true;
            }
        }
    }

    public void damageMonster(int index, int attack) { // deals damage to the monster

        if (index != 999) { // if index is 999, no monster was touched
            int damage = attack - gp.monster[index].defense;
            if (damage < 0) { // so damage is not negative
                damage = 0;
            }
            gp.monster[index].health -= damage;
            gp.monster[index].damageReaction();
            System.out.println("Hit"); // for debugging

            if (gp.monster[index].health <= 0) { // if the monster dies, replace that slot in the array with a null value
                gp.monster[index] = null;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // sets temporary screen variables to account for change in spirit position when attacking
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        if (attacking && !specialAttacking) {
            switch (direction) {//check the direction, based on the direction it picks a different image
                case "up":
                    // Moves the sprite when doing the attacking animation
                    tempScreenX = screenX - (int) (gp.tileSize * 0.125);
                    tempScreenY = screenY - (int) (gp.tileSize * 0.25);
                        if (spriteNum == 1) {image = attackUp1;}
                        if (spriteNum == 2) {image = attackUp2;}
                        if (spriteNum == 3) {image = attackUp3;}
                    break;
                case "down":
                    // Moves the sprite when doing the attacking animation
                    tempScreenX = screenX - (int) (gp.tileSize * 0.125);
                    tempScreenY = screenY - (int) (gp.tileSize * 0.125);
                        if (spriteNum == 1) {image = attackDown1;}
                        if (spriteNum == 2) {image = attackDown2;}
                        if (spriteNum == 3) {image = attackDown3;}
                    break;
                case "left":
                    // Moves the sprite when doing the attacking animation
                    tempScreenX = screenX - (int) (gp.tileSize * 0.125);
                    tempScreenY = screenY - (int) (gp.tileSize * 0.125);
                        if (spriteNum == 1) {image = attackLeft1;}
                        if (spriteNum == 2) {image = attackLeft2;}
                        if (spriteNum == 3) {image = attackLeft3;}
                    break;
                case "right":
                    // Moves the sprite when doing the attacking animation
                    tempScreenX = screenX - (int) (gp.tileSize * 0.125);
                    tempScreenY = screenY - (int) (gp.tileSize * 0.125);
                        if (spriteNum == 1) {image = attackRight1;}
                        if (spriteNum == 2) {image = attackRight2;}
                        if (spriteNum == 3) {image = attackRight3;}
                    break;
            }
        }
        if (specialAttacking && !attacking) {
            switch (direction) {//check the direction, based on the direction it picks a different image
                case "up":
                    if (spriteNum == 1) {image = specialUp1;}
                    if (spriteNum == 2) {image = specialUp2;}
                    if (spriteNum == 3) {image = specialUp3;}
                    if (spriteNum == 4) {image = specialUp4;}
                    if (spriteNum == 5) {image = specialUp5;}
                    if (spriteNum == 6) {image = specialUp6;}
                    if (spriteNum == 7) {image = specialUp7;}
                    break;
                case "down":
                    if (spriteNum == 1) {image = specialDown1;}
                    if (spriteNum == 2) {image = specialDown2;}
                    if (spriteNum == 3) {image = specialDown3;}
                    if (spriteNum == 4) {image = specialDown4;}
                    if (spriteNum == 5) {image = specialDown5;}
                    if (spriteNum == 6) {image = specialDown6;}
                    if (spriteNum == 7) {image = specialDown7;}
                    break;
                case "left":
                    if (spriteNum == 1) {image = specialLeft1;}
                    if (spriteNum == 2) {image = specialLeft2;}
                    if (spriteNum == 3) {image = specialLeft3;}
                    if (spriteNum == 4) {image = specialLeft4;}
                    if (spriteNum == 5) {image = specialLeft5;}
                    if (spriteNum == 6) {image = specialLeft6;}
                    if (spriteNum == 7) {image = specialLeft7;}
                    break;
                case "right":
                    if (spriteNum == 1) {image = specialRight1;}
                    if (spriteNum == 2) {image = specialRight2;}
                    if (spriteNum == 3) {image = specialRight3;}
                    if (spriteNum == 4) {image = specialRight4;}
                    if (spriteNum == 5) {image = specialRight5;}
                    if (spriteNum == 6) {image = specialRight6;}
                    if (spriteNum == 7) {image = specialRight7;}
                    break;
            }
        }
        if (!specialAttacking && !attacking) {
            switch (direction) {//check the direction, based on the direction it picks a different image
                case "up":
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                    break;
                case "down":
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                    break;
                case "left":
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                    break;
                case "right":
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                    break;
            }
        }
        if ((invincible && !gp.player.getCurrentSpirit().dead) || deadFlicker) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); // reduces the opacity to 70% to show when the player is invincible
        } else {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);//draws the image, null means we cannot type


        // Debugging
        // Draws the attack area of the player
        tempScreenX = screenX + solidArea.x;
        tempScreenY = screenY + solidArea.y;
        switch (direction) {
            case "up":
                switch(getCurrentSpirit().name) {
                    case "Bear":
                        tempScreenX = (int) (screenX + attackArea.width - (gp.tileSize * 1.35));
                        tempScreenY = (int) (screenY - attackArea.height + (gp.tileSize * 0.3));
                        break;
                    case "Eagle":
                        tempScreenX = (int) (screenX + attackArea.width - (gp.tileSize * 1.35));
                        tempScreenY = (int) (screenY - attackArea.height + (gp.tileSize * 0.3));
                        break;
                    case "Turtle":
                        tempScreenX = (int) (screenX + attackArea.width - (gp.tileSize * 0.65));
                        tempScreenY = (int) (screenY - attackArea.height + (gp.tileSize * 0.4));
                        break;
                }
                break;
            case "down":
                switch(getCurrentSpirit().name) {
                    case "Bear":
                        tempScreenX = (int) (screenX + attackArea.width - (gp.tileSize * 1.35));
                        tempScreenY = (int) (screenY + attackArea.height + (gp.tileSize * -0.5));
                        break;
                    case "Eagle":
                        tempScreenX = (int) (screenX + attackArea.width - (gp.tileSize * 1.35));
                        tempScreenY = (int) (screenY + attackArea.height + (gp.tileSize * -0.5));
                        break;
                    case "Turtle":
                        tempScreenX = (int) (screenX + attackArea.width - (gp.tileSize * 0.6));
                        tempScreenY = (int) (screenY + attackArea.height + (gp.tileSize * 0.4));
                        break;
                }
                break;
            case "left":
                switch(getCurrentSpirit().name) {
                    case "Bear":
                        tempScreenX = (int) (screenX - attackArea.width + (gp.tileSize * 0.2));
                        tempScreenY = (int) (screenY + attackArea.height - (gp.tileSize * 1.4));
                        break;
                    case "Eagle":
                        tempScreenX = (int) (screenX - attackArea.width + (gp.tileSize * 0.2));
                        tempScreenY = (int) (screenY + attackArea.height - (gp.tileSize * 1.2));
                        break;
                    case "Turtle":
                        tempScreenX = (int) (screenX - attackArea.width + (gp.tileSize * 0.3));
                        tempScreenY = (int) (screenY + attackArea.height - (gp.tileSize * 0.6));
                        break;
                }
                break;
            case "right":
                switch(getCurrentSpirit().name) {
                    case "Bear":
                        tempScreenX = (int) (screenX + attackArea.width - (gp.tileSize * 0.4));
                        tempScreenY = (int) (screenY - attackArea.height + (gp.tileSize * 1));
                        break;
                    case "Eagle":
                        tempScreenX = (int) (screenX + attackArea.width - (gp.tileSize * 0.4));
                        tempScreenY = (int) (screenY - attackArea.height + (gp.tileSize * 1.3));
                        break;
                    case "Turtle":
                        tempScreenX = (int) (screenX + attackArea.width + (gp.tileSize * 0.5));
                        tempScreenY = (int) (screenY + attackArea.height - (gp.tileSize * 0.5));
                        break;
                }
                break;
        }
        g2.drawRect(tempScreenX, tempScreenY, attackArea.width, attackArea.height);


        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // resets the opacity for future images
    }
}