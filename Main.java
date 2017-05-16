import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.awt.image.WritableRaster;

public class Main {
    public static void main(String[] args){
        int r, g, b; //Color values

        //Hold the random numbers
        int[] data = new int[16384];

        //Call random.org twice, max request size is 10,000 numbers
        int[] output1 = getUrlContents("https://www.random.org/integers/?num=8192&min=1&max=255&col=1&base=10&format=plain&rnd=new");
        int[] output2 = getUrlContents("https://www.random.org/integers/?num=8192&min=1&max=255&col=1&base=10&format=plain&rnd=new");

        int num = 0;
        //Combine the random numbers into one array
        for(int i = 0; i < output1.length; i++)
        {
            data[num] = output1[i];
            num++;
        }
        for(int i = 0; i < output2.length; i++)
        {
            data[num] = output2[i];
            num++;
        }

        BufferedImage img = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);

        //Create and populate the pixels
        int count = 0;
        for(int i = 0; i < 128; i++){
            for(int j = 0; j < 128; j++){
                if(count >= 16382){
                    count = 0;
                }
                r = data[count];
                g = data[count+1];
                b = data[count+2];
                Color myColor = new Color(r, g, b); // Color white
                int rgb = myColor.getRGB();
                img.setRGB(i, j, rgb);
                count += 3;
            }
        }

        //Write the file
        File outputfile = new File("random.bmp");
        try {
            ImageIO.write(img, "BMP", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Get random numbers from random.org and return in array
    private static int[] getUrlContents(String theUrl)
    {
        ArrayList numbers = new ArrayList();

        // many of calls throw exceptions
        // wrapped all in one try/catch statement
        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                numbers.add(line);
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        int[] numArr = new int[numbers.size()];
        //Convert arraylist to array
        for(int i = 0; i < numbers.size(); i++){
            numArr[i] = Integer.parseInt((String)numbers.get(i));
        }

        return numArr;
    }
}
