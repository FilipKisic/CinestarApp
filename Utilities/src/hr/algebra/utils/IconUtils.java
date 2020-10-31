package hr.algebra.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author dnlbe
 */
public class IconUtils {
    
    public static ImageIcon createIcon(String path, int width, int height) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(path));
        Image image = bufferedImage.getScaledInstance(width, height,Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
    
}
