package pdfparser;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

/*
* A Demo class for using PDFBox. How we can get images and text from a pdf like Aadhar.
*
*
*/
/**
 * Stash
 */
public class App {

    /**
     * Stash 2
     **/

    public static void main(String[] args) throws InvalidPasswordException, IOException {

        String inputFilePath = "C:/Users/g522257/Downloads/in.gov.uidai-ADHAR-521215359778.pdf"; // path for the input
                                                                                                 // file.
        File inputFile = new File(inputFilePath);

        PDDocument doc = PDDocument.load(inputFile); // load the file and get PDDocument

        PDDocumentInformation documentInformation = doc.getDocumentInformation(); // Get meta information about
                                                                                  // document.

        documentInformation.getMetadataKeys().stream()
                .forEach(x -> System.err.println(documentInformation.getPropertyStringValue(x))); // Print all the info.

        PDFTextStripper strpper = new PDFTextStripper(); // Get Simple Text from whole of the document.
        System.out.println(strpper.getText(doc)); // print all the text on console.

        PDPage page = doc.getPage(0); // get first page from the document.
        // You a loop or iterator to extract images from all pages of pdf.

        PDResources resources = page.getResources(); // Get all the resources from the page.

        List<BufferedImage> images = new ArrayList<>();
        resources.getXObjectNames().forEach(x -> {
            try {
                PDXObject xObject = resources.getXObject(x);

                if (xObject instanceof PDImageXObject) { // check whether it is a image or not.
                    PDImageXObject imgObj = (PDImageXObject) xObject;
                    BufferedImage image = imgObj.getImage(); // if it is a image then add to List for BufferedImagde.
                    images.add(image);
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        });

        System.err.println(images.size());

        // Create JFrame for each image and display them.

        images.forEach(x -> {
            JFrame frame = new JFrame();
            frame.getContentPane().setLayout(new FlowLayout());
            frame.getContentPane().add(new JLabel(new ImageIcon(x)));
            frame.pack();
            frame.setVisible(true);
        });

    }

}
