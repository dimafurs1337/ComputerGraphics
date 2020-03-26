import process.HistogramEqualization;
import process.LinearContrasting;
import process.Sharpness;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageProcess extends JFrame {
    private JLabel imageLabel;
    private JLabel processedImageLabel;

    private String selectedImagePath;

    public ImageProcess() {
        super("Image Process");

        JButton browseButton = new JButton("Choose image");
        browseButton.setBounds(20,500,150,40);
        browseButton.addActionListener(e -> {
            JFileChooser file = new JFileChooser();
            file.setCurrentDirectory(new File(System.getProperty("user.home")));

            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
            file.addChoosableFileFilter(filter);
            int result = file.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = file.getSelectedFile();
                this.selectedImagePath = selectedFile.getAbsolutePath();
                imageLabel.setIcon(resizeImage(this.selectedImagePath));
            }
            else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.println("No file selected");
            }
        });
        this.add(browseButton);

        JSlider sharpFactor = new JSlider(JSlider.HORIZONTAL,
                1, 10, 1);
        sharpFactor.setBounds(200,550,200,100);
        sharpFactor.setMinorTickSpacing(1);
        sharpFactor.setMajorTickSpacing(1);
        sharpFactor.setPaintTicks(true);
        sharpFactor.setPaintLabels(true);
        this.add(sharpFactor);

        JButton sharpnessButton = new JButton("Sharpness");
        sharpnessButton.setBounds(200,500, 200, 40);
        sharpnessButton.addActionListener(e -> {
            if (this.selectedImagePath != null) {
                File imageFile = new File(this.selectedImagePath);
                try {
                    BufferedImage originalImage = ImageIO.read(imageFile);
                    BufferedImage processedImage = Sharpness.sharpenImage(originalImage, sharpFactor.getValue());
                    String destFileName = Paths.get(imageFile.getParentFile().getAbsolutePath(),
                            "sharp"+imageFile.getName()).toString();
                    ImageIO.write(processedImage, "jpg", new File(destFileName));
                    processedImageLabel.setIcon(resizeImage(destFileName));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.add(sharpnessButton);



        JButton linearContrastingButton = new JButton("Linear contrasting");
        linearContrastingButton.setBounds(420,500, 200, 40);
        linearContrastingButton.addActionListener(e -> {
            if (this.selectedImagePath != null) {
                File imageFile = new File(this.selectedImagePath);
                try {
                    BufferedImage originalImage = ImageIO.read(imageFile);
                    BufferedImage processedImage = LinearContrasting.whiteBalanceBuffImage(originalImage);
                    String destFileName = Paths.get(imageFile.getParentFile().getAbsolutePath(),
                            "linear"+imageFile.getName()).toString();

                    ImageIO.write(processedImage, "jpg", new File(destFileName));
                    processedImageLabel.setIcon(resizeImage(destFileName));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.add(linearContrastingButton);



        JButton equalizeButton = new JButton("Equalize");
        equalizeButton.setBounds(640,500, 200, 40);
        equalizeButton.addActionListener(e -> {
            if (this.selectedImagePath != null) {
                File imageFile = new File(this.selectedImagePath);
                try {
                    BufferedImage originalImage = ImageIO.read(imageFile);
                    BufferedImage processedImage = HistogramEqualization.equalize(originalImage);
                    String destFileName = Paths.get(imageFile.getParentFile().getAbsolutePath(),
                            "equal"+imageFile.getName()).toString();

                    ImageIO.write(processedImage, "jpg", new File(destFileName));
                    processedImageLabel.setIcon(resizeImage(destFileName));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.add(equalizeButton);

        imageLabel = new JLabel();
        imageLabel.setBounds(10, 10, 600, 400);
        this.add(imageLabel);

        processedImageLabel = new JLabel();
        processedImageLabel.setBounds(710, 10, 600, 400);
        this.add(processedImageLabel);

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(50, 50);
        setSize(900,700);
        setVisible(true);
    }

    private ImageIcon resizeImage(String ImagePath) {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    public static void main(String[] args){
        new ImageProcess();
    }
}

