package fr.esiea.ebooks.data;

import fr.esiea.ebooks.model.Contact;
import fr.esiea.ebooks.model.ContactsList;
import fr.esiea.ebooks.util.Util;
import fr.esiea.ebooks.util.error.DBConnectionNotFound;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private String ID;
    private String query, queryTotal, queryB, URL;
    private String task;
    private JSONArray data;
    private int rowCount, rowFilterCount = 0;
    private int columnCount, currentPage, pageCount;
    private ReportColumnSorter sorter;
    private ReportColumnFilter filter;
    private ReportWhere where;
    private final int ROWS_PER_PAGE = 20;
    private int OFFSET = 0;
    ContactsList contactList = ContactsList.getInstance();

    public Report(String[] columnNames, String URL) throws Exception {
        try {
            this.columnNames = columnNames;
            this.URL = URL;
            this.rowCount = 0;
            this.columnCount = columnNames.length;
            this.pageCount = 0;
            String[] colNames = {"Prénom","Nom","Date de Naissance","Email","Actif"};
            this.sorter = new ReportColumnSorter(colNames);
            this.filter = new ReportColumnFilter(colNames);
            this.where = new ReportWhere();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Execute the query configurated. It is use to start the
     * Ejecuta el query configurado. Se usa para empezar la carga del objeto
     */
    public void ExecuteQuery(HttpServletRequest request) throws Exception {
        try {
            ExecuteQueryForTotals(request);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void ExecuteAddress(HttpServletRequest request,String ID) throws Exception {
        try {
            ExecuteAddressForTotals(request,ID);

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Obtiene el número de columnas del reporte
     * @return  Número de columnas en el reporte
     */
    public int getColumnCount() {
        if (this.columnNames != null) {
            return this.columnNames.length;
        } else {
            return 0;
        }
    }

    /**
     * Obtiene el número de la página actual
     * @return  número de página
     */
    public int getCurrentPage() {
        return this.currentPage;
    }

    /**
     * Obtiene el número total de registros en el query
     * @return  número total de registros
     */
    public int getRowCount() {
        return this.rowCount;
    }

    /**
     * Obtiene el número total de registros filtrados en el último filtro consultado
     * @return  número total de registros filtrados
     */
    public int getRowFilterCount() {
        return this.rowFilterCount;
    }

    /**
     * Obtiene el número total de páginas de acuerdo al número de registros por página configurado
     * @return  número total de páginas
     */
    public int getPageCount() {
        return this.pageCount;
    }

    /**
     * Obtiene el arreglo de nombre de columnas del reporte
     * @return
     */
    public String[] getColumnNames() {
        return this.columnNames;
        //return Arrays.asList(colNames).iterator();
    }

    /**
     * Obtiene el URL que responde al paginado asíncrono
     * @return  La cadena conteniendo el URL que el objeto javascript debe llamar para procesamiento asíncrono de búsquedas,
     * ordenamiento y filtrado
     */
    public String getURL() {
        return this.URL;
    }

    /**
     * Obtiene los registros desde la base de datos en formato JSON
     * @return  Un arreglo JSON {@link JSONArray} de filas. Cada fila es otro arreglo JSON de valores de las columnas.
     */
    public JSONArray getData() {
        return this.data;
    }

    public Boolean ExecuteQueryForTotals(HttpServletRequest request) throws Exception {

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

    /**
     * Permite cargar los registros de la página anterior
     * @param conn  La conexión a la base de datos si se tiene una. Si la conexión es nula, se pide una del pool.
     * Ver {@link PoolConnection}
     * @throws Exception
     */
    public void getPreviousPage(Connection conn) throws Exception {
        try {
            if (this.currentPage - 1 < 0) {
                return;
            }
            this.currentPage--;
            getCurrentPage(conn);

        } catch (Exception e) {
            throw e;
        } finally {

        }
    }

    /**
     * Permite obtener los registros de la base de datos de acuerdo a un inicio y un límite. Usado por el objeto
     * javascript de paginación. Esta función carga los registros en un arreglo JSON de filas. Donde cada fila contiene
     * un arreglo JSON de valores de columnas.
     * @param offset    Número inicial del registro donde empezar
     * @param limit     Número total de registros a cargar
     * @throws Exception
     */

    public void getRecordsDocuments(HttpServletRequest request, int offset, int limit) throws Exception {


    this.rowCount = contactList.size();

    this.data = null;
    this.data = new JSONArray();
    this.rowFilterCount = 0;

        for (int i = 0;i<contactList.size();i++) {
            JSONArray row = new JSONArray();
            this.rowFilterCount++;

                Object value = contactList.getContact(i).getFirstName();
                row.add((value != null ? value.toString() : "&nbsp;"));

                value = contactList.getContact(i).getLastName();
                row.add((value != null ? value.toString() : "&nbsp;"));

                value = contactList.getContact(i).getBirthday();
                row.add((value != null ? value.toString() : "&nbsp;"));

                value = contactList.getContact(i).getEmail();
                row.add((value != null ? value.toString() : "&nbsp;"));

                value = contactList.getContact(i).isActif();
                row.add((value != null ? value.toString() : "&nbsp;"));

                for (int j = 0; this.columnExtras != null && j < this.columnExtras.length; j++) {
                    String html = Util.replaceValuesHTML(this.columnExtras[j],i);
                        row.add(html);
                }



            this.data.add(row);
        }
    }

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

                Object value = contact.getAdress(i).getNumber();
                row.add((value != null ? value.toString() : "&nbsp;"));

                value = contact.getAdress(i).getStreet();
                row.add((value != null ? value.toString() : "&nbsp;"));

                value = contact.getAdress(i).getPostalCode();
                row.add((value != null ? value.toString() : "&nbsp;"));

                value = contact.getAdress(i).getCity();
                row.add((value != null ? value.toString() : "&nbsp;"));

                for (int j = 0; this.columnExtras != null && j < this.columnExtras.length; j++) {
                    String html = Util.replaceValuesHTMLAddress(this.columnExtras[j],contact,i);
                        row.add(html);
                }



            this.data.add(row);
        }
    }

    /**
     * Permite cargar los registros de la página siguiente
     * @param conn  La conexión a la base de datos si se tiene una. Si la conexión es nula, se pide una del pool.
     * Ver {@link PoolConnection}
     * @throws Exception
     */
    public void getNextPage(Connection conn) throws Exception {
        try {
            if ((this.currentPage + 1) * this.ROWS_PER_PAGE > this.rowCount) {
                return;
            }
            this.currentPage++;
            getCurrentPage(conn);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Permite cargar los registros de la página actual
     * @param conn  La conexión a la base de datos si se tiene una. Si la conexión es nula, se pide una del pool.
     * Ver {@link PoolConnection}
     * @throws Exception
     */
    private void getCurrentPage(Connection conn) throws Exception {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //Futuro: Mejorar rendimiento trayendo bloques de paginas y no pagina por pagina
            this.OFFSET = currentPage * this.ROWS_PER_PAGE;
            if (rowCount > 0) {
                if (conn == null) {
                    if (conn == null) {
                        throw new DBConnectionNotFound("Aucune connexion disponible");
                    }
                }

                String sortPartQuery = this.sorter.GenerateSortSql();
                if (sortPartQuery == null) {
                    sortPartQuery = "";
                }
                String filterPartQuery = this.filter.GenerateFilterSql();
                if (filterPartQuery == null) {
                    filterPartQuery = "";
                }
                String queryPrepared = this.query + filterPartQuery + sortPartQuery + " LIMIT " + this.ROWS_PER_PAGE + " OFFSET " + this.OFFSET;

                statement = conn.prepareStatement(queryPrepared);
                rs = statement.executeQuery();
                this.data = null;
                this.data = new JSONArray();
                this.rowFilterCount = 0;
                while (rs.next()) {
                    JSONArray row = new JSONArray();
                    this.rowFilterCount++;
                    for (int i = 1; i <= this.columnNames.length; i++)
                        row.add(rs.getObject(i));

                    this.data.add(row);
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    /**
     * Permite limpiar la información de ordenamiento. Se usa esta limpieza por cada request asíncrono
     * del objeto javascript que atiende el ordenamiento
     */
    public void clearSort() {
        this.sorter.clearSort();
    }

    /**
     * Añade una columna en el ordenamiento con su dirección (asc ó desc). Esta información es enviada por el objeto javascript
     * en cada request asíncrono
     * @param sColNumber
     * @param sColDir
     */
    public void addSort(String sColNumber, String sColDir) {
        this.sorter.addSort(sColNumber, sColDir);
    }

    /**
     * Permite limpiar la información de filtrado. Se usa por cada request asíncrono
     */
    public void clearFilter() {
        this.filter.clearFilter();
    }

    /**
     * Añade un nuevo filtro dado la columna y su valor
     * @param sColNumber    Índice de la columna a ser filtrada
     * @param sColValue     Valor de filtro de la columna
     */
    public void addFilter(String sColNumber, String sColValue) {
        this.filter.addFilter(sColNumber, sColValue);
    }

    /**
     * Configura la búsqueda genérica de todas las columnas
     * @param search    Valor a buscar
     */
    public void setSearch(String search) {
        this.filter.setSearch(search);
    }

    /**
     * Permite configurar los parámetros del requerimiento (request) asíncrono. Esta función
     * carga los registros de acuerdo a lo requerido y los deja en el arreglo JSON de datos.
     * El controlador se encarga de enviar este objeto a la vista de paginación para poder
     * generar el objeto javascript necesario para atender la petición.
     * @param request   Request asíncrono recibido desde el objeto javascript
     * @throws Exception
     */

    public void configureDatatableParametersDocuments(HttpServletRequest request) throws Exception {
        try {
            //Carga de datos provenientes del datatable
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
            this.getRecordsDocuments(request, offset, limit);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void configureDatatableParametersAddress(HttpServletRequest request) throws Exception {
        try {
            //Carga de datos provenientes del datatable
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
     * Obtiene la definición javascript de las columnas del objeto para armar la salida
     * @return  La definición en javascript de las columnas del objeto.<br/>
     * Ejemplo:<br/>
     * {<br/>
     *      {sWidth: 10%, sClass:left, bVisible: true, bSortable: true},<br/>
     *      {sWidth: 20%, sClass:right, bVisible: false, bSortable: false},<br/>
     * }
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

    /**
     * Permite configurar las alineaciones de las columnas
     * @param columnAligns  Arreglo de cadenas con las alineaciones: left, right o center
     */
    public void setColumnAlignments(String[] columnAligns) {
        this.columnAligns = columnAligns;
    }

    /**
     * Permite configurar los anchos de las columnas
     * @param columnWidths  Arreglo de cadenas con los anchos en porcentajes. <br/>
     * Ejemplo:<br/>
     *  report.setColumnWidths(new String[]{"10%", "5%", "20%"});
     */
    public void setColumnWidths(String[] columnWidths) {
        this.columnWidths = columnWidths;
    }

    /**
     * Permite configurar las columnas extras. Estas columnas extras son html puro que el objeto escribe en la salida.
     * @param columnExtras  Arreglo de cadenas de las columnas extras. Estas cadenas deben contener html.<br/>
     * Si se necesitan referenciar a datos de alguna columna de la fila actual, se debe usar la notación
     * <code>{<numeroDeColumna>}</code>
     * Ejemplo:<br/>
     * report.setColumnExtras(new String[]{"<form method=\"post\" action=\"procesa.html\"><input type=\"text\" name=\"campo1\" value=\"{0}\"/></form>"});
     */
    public void setColumnExtras(String[] columnExtras) {
        this.columnExtras = columnExtras;
    }

    /**
     * Permite obtener las columnas extras para escribirlas en la salida
     * @return  Arreglo de cadenas con las columnas extras en HTML
     */
    public String[] getColumnExtras() {
        return new String[(this.columnExtras != null ? this.columnExtras.length : 0)];
    }

    /**
     * Configura la visibilidad de las columnas
     * @param columnVisibles    Arreglo de lógicos que permite especificar si la columna es visible o invisible
     */
    public void setColumnVisibles(Boolean[] columnVisibles) {
        this.columnVisibles = columnVisibles;
    }

    /**
     * Configura la ordenabilidad de las columnas
     * @param columnSortables   Arreglo de lógicos que permite especificar si la columna permite ordenamiento o no
     */
    public void setColumnSortables(Boolean[] columnSortables) {
        this.columnSortables = columnSortables;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    

    /**
     * Configura la buscabilidad de las columnas
     * @param columnSearchables Arreglo de lógicos que permite especificar si la columna permite filtros de búsqueda
     */
    public void setColumnSearchables(Boolean[] columnSearchables) {
        this.columnSearchables = columnSearchables;
    }

    /**
     * Obtiene la definición de búsquedas por columna en el objeto. Es usada en la presentación del objeto
     * @return  Arreglo de cadenas por columna que permite configurar la búsqueda o filtro por columnas
     */
    public String[] getJavascriptSearchDefinition() {
        //int len=(columnNames!=null?columnNames.length:0)+(columnExtras!=null?columnExtras.length:0);
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

    /**
     * Permite limpiar los filtros por columna. Usada por cada request asíncrono para empezar una nueva especificación
     * de filtros.
     */
    public void clearWhere() {
        this.where.clearWhere();
    }

    /**
     * Añade una búsqueda o filtro por columna especificada al momento de request asíncrono
     * mediante un valor determinado
     * @param colName   Nombre de la columna
     * @param colValue  Valor de filtro de la columna
     */
    public void addWhere(String colName, String colValue) {
        this.where.addWhere(colName, colValue);
    }

    /**
     * Añade una búsqueda o filtro por columna especificada al momento del request asíncrono
     * mediante rango de valores. Usada para rangos de fechas o de valores
     * @param colName   Nombre de la columna
     * @param from      Valor inicial
     * @param to        Valor final
     */
    public void addWhereBetween(String colName, String from, String to) {
        this.where.addWhereBetween(colName, from, to);
    }

    /**
     * Permite saber si la llamada es un requerimiento asincrono ajax de paginación, ordenamiento y/o búsqueda
     * @param request   Request HTTP
     * @return  Verdadero si la llamada es un requerimiento asíncrono de ajax
     */
    public static boolean isAjaxCall(HttpServletRequest request) {
        return (request.getParameter("sEcho") != null );
    }

    /**
     * Permite saber si la llamada es un filtrado de valores de alguna forma anterior al reporte. Algunos reportes
     * cuentan con una forma de filtrado antes de mostrar el reporte.
     * @param request   Request HTTP
     * @return  Verdadero si la llamada se hizo desde una pantalla de filtrado
     */
    public static boolean isFilterCall(HttpServletRequest request) {
        return ("Consult".equals(request.getParameter("isFilterSubmit")) /*&& request.getSession().getAttribute("report")==null*/);
    }

    public ReportWhere getWhere() {
        return this.where;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
