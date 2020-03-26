package process;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Sharpness {

    public static BufferedImage sharpenImage(BufferedImage image, int factor)  {
        float[] filter = { -1, -1, -1, -1, 9, -1, -1, -1, -1 };
        for(int i=0; i<filter.length;i++){
            filter[i] *= factor;
        }

        Kernel kernel = new Kernel(3, 3, filter);
        BufferedImageOp op = new ConvolveOp(kernel);
        image = op.filter(image, null);
        return image;
    }

}
