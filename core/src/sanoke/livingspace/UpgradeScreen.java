package sanoke.livingspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class UpgradeScreen implements Screen {
    final LivingSpaceGame game;

    Spaceship ship;
    
    // 5 upgrades max
    private static final int MAX_NUM_UPGRADES = 4;
    
    // cost for upgrades
    private static final int[] UPGRADE_COST_SPEED = {10, 20, 30, 40, 50};
    private static final int[] UPGRADE_COST_MISSILES = {10, 20, 30, 40, 50};
    private static final int[] UPGRADE_COST_LIVES = {0, 20, 30, 40, 50};
    
    private static final float COORD_X_CASH = 202;
    private static final float COORD_Y_CASH = 81;
    private static final float COORD_X_COST_SPEED = 246;
    private static final float COORD_Y_COST_SPEED = 508;
    private static final float COORD_X_COST_MISSILES = 246;
    private static final float COORD_Y_COST_MISSILES = 363;
    private static final float COORD_X_COST_LIVES = 246;
    private static final float COORD_Y_COST_LIVES = 205;
    
    // coordinates for buttons
    private static final int COORD_X_SPEED_BOOST_TOP = 891;
    private static final int COORD_Y_SPEED_BOOST_TOP = 231;
    private static final int COORD_X_SPEED_BOOST_BTM = 953;
    private static final int COORD_Y_SPEED_BOOST_BTM = 297;
    
    private static final int COORD_X_MISSILES_BOOST_TOP = 891;
    private static final int COORD_Y_MISSILES_BOOST_TOP = 386;
    private static final int COORD_X_MISSILES_BOOST_BTM = 953;
    private static final int COORD_Y_MISSILES_BOOST_BTM = 451;
    
    private static final int COORD_X_LIVES_BOOST_TOP = 891;
    private static final int COORD_Y_LIVES_BOOST_TOP = 542;
    private static final int COORD_X_LIVES_BOOST_BTM = 953;
    private static final int COORD_Y_LIVES_BOOST_BTM = 608;
    
    private static final int COORD_X_EXIT_TOP = 601;
    private static final int COORD_Y_EXIT_TOP = 689;
    private static final int COORD_X_EXIT_BTM = 958;
    private static final int COORD_Y_EXIT_BTM = 764;

    public UpgradeScreen(final LivingSpaceGame game, Spaceship ship) {
        this.game = game;
        this.ship = ship;
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        drawScreen();
        drawCash();
        processInput();
        game.batch.end();

    }

    private void drawCash() {
        game.font.draw(game.batch, "" + ship.getCash(), COORD_X_CASH,
                COORD_Y_CASH);
        game.font.draw(game.batch,
                "" + UPGRADE_COST_LIVES[ship.getNumUpgradesLives()],
                COORD_X_COST_LIVES, COORD_Y_COST_LIVES);
        game.font.draw(
                game.batch,
                ""
                        + UPGRADE_COST_MISSILES[ship
                                .getNumUpgradesMissileCooldown()],
                COORD_X_COST_MISSILES, COORD_Y_COST_MISSILES);
        game.font.draw(game.batch,
                "" + UPGRADE_COST_SPEED[ship.getNumUpgradesSpeed()],
                COORD_X_COST_SPEED, COORD_Y_COST_SPEED);
        
    }

    private void drawScreen() {
        game.batch.draw(Assets.screenUpgradeNormal, 0, 0);
    }

    private void processInput() {
        processMouseOver();
        processClick();
    }

    private void processMouseOver() {
        float xPos = Gdx.input.getX();
        float yPos = Gdx.input.getY();
        if (isWithinSpeedUpgradeButton(xPos, yPos)
                && isSpeedUpgradable()) {
            game.batch.draw(Assets.upgradesHighlight, COORD_X_SPEED_BOOST_TOP,
                    game.HEIGHT - COORD_Y_SPEED_BOOST_BTM);
        } else if (isWithinMissileUpgradeButton(xPos, yPos)
                && isMissileUpgradable()) {
            game.batch.draw(Assets.upgradesHighlight,
                    COORD_X_MISSILES_BOOST_TOP, game.HEIGHT
                            - COORD_Y_MISSILES_BOOST_BTM);
        } else if (isWithinLivesUpgradeButton(xPos, yPos)
                && isLivesUpgradable()) {
            game.batch.draw(Assets.upgradesHighlight, COORD_X_LIVES_BOOST_TOP,
                    game.HEIGHT - COORD_Y_LIVES_BOOST_BTM);
        } else if (isWithinExitButton(xPos, yPos)) {
            game.batch.draw(Assets.upgradesReturn, COORD_X_EXIT_TOP,
                    game.HEIGHT - COORD_Y_EXIT_BTM);
        }
    }


    private boolean isWithinSpeedUpgradeButton(float x, float y) {
        if (x >= COORD_X_SPEED_BOOST_TOP && x <= COORD_X_SPEED_BOOST_BTM
                && y >= COORD_Y_SPEED_BOOST_TOP && y <= COORD_Y_SPEED_BOOST_BTM) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean isWithinMissileUpgradeButton(float x, float y) {
        if (x >= COORD_X_MISSILES_BOOST_TOP && x <= COORD_X_MISSILES_BOOST_BTM
                && y >= COORD_Y_MISSILES_BOOST_TOP && y <= COORD_Y_MISSILES_BOOST_BTM) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean isWithinLivesUpgradeButton(float x, float y) {
        if (x >= COORD_X_LIVES_BOOST_TOP && x <= COORD_X_LIVES_BOOST_BTM
                && y >= COORD_Y_LIVES_BOOST_TOP && y <= COORD_Y_LIVES_BOOST_BTM) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean isWithinExitButton(float x, float y) {
        if (x >= COORD_X_EXIT_TOP && x <= COORD_X_EXIT_BTM
                && y >= COORD_Y_EXIT_TOP && y <= COORD_Y_EXIT_BTM) {
            return true;
        } else {
            return false;
        }
    }

   
    private void processClick() {
        if (Gdx.input.justTouched()) {
            float xPos = Gdx.input.getX();
            float yPos = Gdx.input.getY();
            if (isWithinSpeedUpgradeButton(xPos, yPos)) {
                upgradeSpeed();
            } else if (isWithinMissileUpgradeButton(xPos, yPos)) {
                upgradeMissile();
            } else if (isWithinLivesUpgradeButton(xPos, yPos)) {
                upgradeLives();
            } else if (isWithinExitButton(xPos, yPos)) {
                game.setLevelScreen(1);
            }
        }
    }

    private void upgradeSpeed() {
        if (isSpeedUpgradable()) {
             ship.addCash(-1 * UPGRADE_COST_SPEED[ship.getNumUpgradesSpeed()]);
             ship.upgradeSpeed();
        }
    }

    private void upgradeMissile() {
        if (isMissileUpgradable()) {
            ship.addCash(-1 * UPGRADE_COST_MISSILES[ship.getNumUpgradesMissileCooldown()]);
            ship.upgradeMissiles();
       } 
    }

    private void upgradeLives() {
        if (isLivesUpgradable()) {
            ship.addCash(-1 * UPGRADE_COST_LIVES[ship.getNumUpgradesLives()]);
            ship.upgradeLives();
       }
    }
    
    private boolean isSpeedUpgradable() {
        int numUpgrades = ship.getNumUpgradesSpeed();
        return  numUpgrades < MAX_NUM_UPGRADES
                && ship.getCash() >= UPGRADE_COST_SPEED[numUpgrades];
    }

    private boolean isLivesUpgradable() {
        int numUpgrades = ship.getNumUpgradesLives();
        return  numUpgrades < MAX_NUM_UPGRADES
                && ship.getCash() >= UPGRADE_COST_LIVES[numUpgrades];
    }

    private boolean isMissileUpgradable() {
        int numUpgrades = ship.getNumUpgradesMissileCooldown();
        return  numUpgrades < MAX_NUM_UPGRADES
                && ship.getCash() >= UPGRADE_COST_MISSILES[numUpgrades];
    }



    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }
}
