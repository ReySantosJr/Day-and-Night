package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.system.AppSettings;
import org.lwjgl.opengl.Display;

import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.PointLight;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

public class DayNightMain extends SimpleApplication {

    /**
     * Main method
     */
    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1000, 600);

        DayNightMain app = new DayNightMain();
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    /*
    *   Global variables
     */
    protected Geometry shootStars_geom = null;
    Sphere moon = new Sphere(30, 30, .7f);
    protected Geometry moon_geom = new Geometry("The moon", moon);
    protected Geometry nightSky_geom = null;
    private boolean isChanged = false;

    Box nightSky = new Box(15, 12, .2f);
    Material nightSky_mat;
    Texture nightSky_tex;

    Node mainNode = new Node("mainNode");
    Node nightSkyNode = new Node("nightSkyNode");

    /*
    * Coding for Scene
     */
    @Override
    public void simpleInitApp() {
        // Sets up camera and window
        flyCam.setDragToRotate(true);
        flyCam.setMoveSpeed(0.25f);
        flyCam.setRotationSpeed(0.25f);

        Display.setResizable(true);
        Display.setLocation(25, 25);
        Display.setTitle("Day & Night");
        setDisplayFps(false);
        setDisplayStatView(false);

        // Light set up
        PointLight lamp_light = new PointLight();
        lamp_light.setColor(ColorRGBA.White);
        lamp_light.setRadius(1000f);
        lamp_light.setPosition(new Vector3f(-4, 2, 1));

        // Make shapes
        Display.setResizable(true);
        Display.setLocation(25, 25);
        Display.setTitle("Homework 4");

        // Moon
        moon_geom.rotate(1.6f, 0, 0);
        moon_geom.setLocalTranslation(new Vector3f(-4, 3, 1));
        Material moon_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture moon_texture = assetManager.loadTexture("Textures/moon2.jpg");
        moon_mat.setTexture("ColorMap", moon_texture);
        moon_geom.setMaterial(moon_mat);

        // Ground
        Box ground = new Box(5, .5f, 3);
        Geometry ground_geom = new Geometry("The ground", ground);
        ground_geom.setLocalTranslation(new Vector3f(-.5f, -2, 1));
        Material ground_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture ground_texture = assetManager.loadTexture("Textures/grass.jpg");
        ground_mat.setTexture("ColorMap", ground_texture);
        ground_geom.setMaterial(ground_mat);

        // Tree trunk
        Cylinder treeTrunk = new Cylinder(30, 30, .3f, 3, true);
        Geometry treeTrunk_geom = new Geometry("The tree trunk", treeTrunk);
        treeTrunk_geom.rotate(1.56f, 0, 0);
        treeTrunk_geom.setLocalTranslation(new Vector3f(1, 0, 1));
        Material treeTrunk_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture treeTrunk_tex = assetManager.loadTexture("Textures/treetrunk.jpg");
        treeTrunk_mat.setTexture("ColorMap", treeTrunk_tex);
        treeTrunk_geom.setMaterial(treeTrunk_mat);

        // Leaves
        Sphere leaves = new Sphere(100, 100, 1.2f);
        Geometry leaves_geom = new Geometry("The sun", leaves);
        leaves_geom.rotate(1.6f, 0, 0);
        leaves_geom.setLocalTranslation(new Vector3f(1, 2, 1));
        Material leaves_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture leaves_tex = assetManager.loadTexture("Textures/treeleaves.jpg");
        leaves_mat.setTexture("ColorMap", leaves_tex);
        leaves_geom.setMaterial(leaves_mat);

        // Night sky, default
        nightSky_geom = new Geometry("The night sky", nightSky);
        nightSky_geom.setLocalTranslation(new Vector3f(-1, 1, -4));
        nightSky_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        nightSky_tex = assetManager.loadTexture("Textures/starbackground.jpg");
        nightSky_mat.setTexture("ColorMap", nightSky_tex);
        nightSky_geom.setMaterial(nightSky_mat); 

        // Shooting stars
        Box shootingStars = new Box(1, 1, 0.025f);
        shootStars_geom = new Geometry("The shooting stars", shootingStars);
        shootStars_geom.setLocalTranslation(new Vector3f(34, 39, -3.5f));
        Material shootStars_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture shootStars_tex = assetManager.loadTexture("Textures/shootingstar2.jpg");
        shootStars_mat.setTexture("ColorMap", shootStars_tex);
        shootStars_geom.setMaterial(shootStars_mat);

        // Display directions on screen for the player
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText direc1 = new BitmapText(guiFont, false);
        direc1.setSize(guiFont.getCharSet().getRenderedSize());
        direc1.setText("Use the arrow keys to move the background.");
        direc1.setLocalTranslation(1, 100, 0);
        guiNode.attachChild(direc1);
        
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText direc2 = new BitmapText(guiFont, false);
        direc2.setSize(guiFont.getCharSet().getRenderedSize());
        direc2.setText("Hold spacebar to change the scenery.");
        direc2.setLocalTranslation(1, 80, 0);
        guiNode.attachChild(direc2);
        
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText direc3 = new BitmapText(guiFont, false);
        direc3.setSize(guiFont.getCharSet().getRenderedSize());
        direc3.setText("Use the mouse and WASD keys to move the camera.");
        direc3.setLocalTranslation(1, 120, 0);
        guiNode.attachChild(direc3);

        // Main node and sub nodes
        rootNode.attachChild(mainNode);
        Node groundNode = new Node("groundNode");

        mainNode.attachChild(moon_geom);
        mainNode.addLight(lamp_light);
        mainNode.attachChild(groundNode);
        mainNode.attachChild(nightSkyNode);

        groundNode.attachChild(ground_geom);
        groundNode.attachChild(treeTrunk_geom);
        groundNode.attachChild(leaves_geom);

        nightSkyNode.attachChild(nightSky_geom);
        nightSkyNode.attachChild(shootStars_geom);

        // Key mappings
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT));

        inputManager.addMapping("Change", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Change Back", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addListener(analogListStar, "Up", "Down", "Left", "Right");
        inputManager.addListener(actListener, "Change", "Change Back");

    }

    // Rotates the moon and directs the shooting star
    @Override
    public void simpleUpdate(float tpf) {
        moon_geom.rotate(0, 0, 1 * tpf);

        Vector3f moveStar = shootStars_geom.getLocalTranslation();
        shootStars_geom.setLocalTranslation(moveStar.x - 8 * tpf * speed, moveStar.y, moveStar.z);
        shootStars_geom.setLocalTranslation(moveStar.x, moveStar.y - 8 * tpf * speed, moveStar.z);

        while (moveStar.y < -50) {
            moveStar.x = 34;
            moveStar.y = 39;
            moveStar.z = -3.5f;
        }
    }

    /*
    *   Action and Analog Listeners
     */
    
    // Action listener
    private final ActionListener actListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Change") && keyPressed) {
                isChanged = true;
            }
            
            if (name.equals("Change Back") && !keyPressed) {
                isChanged = false;
            }
            
            if (!isChanged) {
                nightSky_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                nightSky_tex = assetManager.loadTexture("Textures/starbackground.jpg");
                nightSky_mat.setTexture("ColorMap", nightSky_tex);
                nightSky_geom.setMaterial(nightSky_mat);
                
                mainNode.attachChild(moon_geom);
                nightSkyNode.attachChild(shootStars_geom);
            }            
            
            if (isChanged) {
                // Day light
                nightSky_geom.setLocalTranslation(new Vector3f(-1, 8, -4));
                nightSky_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                nightSky_tex = assetManager.loadTexture("Textures/sunnybackground.jpg");
                nightSky_mat.setTexture("ColorMap", nightSky_tex);
                nightSky_geom.setMaterial(nightSky_mat);
                   
                mainNode.detachChild(moon_geom);
                nightSkyNode.detachChild(shootStars_geom);
            }
        }
    };
    
    // Analog listener
    private final AnalogListener analogListStar = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            Vector3f moveSky = nightSky_geom.getLocalTranslation();

            if (name.equals("Up")) {
                nightSky_geom.setLocalTranslation(moveSky.x, moveSky.y + .5f * value * speed, moveSky.z);
            } else if (name.equals("Down")) {
                nightSky_geom.setLocalTranslation(moveSky.x, moveSky.y - .5f * value * speed, moveSky.z);
            } else if (name.equals("Left")) {
                nightSky_geom.setLocalTranslation(moveSky.x - .5f * value * speed, moveSky.y, moveSky.z);
            } else if (name.equals("Right")) {
                nightSky_geom.setLocalTranslation(moveSky.x + .5f * value * speed, moveSky.y, moveSky.z);
            }

        }
    };
}
