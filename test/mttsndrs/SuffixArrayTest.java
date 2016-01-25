package mttsndrs;

import java.io.BufferedReader;
import java.io.FileReader;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the SuffixArray class.
 * 
 * @author Matt Saunders
 */
public class SuffixArrayTest
{
    /**
     * Test of getLongestCommonSubstring method, of class SuffixArray.
     */
    @Test
    public void testGetLongestCommonSubstring()
    {
        System.out.println("getLongestCommonSubstring");
        SuffixArray instance = new SuffixArray();

        String input1 = null, input2 = null, input3 = null, expResult = null;
        int index1 = 0, index2 = 0, index3 = 0;

        input1 = "The rain in Spain stays mainly in the plain";
        input2 = "The red herring in the Bering stays mainly out of sight";
        input3 = "The sleet in Crete stays neatly in the street";
        expResult = "ly in the ";

        index1 = instance.insert(input1);
        index2 = instance.insert(input2);
        index3 = instance.insert(input3);

        // Only compare the first and third texts, ignore the second
        String result = instance.getLongestCommonSubstring(index1, index3);
        assertEquals(expResult, result);

        System.out.format("Longest common substring is: '%s'%n", result);

        // Another test, this time using longer texts read from files        
        try
        {
            input1 = readFileAsString("C:/Documents and Settings/Matt Saunders/My Documents/My Dropbox/Projects/PracticeCode/resources/hotb.txt");
            input2 = readFileAsString("C:/Documents and Settings/Matt Saunders/My Documents/My Dropbox/Projects/PracticeCode/resources/asis.txt");
            /*
            input1 = readFileAsString("C:/Documents and Settings/Matt Saunders/My Documents/My Dropbox/Projects/PracticeCode/resources/alice.txt");
            input2 = readFileAsString("C:/Documents and Settings/Matt Saunders/My Documents/My Dropbox/Projects/PracticeCode/resources/looking-glass.txt");
             */
        }
        catch( java.io.IOException ex )
        {
            System.out.format("Caught exception while trying to read input file: %s", ex.getMessage());
        }

        instance = new SuffixArray();

        index1 = instance.insert(input1);
        index2 = instance.insert(input2);

        result = instance.getLongestCommonSubstring(index1, index2);
        System.out.format("Longest common substring is: '%s'%n", result);
    }

    private static String readFileAsString(String filePath)
        throws java.io.IOException
    {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        
        while((numRead=reader.read(buf)) != -1)
        {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }

        reader.close();
        return fileData.toString();
    }

}