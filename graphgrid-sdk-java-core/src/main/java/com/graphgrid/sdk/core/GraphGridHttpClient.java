package com.graphgrid.sdk.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphgrid.sdk.core.handler.DefaultResponseHandler;
import com.graphgrid.sdk.core.handler.ResponseHandler;
import com.graphgrid.sdk.core.model.GraphGridServiceRequest;
import com.graphgrid.sdk.core.model.GraphGridServiceResponse;
import com.graphgrid.sdk.core.utils.HttpMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Map;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class GraphGridHttpClient
{
    // todo make object mapper resusable and configurable
    // either through http client factory or within constructor
    private ObjectMapper objectMapper;

    public GraphGridHttpClient()
    {
        this.objectMapper = new ObjectMapper();
    }

    public <T extends GraphGridServiceResponse> T invoke( GraphGridServiceRequest ggRequest, Class<T> responseType, HttpMethod httpMethod ) throws IOException
    {
        final String url = ggRequest.getEndpoint().toString();
        final HttpClient client = HttpClientBuilder.create().build();

        HttpUriRequest request = null;
        if ( httpMethod == HttpMethod.GET )
        {
            request = new HttpGet( url );
        }
        else if ( httpMethod == HttpMethod.POST )
        {
            request = new HttpPost( url );
            ((HttpPost) request).setEntity( new StringEntity( parseRequestToJsonString( ggRequest ) ) );
        }
        else if ( httpMethod == HttpMethod.PUT )
        {
            request = new HttpPut( url );
            ((HttpPut) request).setEntity( new StringEntity( parseRequestToJsonString( ggRequest ) ) );
        }
        else if ( httpMethod == HttpMethod.DELETE )
        {
            request = new HttpDelete( url );
        }
        else if ( httpMethod == HttpMethod.PATCH )
        {
            request = new HttpPatch( url );
        }

        request = addHeaders( ggRequest.getHeaders(), request );

        HttpResponse response = client.execute( request );

        return (T) parseResponse( ggRequest.getResponseHandler(), response, objectMapper, responseType );
    }

    // todo delegate to request handler
    // todo enable configuring and reusing object mapper
    private String parseRequestToJsonString( Object obj ) throws IOException
    {
        return new ObjectMapper().writer().writeValueAsString( obj );
    }

    private HttpUriRequest addHeaders( final Map<String,String> headers, HttpUriRequest request )
    {
        if ( headers != null )
        {
            for ( Map.Entry<String,String> e : headers.entrySet() )
            {
                request.addHeader( e.getKey(), e.getValue() );
            }
        }
        // add request header
        request.addHeader( "User-Agent", USER_AGENT );
        return request;

    }

    public <T extends GraphGridServiceResponse> T invoke( GraphGridServiceRequest ggRequest, Class<T> responseType ) throws IOException
    {
        String url = ggRequest.getEndpoint().toString();
        HttpClient client = HttpClientBuilder.create().build();
        final HttpGet request = new HttpGet( url );


        final Map<String,String> headers = ggRequest.getHeaders();
        if ( headers != null )
        {
            for ( Map.Entry<String,String> e : headers.entrySet() )
            {
                request.addHeader( e.getKey(), e.getValue() );
            }
        }
        // add request header
        request.addHeader( "User-Agent", USER_AGENT );


        HttpResponse response = client.execute( request );

        return (T) parseResponse( ggRequest.getResponseHandler(), response, objectMapper, responseType );
    }


    public <T extends GraphGridServiceResponse> T parseResponse( HttpResponse httpResponse, Class<T> responseType ) throws IOException
    {
        return (T) new ObjectMapper().readValue( new DefaultResponseHandler().handle( httpResponse ), responseType );
    }

    public <T extends GraphGridServiceResponse> T parseResponse( HttpResponse httpResponse, ObjectMapper mapper, Class<T> responseType ) throws IOException
    {
        return (T) mapper.readValue( new DefaultResponseHandler().handle( httpResponse ), responseType );
    }

    public <T extends GraphGridServiceResponse> T parseResponse( ResponseHandler handler, HttpResponse httpResponse, Class<T> responseType ) throws IOException
    {
        return (T) new ObjectMapper().readValue( handler.handle( httpResponse ), responseType );
    }

    public <T extends GraphGridServiceResponse> T parseResponse( ResponseHandler handler, HttpResponse httpResponse, ObjectMapper mapper,
            Class<T> responseType ) throws IOException
    {
        if ( handler == null && mapper == null )
        {
            return parseResponse( httpResponse, responseType );
        }
        else if ( handler != null && mapper == null )
        {
            return parseResponse( handler, httpResponse, responseType );
        }
        else if ( handler == null )
        {
            return parseResponse( httpResponse, mapper, responseType );
        }
        else
        {
            return (T) mapper.readValue( handler.handle( httpResponse ), responseType );
        }
    }

}
