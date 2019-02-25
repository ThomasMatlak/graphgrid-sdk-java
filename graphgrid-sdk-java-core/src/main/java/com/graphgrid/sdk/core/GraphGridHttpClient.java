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
            ((HttpPost) request).setEntity( new StringEntity( parseRequestToJsonString( ggRequest.getBody() ) ) );
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

        return (T) processResponse( ggRequest.getResponseHandler(), response, objectMapper, responseType );
    }

    // todo delegate to request handler
    // todo enable configuring and reusing object mapper
    private String parseRequestToJsonString( Object obj ) throws IOException
    {
        return objectMapper.writer().writeValueAsString( obj );
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
        // todo move to different location
        // add request header
        request.addHeader( "User-Agent", USER_AGENT );
        request.addHeader( "Content-type", "application/json" );
        return request;

    }


    public <T extends GraphGridServiceResponse> T processResponse( ResponseHandler handler, HttpResponse httpResponse, ObjectMapper mapper,
            Class<T> responseType ) throws IOException
    {
        if ( handler == null && mapper == null )
        {
            return (T) new DefaultResponseHandler().handle( httpResponse, objectMapper, responseType );
        }
        else if ( handler != null && mapper == null )
        {
            return (T) handler.handle( httpResponse, objectMapper, responseType );
        }
        else if ( handler == null && mapper != null )
        {
            return (T) new DefaultResponseHandler().handle( httpResponse, mapper, responseType );
        }
        else
        {
            return (T) handler.handle( httpResponse, mapper, responseType );
        }
    }

}
