package mttsndrs;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Builds a suffix array based on one or more texts, and can find the longest
 * common substring between any two of them.
 * 
 * @author Matt Saunders
 */
public class SuffixArray
{
    private List<String> texts = new ArrayList<String>();
    private List<Suffix> suffixes = new ArrayList<Suffix>();
    boolean isSorted = false;

    /**
     * Adds a new text to the suffix array for future searches.
     * The new text will be assigned an index within the array, to reference
     * when making future searches.
     *
     * The internal array will be left unsorted after the insert, the caller
     * can either explicitly call sort() after all inserts are complete, or
     * otherwise it will be automatically sorted when getLongestCommonSubstring()
     * is called.
     *
     * @param text The new text
     * @return the index of the just-added text within the array
     */
    public int insert( String text )
    {
        texts.add(text);        
        int textIndex = texts.size() - 1;

        for( int i = 0; i < text.length(); i++ )
        {
            suffixes.add( new Suffix(text, i, textIndex) );
        }

        // Mark the suffixes as unsorted
        isSorted = false;

        return textIndex;
    }

    /**
     * Finds the longest common substring between two texts in the suffix
     * array, referenced by the indexes assigned to them when they were
     * inserted.
     *
     * For example, if text 1 is "The rain in Spain stays mainly in the plain"
     * and text 2 is "The sleet in Crete stays neatly in the street", then
     * the longest common substring between them is "ly in the ".
     * 
     * @param index1 the index of the first text to compare
     * @param index2 the index of the second text to compare
     * @return the longest common substring between them, or null if none exists.
     */
    public String getLongestCommonSubstring( int index1, int index2 )
    {
        String longestCommonSubstring = null;
        int bestSoFar = 0;

        // Make sure the array is sorted before we start the search
        if( !isSorted ) { sort(); }

        for( int i = 0; i < suffixes.size()-1; i++ )
        {
            Suffix s1 = suffixes.get(i);

            // Look forward in the list until we find a suffix from the other text.
            
            for( int j = i+1; j < suffixes.size(); j++ )
            {
                Suffix s2 = suffixes.get(j);

                if(( s1.textIndex == index1 && s2.textIndex == index2 )
                || ( s2.textIndex == index1 && s1.textIndex == index2 ))
                {
                    int commonLength = s1.commonLengthWith(s2);

                    if( commonLength <= bestSoFar )
                    {
                        // No need to pursue suffix s1 any further
                        break;
                    }
                    else
                    {
                        longestCommonSubstring = s1.substring(0, commonLength);
                        bestSoFar = commonLength;
                        break;
                    }
                }
                else if( (j-i) > texts.size() )
                {
                    // We only need to look ahead as many items as there
                    // are texts in the array.
                    break;
                }
            }
        }

        return longestCommonSubstring;
    }

    /**
     * Sort the internal suffix array.
     */
    public void sort()
    {
        // Sort the suffix array to bring suffixes with common strings together
        Collections.sort(suffixes);

        isSorted = true;
    }

    /**
     * Clear out the suffix array.
     */
    public void clear()
    {
        texts.clear();
        suffixes.clear();
    }

    /**
     * Implements a single suffix within the suffix array.
     */
    private class Suffix
        implements Comparable
    {
        private String text = null;

        private int startIndex = 0;
        private int textIndex = 0;
        private int length = 0;

        /**
         * Creates a new Suffix based on a text fragment.
         *
         * @param text the complete original text
         * @param startIndex the start index of this suffix within the text
         */
        public Suffix(String text, int startIndex, int textIndex)
        {
            this.text = text;
            this.startIndex = startIndex;
            this.textIndex = textIndex;
            this.length = text.length() - startIndex;
        }

        /**
         * Given another suffix, finds the length of common text that they
         * share, starting from the beginning of both suffixes.
         *
         * @param that Another suffix to compare
         * @return the length of common text they share
         */
        public int commonLengthWith( Suffix that )
        {
            int commonLength = 0;

            while(( commonLength < this.length() )
               && ( commonLength < that.length() )
               && ( this.charAt(commonLength) == that.charAt(commonLength) ))
            {
                commonLength++;
            }

            return commonLength;
        }

        /**
         * Performs a lexicographic comparison of the two suffixes.
         *
         * @param thatObject the object being compared
         * @return &gt; 0 if this object falls before the other in order,
         *         &lt; 0 if after, and zero if they are equivalent.
         */
        public int compareTo( Object thatObject )
        {
            Suffix that = (Suffix) thatObject;

            for( int i = 0; i < this.length() && i < that.length(); i++ )
            {
                char left = this.charAt(i);
                char right = that.charAt(i);

                if( left > right )
                {
                    return 1;
                }
                else if( left < right )
                {
                    return -1;
                }
            }

            // So far they are equivalent up to the length of the shorter suffix
            if( this.length() > that.length() )
            {
                return 1;
            }
            else if( this.length() < that.length() )
            {
                return -1;
            }

            // OK, they are equivalent
            return 0;
        }

        @Override
        public String toString()
        {
            return text.substring(startIndex);
        }

        public String substring(int relativeStartIndex, int relativeEndIndex)
        {
            return text.substring(startIndex + relativeStartIndex,
                    startIndex + relativeEndIndex);
        }

        public char charAt(int index)
        {
            return text.charAt(startIndex + index);
        }

        public int length()
        {
            return length;
        }
    }
}
