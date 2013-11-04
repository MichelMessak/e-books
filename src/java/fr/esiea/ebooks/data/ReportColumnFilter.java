package fr.esiea.ebooks.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Handler for a reprt filter by column
 * @author Dispa Cécile
 */

public class ReportColumnFilter
{
    String[] colNames;
    HashMap filters;
    String search;

    public ReportColumnFilter(String[] colNames)
    {
        this.colNames=colNames;
        this.filters=new HashMap();
    }

    public void clearFilter()
    {
        this.filters.clear();
    }

    public void addFilter(String sColNumber, String sColValue)
    {
       this.filters.put(sColNumber, sColValue);
    }

    //Generate filter of a column
    public String [] GenerateFilter()
    {
  
            String sort[ ] = new String[2];
            Iterator iter=this.filters.entrySet().iterator();
            while(iter.hasNext())
            {
                Map.Entry entry=(Map.Entry)iter.next();
                String sColNumber=(String)entry.getKey();
                String sColValue=(String)entry.getValue();

                if(sColNumber!=null && sColValue!=null)
                {
                    int iColNumber=Integer.parseInt(sColNumber);
                    if(iColNumber>=0 && iColNumber<this.colNames.length)
                    {   
                        sort[0]=sColNumber;
                        sort[1]=sColValue;
                    }
                }
            }


            return sort;
           }

    public void setSearch(String search)
    {
        this.search=search;
    }
}
