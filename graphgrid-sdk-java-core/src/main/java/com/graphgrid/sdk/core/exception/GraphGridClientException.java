package com.graphgrid.sdk.core.exception;

public class GraphGridClientException extends RuntimeException
{
    private int httpStatusCode;

    private static final long serialVersionUID = 1308888184430076531L;

    public GraphGridClientException( String message )
    {
        super( message );
    }

    public GraphGridClientException()
    {
        super( "graph grid service error" );
    }

    public GraphGridClientException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public GraphGridClientException withStatusCode( int httpStatusCode )
    {
        this.httpStatusCode = httpStatusCode;
        return this;
    }

    public int getHttpStatusCode()
    {
        return httpStatusCode;
    }

    public void setHttpStatusCode( int httpStatusCode )
    {
        this.httpStatusCode = httpStatusCode;
    }
}
