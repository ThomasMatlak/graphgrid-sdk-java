package com.graphgrid.sdk.core.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graphgrid.sdk.core.handler.RequestHandler;
import com.graphgrid.sdk.core.handler.ResponseHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;


abstract public class GraphGridServiceRequest
{
    @JsonIgnore
    private URL endpoint;
    @JsonIgnore
    private String serviceUrl;
    @JsonIgnore
    private Object body;
    @JsonIgnore
    private Map<String,String> headers;
    @JsonIgnore
    private Map<String,List<String>> customQueryParameters;
    @JsonIgnore
    private RequestHandler requestHandler;
    @JsonIgnore
    private ResponseHandler responseHandler;

    public GraphGridServiceRequest()
    {
    }

    @SuppressWarnings( "unchecked" )
    public <T extends GraphGridServiceRequest> T withHeaders( Map<String,String> headers )
    {
        setHeaders( headers );
        T t = (T) this;
        return t;
    }


    public URL getEndpoint() throws MalformedURLException
    {
        return endpoint;
    }

    public void setEndpoint( URL endpoint )
    {
        this.endpoint = endpoint;
    }

    public Object getBody()
    {
        return body;
    }

    public void setBody( Object body )
    {
        this.body = body;
    }

    public Map<String,String> getHeaders()
    {
        return headers;
    }

    public void setHeaders( Map<String,String> headers )
    {
        this.headers = headers;
    }

    public Map<String,List<String>> getCustomQueryParameters()
    {
        return customQueryParameters;
    }

    public void setCustomQueryParameters( Map<String,List<String>> customQueryParameters )
    {
        this.customQueryParameters = customQueryParameters;
    }

    public RequestHandler getRequestHandler()
    {
        return requestHandler;
    }

    public void setRequestHandler( RequestHandler requestHandler )
    {
        this.requestHandler = requestHandler;
    }

    public ResponseHandler getResponseHandler()
    {
        return responseHandler;
    }

    public void setResponseHandler( ResponseHandler responseHandler )
    {
        this.responseHandler = responseHandler;
    }

    public String getServiceUrl()
    {
        return serviceUrl;
    }

    public void setServiceUrl( String serviceUrl )
    {
        this.serviceUrl = serviceUrl;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder( this ).append( "endpoint", endpoint ).append( "serviceUrl", serviceUrl ).append( "body", body ).append( "headers", headers )
                .append( "customQueryParameters", customQueryParameters ).toString();
    }
}
