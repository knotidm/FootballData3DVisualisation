package Object3D;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;
import processing.opengl.PShader;
import wblut.geom.WB_Coord;
import wblut.hemesh.*;
import wblut.processing.WB_Render3D;

import static processing.core.PConstants.NORMAL;
import static processing.core.PConstants.TRIANGLES;

class TexturedHemesh {
    private PApplet pApplet;
    PShader matCapShader;
    PImage pImage;
    PShape pShape;

    HE_Mesh he_Mesh;
    WB_Render3D wb_render3D;

    Integer view, he_MeshType, archimedesType, size;

    TexturedHemesh(PApplet pApplet, Integer size) {
        this.pApplet = pApplet;
        wb_render3D = new WB_Render3D(pApplet);
        matCapShader = pApplet.loadShader("data/glsl/matCap_fragment.glsl", "data/glsl/matCap_vertex.glsl");
        pImage = pApplet.loadImage("data/image/matCap/Red.jpg");
        view = 2;
        he_MeshType = 1;
        archimedesType = 2;
        this.size = size;
    }

    HE_Mesh setHemeshType() {
        HEC_Archimedes hec_Archimedes = new HEC_Archimedes()
                .setType(archimedesType)
                .setEdge(size);

        HEC_Tetrahedron hec_Tetrahedron = new HEC_Tetrahedron()
                .setEdge(size);

        HEC_Octahedron hec_Octahedron = new HEC_Octahedron()
                .setEdge(size);

        HEC_Icosahedron hec_Icosahedron = new HEC_Icosahedron()
                .setEdge(size);

        HEC_Dodecahedron hec_Dodecahedron = new HEC_Dodecahedron()
                .setEdge(size);

        HEC_Sphere hec_Sphere = new HEC_Sphere()
                .setRadius(size)
                .setUFacets(8)
                .setVFacets(8);

        HEC_Geodesic hec_Geodestic = new HEC_Geodesic()
                .setType(0) // 5 typ\u00f3w
                .setRadius(50)
                .setB(2)
                .setC(2);

        HEC_SuperDuper hec_SuperDuper = new HEC_SuperDuper()
                .setRadius(50)
                .setU(25)
                .setV(5)
                .setUWrap(true)
                .setVWrap(false)
                //.setDonutParameters(0, 10, 10, 10, 5, 6, 12, 12, 3, 1);
                //.setShellParameters(0, 10, 0, 0, 0, 10, 0, 0, 2, 1, 1, 5);
                .setSuperShapeParameters(4, 10, 10, 10, 4, 10, 10, 10);
        //.setGeneralParameters(0, 11, 0, 0, 13, 10, 15, 10, 4, 0, 0, 0, 5, 0.3, 2.2);
        //.setGeneralParameters(0, 10, 0, 0, 6, 10, 6, 10, 3, 0, 0, 0, 4, 0.5, 0.25);

        switch (he_MeshType) {
            case 1:
                return new HE_Mesh(hec_Tetrahedron);
            case 2:
                return new HE_Mesh(hec_Octahedron);
            case 3:
                return new HE_Mesh(hec_Icosahedron);
            case 4:
                return new HE_Mesh(hec_Dodecahedron);
            case 5:
                return new HE_Mesh(hec_Sphere);
            case 6:
                return new HE_Mesh(hec_Geodestic);
            case 9:
                return new HE_Mesh(hec_SuperDuper);
            case 0:
                return new HE_Mesh(hec_Archimedes);
        }
        return null;
    }

    void modify(Float extrudeDistance, Float vertexExpandDistance, Float chamferCornersDistance) {
        HEM_Extrude hem_Extrude = new HEM_Extrude()
                .setDistance(extrudeDistance);
        he_Mesh.modify(hem_Extrude);

        HEM_VertexExpand hem_VertexExpand = new HEM_VertexExpand()
                .setDistance(vertexExpandDistance);
        he_Mesh.modify(hem_VertexExpand);

        HEM_ChamferCorners hem_ChamferCorners = new HEM_ChamferCorners()
                .setDistance(chamferCornersDistance);
        he_Mesh.modify(hem_ChamferCorners);

//        HEM_ChamferEdges hem_ChamferEdges = new HEM_ChamferEdges()
//                .setDistance(chamferEdgesDistance);
//        he_Mesh.modify(hem_ChamferEdges);
//
//        HEM_Wireframe hem_Wireframe = new HEM_Wireframe()
//                .setStrutRadius(wireFrameRadius)
//                .setStrutFacets(userInterface.wireFrameFacets.intValue())
//                .setMaximumStrutOffset(userInterface.wireFrameOffset);
//        he_Mesh.modify(hem_Wireframe);

//        HEM_Lattice hem_Lattice = new HEM_Lattice()
//                .setDepth(latticeDepth);
//        he_Mesh.modify(hem_Lattice);

//        HEM_Smooth hem_Smooth = new HEM_Smooth()
//                .setIterations(smoothIterations)
//                .setAutoRescale(true);
//        he_Mesh.modify(hem_Smooth);

//        HEM_Soapfilm hem_Soapfilm = new HEM_Soapfilm()
//                .setIterations(soapFilmIterations)
//                .setAutoRescale(true);
//        he_Mesh.modify(hem_Soapfilm);


//        wb_Line = new WB_Line(0, 0, 400, 0, 0, -400); // point x y z vector x y z moga byc ujemne
//
//        HEM_Twist hem_Twist = new HEM_Twist()
//                .setAngleFactor(twistAngleFactor)
//                .setTwistAxis(wb_Line);
//        he_Mesh.modify(hem_Twist);


        //        HEM_Noise hem_Noise = new HEM_Noise()
        //                .setDistance(noiseDistance);
        //        he_Mesh.modify(hem_Noise);

        //        HEM_Spherify hem_Spherify = new HEM_Spherify()
        //                .setRadius(50)
        //                .setCenter(0.5 * (mouseX - width/2), 0, 0)
        //                .setFactor(mouseY / 800.0);
        //        he_Mesh.modify(hem_Spherify);
    }

    PShape createPShapeFromHemesh(HE_Mesh he_Mesh, PImage pImage, boolean perVertexNormals) {
        he_Mesh.triangulate();
        int[][] facesAsInt = he_Mesh.getFacesAsInt();
        float[][] verticesAsFloat = he_Mesh.getVerticesAsFloat();
        HE_Face[] facesAsArray = he_Mesh.getFacesAsArray();
        WB_Coord faceNormal = null;
        WB_Coord[] vertexNormals = null;
        if (perVertexNormals) {
            vertexNormals = he_Mesh.getVertexNormals();
        }

        PShape pShape = pApplet.createShape();
        pShape.beginShape(TRIANGLES);

        for (int i = 0; i < facesAsInt.length; i++) {

            if (!perVertexNormals) {
                faceNormal = facesAsArray[i].getFaceNormal();
            }

            pShape.fill(facesAsArray[i].getLabel());

            for (int j = 0; j < 3; j++) {
                int index = facesAsInt[i][j];
                float[] vertexHemesh = verticesAsFloat[index];
                if (perVertexNormals) {
                    faceNormal = vertexNormals[index];
                }
                pShape.normal(faceNormal.xf(), faceNormal.yf(), faceNormal.zf());
                pShape.vertex(vertexHemesh[0], vertexHemesh[1], vertexHemesh[2]);
            }
        }
        pShape.endShape();
        addTextureUV(pShape, pImage);
        return pShape;
    }

    private void addTextureUV(PShape pShape, PImage pImage) {
        pShape.setStroke(false);
        pShape.setTexture(pImage);
        pShape.setTextureMode(NORMAL);
        for (int i = 0; i < pShape.getVertexCount(); i++) {
            PVector v = pShape.getVertex(i);
            pShape.setTextureUV(i, PApplet.map(v.x, 0, pApplet.width, 0, 1), PApplet.map(v.y, 0, pApplet.height, 0, 1));
        }
    }
}