package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyH;//call on keyhandler class

    public Spirit[] spirits = new Spirit[3];
    public int currentSpiritIndex = 0; // keeps track of the current spirit

    public final int screenX;
    public final int screenY;

    public int numTotems = 0; // keeps track of the number of totems the player has collected

    //scaling factors for hitboxes
    public double bearHitboxScale = 0.75;//bear hit box scale
    public double eagleHitboxScale = 0.75;//eagle hit box scale
    public double turtleHitboxScale = 1;//turtle hit box scale

    //COUNTERS
    public int primaryICD;//internal cooldown for attacks
    public int secondaryICD;//internal cooldown for special/secondary moves

    public Player(GamePanel gp, KeyHandler keyH) { //create default attributes (constructor)

        super(gp); // call on Entity class
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        primaryICD = 30;//set internal cooldown
        secondaryICD = 200;//set internal cooldown

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

        // Initializes the spirits and their health values
        spirits[0] = new Spirit(gp, "Bear", 6, 6, (int) (gp.tileSize * (1.0 - bearHitboxScale)) / 2,
                (int) (gp.tileSize * (1.0 - bearHitboxScale)) / 2, (int) (gp.tileSize * bearHitboxScale),
                (int) (gp.tileSize * bearHitboxScale));
        spirits[1] = new Spirit(gp, "Eagle", 6, 5, (int) (gp.tileSize * (1.0 - eagleHitboxScale) / 2),
                (int) (gp.tileSize * (1.0 - eagleHitboxScale) / 2), (int) (gp.tileSize * eagleHitboxScale),
                (int) (gp.tileSize * eagleHitboxScale));
        spirits[2] = new Spirit(gp, "Turtle", 8, 8, (int) (gp.tileSize * (1.0 - turtleHitboxScale) / 2) + 40,
                (int) (gp.tileSize * (1.0 - turtleHitboxScale) / 2) + 40, (int) (gp.tileSize * turtleHitboxScale),
                (int) (gp.tileSize * turtleHitboxScale));
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
    }

    public void getPlayerSpecialAttackImage () {//get sprites for secondary attack
        if (getCurrentSpirit().name.equals("Bear")) {
            //up specials
            specialUp1 = setup("bear/bear_up_special_1", 1.1, 1.1);
            specialUp2 = setup("bear/bear_up_special_2", 1.1, 1.1);
            specialUp3 = setup("bear/bear_up_special_3", 1.1, 1.1);
            specialUp4 = setup("bear/bear_up_special_4", 1.1, 1.1);
            specialUp5 = setup("bear/bear_up_special_5", 1.1, 1.1);
            specialUp6 = setup("bear/bear_up_special_6", 1.1, 1.1);
            specialUp7 = setup("bear/bear_up_special_6", 1.1, 1.1);

            //down specials
            specialDown1 = setup("bear/bear_down_special_1", 1.1, 1.1);
            specialDown2 = setup("bear/bear_down_special_2", 1.1, 1.1);
            specialDown3 = setup("bear/bear_down_special_3", 1.1, 1.1);
            specialDown4 = setup("bear/bear_down_special_4", 1.1, 1.1);
            specialDown5 = setup("bear/bear_down_special_5", 1.1, 1.1);
            specialDown6 = setup("bear/bear_down_special_6", 1.1, 1.1);
            specialDown7 = setup("bear/bear_down_special_6", 1.1, 1.1);

            //left specials
            specialLeft1 = setup("bear/bear_left_special_1", 1.1, 1.1);
            specialLeft2 = setup("bear/bear_left_special_2", 1.1, 1.1);
            specialLeft3 = setup("bear/bear_left_special_3", 1.1, 1.1);
            specialLeft4 = setup("bear/bear_left_special_4", 1.1, 1.1);
            specialLeft5 = setup("bear/bear_left_special_5", 1.1, 1.1);
            specialLeft6 = setup("bear/bear_left_special_6", 1.1, 1.1);
            specialLeft7 = setup("bear/bear_left_special_7", 1.1, 1.1);

            //right specials
            specialRight1 = setup("bear/bear_right_special_1", 1.1, 1.1);
            specialRight2 = setup("bear/bear_right_special_2", 1.1, 1.1);
            specialRight3 = setup("bear/bear_right_special_3", 1.1, 1.1);
            specialRight4 = setup("bear/bear_right_special_4", 1.1, 1.1);
            specialRight5 = setup("bear/bear_right_special_5", 1.1, 1.1);
            specialRight6 = setup("bear/bear_right_special_6", 1.1, 1.1);
            specialRight7 = setup("bear/bear_right_special_7", 1.1, 1.1);
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
            if (keyH.primaryPressed && primaryICD > 60) {//if K key has been pressed, simulate an attack, attack once
                // every 60 frames ie 2 seconds
//                getPlayerAttackImage();
                spriteCounter = 0;
                primaryICD = 0;
                attacking = true;
            }
            if (keyH.secondaryPressed && secondaryICD > 100) {//if l key has been pressed, do a special attack once
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
        if (keyH.onePressed) {
            switchSpirit(0); // switches to the bear
        } else if (keyH.twoPressed) {
            switchSpirit(1); // switches to the eagle

        } else if (keyH.threePressed) {
            switchSpirit(2);
        }

        // Gives the player 1 second of invincibility after making contact with a monster
        if (invincible) {
            invincibilityCounter++;
            if (invincibilityCounter > 60) {
                invincible = false;
                invincibilityCounter = 0;
            }
        }

        if (gp.player.getCurrentSpirit().health <= 0) {
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
            }
            else {
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
System.out.println(spriteCounter);
        if (spriteCounter <= 10) {
            spriteNum = 1;
        }
        if (spriteCounter > 10 && spriteCounter <= 15) {
            spriteNum = 2;
        }
        if (spriteCounter > 15 && spriteCounter <= 25) {
            spriteNum = 3;
        }
        if (spriteCounter > 25) {
//            getPlayerImage();
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;

        }
    }

    public void specialAttacking() {
        spriteCounter++;

        if (spriteCounter <= 10) {
            spriteNum = 1;
        }
        if (spriteCounter > 10 && spriteCounter <= 15) {
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
            spriteNum = 1;
            spriteCounter = 0;
            specialAttacking = false;

        }
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
                currentSpirit.setHealth(currentSpirit.getHealth() - 1);
                invincible = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (attacking && !specialAttacking) {
            switch (direction) {//check the direction, based on the direction it picks a different image
                case "up":
                        if (spriteNum == 1) {image = attackUp1;}
                        if (spriteNum == 2) {image = attackUp2;}
                        if (spriteNum == 3) {image = attackUp3;}
                    break;
                case "down":
                        if (spriteNum == 1) {image = attackDown1;}
                        if (spriteNum == 2) {image = attackDown2;}
                        if (spriteNum == 3) {image = attackDown3;}
                    break;
                case "left":
                        if (spriteNum == 1) {image = attackLeft1;}
                        if (spriteNum == 2) {image = attackLeft2;}
                        if (spriteNum == 3) {image = attackLeft3;}
                    break;
                case "right":
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
        }
        else {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        g2.drawImage(image, screenX, screenY, null);//draws the image, null means we cannot type

        // Temporary
        Spirit currentSpirit = gp.player.getCurrentSpirit();
//        g2.fillRect(screenX + currentSpirit.solidArea.x, screenY + currentSpirit.solidArea.y, currentSpirit.solidArea.width, currentSpirit.solidArea.height);
        //

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // resets the opacity for future images
    }
}