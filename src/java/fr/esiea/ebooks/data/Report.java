package fr.esiea.ebooks.data;

import fr.esiea.ebooks.model.Contact;
import fr.esiea.ebooks.model.ContactsList;
import fr.esiea.ebooks.util.Util;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;

/**
 *
 * Manager reporting and paging
 * Configure querys to order, to filter by column, to search by all the column etc...
 * @author Michel Messak
 */
public class Report {

    private String[] columnNames;
    private String[] columnWidths;
    private String[] columnAligns;
    private String[] columnExtras;
    private Boolean[] columnVisibles;
    private Boolean[] columnSortables;
    private Boolean[] columnSearchables;
    private String ID,URL;
    private JSONArray data;
    private int rowCount, rowFilterCount = 0;
    private int columnCount, currentPage, pageCount;
    private ReportColumnSorter sorter;
    private ReportColumnFilter filter;
    private final int ROWS_PER_PAGE = 15;
    private int OFFSET = 0;
    
    ContactsList contactList = ContactsList.getInstance();

    public Report(String[] columnNames, String URL) throws Exception {
        try {
            this.columnNames = columnNames;
            this.rowCount = 0;
            this.URL = URL;
            this.columnCount = columnNames.length;
            this.pageCount = 0;
            this.sorter = new ReportColumnSorter(columnNames);
            this.filter = new ReportColumnFilter(columnNames);
        } catch (Exception ex) {
            throw ex;
        }
    }

     public String getURL() {
        return this.URL;
    }

    public int getColumnCount() {
        if (this.columnNames != null) {
            return this.columnNames.length;
        } else {
            return 0;
        }
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public int getRowFilterCount() {
        return this.rowFilterCount;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public String[] getColumnNames() {
        return this.columnNames;
    }

    public JSONArray getData() {
        return this.data;
    }

    public Boolean ExecuteContactForTotals(HttpServletRequest request) throws Exception {

                this.rowCount = contactList.size();

            if (this.rowCount == 0) {
                this.pageCount = 0;
            } else {
                this.pageCount = this.rowCount / this.ROWS_PER_PAGE;
                if (this.pageCount * this.ROWS_PER_PAGE < this.rowCount) {
                    this.pageCount++;
                }
            }

            this.currentPage = -1;

            return true;

    }

    public Boolean ExecuteAddressForTotals(HttpServletRequest request,String ID) throws Exception {

         this.ID = ID;
         for (int i=0;i<contactList.size();i++)
             if(contactList.getContact(i).getID().equals(ID))
                this.rowCount = contactList.getContact(i).getAllAdress().size();

            if (this.rowCount == 0) {
                this.pageCount = 0;
            } else {
                this.pageCount = this.rowCount / this.ROWS_PER_PAGE;
                if (this.pageCount * this.ROWS_PER_PAGE < this.rowCount) {
                    this.pageCount++;
                }
            }

            this.currentPage = -1;

            return true;

    }

    //Put all the contact value into a JSONArray

    public void copyValueContact(JSONArray row, int position) throws Exception{

    String value = contactList.getContact(position).getFirstName();
    row.add((value != null ? value.toString() : "&nbsp;"));

    value = contactList.getContact(position).getLastName();
    row.add((value != null ? value.toString() : "&nbsp;"));

    value = contactList.getContact(position).getBirthday();
    row.add((value != null ? value.toString() : "&nbsp;"));

    value = contactList.getContact(position).getEmail();
    row.add((value != null ? value.toString() : "&nbsp;"));

    value = String.valueOf(contactList.getContact(position).isActif());
    row.add((value != null ? value.toString() : "&nbsp;"));

    for (int j = 0; this.columnExtras != null && j < this.columnExtras.length; j++) {
        String html = Util.replaceValuesHTMLContact(this.columnExtras[j],position);
            row.add(html);
    }

this.data.add(row);
 }

    //Put all the Address value from a contact into a JSONArray

    public void copyValueAddress(JSONArray row, int position, Contact contact) throws Exception{

    Object value = contact.getAdress(position).getNumber();
    row.add((value != null ? value.toString() : "&nbsp;"));

    value = contact.getAdress(position).getStreet();
    row.add((value != null ? value.toString() : "&nbsp;"));

    value = contact.getAdress(position).getPostalCode();
    row.add((value != null ? value.toString() : "&nbsp;"));

    value = contact.getAdress(position).getCity();
    row.add((value != null ? value.toString() : "&nbsp;"));

    for (int j = 0; this.columnExtras != null && j < this.columnExtras.length; j++) {
        String html = Util.replaceValuesHTMLAddress(this.columnExtras[j],contact,position);
            row.add(html);
    }

    this.data.add(row);
 }

    // Set the data into a JSON Array
    public void getRecordsContact(HttpServletRequest request, int offset, int limit) throws Exception {
        
    this.rowCount = contactList.size();

    this.data = null;
    this.data = new JSONArray();
    this.rowFilterCount = 0;

        for (int i = offset;i<limit+offset && i<contactList.size() ;i++) {
            JSONArray row = new JSONArray();
            this.rowFilterCount++;

             String sortPartQuery = this.sorter.GenerateSort();
                if (sortPartQuery == null) {
                    sortPartQuery = "";
                }
                    String [] filterPartQuery = this.filter.GenerateFilter();
                if (filterPartQuery[0] != null) {

                    switch (Integer.parseInt(filterPartQuery[0])){
                        case 0 : String value = contactList.getContact(i).getFirstName();
                          if (value.startsWith(filterPartQuery[1]))
                              this.copyValueContact(row,i);break;
                          
                        case 1 : value = contactList.getContact(i).getLastName();
                          if (value.startsWith(filterPartQuery[1]))
                            this.copyValueContact(row,i);break;
                          
                          
                        case 2 : value = contactList.getContact(i).getBirthday();
                          if (value.startsWith(filterPartQuery[1]))
                              this.copyValueContact(row,i);break;

                        case 3 :  value = contactList.getContact(i).getEmail();
                          if (value.startsWith(filterPartQuery[1]))
                              this.copyValueContact(row,i);break;

                        default : value = String.valueOf(contactList.getContact(i).isActif());
                          if (value.startsWith(filterPartQuery[1]))
                            this.copyValueContact(row,i);break;
                    }
                    
                }

                else {
                    this.copyValueContact(row,i);
                }
        }
    }

    //Set the data into a JSONArray

    public void getRecordsAddress(HttpServletRequest request, int offset, int limit) throws Exception {

         Contact contact = null;

        for (int i=0;i<contactList.size();i++)
             if(contactList.getContact(i).getID().equals(this.ID))
                contact = contactList.getContact(i);

    this.rowCount = contact.getAllAdress().size();

    this.data = null;
    this.data = new JSONArray();
    this.rowFilterCount = 0;

        for (int i = 0;i<contact.getAllAdress().size();i++) {
            JSONArray row = new JSONArray();
            this.rowFilterCount++;

            this.copyValueAddress(row, i, contact);
                
        }
    }

    public void clearSort() {
        this.sorter.clearSort();
    }

    public void addSort(String sColNumber, String sColDir) {
        this.sorter.addSort(sColNumber, sColDir);
    }

    public void clearFilter() {
        this.filter.clearFilter();
    }

    public void addFilter(String sColNumber, String sColValue) {
        this.filter.addFilter(sColNumber, sColValue);
    }

    public void setSearch(String search) {
        this.filter.setSearch(search);
    }


    /*
     * Configure all the parameters for showing contact datas like the sorting column and the filter column
     */
    
    public void configureDatatableParametersContact(HttpServletRequest request) throws Exception {
        try {
            
            //Sorting columns
            String sSortingCols = request.getParameter("iSortingCols");
            if (sSortingCols != null) {
                this.clearSort();
                int iSortingCols = Integer.parseInt(sSortingCols);
                for (int i = 0; i < iSortingCols; i++) {
                    String sColNumber = request.getParameter("iSortCol_" + i);
                    String sColDir = request.getParameter("sSortDir_" + i);
                    this.addSort(sColNumber, sColDir);
                }
            } else {
                this.clearSort();
            }

            this.clearFilter();

            for (int i = 0; i < this.columnNames.length; i++) {
                String sColValue = request.getParameter("sSearch_" + i);
                 if (sColValue != null && !sColValue.trim().equals("")) {
                    this.addFilter("" + i, sColValue);
                }
            }

            //Filter general
            String sSearch = request.getParameter("sSearch");
            this.setSearch(sSearch);

            String sLimit = request.getParameter("iDisplayLength");
            String sOffset = request.getParameter("iDisplayStart");
            int limit = 0, offset = 0;
            if (sLimit != null) {
                limit = Integer.parseInt(sLimit);
            }
            if (sOffset != null) {
                offset = Integer.parseInt(sOffset);
            }
            this.getRecordsContact(request, offset, limit);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /*
     * Configure all the parameters for showing address datas from a specific contact like the sorting column and the filter column
     */

    public void configureDatatableParametersAddress(HttpServletRequest request) throws Exception {
        try {
            
            //Sorting
            String sSortingCols = request.getParameter("iSortingCols");
            if (sSortingCols != null) {
                this.clearSort();
                int iSortingCols = Integer.parseInt(sSortingCols);
                for (int i = 0; i < iSortingCols; i++) {
                    String sColNumber = request.getParameter("iSortCol_" + i);
                    String sColDir = request.getParameter("sSortDir_" + i);
                    this.addSort(sColNumber, sColDir);
                }
            } else {
                this.clearSort();
            }

            this.clearFilter();
            for (int i = 0; i < this.columnNames.length; i++) {
                String sColValue = request.getParameter("sSearch_" + i);
                if (sColValue != null && !sColValue.trim().equals("")) {
                    this.addFilter("" + i, sColValue);
                }
            }

            //Filter general
            String sSearch = request.getParameter("sSearch");
            this.setSearch(sSearch);

            String sLimit = request.getParameter("iDisplayLength");
            String sOffset = request.getParameter("iDisplayStart");
            int limit = 0, offset = 0;
            if (sLimit != null) {
                limit = Integer.parseInt(sLimit);
            }
            if (sOffset != null) {
                offset = Integer.parseInt(sOffset);
            }
            this.getRecordsAddress(request, offset, limit);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Obtain the javascript definitin f the column to configure the return
     */
    public String getJavascriptColumnDefinition() {
        String totalDef = null;
        for (int i = 0; i < this.columnNames.length; i++) {
            String def = null;
            if (this.columnWidths != null && i < this.columnWidths.length && this.columnWidths[i] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"sWidth\": \"" + this.columnWidths[i] + "\"";
            }
            if (this.columnAligns != null && i < this.columnAligns.length && this.columnAligns[i] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"sClass\": \"" + this.columnAligns[i] + "\"";
            }
            if (this.columnVisibles != null && i < this.columnVisibles.length && this.columnVisibles[i] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"bVisible\": " + this.columnVisibles[i];
            }
            if (this.columnSortables != null && i < this.columnSortables.length && this.columnSortables[i] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"bSortable\": " + this.columnSortables[i];
            } else {
                //SGH: El default en datatable es ordenar. Yo lo necesito al reves
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"bSortable\": false";
            }
            if (def != null) {
                def = "{ " + def + " }";
            }
            if (def != null) {
                if (totalDef == null) {
                    totalDef = "";
                }
                totalDef += def + ",";
            }
        }
        for (int i = 0; this.columnExtras != null && i < this.columnExtras.length; i++) {
            int otherIndex = i + (this.columnNames != null ? this.columnNames.length : 0);
            String def = null;
            if (this.columnExtras[i] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"sType\": \"html\"";
            }
            if (this.columnWidths != null && otherIndex < this.columnWidths.length && this.columnWidths[otherIndex] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"sWidth\": \"" + this.columnWidths[otherIndex] + "\"";
            }
            if (this.columnAligns != null && otherIndex < this.columnAligns.length && this.columnAligns[otherIndex] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"sClass\": \"" + this.columnAligns[otherIndex] + "\"";
            }
            if (this.columnVisibles != null && otherIndex < this.columnVisibles.length && this.columnVisibles[otherIndex] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"bVisible\": " + this.columnVisibles[otherIndex];
            }
            if (this.columnSortables != null && otherIndex < this.columnSortables.length && this.columnSortables[otherIndex] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"bSortable\": " + this.columnSortables[otherIndex];
            } else {
                //SGH: El default en datatable es ordenar. Yo lo necesito al reves
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"bSortable\": false";
            }
            if (def != null) {
                def = "{ " + def + " }";
            }
            if (def != null) {
                if (totalDef == null) {
                    totalDef = "";
                }
                totalDef += def + ",";
            }
        }
        if (totalDef == null) {
            return "";
        }
        return totalDef;
    }

    public void setColumnAlignments(String[] columnAligns) {
        this.columnAligns = columnAligns;
    }

    public void setColumnWidths(String[] columnWidths) {
        this.columnWidths = columnWidths;
    }

    public void setColumnExtras(String[] columnExtras) {
        this.columnExtras = columnExtras;
    }

    public String[] getColumnExtras() {
        return new String[(this.columnExtras != null ? this.columnExtras.length : 0)];
    }

    public void setColumnVisibles(Boolean[] columnVisibles) {
        this.columnVisibles = columnVisibles;
    }

    public void setColumnSortables(Boolean[] columnSortables) {
        this.columnSortables = columnSortables;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setColumnSearchables(Boolean[] columnSearchables) {
        this.columnSearchables = columnSearchables;
    }

    //Obtain the definition of the serch for the objet column. 

    public String[] getJavascriptSearchDefinition() {
        
        if (this.columnSearchables == null) {
            return null;
        }
        String[] search = new String[this.columnSearchables.length];
        for (int i = 0; i < this.columnSearchables.length; i++) {
            if (this.columnSearchables[i] != null && this.columnSearchables[i]) {
                search[i] = "<input type=\"text\" name=\"search_col_\"" + i + "\" placeholder=\"" + (i < this.columnNames.length ? this.columnNames[i] : "") + "\" class=\"search_init\" style=\"color: #383838\"/>";
            } else {
                search[i] = "";
            }
        }
        return search;

    }

    public static boolean isAjaxCall(HttpServletRequest request) {
        return (request.getParameter("sEcho") != null );
    }

    public static boolean isTaskCall(HttpServletRequest request) {
        return ("Consult".equals(request.getParameter("isTaskSubmit")));
    }
    
}
