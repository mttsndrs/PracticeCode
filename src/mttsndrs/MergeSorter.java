package mttsndrs;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matt Saunders
 */
public class MergeSorter
{
    public static List<Integer> mergeSort( List<Integer> unsortedList )
    {
        if( unsortedList.size() <= 1 )
        {
            return unsortedList;
        }

        List<Integer> left = new ArrayList<Integer>();
        List<Integer> right = new ArrayList<Integer>();
        List<Integer> result = null;

        int middle = unsortedList.size() / 2;

        // Divide our unsorted data into two buckets
        for( int i = 0; i < middle; i++ )
        {
            left.add( unsortedList.get(i) );
        }

        for( int i = middle; i < unsortedList.size(); i++ )
        {
            right.add( unsortedList.get(i) );
        }

        // Sort the two buckets recursively
        left = mergeSort(left);
        right = mergeSort(right);

        if( left.get(left.size()-1) > right.get(0) )
        {
            result = mergeSortedLists( left, right );
        }
        else
        {
            left.addAll(right);
            result = left;
        }

        return result;
    }

    public static List<Integer> mergeSortedLists( List<Integer> left, List<Integer> right )
    {
        List<Integer> result = new ArrayList<Integer>();

        while( left.size() > 0 && right.size() > 0 )
        {
            if( left.get(0) < right.get(0) )
            {
                result.add( left.remove(0) );
            }
            else
            {
                result.add( right.remove(0) );
            }
        }

        if( left.size() > 0 )
        {
            result.addAll( left );
            left.clear();
        }
        else
        {
            result.addAll( right );
            right.clear();
        }
        
        return result;
    }

}
