package fr.esiea.ebooks.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Handler for report filter
 * @author Michel Messak
 */

public class ReportColumnSorter
{
    String[] colNames;
    HashMap sorts;

    public ReportColumnSorter(String[] colNames)
    {
        this.colNames=colNames;
        this.sorts=new HashMap();
    }

    public void clearSort()
    {
        this.sorts.clear();
    }

    public void addSort(String colNumber, String colDir)
    {
        this.sorts.put(colNumber, colDir);
    }


    //Generate the sorting column
    public String GenerateSort()
    {
        String sort=null;
        Iterator iter=this.sorts.entrySet().iterator();
        while(iter.hasNext())
        {
            Map.Entry entry=(Map.Entry)iter.next();
            String sColNumber=(String)entry.getKey();
            String sColDir=(String)entry.getValue();

            if(sColNumber!=null && sColDir!=null)
            {
                int iColNumber=Integer.parseInt(sColNumber);
                if(iColNumber>=0 && iColNumber<this.colNames.length)
                {
                    if(sort==null) sort="";
                    if(!sort.equals("")) sort+=", ";
                    sort+=this.colNames[iColNumber];
                }
            }
        }
        return sort;
    }
}
