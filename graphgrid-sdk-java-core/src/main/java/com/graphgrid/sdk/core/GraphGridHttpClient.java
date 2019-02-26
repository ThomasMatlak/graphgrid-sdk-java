package com.graphgrid.sdk.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphgrid.sdk.core.exception.GraphGridClientException;
import com.graphgrid.sdk.core.handler.DefaultRequestHandler;
import com.graphgrid.sdk.core.handler.DefaultResponseHandler;
import com.graphgrid.sdk.core.handler.RequestHandler;
import com.graphgrid.sdk.core.handler.ResponseHandler;
import com.graphgrid.sdk.core.model.GraphGridServiceRequest;
import com.graphgrid.sdk.core.model.GraphGridServiceResponse;
import com.graphgrid.sdk.core.utils.HttpMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GraphGridHttpClient
{

    private static final Logger LOGGER = LoggerFactory.getLogger( GraphGridHttpClient.class );

    private ObjectMapper objectMapper;

    private HttpClient apacheClient;

    public GraphGridHttpClient()
    {
        this( new ObjectMapper(), HttpClientBuilder.create().build() );
    }

    public GraphGridHttpClient( ObjectMapper objectMapper, HttpClient client )
    {
        this.objectMapper = objectMapper;
        this.apacheClient = client;
    }

    public <T extends GraphGridServiceResponse> T invoke( GraphGridServiceRequest ggRequest, Class<T> responseType, HttpMethod httpMethod )
    {
        IOException ex = null;
        try
        {
            HttpResponse response = this.executeRequest( ggRequest.getRequestHandler(), ggRequest, httpMethod );
            return (T) processResponse( ggRequest.getResponseHandler(), response, objectMapper, responseType );
        }
        catch ( IOException e )
        {
            LOGGER.error( "error processing request " + ggRequest.toString(), e.getMessage() );
            ex = e;
        }
        throw new GraphGridClientException( "error processing request " + ggRequest.toString(), ex );
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

    private HttpResponse executeRequest( RequestHandler handler, GraphGridServiceRequest request, HttpMethod httpMethod ) throws IOException
    {
        if ( handler == null )
        {
            return new DefaultRequestHandler().executeRequest( request, httpMethod, this.apacheClient );
        }
        else
        {
            return request.getRequestHandler().executeRequest( request, httpMethod, this.apacheClient );
        }
    }


}
