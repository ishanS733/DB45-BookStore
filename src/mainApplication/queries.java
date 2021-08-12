package mainApplication;

public class queries{
    String userQuery, queryReply;

    public queries(String userQuery, String queryReply){
        this.userQuery=userQuery;
        this.queryReply=queryReply;
    }

    public String getUserQuery() {
        return userQuery;
    }

    public void setUserQuery(String userQuery) {
        this.userQuery = userQuery;
    }

    public String getQueryReply() {
        return queryReply;
    }

    public void setQueryReply(String queryReply) {
        this.queryReply = queryReply;
    }
}