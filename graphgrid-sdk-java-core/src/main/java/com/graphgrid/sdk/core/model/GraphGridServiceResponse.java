package com.graphgrid.sdk.core.model;


import com.graphgrid.sdk.core.exception.GraphGridClientException;

public class GraphGridServiceResponse
{

    private Object response;

    private GraphGridClientException exception;

    private String statusText;

    private int statusCode;


    public GraphGridServiceResponse()
    {
    }

    /**
     * used when response is exception
     *
     * @param exception
     * @param statusText
     * @param statusCode
     */
    public GraphGridServiceResponse( GraphGridClientException exception, String statusText, int statusCode )
    {
        this.exception = exception;
        this.statusText = statusText;
        this.statusCode = statusCode;
    }


    @SuppressWarnings( "unchecked" )
    public <T extends GraphGridServiceResponse> T withException( GraphGridClientException exception )
    {
        setException( exception );
        T t = (T) this;
        return t;
    }


    public Object getResponse()
    {
        return response;
    }

    public void setResponse( Object response )
    {
        this.response = response;
    }

    public GraphGridClientException getException()
    {
        return exception;
    }

    public void setException( GraphGridClientException exception )
    {
        this.exception = exception;
    }

    public String getStatusText()
    {
        return statusText;
    }

    public void setStatusText( String statusText )
    {
        this.statusText = statusText;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode( int statusCode )
    {
        this.statusCode = statusCode;
    }

}
