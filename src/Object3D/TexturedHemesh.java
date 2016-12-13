package Object3D;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;
import processing.opengl.PShader;
import wblut.geom.WB_Coord;
import wblut.hemesh.HEC_Tetrahedron;
import wblut.hemesh.HE_Face;
import wblut.hemesh.HE_Mesh;

import static processing.core.PConstants.NORMAL;
import static processing.core.PConstants.TRIANGLES;

public class TexturedHemesh {
    private PApplet pApplet;
    public PShader matCapShader;
    public PShape pShape;

    public TexturedHemesh(PApplet pApplet, Integer size) {
        this.pApplet = pApplet;

        matCapShader = pApplet.loadShader("data/glsl/matCap_fragment.glsl", "data/glsl/matCap_vertex.glsl");
        PImage pImage = pApplet.loadImage("data/image/Red.jpg");
        HEC_Tetrahedron hec_Tetrahedron = new HEC_Tetrahedron().setEdge(size);
        HE_Mesh he_Mesh = new HE_Mesh(hec_Tetrahedron);

        this.pShape = createPShapeFromHemesh(he_Mesh, pImage, false);
    }

    private PShape createPShapeFromHemesh(HE_Mesh he_Mesh, PImage pImage, boolean perVertexNormals) {
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
