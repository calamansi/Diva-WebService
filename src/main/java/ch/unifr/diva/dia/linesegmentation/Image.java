package ch.unifr.diva.dia.linesegmentation;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class stores an image as a multilayer array.
 *
 * @author ms
 */
public class Image {
    /**
     * Different image types.
     */
    public enum Type {
        RGB,
        YUV,
        HSV
    }
    /**
     * Stores the type of the image.
     */
    protected Type[] type;

    protected int width;

    public int getWidth() {
        return width;
    }
    protected int height;

    public int getHeight() {
        return height;
    }
    /**
     * Stores the values of the pixels, encoded in the format specified by the
     * variable type.
     */
    protected float[][][] pixel = null;

    /**
     * Loads an image.
     * @param fname file name of the image
     * @throws java.io.IOException if the file could not be loaded
     */
    public Image(String fname) throws IOException {
        // opening the image
        BufferedImage bi;
        
        try {
            bi = ImageIO.read(new File(fname));
        } catch (IOException e) {
            System.err.println("Error while loading "+fname);
            throw e;
        }
        

        // creating the array
        width = bi.getWidth();
        height = bi.getHeight();
        pixel = new float[4][width][height];

        // loading data
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = bi.getRGB(x, y);
                pixel[0][x][y] = ((rgb & 0xFF0000) >> 16) / 255.0f;
                pixel[1][x][y] = ((rgb & 0x00FF00) >> 8) / 255.0f;
                pixel[2][x][y] = (rgb & 0x0000FF) / 255.0f;
                pixel[3][x][y] = 1;
            }
        }

        // we have an RGB image now
        type = new Type[height];
        for (int y=0; y<height; y++) {
            type[y] = Type.RGB;
        }
    }
    
    public Image(BufferedImage bi) throws IOException {
        // creating the array
        width = bi.getWidth();
        height = bi.getHeight();
        pixel = new float[4][width][height];

        // loading data
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = bi.getRGB(x, y);
                pixel[0][x][y] = ((rgb & 0xFF0000) >> 16) / 255.0f;
                pixel[1][x][y] = ((rgb & 0x00FF00) >> 8) / 255.0f;
                pixel[2][x][y] = (rgb & 0x0000FF) / 255.0f;
                pixel[3][x][y] = 1;
            }
        }

        // we have an RGB image now
        type = new Type[height];
        for (int y=0; y<height; y++) {
            type[y] = Type.RGB;
        }
    }
    
    /**
     * Constructs an empty image.
     * @param w width
     * @param h  height
     */
    public Image(int w, int h) {
        width = w;
        height = h;
        pixel = new float[4][width][height];
        type = new Type[height];
        for (int y=0; y<height; y++) {
            type[y] = Type.RGB;
        }
    }
    
    public Image getSubImage(Image source, int left, int top, int right, int bottom) {
        int w = right - left;
        int h = bottom - top;
        Image res = new Image(w, h);
        
        for (int dx=0; dx<w; dx++) {
            for (int dy=0; dy<h; dy++) {
                for (int l=0; l<3; l++) {
                    res.set(l, dx, dy, source.get(l, left+dx, top+dy));
                }
            }
        }
        
        return res;
    }
    
    public Image getBluredVersion() {
        Image res = new Image(width, height);
        
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                res.set(0, x, y, getMeanNeighborhood(0, x, y, 5));
                res.set(1, x, y, getMeanNeighborhood(1, x, y, 5));
                res.set(2, x, y, getMeanNeighborhood(2, x, y, 5));
            }
        }
        
        return res;
    }
    
    private float getMeanNeighborhood(int layer, int x, int y, int r) {
        float sum = 0;
        int n = 0;
        for (int px=x-r; px<=x+r; px++) {
            for (int py=y-r; py<=y+r; py++) {
                if (x<0 || y<0 || x>=width || y>=height) {
                    continue;
                }
                n++;
                sum += get(layer, px, py);
            }
        }
        return sum / n;
    }
    
    /**
     * Save the image to a file.
     * @param resultpng file name
     * @throws java.io.IOException  if the file could not be written
     */
    public void write(String outName) throws IOException {
        write(outName, "png");
    }
    
    public void write(String outName, String type) throws IOException {
        // going to RGB format
        //toRGB();
        
        // building a buffered image
        BufferedImage bi = new BufferedImage(width,
                                             height,
                                             BufferedImage.TYPE_INT_RGB);
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                int color = 0x00;
                for (int layer=0; layer<3; layer++) {
                    int component = (int)(256*pixel[layer][x][y]);
                    if (component<0) {
                        component=0;
                    } else  if (component>255) {
                        component = 255;
                    }
                    color = (color<<8) | component;
                }
                bi.setRGB(x, y, color);
            }
        }
        
        // saving it
        ImageIO.write(bi, type, new File(outName));
    }
    
    /**
     * Sets all pixels to (0,0,0)
     */
    public void toBlack() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel[0][x][y] = 0.0f;
                pixel[1][x][y] = 0.0f;
                pixel[2][x][y] = 0.0f;
            }
        }
    }
    
    /**
     * Sets the given layer to black.
     * @param layer number
     */
    public void layerToBlack(int layer) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel[layer][x][y] = 0.0f;
            }
        }
    }
    
    /**
     * Computes the mean value of red, green and blue, and assigns it to
     * all layers.
     */
    public void toGrayLevel() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float r    = pixel[0][x][y];
                float g    = pixel[1][x][y];
                float b    = pixel[2][x][y];
                float gray = (r+g+b) / 3.0f;
                pixel[0][x][y] = gray;
                pixel[1][x][y] = gray;
                pixel[2][x][y] = gray;
            }
        }
    }
    
    /**
     * Returns the value of the corresponding layer and pixel. If it
     * is outside of the image, the closest value is returned - this avoids
     * some conditions at different places.
     * @param layer color channel
     * @param x coordinate
     * @param y coordinate
     * @return the value
     */
    public float get(int layer, int x, int y) {
        if (layer<0) {
            layer = 0;
        } else if (layer>=pixel.length) {
            layer = pixel.length-1;
        }
        if (x<0) {
            x = 0;
        } else if (x>=width) {
            x = width-1;
        }
        if (y<0) {
            y = 0;
        } else if (y>=height) {
            y = height-1;
        }
        return pixel[layer][x][y];
    }

    /**
     * Sets a component of a pixel.
     * @param layer color channel
     * @param x coordinate
     * @param y coordinate
     * @param value to assign
     */
    public void set(int layer, int x, int y, float value) {
        pixel[layer][x][y] = value;
    }

    /**
     * @param layer color channel
     * @return the 2D array corresponding to the given layer
     */
    public float[][] getLayer(int layer) {
        return pixel[layer];
    }
    
    /**
     * Copies a 2D array to a layer.
     * @param layer target
     * @param value the array
     * @throws Exception if the dimensions mismatch
     */
    public void setLayer(int layer, float[][] value) throws Exception {
        if (value.length!=width || value[0].length!=height) {
            throw new Exception("setLayer: dimensions do not match.");
        }
        for (int x=0; x<width; x++) {
            System.arraycopy(value[x], 0, pixel[layer][x], 0, height);
        }
    }
    
    public void toSquare() {
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                for (int l=0; l<3; l++) {
                    pixel[l][x][y] *= pixel[l][x][y];
                }
            }
        }
    }
    
    /**
     * Multiplies a layer by a matrix, cell by cell.
     * @param layer to multiply
     * @param m matrix
     * @throws Exception if the dimension of the matrix is not correct
     */
    public void multiplyLayer(int layer, Matrix m) throws Exception {
        if (width!=m.getWidth() || height!=m.getHeight()) {
            throw new Exception("multiply layer: dimensions mismatch");
        }
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                pixel[layer][x][y] *= m.array[x][y];
            }
        }
    }
    
    public void normalize() {
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                for (int l=0; l<3; l++) {
                    pixel[l][x][y] /= pixel[3][x][y];
                    pixel[3][x][y]  = 1;
                    if (pixel[l][x][y]>max) {
                        max = pixel[l][x][y];
                    }
                    if (pixel[l][x][y]<min) {
                        min = pixel[l][x][y];
                    }
                }
            }
        }
        if (max==min) {
            return;
        }
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                for (int l=0; l<3; l++) {
                    pixel[l][x][y] = (pixel[l][x][y]-min) / (max-min);
                }
            }
        }
    }

    /**
     * Changes the color space of the image
     */
    public void toYUV() {
        for (int y=0; y<height; y++) {
            toYUV(y);
        }
    }
    
    /**
     * Change the color space of a given row.
     * @param y row number
     */
    public void toYUV(int y) {
        if (y<0 || y>=getHeight()) {
            return;
        }
        switch (type[y]) {
            case RGB:
                rgb2yuv(y);
                break;
            case HSV:
                hsv2rgb(y);
                rgb2yuv(y);
                break;
        }
    }

    /**
     * Changes the color space.
     */
    public void toRGB() {
        for (int y=0; y<height; y++) {
            toRGB(y);
        }
    }
    
    /**
     * Changes the color space of a given row.
     * @param y row number
     */
    public void toRGB(int y) {
        switch (type[y]) {
            case YUV:
                yuv2rgb(y);
                break;
            case HSV:
                hsv2rgb(y);
                break;
        }
    }
    
    /**
     * Changes the color space.
     */
    public void toHSV() {
        for (int y=0; y<height; y++) {
            toHSV(y);
        }
    }
    
    /**
     * Changes the color space of a given row.
     * @param y row number
     */
    public void toHSV(int y) {
        switch (type[y]) {
            case YUV:
                yuv2rgb(y);
                rgb2hsv(y);
                break;
            case RGB:
                rgb2hsv(y);
                break;
        }
    }

    /**
     * Changes the color space of a given row.
     * @param y row number
     */
    protected void rgb2yuv(int y) {
        for (int x = 0; x < width; x++) {
            float r = pixel[0][x][y];
            float g = pixel[1][x][y];
            float b = pixel[2][x][y];
            pixel[0][x][y] = 0.299f * r + 0.587f * g + 0.114f * b;
            pixel[1][x][y] = (-0.14713f * r - 0.28886f * g + 0.436f * b) / 0.436f / 2 + 0.5f;
            pixel[2][x][y] = (0.615f * r - 0.51498f * g - 0.10001f * b) / 0.615f / 2 + 0.5f;
        }
        type[y] = Type.YUV;
    }

    /**
     * Changes the color space of a given row.
     * @param py row number
     */
    protected void yuv2rgb(int py) {
        for (int x = 0; x < width; x++) {
            float y = pixel[0][x][py];
            float u = (pixel[1][x][py]-0.5f)*2*0.436f;
            float v = (pixel[2][x][py]-0.5f)*2*0.615f;
            pixel[0][x][py] = y + 1.13983f * v;
            pixel[1][x][py] = y - 0.39465f * u - 0.58060f * v;
            pixel[2][x][py] = y + 2.03211f * u;
        }
        type[py] = Type.RGB;
    }
    
    /**
     * Changes the color space of a given row.
     * @param y row number
     */
    protected void rgb2hsv(int y) {
        for (int x=0; x<width; x++) {
            float max = pixel[0][x][y];
            float min = max;
            for (int i=1; i<3; i++) {
                if (max>pixel[i][x][y]) {
                    max = pixel[i][x][y];
                }
                if (min<pixel[i][x][y]) {
                    min = pixel[i][x][y];
                }
            }
            float r = pixel[0][x][y];
            float g = pixel[1][x][y];
            float b = pixel[2][x][y];
            float h = 0;
            if (max!=min) {
                if (max==r) {
                    h = (g-b)/(max-min)/6.0f + 1;
                } else if (max==g) {
                    h = (b-r)/(max-min)/6.0f + 1.0f/3.0f;
                } else if (max==b) {
                    h = (r-g)/(max-min)/6.0f + 2.0f/3.0f;
                }
                while (h>1.0f) {
                    h-=1.0f;
                }
            }
            float s = (max==0) ? 0 : 1.0f-min/max;
            float v = max;
            pixel[0][x][y] = h;
            pixel[1][x][y] = s;
            pixel[2][x][y] = v;
        }
        type[y] = Type.HSV;
    }
    
    /**
     * Changes the color space of a given row.
     * @param y row number
     */
    protected void hsv2rgb(int y) {
        for (int x=0; x<width; x++) {
            float h = pixel[0][x][y];
            float s = pixel[1][x][y];
            float v = pixel[2][x][y];
            int hi = (int)(6.0f*h);
            float f = 6.0f*h - hi;
            float l = v * (1.0f-s);
            float m = v * (1.0f - f*s);
            float n = v * (1.0f - (1-0f-f)*s);
            switch (hi) {
                case 0:
                    pixel[0][x][y] = v;
                    pixel[1][x][y] = n;
                    pixel[2][x][y] = l;
                    break;
                 case 1:
                    pixel[0][x][y] = m;
                    pixel[1][x][y] = v;
                    pixel[2][x][y] = l;
                    break;
                 case 2:
                    pixel[0][x][y] = l;
                    pixel[1][x][y] = v;
                    pixel[2][x][y] = n;
                    break;
                 case 3:
                    pixel[0][x][y] = l;
                    pixel[1][x][y] = m;
                    pixel[2][x][y] = v;
                    break;
                 case 4:
                    pixel[0][x][y] = n;
                    pixel[1][x][y] = l;
                    pixel[2][x][y] = v;
                    break;
                 case 5:
                    pixel[0][x][y] = v;
                    pixel[1][x][y] = l;
                    pixel[2][x][y] = m;
                    break;
            }
        }
        type[y] = Type.RGB;
    }
    
    /**
     * Substract the values to 1.
     */
    public void invert() {
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                for (int l=0; l<3; l++) {
                    pixel[l][x][y] = 1-pixel[l][x][y];
                }
            }
        }
    }
}
