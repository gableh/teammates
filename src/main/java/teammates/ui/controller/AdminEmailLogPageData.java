package teammates.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import teammates.common.datatransfer.AccountAttributes;
import teammates.common.util.Const;
import teammates.common.util.EmailLogEntry;
import teammates.common.util.TimeHelper;

public class AdminEmailLogPageData extends PageData {

    private String filterQuery;
    private String queryMessage;
    private List<EmailLogEntry> logs;
    private List<String> versions;
    
    private boolean shouldShowAll;
    private String statusForAjax;
    private QueryParameters q;

    public AdminEmailLogPageData(AccountAttributes account, String filterQuery, boolean shouldShowAll) {
        super(account);
        this.filterQuery = filterQuery;
        this.shouldShowAll = shouldShowAll;
    }
    
    /************* Getter methods *************/
    
    public String getFilterQuery() {
        return filterQuery;
    }
    
    public String getQueryMessage() {
        return queryMessage;
    }
    
    public List<EmailLogEntry> getLogs() {
        return logs;
    }
    
    public List<String> getVersions() {
        return versions;
    }
    
    public boolean isShouldShowAll() {
        return shouldShowAll;
    }
    
    public String getStatusForAjax() {
        return statusForAjax;
    }
    
    /************* Setter methods *************/
    
    public void setFilterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
    }
    
    public void setQueryMessage(String queryMessage) {
        this.queryMessage = queryMessage;
    }
    
    public void setLogs(List<EmailLogEntry> logs) {
        this.logs = logs;
    }
    
    public void setVersions(List<String> versions) {
        this.versions = versions;
    }
    
    public void setShouldShowAll(boolean shouldShowAll) {
        this.shouldShowAll = shouldShowAll;
    }
    
    public void setStatusForAjax(String statusForAjax) {
        this.statusForAjax = statusForAjax;
    }
    
    /**
     * Creates a QueryParameters object used for filtering
     */
    public void generateQueryParameters(String query) {
        query = query.toLowerCase();
        
        try {
            q = parseQuery(query);
        } catch (Exception e) {
            this.queryMessage = "Error with the query: " + e.getMessage();
        }
    }
    
    
    /**
     * Converts the query string into a QueryParameters object
     * 
     */
    private QueryParameters parseQuery(String query) throws Exception {
        QueryParameters q = new QueryParameters();
        setVersions(new ArrayList<String>());
        
        if (query == null || query.isEmpty()) {
            return q;
        }
        
        query = query.replaceAll(" and ", "|");
        query = query.replaceAll(", ", ",");
        query = query.replaceAll(": ", ":");
        String[] tokens = query.split("\\|", -1); 
       
        for (int i = 0; i < tokens.length; i++) {           
            String[] pair = tokens[i].split(":", -1);
            
            if (pair.length != 2) {
                throw new Exception("Invalid format");
            }
            
            String[] values = pair[1].split(",", -1);
            String label = pair[0];
            
            if ("version".equals(label)) {
                //version is specified in com.google.appengine.api.log.LogQuery,
                //it does not belong to the internal class "QueryParameters"
                //so need to store here for future use
                for (int j = 0; j < values.length; j++) {
                    getVersions().add(values[j].replace(".", "-"));
                }
                
            } else {
                q.add(label, values);
            }
        }
        
        return q;
    }
    
    /**
     * Performs the actual filtering, based on QueryParameters
     * returns false if the logEntry fails the filtering process
     */
    public boolean shouldShowLog(EmailLogEntry logEntry) {
        
        if (q == null) {
            if (this.queryMessage == null) {
                this.queryMessage = "Error parsing the query. QueryParameters not created.";
            }
            return true;
        }
        
        //Filter based on what is in the query
        if (q.isToDateInQuery && logEntry.getTime() > q.toDateValue) {
            return false;
        }
        if (q.isFromDateInQuery && logEntry.getTime() < q.fromDateValue) {
            return false;
        }
        if (q.isReceiverInQuery) {
            
            for (String keyString : q.receiverValues) {
                if (!logEntry.getReceiver().toLowerCase().contains(keyString.toLowerCase())) {
                    return false;
                }
            }
            logEntry.highlightKeyStringInMessageInfoHtml(q.receiverValues, "receiver");
        }
        if (q.isSubjectInQuery) {
    
            for (String keyString : q.subjectValues) {
                if (!logEntry.getSubject().toLowerCase().contains(keyString.toLowerCase())) {
                    return false;
                }
            }
            logEntry.highlightKeyStringInMessageInfoHtml(q.subjectValues, "subject");
        }
        if (q.isInfoInQuery) {
            
            for (String keyString : q.infoValues) {
                if (!logEntry.getContent().toLowerCase().contains(keyString.toLowerCase())) {
                    return false;
                }
            }
            logEntry.highlightKeyStringInMessageInfoHtml(q.infoValues, "content");
        }
        
        return true;
    }


    /**
     * QueryParameters inner class. Used only within this servlet, to hold the query data once it is parsed
     * The boolean variables determine if the specific label was within the query
     * The XXValue variables hold the data linked to the label in the query
     */
    private class QueryParameters {        
        public boolean isToDateInQuery;
        public long toDateValue;
        
        public boolean isFromDateInQuery;
        public long fromDateValue;
        
        public boolean isReceiverInQuery;
        public String[] receiverValues;
        
        public boolean isSubjectInQuery;
        public String[] subjectValues;
        
        public boolean isInfoInQuery;
        public String[] infoValues;
        
        public QueryParameters() {
            isToDateInQuery = false;
            isFromDateInQuery = false;
            isReceiverInQuery = false;
            isSubjectInQuery = false;
            isInfoInQuery = false;
        }
        
        /**
         * add a label and values in
         */
        public void add(String label, String[] values) throws Exception {
            if ("after".equals(label)) {
                isFromDateInQuery = true;                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
                Date d = sdf.parse(values[0] + " 0:00");                          
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                cal = TimeHelper.convertToUserTimeZone(cal, -Const.SystemParams.ADMIN_TIME_ZONE_DOUBLE);
                fromDateValue = cal.getTime().getTime();
                
            } else if ("before".equals(label)) {
                isToDateInQuery = true;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
                Date d = sdf.parse(values[0] + " 23:59");  
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                cal = TimeHelper.convertToUserTimeZone(cal, -Const.SystemParams.ADMIN_TIME_ZONE_DOUBLE);
                toDateValue = cal.getTime().getTime();          
            } else if ("receiver".equals(label)) {
                isReceiverInQuery = true;
                receiverValues = values;
            } else if ("subject".equals(label)) {
                isSubjectInQuery = true;
                subjectValues = values;
            } else if ("info".equals(label)) {
                isInfoInQuery = true;
                infoValues = values;
            } else {
                throw new Exception("Invalid label");
            }
        }
    }

    
    
}
