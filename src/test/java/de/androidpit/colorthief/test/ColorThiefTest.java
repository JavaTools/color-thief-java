/*
 * Java Color Thief
 * by Sven Woltmann, Fonpit AG
 * 
 * http://www.androidpit.com
 * http://www.androidpit.de
 *
 * License
 * -------
 * Creative Commons Attribution 2.5 License:
 * http://creativecommons.org/licenses/by/2.5/
 *
 * Thanks
 * ------
 * Lokesh Dhakar - for the original Color Thief JavaScript version
 * available at http://lokeshdhakar.com/projects/color-thief/
 */

package de.androidpit.colorthief.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

import de.androidpit.colorthief.ColorThief;
import de.androidpit.colorthief.MMCQ.CMap;
import de.androidpit.colorthief.MMCQ.VBox;

public class ColorThiefTest
{

    public static void main(String[] args) throws IOException
    {
        System.setOut(new PrintStream(new File("index.html")));
        System.out.println(
                "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "\n" +
                        "\t<head>\n" +
                        "\t\t<meta charset='utf-8'> \n" +
                        "\t\t<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\"/>\n" +
                        "\t\t<link rel=\"icon\" type=\"image/png\" href=\"favicon.png\"/>\n" +
                        "\t\t<script type=\"text/javascript\" src=\"app.js\"></script>\n" +
                        "\t\t<title>HTML Snippet</title>\n" +
                        "\t\t<style>\n" +
                        "\t\t\t.colors { clear: both; width: 900px; }\n" +
                        "\t\t\t.dominant { float: left; width:900px; height: 100px; }\n" +
                        "\t\t\t.color { float: left; width: 100px; height: 100px; }\n" +
                        "\t\t\timg { clear: both; height: 200px; border:0; padding:0; margin:0}\n" +
                        "\t\t</style>\n" +
                        "\n" +
                        "\t</head>\n" +
                        "\n" +
                        "\t<body>"
        );
        test("examples/img/shirt1.jpg");
        test("examples/img/shirt2.jpg");
        System.out.println("</body></html>");
    }

    private static void test(String pathname) throws IOException
    {
        System.out.println("<div class='colors'><img src=\"" + pathname + "\"></div>");
        System.out.println("<div class='colors'>");

        BufferedImage img = ImageIO.read(new File(pathname));

        // The dominant color is taken from a 5-map
        CMap result = ColorThief.getColorMap(img, 5);
        VBox dominantColor = result.vboxes.get(0);
        printVBox(dominantColor,true);

        result = ColorThief.getColorMap(img, 10);
        for (VBox vbox : result.vboxes)
        {
            printVBox(vbox,false);
        }
        System.out.println("</div>");
    }

    private static void printVBox(VBox vbox, boolean large)
    {
        int[] rgb = vbox.avg(false);

        // Create color String representations
        String rgbString = createRGBString(rgb);
        String rgbHexString = createRGBHexString(rgb);

        StringBuilder line = new StringBuilder();


        line.append(large ? "<div class='dominant' " : "<div class='color' ");
        line.append("style=\"background:").append(rgbString).append(";\"></div>");

        System.out.println(line);
    }

    private static String createRGBString(int[] rgb) {
        return "rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")";
    }

    private static String createRGBHexString(int[] rgb) {
        String rgbHex = Integer.toHexString(rgb[0] << 16 | rgb[1] << 8 | rgb[2]);
        int length = rgbHex.length();
        if (length < 6) {
            rgbHex = "00000".substring(0, 6 - length) + rgbHex;
        }
        return "#" + rgbHex;
    }

}
