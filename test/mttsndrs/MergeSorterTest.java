/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mttsndrs;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matt Saunders
 */
public class MergeSorterTest {

    /**
     * Test of mergeSort method, of class MergeSorter.
     */
    @Test
    public void testMergeSort()
    {
        System.out.println("mergeSort");
        List<Integer> unsortedList = new ArrayList<Integer>();
        Random random = new SecureRandom();

        for( int i = 0; i < 1000; i++ )
        {
            unsortedList.add( random.nextInt() );
        }

        List result = MergeSorter.mergeSort(unsortedList);
        Collections.sort(unsortedList);
        
        assertTrue(unsortedList.equals(result));
    }

    /**
     * Test of mergeSortedLists method, of class MergeSorter.
     */
    @Test
    public void testMergeSortedLists()
    {
        System.out.println("mergeSortedLists");
        List<Integer> evens = new ArrayList<Integer>();
        List<Integer> odds = new ArrayList<Integer>();
        List<Integer> all = new ArrayList<Integer>();
        Random random = new SecureRandom();

        for( int i = 0; i < 1000; i++ )
        {
            int n = random.nextInt();

            if( n % 2 == 0 )
            {
                evens.add(n);
            }
            else
            {
                odds.add(n);
            }

            all.add(n);
        }

        Collections.sort(evens);
        Collections.sort(odds);
        Collections.sort(all);

        List result = MergeSorter.mergeSortedLists(evens, odds);

        assertTrue(all.equals(result));
    }
}