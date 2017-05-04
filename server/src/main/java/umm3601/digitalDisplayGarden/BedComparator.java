package umm3601.digitalDisplayGarden;

import java.util.Comparator;

/**
 * Compares Strings that may or may not begin with
 * a number, and then some characters afterwards prioritizing
 * characters representing cardinal directions.
 *
 * 1 < 2 < 2N < 2E < 2S < 2W < 2A < 2B < 2C < 20 ...
 */
public class BedComparator implements Comparator<String>{

    /**
     * Get the number corresponding to a Bed
     * @param bedName
     * @return
     */
    private int getIndexAfterNum(String bedName)
    {
        int afterNum = 0;
        while(afterNum < bedName.length() && Character.isDigit(bedName.charAt(afterNum)))
            afterNum++;
        return afterNum;
    }

    /**
     * Takes a capital letter return the ordinal based on cardinal directions.
     * N = 1, E = 2, S = 3, W = 4
     * Otherwise returns a number in the interval [5,30] - [4,13,18,22] Creating an otherwise alphabetic order
     * @param c
     * @return
     */
    private int getOrdinal(char c)
    {
        switch(c)
        {
            case 'N':
                return 1;
            case 'E':
                return 2;
            case 'S':
                return 3;
            case 'W':
                return 4;
            default:
                return c - 'A' + 5;
        }
    }

    /**
     * Compare a Bed name to another. As such:
     * 1, 2, 2N, 2W, 3, LG, ETC
     * @param lhs
     * @param rhs
     * @return
     */
    public int compare(String lhs, String rhs)
    {
        if(lhs.equals(rhs))
            return 0;

        int li = getIndexAfterNum(lhs);
        int ri = getIndexAfterNum(rhs);
        if(li != 0) {
            int lNum = Integer.parseInt(lhs.substring(0, li));
            if(ri != 0) {
                //Both are numbers
                int rNum = Integer.parseInt(rhs.substring(0, ri));

                //Compare the numbers
                int cmp = Integer.compare(lNum, rNum);
                if (cmp != 0)
                    return cmp;

                lhs = lhs.substring(li).toUpperCase();
                rhs = rhs.substring(ri).toUpperCase();
                int minLen = Math.min(lhs.length(), rhs.length());

                //Read character by Character comparing Ordinals
                for(int curChar = 0; curChar < minLen; curChar++)
                {
                    lNum = getOrdinal(lhs.charAt(curChar));
                    rNum = getOrdinal(rhs.charAt(curChar));
                    cmp = Integer.compare(lNum, rNum);
                    if(cmp != 0)
                        return cmp;
                }

                if(lhs.length() < rhs.length())
                    return -1;
                else return 1;
            }
            else
                //lhs has a number in it, rhs doesn't
                return -1;

        }
        else
        {

            if(ri != 0)
                //lhs does not have a number in it, rhs does
                return 1;
            else
                //Both lhs and rhs are not numbers
                return lhs.compareTo(rhs);

        }
    }


}
