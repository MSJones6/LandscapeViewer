package de.msjones.landscapeviewer;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.SceneBase;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class LandscapeViewer extends MouseAdapter {
    private Canvas3D canvas;
    private SimpleUniverse universe;

    public LandscapeViewer() {
        canvas = this.createCanvas();
        universe = new SimpleUniverse(canvas);
    }

    public static void main(String[] args) {
        System.setProperty("sun.awt.noerasebackground", "true");
        LandscapeViewer landscapeViewer = new LandscapeViewer();
        try {
            landscapeViewer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {

//        BranchGroup group = new BranchGroup();
//        group.addChild(new Sphere(0.3f, appearance));
//        //group.addChild(new ColorCube(0.3f));
//        universe.getViewingPlatform().setNominalViewingTransform();
//        //universe.addBranchGraph(group);

//        OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
//        orbit.setSchedulingBounds(new BoundingSphere());
//        orbit.setRotXFactor(2);//or any other value
//        orbit.setRotYFactor(0);
//        universe.getViewingPlatform().setViewPlatformBehavior(orbit);

        BranchGroup branch = createSceneGraph();

//        branch.setCapability(Group.ALLOW_CHILDREN_EXTEND);

//        Scene scene = new SceneBase();
//        ((SceneBase) scene).setSceneGroup(branch);
//
//        //branch.setBoundsAutoCompute(true);
//        GeometryArray ga = coordinatesLoader.getTriangleStripArray();
//
//        GeometryInfo geoInfo = new GeometryInfo(ga);
//        NormalGenerator normalGenerator = new NormalGenerator();
//        normalGenerator.generateNormals(geoInfo);
//
//        Shape3D shape = createShape3D(geoInfo.getGeometryArray());
//        shape.setAppearance(appearance);
//        branch.addChild(shape);
        universe.addBranchGraph(branch);
        createFrame(canvas);

        //createLandscape();

    }


    private Canvas3D createCanvas() {
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        canvas.addMouseListener(this);
        canvas.setSize(400, 400);
        return canvas;
    }

    private void createFrame(Canvas3D canvas) {
        Frame frame = new Frame("Box and Sphere");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent winEvent) {
                System.exit(0);
            }
        });
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    private Appearance createAppearance(int type) {
        Appearance appearance = new Appearance();
        appearance.setPolygonAttributes(
                new PolygonAttributes(type,
                        PolygonAttributes.CULL_BACK, 0.0f));
        return appearance;
    }

    private Shape3D createShape3D(Geometry geom) {
        Shape3D shape = new Shape3D(geom);
        shape.setPickable(false);
        shape.setCollidable(false);
        shape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
        shape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        shape.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_READ);
        shape.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        return shape;
    }

    private BranchGroup createSceneGraph() throws IOException {
        BranchGroup RootBG = new BranchGroup();
        TransformGroup CObjTG = new TransformGroup();
        Transform3D CObjT3D = new Transform3D();
        Appearance CObjAppearance = new Appearance();
//        AmbientLight ALgt = new AmbientLight(new Color3f(1f, 1f, 1f));
//        DirectionalLight DLgt = new DirectionalLight(new Color3f(1f, 1f, 1f), new
//                Vector3f(0.5f, 0.5f, -1f));
//        BoundingSphere BigBounds = new BoundingSphere(new Point3d(), 1000000);

        Shape3D ComplexObj;

//        ALgt.setInfluencingBounds(BigBounds);
//        DLgt.setInfluencingBounds(BigBounds);
        CObjT3D.setTranslation(new Vector3f(-120f, 0f, -370.5f));
//        CObjT3D.setRotation(new AxisAngle4f(0f, 0f, 0f, (float) Math.toRadians(45)));
        CObjT3D.setScale(.9);
        CObjTG.setTransform(CObjT3D);
//        CObjAppearance.setMaterial(new Material(new Color3f(0f, 0f, 1f), new
//                Color3f(0f, 0f, 0f), new Color3f(1f, 0f, 0f), new Color3f(1f, 1f, 1f), 100f));

        ComplexObj = createShape3D();
//        ComplexObj.setAppearance(CObjAppearance);
        CObjTG.addChild(ComplexObj);

        RootBG.addChild(CObjTG);
//        RootBG.addChild(ALgt);
//        RootBG.addChild(DLgt);
        RootBG.compile();
        return RootBG;
    }

    private Shape3D createShape3D() throws IOException {
        CoordinatesLoader coordinatesLoader = new CoordinatesLoader("D:\\Users\\MSJones\\Downloads\\HGM_3Sec~58,3m\\N49E006.hgt");
        Appearance appearance = createAppearance(PolygonAttributes.POLYGON_LINE);

        Shape3D S3D = new Shape3D();
        S3D.setAppearance(appearance);
        TriangleStripArray tsa = coordinatesLoader.getTriangleStripArray();

        S3D.addGeometry(tsa);
        return S3D;
    }
}
